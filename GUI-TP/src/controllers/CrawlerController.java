package controllers;



import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import app.Crawler;
import crawling.DownloadURL;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

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
    
    
    /**
     * Activation et desactivation des controles pour le telechargement
     */
    private void toggleDownload(boolean downloading) {
    	if (downloading) {
    		downloadMessage.setVisible(true);
            //downloadProgressLbl.setVisible(true);
            downloadProgressBar.setVisible(true);
            downloadCancelBtn.setVisible(true);
            
            downloadStartBtn.getParent().setDisable(true);
    	}
    	else {
    		downloadMessage.setVisible(false);
    		//downloadMessage.textProperty().set(RESOURCE_FACTORY.getStringBinding("download.message.finish").get());
    		
            downloadProgressLbl.setVisible(false);
            downloadProgressBar.setVisible(false);
            downloadCancelBtn.setVisible(false);
            
            downloadStartBtn.getParent().setDisable(false);
    	}
    }
    
    /**
     * Classe pour executer le telechargement en arriere-plan
     *
     */
    DownloadService downloadService;
    private static class DownloadService extends Service<Void> {

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					DownloadURL d = new DownloadURL();
					d.setJavaFXTask(this);
					//d.createSingleWebpage(url.get(), path.get());
					d.createWebpageTree(url.get(), path.get(), depth.get(), media.get());
					return null;
				}
				
            };
		}
		
    	
		// Properties pour configurer le telechargement
        private StringProperty url = new SimpleStringProperty();
        private StringProperty path = new SimpleStringProperty();
        private IntegerProperty depth = new SimpleIntegerProperty();
        private BooleanProperty media = new SimpleBooleanProperty();

        public final String getUrl() {
            return url.get();
        }
        public final void setUrl(String value) {
            url.set(value);
        }
        public final String getPath() {
			return path.get();
		}
		public void setPath(String value) {
			this.path.set(value);
		}
		public final Integer getDepth() {
			return depth.get();
		}
		public void setDepth(Integer value) {
			this.depth.set(value);
		}
		public final Boolean getMedia() {
			return media.get();
		}
		public void setMedia(Boolean value) {
			this.media.set(value);
		}
		
		public final StringProperty urlProperty() {
           return url;
        }
		public final StringProperty pathProperty() {
           return path;
        }
		public final IntegerProperty depthProperty() {
           return depth;
        }
		public final BooleanProperty mediaProperty() {
           return media;
        }

	}

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
        langChoiceBox.setItems(FXCollections.observableArrayList("Français", "English", "русский"));
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
				else if (langue.equals(Crawler.LANG_RU)) {
					locale = new Locale("ru", "");
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
        downloadDepthChoiceBox.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5));
        downloadDepthChoiceBox.setValue(0);
        
        
        /*
         * Telechargement
         */
        // Valeurs par defaut
        downloadURL.setText("https://www.google.com");
        downloadFolder.setText("Test_");
        
        // On cache les messages et autres controles de telechargement
        toggleDownload(false);
        
        // Action du bouton [Telecharger]
        downloadStartBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event){
        		toggleDownload(true);
        		
        		// Demarrage du telechargement
        		// Configuration de l'instance du service de telechargement
                downloadService = new DownloadService();
                
                // On associe la barre avec le telechargement
                downloadProgressBar.progressProperty().bind(downloadService.progressProperty());
                
                // Action a la fin
                downloadService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                    	//System.out.println(service.getUrl() + "|" + service.getPath() + "|" + service.getDepth() + "|" + service.getMedia());
                        toggleDownload(false);
                    }
                });
                
                // Action lors d'une annulation
                downloadService.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                    	toggleDownload(false);
                    }
                });
                
        		// Parametres du telechargement
        		downloadService.setUrl(downloadURL.getText());
        		downloadService.setPath(downloadFolder.getText());
        		downloadService.setDepth(downloadDepthChoiceBox.getValue());
        		downloadService.setMedia(downloadCheckMedia.isSelected());
        		
                downloadService.restart();
        	}
        });
        
        // Action du bouton [Annuler]
        downloadCancelBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event){
        		downloadService.cancel();
        		
        		//toggleDownload(false);
        	}
        });
        
        // Action du bouton qui ouvre le FileChooser
        downloadBrowseBtn.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				DirectoryChooser chooser = new DirectoryChooser();
		        chooser.setTitle(RESOURCE_FACTORY.getStringBinding("download.fileChooser.title").get());
		        final File selectedDirectory = chooser.showDialog(tab1Title.getTabPane().getScene().getWindow());
		        
		        if (selectedDirectory != null) {
	                downloadFolder.setText(selectedDirectory.getAbsolutePath());
	            }
			}
		});
        
        
        
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




