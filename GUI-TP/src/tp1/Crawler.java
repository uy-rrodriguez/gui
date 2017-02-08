package tp1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Crawler extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("layout/application.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Crawler");
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
		
	}

	public static void main(String[] args) {
		launch(Crawler.class, args);
	}
}
