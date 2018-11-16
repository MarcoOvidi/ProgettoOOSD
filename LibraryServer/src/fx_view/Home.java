package fx_view;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;

import controller.HomePageController;
import controller.PageViewController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vo.UUIDDocument;
import vo.UUIDScanningWorkProject;

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
	private Tab transcriptionTab;

	@FXML
	private Tab scanningTab;

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

		if (HomePageController.checkIsDigitalizer()) {

			HomePageController.loadMyScanningProjects();

			Image pageIcon = new Image("images/blank.png");
			for (Entry<UUIDScanningWorkProject, String[]> entry: HomePageController.getMySPrj().entrySet()) {
				ImageView miniature = new ImageView(pageIcon);
				Label label = new Label(entry.getValue()[0]);
				VBox elem = new VBox();

				miniature.setFitWidth(100);
				miniature.setFitHeight(140);
				elem.setId("project-miniature");
				elem.getChildren().add(miniature);
				elem.getChildren().add(label);
				elem.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					try {
						LoadScan.setToOpenDocumentFromScanningProject(entry.getKey());
						SceneController.loadScene("loadScan");
					} catch (NullPointerException | DatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				sProjects.getChildren().add(elem);
			}
		} else {
			scanningTab.setDisable(true);
		}
	}

	@FXML
	public void loadTranscriptionProjects() {
		if (HomePageController.checkIsTranscriber()) {

			HomePageController.loadMyTranscriptionProjects();

			Image pageIcon = new Image("images/blank.png");
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
		} else {
			transcriptionTab.setDisable(true);
		}
	}

	@FXML
	public void loadBookmarks() {
		HomePageController.loadMyCollection();

		// TODO prendere la vera immagine dal String[] appena disponibili
		Image pageIcon = new Image("images/libricino.png");

		for (Entry<UUIDDocument, String[]> entry : HomePageController.getMyCollection().entrySet()) {
			ImageView miniature = new ImageView(pageIcon); // cambiare con entry[0]
			Label label = new Label(entry.getValue()[0]);
			VBox elem = new VBox();

			miniature.setFitWidth(100);
			miniature.setFitHeight(140);
			elem.setId("project-miniature");
			elem.getChildren().add(miniature);
			elem.getChildren().add(label);

			elem.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

				PageViewController.showDocument(entry.getKey());
				

				event.consume();
			});

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

			for (Entry<UUIDDocument, String[]> entry : newsMap.entrySet()) {

				Label title = new Label(entry.getValue()[0] + "               ( " + entry.getValue()[1] + " giorni fa) " + entry.getValue()[2]);

				HBox row = new HBox();

				if (c % 2 == 0)
					row.setId("news-row");
				else
					row.setId("news-row1");
				row.getChildren().add(title);
				
				row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					PageViewController.showDocument(entry.getKey());
					event.consume();
				});
				
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
