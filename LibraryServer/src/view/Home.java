package view;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class Home {
	
	@FXML
	private AnchorPane topbar;
	
	@FXML
	private HBox myProjects;
	
	@FXML
	private HBox bookmarks;
	
	@FXML
	private VBox news;
	
	@FXML
	private VBox collections;
	

	
	@FXML 
	public void initialize() {
		loadNews();
		loadCollections();
		loadMyProjects();
		loadBookmarks();		
		
	}
	

	
	
	
	@FXML
	public void loadMyProjects() {
		List<String> newsList = Arrays.asList("Categoria1", "Categoria2", "Categoria3", "Categoria4", "Categoria5", "Categoria6", "Categoria7", "Categoria8", "Categoria9", "Categoria10");
        Image pageIcon=new Image("http://www.clker.com/cliparts/3/6/2/6/1348002494474708155New%20Page%20Icon.svg.med.png");
        int c=0;
        
		for(String text: newsList) {
			ImageView miniature = new ImageView(pageIcon);
			Label label=new Label(text);
			VBox elem=new VBox();
		
			miniature.setFitWidth(100);
			miniature.setFitHeight(140);
			elem.setId("project-miniature");
			elem.getChildren().add(miniature);
			elem.getChildren().add(label);
			myProjects.getChildren().add(elem);
			c++;
		}
	}

	@FXML
	public void loadBookmarks() {
		List<String> newsList = Arrays.asList("Categoria1", "Categoria2", "Categoria3", "Categoria4", "Categoria5", "Categoria6", "Categoria7", "Categoria8", "Categoria9", "Categoria10");
        Image pageIcon=new Image("https://images-fe.ssl-images-amazon.com/images/I/41s%2BjktVLxL.png");
        int c=0;
		
        for(String text: newsList) {
			ImageView miniature = new ImageView(pageIcon);
			Label label=new Label(text);
			HBox elem=new HBox();
			
			miniature.setFitWidth(120);
			miniature.setFitHeight(140);
			elem.setId("bookmark");
			elem.getChildren().add(miniature);
			bookmarks.getChildren().add(miniature);
			c++;
		}
	} 
	
	@FXML 
	public void loadNews() {
		List<String> newsList = Arrays.asList("Hello", "World!", "How", "Are", "You", "World!", "How", "Are", "You", "World!", "How", "Are", "You", "World!", "How", "Are", "You");
		int c=0;
		
		for(String text: newsList) {
			Label label=new Label(text);
			HBox row=new HBox();
		
			if(c%2==0)
				row.setId("news-row");
			else 
				row.setId("news-row1");
			row.getChildren().add(label);
			news.getChildren().add(row);
			c++;
		}
	}
	
	@FXML
	public void loadCollections() {
		List<String> newsList = Arrays.asList("Categoria1", "Categoria2", "Categoria3", "Categoria4", "Categoria5", "Categoria6", "Categoria7", "Categoria8", "Categoria9", "Categoria10");
		int c=0;
		
		for(String text: newsList) {	
			Label label=new Label(text);
			HBox row=new HBox();
			
			row.setId("categories");
			row.getChildren().add(label);
			collections.getChildren().add(row);
			c++;
		}
	}
	
	
}
