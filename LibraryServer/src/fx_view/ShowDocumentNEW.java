package fx_view;

import java.util.Comparator;
import java.util.LinkedList;

import controller.LocalSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Page;

public class ShowDocumentNEW {

	@FXML
	private Button backButton;

	@FXML
	private ListView<VBox> pageList;

	@FXML
	private VBox transcriptionList;

	@FXML
	private ImageView currentPage;

	@FXML
	private TextArea transcription;

	@FXML
	private Button previous;
	@FXML
	private Button next;
	@FXML
	private Button top;
	@FXML
	private Button bottom;
	@FXML
	private Label number;

	@FXML
	public void initialize() {
		loadPageList();
		initBackButton();
		initNavigationButton();
		initKeyboardNavigation();

		pageList.getSelectionModel().selectFirst();
		updatePageNumber();

		Platform.runLater(() -> pageList.requestFocus());

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
		
		/*if (pages.isEmpty()) { 
			
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20));
		
		pageList.getItems().add(vbox);
		return;
		}*/

		
		for (Page page : pages) {
			Image pageIcon = new Image(page.getScan().getImage().getUrl());
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(20));
			/*
			 * vbox.focusedProperty().addListener((ov, oldV, newV) -> { if (!newV) {
			 * vbox.setStyle("-fx-background: #444;"); } });
			 */
			ImageView miniature = new ImageView(pageIcon);
			miniature.setFitWidth(90);
			miniature.setFitHeight(130);
			vbox.getChildren().add(miniature);
			vbox.getChildren().add(new Label(page.getPageNumber().toString()));
			vbox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				number.setText(page.getPageNumber().toString());
				viewPage(pageIcon);
				event.consume();
			});

			pageList.getItems().add(vbox);

		}

		viewPage(new Image(pages.get(0).getScan().getImage().getUrl()));
	}

	private void viewPage(Image pageImage) {
		currentPage.setImage(pageImage);
		currentPage.setFitWidth(450);
		currentPage.setFitHeight(560);
	}

	private void initBackButton() {
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadPreviousScene();
			event.consume();
		});
		backButton.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				SceneController.loadPreviousScene();
				event.consume();
			}
		});
	}

	private void initNavigationButton() {
		previous.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			previous();
		});

		next.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			next();
		});

		top.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			top();
		});

		bottom.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			bottom();
		});
	}

	private void updatePageNumber() {
		number.setText(((Label) pageList.getSelectionModel().getSelectedItem().getChildren().get(1)).getText());
		pageList.scrollTo(pageList.getSelectionModel().getSelectedIndex());
	}

	private void initKeyboardNavigation() {
		// currentPage.setFocusTraversable(true);
		previous.setFocusTraversable(false);
		next.setFocusTraversable(false);
		top.setFocusTraversable(false);
		bottom.setFocusTraversable(false);
		transcription.setFocusTraversable(false);

		pageList.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.LEFT) {
				previous();
			}
			if (event.getCode() == KeyCode.RIGHT) {
				next();
			}
			if (event.getCode() == KeyCode.UP) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() - 1;
				if (newIndex >= 0)
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
			}
			if (event.getCode() == KeyCode.DOWN) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() + 1;
				if (newIndex < pageList.getItems().size())
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
			}
			if (event.getCode() == KeyCode.PAGE_UP) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() - 2;
				if (newIndex >= 0)
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
				else if (newIndex == -1)
					number.setText(((Label) pageList.getItems().get(0).getChildren().get(1)).getText());
			}
			if (event.getCode() == KeyCode.PAGE_DOWN) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() + 2;
				if (newIndex < pageList.getItems().size())
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
				else if (newIndex == pageList.getItems().size())
					number.setText(((Label) pageList.getItems().get(newIndex - 1).getChildren().get(1)).getText());
			}
			if (event.getCode() == KeyCode.HOME) {
				number.setText(((Label) pageList.getItems().get(0).getChildren().get(1)).getText());
			}
			if (event.getCode() == KeyCode.END) {
				number.setText(((Label) pageList.getItems().get(pageList.getItems().size() - 1).getChildren().get(1))
						.getText());
			}
			if (event.getCode() == KeyCode.ESCAPE) {
				backButton.requestFocus();
			}
		});
	}

	private void next() {
		pageList.getSelectionModel().selectNext();
		updatePageNumber();
	}

	private void previous() {
		pageList.getSelectionModel().selectPrevious();
		updatePageNumber();
	}

	private void top() {
		pageList.getSelectionModel().selectFirst();
		updatePageNumber();
	}

	private void bottom() {
		pageList.getSelectionModel().selectLast();
		updatePageNumber();
	}

}
