
import java.io.File;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.web.WebEngine;


public class Visualize {
	private String FilePath;	
	private File currentFile;
	private final WebView visualizeWebView;
	private final WebEngine visualizeWebEngine;
	
	
	public Visualize(WebView visualizeWebView)
	{
		this.visualizeWebView = visualizeWebView;
		this.visualizeWebEngine = this.visualizeWebView.getEngine();
		this.currentFile = new File(System.getProperty("user.home"));
	}
	
	public String setFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("choisissez votre fichier");
		
		File file = new File(System.getProperty("user.home"));
		fileChooser.setInitialDirectory(file);
		
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Html Files", "*.html"));
		File selectedFile = fileChooser.showOpenDialog(null);
		if(selectedFile == null) return "";
		else{
			currentFile = new File(selectedFile.getAbsolutePath());
			FilePath = selectedFile.getAbsolutePath();
			return selectedFile.getAbsolutePath();
		}
	}
	
	public void launch()
	{
		if(this.FilePath != null)
		{
			String file = "file://"+this.FilePath;
			this.visualizeWebEngine.load(file);
		}
	}
	

}
