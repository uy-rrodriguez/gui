package tp1.web;

import java.net.URL;

public class Downloader {
	
	private StringBuffer getContent(URL url) { 
        // Récupération du fichier .html depuis le serveur
		url.openStream();
		String text = new String();
	        try {
	            HttpGet httpget = new HttpGet(url.getUrlString()); //construction
		    //  de l'objet qui fera la connexion
	
	            System.out.println("executing request " + httpget.getURI());
	            // construction de l'objet qui gerera le dialogue avec le serveur 
	            ResponseHandler responseHandler = 
			new BasicResponseHandler();
	            text = httpclient.execute(httpget, responseHandler); //et on y va
	            System.out.println("----------------------------------------");
	            System.out.println(text);
	            System.out.println("----------------------------------------");
		}
		catch(Throwable t) {
		    System.out.println("OOPS YIKES "+ t . toString());
		    t . printStackTrace();
		}
		finally {
	            // Lorsque on n'a plus besoin de l'objet de type HttpClient
	            // on ferme la connexion pour eliberer rapidement les resources
	            // systeme qu'on avait monopolisees
	            httpclient.getConnectionManager().shutdown();
	        }
		// appeler la methode de SimpleCrawler qui marque l'URL comme visite
		// et qui l'insere dans la liste des URLs visites
	
		// appeler la methode de CrawlerUrl qui recoit le texte HTML brut
		//  (et le donne au parseur jsoup, pour en extraire le texte, le titre,
		//   les liens, etc,); l'objet CrawlerUrl a utiliser est 'url'
        return text;

    }    
}
