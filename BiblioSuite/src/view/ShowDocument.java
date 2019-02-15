package view;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import controller.DownloadController;
import controller.LocalSessionBridge;
import controller.PageViewController;
import dao.concrete.DatabaseException;
import dao.concrete.HomePageQuerySet;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Page;
import vo.TEItext;
import vo.UUIDDocument;

public class ShowDocument {
	public static boolean isEmpty = false;

	@FXML
	private HBox content;
	
	
	@FXML
	private Button backButton;

	@FXML
	private Button addDocumentToUserCollections;
	@FXML
	private Button downloadButton;
	@FXML
	private Button moreButton;

	@FXML
	private ListView<VBox> pageList;

	/*
	 * @FXML private VBox transcriptionVBox;
	 */

	@FXML
	private ImageView currentPage;

	@FXML
	private CodeArea transcription;

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
	private Label message;
	
	private PopOver popOver = new PopOver();

	@FXML
	public void initialize() {
		loadPageList();
		initBackButton();
		if (isEmpty) {
			//message.setText("Empty Document");
			transcription.setVisible(false);
			pageList.setVisible(false);
			next.setVisible(false);
			previous.setVisible(false);
			top.setVisible(false);
			bottom.setVisible(false);
			isEmpty = false; // for when opening the next document
			return;
		}
		initMoreButton();
		initButtons();
		initNavigationButton();
		initKeyboardNavigation();
		initTranscription();
		
		
		pageList.getSelectionModel().selectFirst();
		updatePageNumber();

		Platform.runLater(() -> pageList.requestFocus());

	}
	
	private void initButtons(){
		if (!LocalSessionBridge.getLocalUser().canDownload()) {
			downloadButton.setVisible(false);
			downloadButton.setDisable(true);
		}

		HashMap<UUIDDocument, String[]> myColl = null;

		try {
			myColl = new HomePageQuerySet().loadMyCollectionList(LocalSessionBridge.getLocalUser().getID());
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (myColl.containsKey(LocalSessionBridge.getOpenedDocumet().getUUID())) {
			addDocumentToUserCollections.setGraphic(new ImageView(new Image("file:resources/favicon/16/bookmark.png")));
			addDocumentToUserCollections.setText("Remove bookmark");
		}
		else {
			addDocumentToUserCollections.setGraphic(new ImageView(new Image("file:resources/favicon/16/bookmark-o.png")));
			addDocumentToUserCollections.setText("Add to Bookmarks");
		}
		
		addDocumentToUserCollections.setOnAction(event -> {
			if (addDocumentToUserCollections.getText().equals("Add to Bookmarks")) {
				addDocumentToUserCollections.setGraphic(new ImageView(new Image("file:resources/favicon/16/bookmark.png")));
				addDocumentToUserCollections.setText("Remove bookmark");
				
				PageViewController.addDocumentFromMyCollection(LocalSessionBridge.getOpenedDocumet().getUUID(),
						LocalSessionBridge.getLocalUser().getID());
			}
			else {
				addDocumentToUserCollections.setGraphic(new ImageView(new Image("file:resources/favicon/16/bookmark-o.png")));
				addDocumentToUserCollections.setText("Add to Bookmarks");
				
				PageViewController.removeDocumentFromMyCollection(LocalSessionBridge.getOpenedDocumet().getUUID(),
						LocalSessionBridge.getLocalUser().getID());
			}
		});
		
		downloadButton.setOnAction(event ->{
			startDownload();
		});

	}

	public void loadPageList() {
		LinkedList<Page> pages = LocalSessionBridge.getOpenedDocumet().getPageList();

		/*
		 * if (pages.isEmpty()) {
		 * 
		 * VBox vbox = new VBox(); vbox.setAlignment(Pos.CENTER); vbox.setPadding(new
		 * Insets(20));
		 * 
		 * pageList.getItems().add(vbox); return; }
		 */
		if (pages.size() == 0) {
			isEmpty = true;
			return;
		}

		pages.sort(new Comparator<Page>() {
			// FIXME spostare da qui per renderlo utilizzabile ovunque
			@Override
			public int compare(Page arg0, Page arg1) {
				return Integer.compare(arg0.getPageNumber(), arg1.getPageNumber());
			}
		});

		for (Page page : pages) {
			Image pageIcon = LocalSessionBridge.loadImage(page.getScan().getImage().getUrl());
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
				updateTranscription(vbox);
				event.consume();
			});

			pageList.getItems().add(vbox);

		}
		// TODO ho aggiunto file:
		viewPage(new Image("file:" + pages.get(0).getScan().getImage().getUrl()));
	}

	private void viewPage(Image pageImage) {
		currentPage.setImage(pageImage);
		currentPage.setFitWidth(450);
		currentPage.setFitHeight(560);
	}

	private void initBackButton() {
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			event.consume();
			SceneController.loadPreviousScene();
		});
		backButton.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				event.consume();
				SceneController.loadScene("home");
			}
		});
	}

	private void initMoreButton() {
		
		moreButton.setOnMouseClicked(event -> {
			// FIXME andrebbero modificati view e controller per evitare di dover ricaricare
			// tutto dal db
			
			if(popOver.isShowing()) {
				popOver.hide();
				moreButton.setText("▼ More");
				return;
			}
			
			popOver = new PopOver();
			AnchorPane popOverPane = new AnchorPane();
			DocumentProperties.setToShowDocument(LocalSessionBridge.getOpenedDocumet().getUUID());

			try {
				popOverPane.getChildren().setAll(((BorderPane) FXMLLoader.load(new Object() {
				}.getClass().getResource("/fx_view/documentProperties2.fxml"))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			popOverPane.setStyle("-fx-background-color: #222;");

			popOver.setContentNode(popOverPane);
			popOver.setArrowLocation(ArrowLocation.TOP_RIGHT);

			//content.setEffect(new GaussianBlur(200.0));
			Animations.blurIn(content);
			popOver.setOnHiding(event2 -> {
				//content.setEffect(null);
				Animations.blurOut(content);
			});
				
			moreButton.setText("▲ More");
			
			popOver.show(moreButton);
		});
		
	}

	private void startDownload() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Download");
		alert.setHeaderText("What do you want to download ?");
		alert.setContentText("Choose your option.");
		ButtonType buttonTypeTwo = new ButtonType("Digitalizations");
		ButtonType buttonTypeOne = new ButtonType("Transcriptions");
		ButtonType buttonTypeThree = new ButtonType("Both");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		if (LocalSessionBridge.getOpenedDocumet().getScanningWorkProject() != null
				&& LocalSessionBridge.getOpenedDocumet().getTranscriptionWorkProject() != null)
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);
		else if (LocalSessionBridge.getOpenedDocumet().getScanningWorkProject() != null
				&& LocalSessionBridge.getOpenedDocumet().getTranscriptionWorkProject() == null)
			alert.getButtonTypes().setAll(buttonTypeTwo, buttonTypeCancel);
		else if (LocalSessionBridge.getOpenedDocumet().getScanningWorkProject() == null
				&& LocalSessionBridge.getOpenedDocumet().getTranscriptionWorkProject() != null)
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			// scegli url
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);

			// Show save file dialog
			Stage t = new Stage();
			t.setTitle("");

			File file = fileChooser.showSaveDialog(t);
			if (file != null) {
				// chiama controller
				DownloadController.getOperaTranscription(LocalSessionBridge.getOpenedDocumet(), file);
			}
		} else if (result.get() == buttonTypeTwo) {
			// scegli url
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);

			// Show save file dialog
			Stage t = new Stage();
			t.setTitle("");

			File file = fileChooser.showSaveDialog(t);
			if (file != null) {
				DownloadController.getOperaDigitalization(LocalSessionBridge.getOpenedDocumet(), file);
			}
		} else if (result.get() == buttonTypeThree) {
			// scegli url
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);

			// Show save file dialog
			Stage t = new Stage();
			t.setTitle("");

			File file = fileChooser.showSaveDialog(t);
			if (file != null) {
				DownloadController.getOpera(LocalSessionBridge.getOpenedDocumet(), file);
			}
		}
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
		updatePage();
		updateTranscription();
	}

	private void updatePage() {
		viewPage(((ImageView) pageList.getSelectionModel().getSelectedItem().getChildren().get(0)).getImage());
	}

	private void updatePage(int index) {
		viewPage(((ImageView) pageList.getItems().get(index).getChildren().get(0)).getImage());
		updateTranscription(index);
	}

	private void updateTranscription() {
		int index = Integer
				.parseInt(((Label) pageList.getSelectionModel().getSelectedItem().getChildren().get(1)).getText());
		setTranscriptionText(LocalSessionBridge.getOpenedDocumet().getPageList().get(index - 1).getTranscription().getText());
	}

	/*
	 * @param index of the page in the pageList of which to load transcription
	 */
	private void updateTranscription(int index) {
		index = Integer.parseInt(((Label) pageList.getItems().get(index).getChildren().get(1)).getText());
		setTranscriptionText(LocalSessionBridge.getOpenedDocumet().getPageList().get(index - 1).getTranscription().getText());
	}

	private void updateTranscription(VBox elem) {
		updateTranscription(pageList.getItems().indexOf(elem));
	}

	private void setTranscriptionText(TEItext teiText) {
		transcription.clear();
		if (teiText.getText() != null) {
			transcription.replaceText(0, 0, teiText.getText());
		}
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
				if (newIndex >= 0) {
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
					updatePage(newIndex);
				}
			}
			if (event.getCode() == KeyCode.DOWN) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() + 1;
				if (newIndex < pageList.getItems().size()) {
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
					updatePage(newIndex);
				}
			}
			if (event.getCode() == KeyCode.PAGE_UP) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() - 2;
				if (newIndex >= 0) {
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
					updatePage(newIndex);
				} else if (newIndex == -1) {
					number.setText(((Label) pageList.getItems().get(0).getChildren().get(1)).getText());
					updatePage(0);
				}
			}
			if (event.getCode() == KeyCode.PAGE_DOWN) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() + 2;
				if (newIndex < pageList.getItems().size()) {
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
					updatePage(newIndex);
				} else if (newIndex == pageList.getItems().size()) {
					number.setText(((Label) pageList.getItems().get(newIndex - 1).getChildren().get(1)).getText());
					updatePage(newIndex - 1);
				}
			}
			if (event.getCode() == KeyCode.HOME) {
				number.setText(((Label) pageList.getItems().get(0).getChildren().get(1)).getText());
				updatePage(0);
			}
			if (event.getCode() == KeyCode.END) {
				number.setText(((Label) pageList.getItems().get(pageList.getItems().size() - 1).getChildren().get(1))
						.getText());
				updatePage(pageList.getItems().size() - 1);
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

	private void initTranscription() {
		transcription.setParagraphGraphicFactory(LineNumberFactory.get(transcription));

		transcription.textProperty().addListener((obs, oldText, newText) -> {
			transcription.setStyleSpans(0, XmlSyntaxHighlight.computeHighlighting(newText));
		});

		transcription.setEditable(false);
		transcription.setWrapText(true);
		transcription.setFocusTraversable(false);
	}

}
