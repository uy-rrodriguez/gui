


import java.net.URL;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import app.Crawler;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

public class CrawlerController {

    private static final ObservableResourceFactory RESOURCE_FACTORY = new ObservableResourceFactory();
	private Visualize vis;
	private Management man;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Tab tab1Title;

    @FXML
    private Tab tab2Title;

    @FXML
    private Tab tab3Title;

    @FXML
    private TextField downloadFolder;

    @FXML
    private TreeView<?> managementTreeView;

    @FXML
    private WebView visualizeWebView;

    @FXML
    private Button downloadStartBtn;

    @FXML
    private TextField downloadURL;

    @FXML
    private Label downloadURLLbl;

    @FXML
    private Label downloadMessage;

    @FXML
    private Label downloadFolderLbl;

    @FXML
    private CheckBox downloadCheckMedia;

    @FXML
    private Label downloadMediaLbl;

    @FXML
    private ProgressBar downloadProgressBar;

    @FXML
    private Label managementFolderLbl;

    @FXML
    private Button visualizeBrowseBtn;

    @FXML
    private TextField managementFolder;

    @FXML
    private ChoiceBox<String> langChoiceBox;

    @FXML
    private ChoiceBox<Integer> downloadDepthChoiceBox;

    @FXML
    private Label downloadProgressLbl;

    @FXML
    private Button managementBrowseBtn;

    @FXML
    private Button downloadBrowseBtn;

    @FXML
    private Label visualizeFileLbl;

    @FXML
    private ScrollPane visualizeScrollPane;

    @FXML
    private Button downloadCancelBtn;

    @FXML
    private TextField visualizeFile;

    @FXML
    private Label downloadDepthLbl;
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        /*
         * Binding des valeurs qui changeron par rapport à la langue
         */
        //ObservableRessourceFactory resourceFactory = ObservableRessourceFactory.getInstance();
        RESOURCE_FACTORY.setResources(resources);
        
        // Tab 1
        tab1Title.textProperty().bind(RESOURCE_FACTORY.getStringBinding("tab1.title"));
        downloadURLLbl.textProperty().bind(RESOURCE_FACTORY.getStringBinding("download.url"));
        downloadFolderLbl.textProperty().bind(RESOURCE_FACTORY.getStringBinding("download.inputFile"));
        downloadDepthLbl.textProperty().bind(RESOURCE_FACTORY.getStringBinding("download.depth"));
        downloadMediaLbl.textProperty().bind(RESOURCE_FACTORY.getStringBinding("download.checkMedia"));
        downloadBrowseBtn.textProperty().bind(RESOURCE_FACTORY.getStringBinding("inputFile.button"));
        downloadStartBtn.textProperty().bind(RESOURCE_FACTORY.getStringBinding("download.buttonStart"));
        
        // Tab 2
        tab2Title.textProperty().bind(RESOURCE_FACTORY.getStringBinding("tab2.title"));
        managementFolderLbl.textProperty().bind(RESOURCE_FACTORY.getStringBinding("management.inputFile"));
        managementBrowseBtn.textProperty().bind(RESOURCE_FACTORY.getStringBinding("inputFile.button"));
        
        // Tab 3
        tab3Title.textProperty().bind(RESOURCE_FACTORY.getStringBinding("tab3.title"));
        visualizeFileLbl.textProperty().bind(RESOURCE_FACTORY.getStringBinding("visualize.inputFile"));
        visualizeBrowseBtn.textProperty().bind(RESOURCE_FACTORY.getStringBinding("inputFile.button"));
        
        // Général
        downloadMessage.textProperty().bind(RESOURCE_FACTORY.getStringBinding("download.message.loading"));
        
        /* 
         * Select pour changer de langue
         */
        langChoiceBox.setItems(FXCollections.observableArrayList("Français", "English"));
        langChoiceBox.setValue("Français");
        langChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String langue =	(String) langChoiceBox.getValue();
				//System.out.println(langue);
		        
		        Locale locale;
				if (langue.equals(Crawler.LANG_EN)) {
					locale = new Locale("en", "");
				}
				else {
					locale = new Locale("fr", "");
				} 
				
				// On change la ressource qui contient la langue et ca changera tous les
				// éléments avec lesquels on a créé le binding.
				ResourceBundle bundle = PropertyResourceBundle.getBundle(Crawler.LANG_BUNDLES, locale);
				RESOURCE_FACTORY.setResources(bundle);
			}
		});
        
        /* 
         * Select pour choisir la profondeur
         */
        downloadDepthChoiceBox.setItems(FXCollections.observableArrayList(0, 1, 2));
        downloadDepthChoiceBox.setValue(0);
        
        
        /*
         * Visualize et Browser
         */
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




