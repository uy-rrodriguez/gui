package crawling;

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
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
	
	private void createChildrenDir(String pathRootDir) throws IOException {
		String pathDir = pathRootDir + DIR_SEP + CHILDREN_DIR;
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
	
	public Webpage createSingleWebpage(String strURL, String pathRootDir) throws IOException {
		try {
			// D'abord, on se connecte a la page
			InputStream is = getStreamFromURL(strURL);
			
			// Creation du repertoire de base
			createRootDir(pathRootDir);
			
			// On cree le repertoire "children"
			createChildrenDir(pathRootDir);
			
			// Creation du fichier info.txt
			createInfoFile(strURL, pathRootDir);
			
			// Si tout va bien, on la stocke
			String pathRealFile = pathRootDir + DIR_SEP + ROOT_HTML;
			writeFileFromStream(pathRealFile, is);
		

			// On cree l'objet Webpage
			Webpage page = new Webpage();
			page.setUrl(strURL);
			page.setFile(new File(pathRealFile));
		
			return page;
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public Webpage createWebpageTree(String strURL, String pathRootDir, int depth) throws IOException {
		System.out.println("createWebpageTree " + strURL + "|" + pathRootDir + "|" + depth);
		Webpage root = createSingleWebpage(strURL, pathRootDir);
		
		if (depth > 0 && root != null) {
			depth--;
			List<String> links = root.getHTMLLinks();
			String pathChildren = pathRootDir + DIR_SEP + CHILDREN_DIR + DIR_SEP;
			
			if (links != null) {
				for (int i = 0; i < links.size(); i++) {
					String link = links.get(i);
					Webpage child = createWebpageTree(link, pathChildren + CHILD_DIR + (i+1), depth);
					
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

		//BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		//BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		InputStreamReader reader = new InputStreamReader(is);
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f));
		
		/*
		String line;
		while ((line = reader.readLine()) != null) {
			writer.write(line);
		}
		*/
		
		char[] c = new char[256];
		while (reader.read(c) >= 0) {
			writer.write(c);
		}
		
		writer.close();
	}
	
	public static void main(String[] args) {
		try {
			DownloadURL d = new DownloadURL();
			Webpage page = d.createWebpageTree("http://www.google.com", "test", 2);
			System.out.println(page);
			
			System.out.println("HTML? " + page.isHTMLFile());
			System.out.println(page.getHTMLLinks());
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
}
