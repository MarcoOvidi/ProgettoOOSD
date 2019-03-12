package fx_view.controller;

import java.text.ParseException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import controller.DocumentInfoController;
import dao.concrete.DatabaseException;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import vo.UUIDDocument;

public class UpdateDocumentProperties {

	private static UUIDDocument toShowDocument;

	private DocumentInfoController documentInfoController;

	@FXML
	TextField title;

	@FXML
	TextField author;

	@FXML
	TextField composition;

	@FXML
	TextArea description;

	@FXML
	JFXToggleButton scanningComplete;

	@FXML
	JFXToggleButton transcriptionComplete;

	@FXML
	TextArea categories;

	@FXML
	TextArea tags;

	@FXML
	JFXButton saveButton;

	@FXML
	public void initialize() throws DatabaseException, ParseException {
		loadInfo();
		loadProjectStatus();
		loadTags();
		loadCategories();
		initSaveButton();
	}

	private void initSaveButton() {
		saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			updateProjectStatus();
		});
	}
	
	private void updateProjectStatus() {
		if (scanningComplete.isSelected() && !documentInfoController.getScanningComplete()) {
			documentInfoController.setScanningComplete(true);
			
		}
		if (transcriptionComplete.isSelected() && !documentInfoController.getTranscriptionComplete()) {
			documentInfoController.setTranscriptionComplete();
		}
		
		if (!scanningComplete.isSelected() && documentInfoController.getScanningComplete()) {
			documentInfoController.setScanningComplete(false);
		}
	}

	private void loadInfo() {
		try {
			documentInfoController = new DocumentInfoController(toShowDocument);
			title.setText(documentInfoController.getTitle());
			author.setText(documentInfoController.getAuthor());
			composition.setText(documentInfoController.getComposition());
			description.setText(documentInfoController.getDescription());

		} catch (LoadException | DatabaseException e) {
			// TODO Manca gestione errori
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.show();
		}
	}

	private void loadProjectStatus() {
		if (documentInfoController.getScanningComplete()) {
			scanningComplete.setSelected(true);
			// scanningComplete.arm();

			if (documentInfoController.getTranscriptionComplete()) {
				transcriptionComplete.setSelected(true);
				// transcriptionComplete.arm();
			}

		} else {
			// transcriptionComplete.setDisable(true);
		}

		scanningComplete.setUnToggleColor(Color.RED);
		scanningComplete.setUnToggleLineColor(Color.CRIMSON);
		scanningComplete.setOnAction(event -> {
			if (scanningComplete.isSelected()) {
				transcriptionComplete.setDisable(false);
			} else {
				transcriptionComplete.setSelected(false);
				transcriptionComplete.setDisable(true);
			}
		});

		transcriptionComplete.setOnAction(event -> {

		});
	}

	private void loadTags() {
		for (String tag : documentInfoController.getTags()) {
			tags.appendText(tag + " ");
		}
	}

	private void loadCategories() {

	}

	public static UUIDDocument getToShowDocument() {
		return toShowDocument;
	}

	public static void setToShowDocument(UUIDDocument toShowDocument) {
		UpdateDocumentProperties.toShowDocument = toShowDocument;
	}

}
