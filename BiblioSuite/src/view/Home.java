package view;

import java.text.ParseException;

import dao.concrete.DatabaseException;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
		
		news.setOnMouseClicked(event ->{
			SceneController.loadScene("news");
		});
		
		myCollection.setOnMouseClicked(event ->{
			SceneController.loadScene("bookMarks");
		});
		
		
		
		
		
		
		
	}



}
