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
	
	public Webpage() {
		url = null;
		setFile(null);
		children = new ArrayList<>();
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
	
	
	public boolean isHTMLFile() throws IOException {
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
		char[] c = new char[20];
		reader.read(c);
		reader.close();
		
		return new String(c).contains("<html");
	}
	
	public List<String> getHTMLLinks() throws IOException {
		List<String> result = new ArrayList<>();
		
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
			Document doc = Jsoup.parse(strBuff.toString());
			//Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
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
	
	
	@Override
	public String toString() {
		return url + " | " + file.getPath() + " | " + children.size();
	}
}
