package view;

import java.util.Map.Entry;

import controller.HomePageController;
import controller.PageViewController;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vo.UUIDDocument;


public class BookMarks {
	
	@FXML
	private HBox bookmarks;

	@FXML
	public void initialize() {
		loadBookmarks();
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

				try {
					PageViewController.showDocument(entry.getKey());
				} catch (LoadException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText(e.getMessage());
					alert.show();
					e.printStackTrace();
				}

				event.consume();
			});

			bookmarks.getChildren().add(elem);
		}

	}
}
