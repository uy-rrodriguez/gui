package crawling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Webpage {

	private String url;
	private File file;
	private List<Webpage> children;
	private Document htmlDocument;
	
	public Webpage() {
		url = null;
		setFile(null);
		children = new ArrayList<>();
		htmlDocument = null;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<Webpage> getChildren() {
		return children;
	}
	
	public void setChildren(List<Webpage> children) {
		this.children = children;
	}

	public void addChildren(Webpage child) {
		this.children.add(child);
	}
	
	public Document getHtmlDocument() {
		return htmlDocument;
	}

	public void setHtmlDocument(Document htmlDocument) {
		this.htmlDocument = htmlDocument;
	}

	public void loadhtmlDocumentFromFile() throws FileNotFoundException, IOException {
		if (isHTMLFile()) {
			// Ouverture du fichier
			StringBuffer strBuff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			while ((line = reader.readLine()) != null) {
				strBuff.append(line);
			}
			reader.close();
			
			// Parsing HTML
			this.htmlDocument = Jsoup.parse(strBuff.toString());
		}
		else  {
			this.htmlDocument = null;
		}
	}
	
	
	public boolean isHTMLFile() throws IOException {
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
		char[] c = new char[20];
		reader.read(c);
		reader.close();
		
		return (new String(c).contains("<html") || new String(c).contains("<!DOCTYPE html"));
	}
	
	public List<String> getHTMLLinks() throws IOException {
		List<String> result = new ArrayList<>();
		
		if (htmlDocument != null) {
			Elements links = htmlDocument.select("a[href]").select(":not(a[href~=(?i)\\.(png|jpe?g|gif)])");
			for (Element link : links) {
	            String urlLink = link.attr("abs:href").trim();
	            if (! urlLink.isEmpty() && ! urlLink.contains("#")) {
	            	result.add(urlLink);
	            }
	        }
		}
		
		// On retourne le resultat
		return result;
	}
	
	public List<String> getVideoLinks() {
		List<String> result = new ArrayList<>();
		
		if (htmlDocument != null) {
			Elements links = htmlDocument.select("a[href$=.mp4],meta[property=og:video],meta[property=og:video:secure_url]");
			for (Element link : links) {
	            String urlLink = link.attr("abs:href").trim();
	            if (! urlLink.isEmpty() && ! urlLink.contains("#")) {
	            	result.add(urlLink);
	            }
	        }
		}
		
		// On retourne le resultat
		return result;
	}
	
	public List<String> getImageLinks() {
		List<String> result = new ArrayList<>();
		
		if (htmlDocument != null) {
			Elements links = htmlDocument.select("img[src],image[src]");
			for (Element link : links) {
	            String urlLink = link.attr("src").trim();
	            if (! urlLink.isEmpty() && ! urlLink.contains("#")) {
	            	result.add(urlLink);
	            }
	        }
		}
		
		// On retourne le resultat
		return result;
	}
	
	
	@Override
	public String toString() {
		return url + " | " + file.getPath() + " | " + children.size();
	}
}
