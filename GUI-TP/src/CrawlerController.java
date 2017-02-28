


import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

public class CrawlerController {
	
	private Visualize vis;
	private Management man;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="downloadFolder"
    private TextField downloadFolder; // Value injected by FXMLLoader

    @FXML // fx:id="managementTreeView"
    private TreeView<?> managementTreeView; // Value injected by FXMLLoader

    @FXML // fx:id="visualizeWebView"
    private WebView visualizeWebView; // Value injected by FXMLLoader

    @FXML // fx:id="downloadStartBtn"
    private Button downloadStartBtn; // Value injected by FXMLLoader

    @FXML // fx:id="downloadURL"
    private TextField downloadURL; // Value injected by FXMLLoader

    @FXML // fx:id="downloadMessage"
    private Label downloadMessage; // Value injected by FXMLLoader

    @FXML // fx:id="downloadCheckMedia"
    private CheckBox downloadCheckMedia; // Value injected by FXMLLoader

    @FXML // fx:id="downloadProgressBar"
    private ProgressBar downloadProgressBar; // Value injected by FXMLLoader

    @FXML // fx:id="visualizeBrowseBtn"
    private Button visualizeBrowseBtn; // Value injected by FXMLLoader

    @FXML // fx:id="managementFolder"
    private TextField managementFolder; // Value injected by FXMLLoader

    @FXML // fx:id="langChoiceBox"
    private ChoiceBox<String> langChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="downloadDepthChoiceBox"
    private ChoiceBox<?> downloadDepthChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="downloadProgressLbl"
    private Label downloadProgressLbl; // Value injected by FXMLLoader

    @FXML // fx:id="managementBrowseBtn"
    private Button managementBrowseBtn; // Value injected by FXMLLoader

    @FXML // fx:id="downloadBrowseBtn"
    private Button downloadBrowseBtn; // Value injected by FXMLLoader

    @FXML // fx:id="visualizeScrollPane"
    private ScrollPane visualizeScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="downloadCancelBtn"
    private Button downloadCancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="visualizeFile"
    private TextField visualizeFile; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert downloadFolder != null : "fx:id=\"downloadFolder\" was not injected: check your FXML file 'application.fxml'.";
        assert managementTreeView != null : "fx:id=\"managementTreeView\" was not injected: check your FXML file 'application.fxml'.";
        assert visualizeWebView != null : "fx:id=\"visualizeWebView\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadStartBtn != null : "fx:id=\"downloadStartBtn\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadURL != null : "fx:id=\"downloadURL\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadMessage != null : "fx:id=\"downloadMessage\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadCheckMedia != null : "fx:id=\"downloadCheckMedia\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadProgressBar != null : "fx:id=\"downloadProgressBar\" was not injected: check your FXML file 'application.fxml'.";
        assert visualizeBrowseBtn != null : "fx:id=\"visualizeBrowseBtn\" was not injected: check your FXML file 'application.fxml'.";
        assert managementFolder != null : "fx:id=\"managementFolder\" was not injected: check your FXML file 'application.fxml'.";
        assert langChoiceBox != null : "fx:id=\"langChoiceBox\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadDepthChoiceBox != null : "fx:id=\"downloadDepthChoiceBox\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadProgressLbl != null : "fx:id=\"downloadProgressLbl\" was not injected: check your FXML file 'application.fxml'.";
        assert managementBrowseBtn != null : "fx:id=\"managementBrowseBtn\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadBrowseBtn != null : "fx:id=\"downloadBrowseBtn\" was not injected: check your FXML file 'application.fxml'.";
        assert visualizeScrollPane != null : "fx:id=\"visualizeScrollPane\" was not injected: check your FXML file 'application.fxml'.";
        assert downloadCancelBtn != null : "fx:id=\"downloadCancelBtn\" was not injected: check your FXML file 'application.fxml'.";
        assert visualizeFile != null : "fx:id=\"visualizeFile\" was not injected: check your FXML file 'application.fxml'.";

        
        /* 
         * Changement de langue
         */
        langChoiceBox.setItems(FXCollections.observableArrayList(
    			"Français",
    			"English"
    		));
        langChoiceBox.setValue("Français");
        langChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String langue =	(String) langChoiceBox.getValue();
				System.out.println(langue);
				/*
				if (langue.equals("Français")) {
					
				}
				*/
			}
		});
        
        this.vis = new Visualize(visualizeWebView);
        this.man = new Management(managementTreeView);
        
        visualizeBrowseBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	
        	public void handle(MouseEvent event){
        		visualizeFile.setText(vis.setFile());
        		vis.launch();
        	}
        });
        
        managementBrowseBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	
        	public void handle(MouseEvent event){
        		String path = man.setDirectory();
        		managementFolder.setText(path);
        		
        	}
        });
        
        
        
    }
}




