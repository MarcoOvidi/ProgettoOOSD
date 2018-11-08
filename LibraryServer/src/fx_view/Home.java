package fx_view;

import java.text.ParseException;
import java.util.HashMap;

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
	private HBox sProjects;

	@FXML
	private HBox tProjects;
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
		loadScanningProjects();
		loadTranscriptionProjects();
		loadBookmarks();
	}

	@FXML
	public void loadScanningProjects() {
		HomePageController.loadMyScanningProjects();
		
		Image pageIcon = new Image(
				"http://www.clker.com/cliparts/3/6/2/6/1348002494474708155New%20Page%20Icon.svg.med.png");
		for (String text : HomePageController.getMySPrj().values()) {
			ImageView miniature = new ImageView(pageIcon);
			Label label = new Label(text);
			VBox elem = new VBox();

			miniature.setFitWidth(100);
			miniature.setFitHeight(140);
			elem.setId("project-miniature");
			elem.getChildren().add(miniature);
			elem.getChildren().add(label);
			sProjects.getChildren().add(elem);
		}
	}
	
	@FXML
	public void loadTranscriptionProjects() {
		HomePageController.loadMyTranscriptionProjects();
		
		Image pageIcon = new Image(
				"http://www.clker.com/cliparts/3/6/2/6/1348002494474708155New%20Page%20Icon.svg.med.png");
		for (String text : HomePageController.getMyTPrj().values()) {
			ImageView miniature = new ImageView(pageIcon);
			Label label = new Label(text);
			VBox elem = new VBox();

			miniature.setFitWidth(100);
			miniature.setFitHeight(140);
			elem.setId("project-miniature");
			elem.getChildren().add(miniature);
			elem.getChildren().add(label);
			tProjects.getChildren().add(elem);
		}
	}

	@FXML
	public void loadBookmarks() {
		HomePageController.loadMyCollection();

		// TODO prendere la vera immagine dal String[] appena disponibili
		Image pageIcon = new Image("https://images-fe.ssl-images-amazon.com/images/I/41s%2BjktVLxL.png");

		for (String[] entry : HomePageController.getMyCollection().values()) {
			ImageView miniature = new ImageView(pageIcon); // cambiare con entry[0]
			Label label = new Label(entry[0]);
			VBox elem = new VBox();

			miniature.setFitWidth(100);
			miniature.setFitHeight(140);
			elem.setId("project-miniature");
			elem.getChildren().add(miniature);
			elem.getChildren().add(label);
			bookmarks.getChildren().add(elem);
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
		HomePageController.loadCategories();

		for (String title : HomePageController.getCategories().values()) {
			Label label = new Label(title);
			HBox row = new HBox();

			row.setId("categories");
			row.getChildren().add(label);
			collections.getChildren().add(row);
		}
	}

}
