package view;

import java.text.ParseException;

import com.jfoenix.controls.JFXCheckBox;

import controller.DocumentInfoController;
import dao.concrete.DatabaseException;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import vo.UUIDDocument;

public class DocumentProperties {

	private static UUIDDocument toShowDocument;

	private DocumentInfoController documentInfoController;

	@FXML
	Label title;

	@FXML
	Label author;
	@FXML
	Label composition;
	@FXML
	Label pages;

	@FXML
	TextArea description;

	@FXML
	JFXCheckBox scanningComplete;

	@FXML
	JFXCheckBox transcriptionComplete;

	@FXML
	FlowPane categories;

	@FXML
	FlowPane tags;

	@FXML
	public void initialize() throws DatabaseException, ParseException {
		loadInfo();
		loadProjectStatus();
		loadTags();
		loadCategories();
	}

	private void loadInfo() {
		try {
			documentInfoController = new DocumentInfoController(toShowDocument);
			title.setText(documentInfoController.getTitle());
			author.setText(documentInfoController.getAuthor());
			composition.setText(documentInfoController.getComposition());
			description.setText(documentInfoController.getDescription());
			pages.setText(documentInfoController.getPagesNumber());

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
			scanningComplete.arm();
		}
		if (documentInfoController.getTranscriptionComplete()) {
			transcriptionComplete.setSelected(true);
			transcriptionComplete.arm();
		}

	}

	private void loadTags() {
		for (String tag : documentInfoController.getTags()) {
			tags.getChildren().add(new Button(tag));
		}
	}

	private void loadCategories() {
		for (String tag : documentInfoController.getInvolvedCollections()) {
			categories.getChildren().add(new Button(tag));
		}
	}

	public static UUIDDocument getToShowDocument() {
		return toShowDocument;
	}

	public static void setToShowDocument(UUIDDocument toShowDocument) {
		DocumentProperties.toShowDocument = toShowDocument;
	}

}
