package fx_view;

import java.util.Comparator;
import java.util.LinkedList;

import controller.LocalSession;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import model.Page;

public class ShowDocument {
	private static int currentPage;

	@FXML
	private Button backButton;

	@FXML
	private ScrollPane scrollPage;

	@FXML
	private VBox pageList;

	@FXML
	private VBox scanList;

	@FXML
	private VBox transcriptionList;

	@FXML
	private ScrollPane scanListScroll;

	@FXML
	public void initialize() {
		loadPageList();
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadPreviousScene();
			event.consume();
		});

		scanListScroll.vvalueProperty().bindBidirectional(scrollPage.vvalueProperty());
	}

	public void loadPageList() {
		LinkedList<Page> pages = LocalSession.getOpenedDocumet().getPageList();
		// System.out.println("1");
		pages.sort(new Comparator<Page>() {
			// FIXME spostare da qui per renderlo utilizzabile ovunque
			@Override
			public int compare(Page arg0, Page arg1) {
				return Integer.compare(arg0.getPageNumber(), arg1.getPageNumber());
			}
		});

		for (Page page : pages) {
			Image pageIcon = new Image(page.getScan().getImage().getUrl());
			VBox hbox = new VBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setPadding(new Insets(20));
			/*
			 * hbox.focusedProperty().addListener((ov, oldV, newV) -> { if (!newV) {
			 * hbox.setStyle("-fx-background: #444;"); } });
			 */
			ImageView miniature = new ImageView(pageIcon);
			miniature.setFitWidth(90);
			miniature.setFitHeight(130);
			hbox.getChildren().add(miniature);
			hbox.getChildren().add(new Label(page.getPageNumber().toString()));
			hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				loadPageAtPosition(pageList.getChildren().indexOf(hbox));
				event.consume();
			});
			/*
			 * hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { Node nodeOut =
			 * hbox.getChildren().get(1); if (nodeOut instanceof Label) { currentPage =
			 * Integer.parseInt(((Label) nodeOut).getText());
			 * System.out.println(currentPage);
			 * loadPageAtPosition(pageList.getChildren().indexOf(hbox)); } else {
			 * System.out.println("Not"); } event.consume(); });
			 */
			pageList.getChildren().add(hbox);

			ImageView scan = new ImageView(pageIcon);
			scan.setFitWidth(450);
			scan.setFitHeight(650);
			// hbox.getChildren().add(new Label(page.getPageNumber().toString()));
			/*
			 * scan.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { Node nodeOut =
			 * hbox.getChildren().get(1); if (nodeOut instanceof Label) { currentPage =
			 * Integer.parseInt(((Label) nodeOut).getText()); loadPage();
			 * System.out.println(scanListScroll.getViewportBounds().getHeight()); } else {
			 * System.out.println("Not"); } event.consume(); });
			 */
			scanList.getChildren().add(scan);
		}
	}

	public void loadPageAtPosition(int pos) {
		scanList.getChildren().get(pos).requestFocus();
	}

}
