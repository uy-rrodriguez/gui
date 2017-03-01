package crawling;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.concurrent.Task;

/*
 * Pour cela, on déclare une java.net.URL, construite avec l'URL de la servlet,
 * puis on ouvre une URLConnection avec la méthode openConnection()
 * et enfin on accède au flux d'entrée de cette connexion avec getInputStream() 
 */
public class DownloadURL {
	public static final String DIR_SEP = "/";
	public static final String ROOT_HTML = "root.html";
	public static final String INFO_FILE = "info.txt";
	public static final String CHILDREN_DIR = "children";
	public static final String CHILD_DIR = "page";
	public static final String MEDIA_DIR = "media";
	
	
	private Task javaFXTask;
	public Task getJavaFXTask() {
		return javaFXTask;
	}
	public void setJavaFXTask(Task javaFXTask) {
		this.javaFXTask = javaFXTask;
	}

	/**
	 * Delete directory tree.
	 * @throws IOException
	 */
	private void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}
	
	private void createInfoFile(String strURL, String pathRootDir) throws IOException {
		String pathInfo = pathRootDir + DIR_SEP + INFO_FILE;
		File infoFile = new File(pathInfo);
		if (infoFile.exists())
			infoFile.delete();
		BufferedWriter writer = new BufferedWriter(new FileWriter(infoFile));
		writer.write(strURL);
		writer.newLine();
		writer.close();
	}
	
	private void deleteAndCreateDir(String pathDir) throws IOException {
		File dir = new File(pathDir);
		if (dir.exists())
			this.delete(dir);
		
		dir.mkdirs();
	}
	
	private void createRootDir(String pathDir) throws IOException {
		File dir = new File(pathDir);
		if (!dir.exists())
			dir.mkdirs();
	}
	
	public Webpage createSingleWebpage(String strURL, String pathRootDir, boolean isMedia) throws IOException {
		if (javaFXTask != null && javaFXTask.isCancelled())
			return null;
		
		try {
			// Nettoyage de l'URL
			if ((!strURL.startsWith("http://") || !strURL.startsWith("https://"))
					&& !strURL.contains("://"))
				strURL = "http://" + strURL;
			
			// D'abord, on se connecte a la page
			InputStream is = getStreamFromURL(strURL);
			
			// Creation du repertoire de base
			createRootDir(pathRootDir);
			
			// On cree le repertoire "children"
			deleteAndCreateDir(pathRootDir + DIR_SEP + CHILDREN_DIR);
			
			// Creation du fichier info.txt
			createInfoFile(strURL, pathRootDir);
			
			// Si tout va bien, on la stocke
			String pathRealFile = pathRootDir + DIR_SEP + ROOT_HTML;
			writeFileFromStream(pathRealFile, is);
		

			// On cree l'objet Webpage
			Webpage page = new Webpage();
			page.setUrl(strURL);
			page.setFile(new File(pathRealFile));
			page.loadhtmlDocumentFromFile();
			
			// Recuperation des fichiers media
			if (isMedia && page.isHTMLFile()) {
				// Creation du repertoire destin
				String mediaPath = pathRootDir + DIR_SEP + MEDIA_DIR;
				deleteAndCreateDir(mediaPath);
				
				// Parsing de media
				List<String> imageLinks = page.getImageLinks();
				List<String> videoLinks = page.getVideoLinks();
				
				List<String> mediaLinks = new ArrayList<>();
				mediaLinks.addAll(imageLinks);
				mediaLinks.addAll(videoLinks);
				
				// Telechargement de media
				for (String link : mediaLinks) {
					if (javaFXTask != null && javaFXTask.isCancelled())
						return null;
					
					try {
						// Recuperation du nom du fichier
						String fileName = link.substring( link.lastIndexOf('/') + 1, link.length() );
						
						// Suppression des variables GET dans le nom
						if (fileName.contains("?"))
							fileName = fileName.substring(0, fileName.indexOf("?"));
						
						// Modification de l'URL pour les path relatifs
						if (! link.contains("http://") || ! link.contains("https://")) {
							String parentURLPath = strURL.substring(0, strURL.lastIndexOf("/"));
							link = parentURLPath + link;
						}
						
						// Creation d'un objet URL
						URL mediaURL = new URL(strURL);
						URLConnection mediaURLConn = mediaURL.openConnection();
						
						System.out.println("Media : " + fileName);
						InputStream mediaIS = mediaURLConn.getInputStream();
						writeBinaryFileFromStream(mediaPath + DIR_SEP + fileName, mediaIS, mediaURLConn.getContentLength());
					}
					catch (Exception e) {
						//e.printStackTrace();
					}
				}
				
				//System.out.println("MEDIA : " + imageLinks);
				//System.out.println(imageLinks);
				//System.out.println(videoLinks);
			}
		
			return page;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Webpage createWebpageTree(String strURL, String pathRootDir, int depth, boolean isMedia) throws IOException {
		System.out.println("createWebpageTree " + strURL + "|" + pathRootDir + "|" + depth);
		Webpage root = createSingleWebpage(strURL, pathRootDir, isMedia);
		
		if (depth > 0 && root != null) {
			depth--;
			List<String> links = root.getHTMLLinks();
			String pathChildren = pathRootDir + DIR_SEP + CHILDREN_DIR + DIR_SEP;
			
			if (links != null) {
				for (int i = 0; i < links.size(); i++) {
					if (javaFXTask != null && javaFXTask.isCancelled())
						return null;
					
					String link = links.get(i);
					Webpage child = createWebpageTree(link, pathChildren + CHILD_DIR + (i+1), depth, isMedia);
					
					if (child != null)
						root.addChildren(child);
				}
			}
		}
		
		return root;
	}
	
	public InputStream getStreamFromURL(String strURL) throws IOException {
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();
		
		return conn.getInputStream();
	}
	
	public void writeFileFromStream(String path, InputStream is) throws IOException {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}

		InputStreamReader reader = new InputStreamReader(is);
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f));
		
		char[] c = new char[256];
		while (reader.read(c) >= 0) {
			writer.write(c);
		}
		
		writer.close();
	}
	
	public void writeBinaryFileFromStream(String path, InputStream is, int contentLength) throws IOException {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}

		InputStream in = new BufferedInputStream(is);
		FileOutputStream out = new FileOutputStream(f);
		
	    byte[] data = new byte[256];
	    int bytesRead = 0;
		
	    while ((bytesRead = in.read(data)) >= 0) {
	    	out.write(data, 0, bytesRead);
			out.flush();
			data = new byte[256];
	    }
		
		in.close();
		out.close();
	}
	
	public static void main(String[] args) {
		try {
			DownloadURL d = new DownloadURL();
			Webpage page = d.createWebpageTree("https://blog.codinghorror.com/", "test", 1, true);
			System.out.println(page);
			
			System.out.println("HTML? " + page.isHTMLFile());
			System.out.println(page.getHTMLLinks());
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
}
