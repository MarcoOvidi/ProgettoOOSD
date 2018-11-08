package fx_view;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import controller.HomePageController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vo.UUIDDocument;

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
	public void initialize() throws DatabaseException, ParseException {
		loadNews();
		loadCollections();
		loadMyProjects();
		loadBookmarks();

	}

	@FXML
	public void loadMyProjects() {
		List<String> newsList = Arrays.asList("Categoria1", "Categoria2", "Categoria3", "Categoria4", "Categoria5",
				"Categoria6", "Categoria7", "Categoria8", "Categoria9", "Categoria10");
		Image pageIcon = new Image(
				"http://www.clker.com/cliparts/3/6/2/6/1348002494474708155New%20Page%20Icon.svg.med.png");
		int c = 0;

		for (String text : newsList) {
			ImageView miniature = new ImageView(pageIcon);
			Label label = new Label(text);
			VBox elem = new VBox();

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
		List<String> newsList = Arrays.asList("Categoria1", "Categoria2", "Categoria3", "Categoria4", "Categoria5",
				"Categoria6", "Categoria7", "Categoria8", "Categoria9", "Categoria10");
		Image pageIcon = new Image("https://images-fe.ssl-images-amazon.com/images/I/41s%2BjktVLxL.png");

		for (String text : newsList) {
			ImageView miniature = new ImageView(pageIcon);
			Label label = new Label(text);
			HBox elem = new HBox();

			miniature.setFitWidth(120);
			miniature.setFitHeight(140);
			elem.setId("bookmark");
			elem.getChildren().add(miniature);
			bookmarks.getChildren().add(miniature);
		}
	}

	@FXML
	public void loadNews() throws DatabaseException, ParseException {
		// List<String> newsList = Arrays.asList("Hello", "World!", "How", "Are", "You",
		// "World!", "How", "Are", "You", "World!", "How", "Are", "You", "World!",
		// "How", "Are", "You");
		try {
			HomePageController.loadNews();
			HashMap<UUIDDocument, String[]> newsMap = HomePageController.getNews();
			int c = 0;

			for (String[] text : newsMap.values()) {
				
				Label title = new Label(text[0] + "               ( " + text[1] + " giorni fa) " + text[2]);				
				
				HBox row = new HBox();

				if (c % 2 == 0)
					row.setId("news-row");
				else
					row.setId("news-row1");
				row.getChildren().add(title);
				news.getChildren().add(row);
				c++;
			}
		} catch (DatabaseException e) {
			Label label = new Label(e.getMessage());
			news.getChildren().add(label);
				
		}
	}

	@FXML
	public void loadCollections() {
		List<String> newsList = Arrays.asList("Categoria1", "Categoria2", "Categoria3", "Categoria4", "Categoria5",
				"Categoria6", "Categoria7", "Categoria8", "Categoria9", "Categoria10");

		for (String text : newsList) {
			Label label = new Label(text);
			HBox row = new HBox();

			row.setId("categories");
			row.getChildren().add(label);
			collections.getChildren().add(row);
		}
	}

}
