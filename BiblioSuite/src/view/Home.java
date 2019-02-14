package view;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.jfoenix.controls.JFXButton;

import controller.HomePageController;
import controller.PageViewController;
import dao.concrete.DatabaseException;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import vo.UUIDDocument;
import vo.UUIDDocumentCollection;
import vo.view.DocumentRow;

public class Home /* extends Application */ {

	@FXML
	private AnchorPane topbar;
	
	@FXML
	VBox catalog;
	
	@FXML
	VBox myCollection;

	@FXML
	VBox news;



	@FXML
	public void initialize() throws DatabaseException, ParseException {
		loadHome();
	}
	
	private void loadHome() {

		Glow glow = new Glow();
		glow.setLevel(1.0);
		
		catalog.setOnMouseEntered(event ->{
			catalog.getChildren().get(0).setEffect(glow);	
			catalog.getChildren().get(1).setEffect(glow);
		});
		
		
		myCollection.setOnMouseEntered(event ->{
			myCollection.getChildren().get(0).setEffect(glow);	
			myCollection.getChildren().get(1).setEffect(glow);
		});
		
		
		
		news.setOnMouseEntered(event ->{
			news.getChildren().get(0).setEffect(glow);
			news.getChildren().get(1).setEffect(glow);
		});
		

		catalog.setOnMouseExited(event ->{
			catalog.getChildren().get(0).setEffect(null);	
			catalog.getChildren().get(1).setEffect(null);
		});
		
		
		myCollection.setOnMouseExited(event ->{
			myCollection.getChildren().get(0).setEffect(null);	
			myCollection.getChildren().get(1).setEffect(null);
		});
		
		
		
		news.setOnMouseExited(event ->{
			news.getChildren().get(0).setEffect(null);
			news.getChildren().get(1).setEffect(null);
		});
		
		catalog.setOnMouseClicked(event ->{
			SceneController.loadScene("catalog");
		});
		
		
		
		
		
		
		
	}



}
