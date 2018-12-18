package view;

import java.text.ParseException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;

import controller.DocumentInfoController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	JFXCheckBox scanningComplete;

	@FXML
	JFXCheckBox transcriptionComplete;

	@FXML
	TextArea categories;

	@FXML
	TextArea tags;
	
	@FXML
	JFXToggleButton digitalizationStatus;
	
	@FXML
	JFXToggleButton transcriptionStatus;
	
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
			
		} catch (LoadException | DatabaseException e) {
			// TODO Manca gestione errori
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.show();
		}
	}

	private void loadProjectStatus() {
		if(documentInfoController.getScanningComplete()) {
			scanningComplete.setSelected(true);
			scanningComplete.arm();
		}
		if(documentInfoController.getTranscriptionComplete())
			transcriptionComplete.setSelected(true);
			transcriptionComplete.arm();
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
