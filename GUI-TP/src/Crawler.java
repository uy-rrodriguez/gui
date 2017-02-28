

import java.util.Locale;
import java.util.PropertyResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Crawler extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// Chargement de la langue
		// Langue française
		Locale locale = new Locale("en", "");
		
		// Fenêtre principale
		Parent root = FXMLLoader.load(
				getClass().getResource("layout/application.fxml"),
				PropertyResourceBundle.getBundle("bundles.Langue", locale));
		
		Scene scene = new Scene(root);
		stage.setTitle("Crawler App");
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
		
	}

	public static void main(String[] args) {
		launch(Crawler.class, args);
	}
}
