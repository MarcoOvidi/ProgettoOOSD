package view;

import java.util.Comparator;
import java.util.LinkedList;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import controller.DocumentInfoController;
import controller.LocalSessionBridge;
import controller.PageTranscriptionController;
import controller.TranscriptionProjectController;
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
import vo.TEItext;
import vo.UUIDPage;

public class PageTranscriptionReview {
	public static boolean isEmpty = false;

	@FXML
	private Button backButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button validateButton;

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
	private TextArea commentField;

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

	@FXML
	public void initialize() {
		loadPageList();
		initBackButton();
		if (isEmpty) {
			message.setText("Empty Document?!");
			transcription.setVisible(false);
			pageList.setVisible(false);
			next.setVisible(false);
			previous.setVisible(false);
			top.setVisible(false);
			bottom.setVisible(false);
			message.toFront();
			isEmpty = false; // for when opening the next document
			return;
		}
		initNavigationButton();
		initKeyboardNavigation();
		initTranscription();
		initSaveButton();
		initValidateButton();

		pageList.getSelectionModel().selectFirst();
		updatePageNumber();

		Platform.runLater(() -> pageList.requestFocus());

	}
	
	private void initSaveButton() {
		saveButton.setOnAction(event -> {
			System.out.println(LocalSessionBridge.getOpenedDocumet().getUUID());
			System.out.println(Integer.parseInt(number.getText()));
			UUIDPage page = DocumentInfoController.getPageID(LocalSessionBridge.getOpenedDocumet().getUUID(), Integer.parseInt(number.getText()));
			
			PageTranscriptionController.saveTranscription(page, transcription.getText());
			PageTranscriptionController.setPageRevised(page, true);	
			TranscriptionProjectController.setTranscriptionComment(page, commentField.getText());
			transcription.setDisable(true); 
			
			validateButton.setDisable(true);
			saveButton.setDisable(true);
			commentField.setDisable(true);
			saveButton.setStyle("-fx-background-color:#e86e0a; -fx-opacity: 1.0;");
			saveButton.setText("Done");
		});
	}
	
	private void initValidateButton() {
		validateButton.setOnAction(event -> {
			UUIDPage page = DocumentInfoController.getPageID(LocalSessionBridge.getOpenedDocumet().getUUID(), Integer.parseInt(number.getText()));
			PageTranscriptionController.saveTranscription(page, transcription.getText());
			PageTranscriptionController.setPageRevised(page, true);
			PageTranscriptionController.setPageValidated(page, true);
			TranscriptionProjectController.setTranscriptionComment(page, commentField.getText());
			transcription.setDisable(true); 
			
			validateButton.setDisable(true);
			saveButton.setDisable(true);
			commentField.setDisable(true);
			validateButton.setStyle("-fx-background-color:#3CB371;-fx-opacity: 1.0;");
			validateButton.setText("Done");
		});
		
		
	}

	public void loadPageList() {
		LinkedList<Page> pages = LocalSessionBridge.getOpenedDocumet().getPageList();
		// System.out.println("1");

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
				updatePageNumber();
			});

			pageList.getItems().add(vbox);

		}

		//Collections.sort(pages);
		//System.out.println(pages.get(0).getScan().getImage().getUrl());
		viewPage(new Image("file:"+pages.get(0).getScan().getImage().getUrl()));
	}

	private void viewPage(Image pageImage) {
		currentPage.setImage(pageImage);
		currentPage.setFitWidth(450);
		currentPage.setFitHeight(560);
	}

	private void initBackButton() {
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			event.consume();
			SceneController.loadScene("transcriptionReview");
		});
		backButton.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				event.consume();
				SceneController.loadScene("transcriptionReview");
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
		updatePage();
		updateTranscription();
		
		saveButton.setText("Save and send back");
		validateButton.setText("Save and Validate");
	}

	private void updatePage() {
		viewPage(((ImageView) pageList.getSelectionModel().getSelectedItem().getChildren().get(0)).getImage());
	}

	/*private void updatePage(int index) {
		viewPage(((ImageView) pageList.getItems().get(index).getChildren().get(0)).getImage());
		updateTranscription(index);
	}*/

	private void updateTranscription() {
		//int index = Integer	.parseInt(((Label) pageList.getSelectionModel().getSelectedItem().getChildren().get(1)).getText());
		int index = pageList.getSelectionModel().getSelectedIndex();
		Page page = LocalSessionBridge.getOpenedDocumet().getPageList().get(index);
		setTranscriptionText(page.getTranscription().getText());
		commentField.setText(page.getTranscription().getComment());
		
		if(page.getTranscription().getValidated()) {
			transcription.setDisable(true);
			saveButton.setDisable(true);
			saveButton.setVisible(false);
			validateButton.setDisable(true);
			validateButton.setVisible(false);
			commentField.setDisable(true);
			commentField.setVisible(false);
			
		}else {
			transcription.setDisable(false);
			saveButton.setDisable(false);
			saveButton.setVisible(true);
			validateButton.setDisable(false);
			validateButton.setVisible(true);
			commentField.setDisable(false);
			commentField.setVisible(true);
			
		}
	}

	/*
	 * @param index of the page in the pageList of which to load transcription
	 */
	private void updateTranscription(int index) {
		index = Integer.parseInt(((Label) pageList.getItems().get(index).getChildren().get(1)).getText());
		setTranscriptionText(LocalSessionBridge.getOpenedDocumet().getPageList().get(index-1).getTranscription().getText());
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
		backButton.setFocusTraversable(false);
		saveButton.setFocusTraversable(false);

		pageList.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			/*if (event.getCode() == KeyCode.LEFT) {
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
					updateTranscription();
				}
			}
			if (event.getCode() == KeyCode.DOWN) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() + 1;
				if (newIndex < pageList.getItems().size()) {
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
					updatePage(newIndex);
					updateTranscription();
				}
			}
			if (event.getCode() == KeyCode.PAGE_UP) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() - 2;
				if (newIndex >= 0) {
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
					updatePage(newIndex);
					updateTranscription();
				} else if (newIndex == -1) {
					number.setText(((Label) pageList.getItems().get(0).getChildren().get(1)).getText());
					updatePage(0);
					updateTranscription();
				}
			}
			if (event.getCode() == KeyCode.PAGE_DOWN) {
				int newIndex = pageList.getFocusModel().getFocusedIndex() + 2;
				if (newIndex < pageList.getItems().size()) {
					number.setText(((Label) pageList.getItems().get(newIndex).getChildren().get(1)).getText());
					updatePage(newIndex);
					updateTranscription();
				} else if (newIndex == pageList.getItems().size()) {
					number.setText(((Label) pageList.getItems().get(newIndex - 1).getChildren().get(1)).getText());
					updatePage(newIndex - 1);
					updateTranscription();
				}
			}
			if (event.getCode() == KeyCode.HOME) {
				number.setText(((Label) pageList.getItems().get(0).getChildren().get(1)).getText());
				updatePage(0);
				updateTranscription();
			}
			if (event.getCode() == KeyCode.END) {
				number.setText(((Label) pageList.getItems().get(pageList.getItems().size() - 1).getChildren().get(1))
						.getText());
				updatePage(pageList.getItems().size() - 1);
			}
			if (event.getCode() == KeyCode.ESCAPE) {
				backButton.requestFocus();
			}*/
			if (event.getCode() == KeyCode.LEFT) {
				previous();
			}
			if (event.getCode() == KeyCode.RIGHT) {
				next();
			}
			if (event.getCode() == KeyCode.UP) {
				event.consume();
				previous();
			}

			if (event.getCode() == KeyCode.DOWN) {
				event.consume();
				next();
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

		transcription.setEditable(true);
		transcription.setWrapText(true);
		transcription.setFocusTraversable(false);
	}

}
