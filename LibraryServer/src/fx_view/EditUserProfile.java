package fx_view;

import java.text.ParseException;

import controller.EditUserController;
import controller.LocalSession;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import vo.UserInformations;
import vo.UserPermissions;

public class EditUserProfile {

	@FXML
	private Label username;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private TextField email;

	@FXML
	private CheckBox download;
	@FXML
	private CheckBox publishDocument;
	@FXML
	private CheckBox editMetaData;
	@FXML
	private CheckBox addNewProject;
	@FXML
	private CheckBox assignDigitalizationTask;
	@FXML
	private CheckBox upload;
	@FXML
	private CheckBox requestTranscriptionTask;
	@FXML
	private CheckBox assignTranscriptionTask;
	@FXML
	private CheckBox modifyTranscription;
	@FXML
	private CheckBox reviewPage;
	@FXML
	private CheckBox reviewTranscription;

	@FXML
	private Button done;

	@FXML
	public void initialize() throws DatabaseException, ParseException {
		loadInfo();
		loadPermissions();
		initDoneButton();
	}

	private void loadInfo() {
		UserInformations info = EditUserController.getToEditUser().getInformations();
		username.setText(EditUserController.getToEditUser().getUsername());
		name.setText(info.getName());
		surname.setText(info.getSurname());
		email.setText(info.getMail().getEmail());
	}

	private void loadPermissions() {
		UserPermissions up = EditUserController.getToEditUser().getPermissions();
		if (up.getAddNewProjectPerm())
			addNewProject.setSelected(true);
		if (up.getAssignDigitalizationTaskPerm())
			assignDigitalizationTask.setSelected(true);
		if (up.getAssignTranscriptionTaskPerm())
			assignTranscriptionTask.setSelected(true);
		if (up.getDownloadPerm())
			download.setSelected(true);
		if (up.getEditMetaDataPerm())
			editMetaData.setSelected(true);
		if (up.getModifyTranscriptionPerm())
			modifyTranscription.setSelected(true);
		if (up.getPublishDocumentPerm())
			publishDocument.setSelected(true);
		if (up.getRequestTranscriptionTaskPerm())
			requestTranscriptionTask.setSelected(true);
		if (up.getReviewPagePerm())
			reviewPage.setSelected(true);
		if (up.getReviewTranscriptionPerm())
			reviewTranscription.setSelected(true);
		if (up.getUploadPerm())
			upload.setSelected(true);

		if (EditUserController.canEditPermissions()) {
			addNewProject.setDisable(false);
			assignDigitalizationTask.setDisable(false);
			assignTranscriptionTask.setDisable(false);
			download.setDisable(false);
			editMetaData.setDisable(false);
			modifyTranscription.setDisable(false);
			publishDocument.setDisable(false);
			requestTranscriptionTask.setDisable(false);
			reviewPage.setDisable(false);
			reviewTranscription.setDisable(false);
			upload.setDisable(false);
		}

	}

	private void initDoneButton() {
		done.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadPreviousScene();
		});
	}

}
