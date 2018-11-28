package view;

import java.text.ParseException;

import controller.EditUserController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import vo.Email;
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
		initAdminOptions();
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
			addNewProject.setSelected(up.getAddNewProjectPerm());
			assignDigitalizationTask.setSelected(up.getAssignDigitalizationTaskPerm());
			assignTranscriptionTask.setSelected(up.getAssignTranscriptionTaskPerm());
			download.setSelected(up.getDownloadPerm());
			editMetaData.setSelected(up.getEditMetaDataPerm());
			modifyTranscription.setSelected(up.getModifyTranscriptionPerm());
			publishDocument.setSelected(up.getPublishDocumentPerm());
			requestTranscriptionTask.setSelected(up.getRequestTranscriptionTaskPerm());
			reviewPage.setSelected(up.getReviewPagePerm());
			reviewTranscription.setSelected(up.getReviewTranscriptionPerm());
			upload.setSelected(up.getUploadPerm());

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
			User editedUser = EditUserController.getToEditUser();
			editedUser.setPermissions(getUpdatedPermissions());
			editedUser.setInformations(getUpdatedInformations(editedUser.getInformations()));	
			
			EditUserController.setModifications(editedUser);
			EditUserController.commitModifications();
			SceneController.loadPreviousScene();
		});
	}
	
	private UserPermissions getUpdatedPermissions() {
		UserPermissions editedPermissions = new UserPermissions();
		editedPermissions.setAddNewProjectPerm(addNewProject.isSelected());
		editedPermissions.setAssignDigitalizationTaskPerm(assignDigitalizationTask.isSelected());
		editedPermissions.setAssignTranscriptionTaskPerm(assignTranscriptionTask.isSelected());
		editedPermissions.setDownloadPerm(download.isSelected());
		editedPermissions.setEditMetaDataPerm(editMetaData.isSelected());
		editedPermissions.setModifyTranscriptionPerm(modifyTranscription.isSelected());
		editedPermissions.setPublishDocumentPerm(publishDocument.isSelected());
		editedPermissions.setRequestTranscriptionTaskPerm(requestTranscriptionTask.isSelected());
		editedPermissions.setReviewPagePerm(reviewPage.isSelected());
		editedPermissions.setReviewTranscriptionPerm(reviewTranscription.isSelected());
		editedPermissions.setUploadPerm(upload.isSelected());
		return editedPermissions;
	}
	
	private UserInformations getUpdatedInformations(UserInformations old) {
		old.setName(name.getText());
		old.setSurname(surname.getText());
		old.setEmail(new Email(email.getText()));
		return old;
	}
	
	private void initAdminOptions() {
		
	}

}
