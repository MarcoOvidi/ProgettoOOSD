package view;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import controller.EditUserController;
import controller.HomePageController;
import controller.LocalSessionBridge;
import controller.LoginController;
import controller.PageViewController;
import controller.SearchController;
import dao.concrete.DatabaseException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Pair;
import vo.UUIDDocument;

public class Container {

	@FXML
	private static BorderPane newContent;

	@FXML
	private TabPane topbar;
	
	@FXML
	private BorderPane content;
	
	@FXML
	private TextField searchBar;
	
	private VBox searchResult;
	
	@FXML
	private Tab logout;

	private static TreeMap<String, Pair<String,String>> map = new TreeMap<String, Pair<String,String>>();

	static {
		map.put("Home", new Pair<String,String>("home","home"));
		map.put("My Profile", new Pair<String,String>("userProfile","user-circle"));
		map.put("Contact Admin", new Pair<String,String>("message","envelope"));
		map.put("Manage Projects", new Pair<String,String>("manageProject","group"));
		map.put("Upload", new Pair<String,String>("loadScan","image"));
		map.put("Transcription", new Pair<String,String>("transcription","file-text"));
		map.put("Review", new Pair<String,String>("scanRevisor","edit"));
		map.put("Transcription Review", new Pair<String,String>("transcriptionReview","edit"));
		map.put("Admin", new Pair<String,String>("adminPanel","briefcase"));
	}
	
	private String genFavIconURL(String str) {
		return ("file:resources/favicon/32/" + str + ".png");
	}

	public void initButtonLink(String label, String link) {

		javafx.scene.image.Image image = new javafx.scene.image.Image(genFavIconURL(map.get(label).getValue()));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(32);
		imageView.setFitWidth(32);
		Tooltip tooltip = new Tooltip(label);
		tooltip.setFont(new Font(14));
		
		Tab tab = new Tab();
		tab.setGraphic(imageView);
		tab.setTooltip(tooltip);
		
		
		topbar.getTabs().add(tab);
		
		if (link.equals(SceneController.getSceneName())) {
			topbar.getSelectionModel().select(tab);
			//tab.setText(tab.getTooltip().getText());
		}
		else if(label.equals("My Profile") && SceneController.getSceneName().equals("editUserProfile") &&
				EditUserController.isSelfEditing()) {
				topbar.getSelectionModel().select(tab);
		}
		else if(label.equals("Admin") && SceneController.getSceneName().equals("editUserProfile") &&
				!EditUserController.isSelfEditing()) {
				topbar.getSelectionModel().select(tab);
		}
	}

	public void initLogoutLink() {
		

		logout.getTabPane().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			// SceneController.loadScene("login");
			LoginController.logout();
			event.consume();
		});
	}

	public static void loadScene(String name) {
			try {
				//newContent = (BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/fx_view/"+name+".fxml"));
				newContent = ((BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/fx_view/"+name+".fxml")));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
		}
	
	@FXML
	public void initialize() throws DatabaseException, ParseException {// container.setSpacing(5);
		List<String> buttons = LocalSessionBridge.getTopBarButtons();

		for (String str : buttons) {
			initButtonLink(str, map.get(str).getKey());
		}

		topbar.setOnMouseClicked(event -> {
			SceneController.loadScene(map.get(topbar.getSelectionModel().getSelectedItem().getTooltip().getText()).getKey());
		});
		
		initSearch();
		
		//topbar.setTabMinWidth(100);
		
		initLogoutLink();
		
		/*for(Tab tab : topbar.getTabs()) {
			try {
				if(tab.getText().equals("Transcription"))
					break;
				tab.setContent((BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/fx_view/"+map.get(tab.getText())+".fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

		//content = newContent;
		content.getChildren().setAll(newContent);
		//content.getChildren().get(0).setEffect(new BoxBlur());
	}
	
	public void partialInit() {
		content.getChildren().setAll(newContent);
	}
	
	private void initSearch() {

		PopOver popOver = new PopOver();
		AnchorPane popOverPane = new AnchorPane();
		
		popOverPane.setPrefHeight(200);
		popOverPane.setPrefWidth(400);

		popOver.setAutoHide(true);
		
		searchResult = new VBox();
		popOverPane.getChildren().setAll(searchResult);
		popOver.setContentNode(popOverPane);
		popOver.setArrowLocation(ArrowLocation.TOP_CENTER);
		//popOver.show(searchBar);
		
		searchBar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int l = newValue.length();

				if (l > 3) {

					LinkedList<UUIDDocument> author = SearchController.searchByAuthorTitle(newValue);
					LinkedList<UUIDDocument> tag = SearchController.searchByTag(newValue);
					
					HashMap<UUIDDocument, String> result = new HashMap<UUIDDocument,String>();
					
					for(UUIDDocument doc : author) {
						result.put(doc, HomePageController.getDocumentTitle(doc));
					}
					
					for(UUIDDocument doc : tag) {
						result.put(doc, HomePageController.getDocumentTitle(doc));
					}
					
					fillResult(result);

					//content.setEffect(new GaussianBlur(200.0));
					Animations.blurOut(content);
					popOver.setOnHiding(event -> {
						//content.setEffect(null);
						Animations.blurIn(content);
					});
						
					popOver.show(searchBar.getParent());
				}
			}
		});
		
		searchBar.requestFocus();
		searchBar.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				searchBar.getParent().requestFocus();
			}
		});
		searchBar.getParent().setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				searchBar.clear();
				searchBar.requestFocus();
			}
		});
	}
	
	public void fillResult(HashMap<UUIDDocument, String> result) {
		if(!searchResult.getChildren().isEmpty())
			searchResult.getChildren().clear();
		int c=0;
		for(Entry<UUIDDocument, String> doc : result.entrySet()) {
			Label d = new Label(doc.getValue());
			d.setId(String.valueOf(doc.getKey().getValue()));
			HBox row= new HBox();
			row.getChildren().add(d);
			if (c % 2 == 0)
				row.getStyleClass().add("search-row");
			else
				row.getStyleClass().add("search-row1");
			row.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						if (mouseEvent.getClickCount() == 1) {
							try {
								PageViewController.showDocument(new UUIDDocument(Integer.valueOf(d.getId())));
							} catch (LoadException e) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setContentText(e.getMessage());
								alert.show();
								e.printStackTrace();
							}
						}
					}
					
				}
			});
			c++;
			searchResult.getChildren().add(row);
		}
		
	}
	
}