package view;

import java.text.ParseException;

import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXCheckBox;

import controller.DocumentInfoController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
	public void initialize() throws DatabaseException, ParseException {		
		loadInfo();
		loadProjectStatus();
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
		if(documentInfoController.getScanningComplete())
			scanningComplete.arm();
		if(documentInfoController.getTranscriptionComplete())
			transcriptionComplete.arm();
	}
	
	private void loadCategories() {
				
	}

	public static UUIDDocument getToShowDocument() {
		return toShowDocument;
	}

	public static void setToShowDocument(UUIDDocument toShowDocument) {
		DocumentProperties.toShowDocument = toShowDocument;
	}

}
