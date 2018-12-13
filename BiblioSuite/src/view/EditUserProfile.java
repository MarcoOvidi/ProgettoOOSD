package view;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Optional;

import controller.AdministrationController;
import controller.EditUserController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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
	private MenuButton roleList;
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
	private Button discard;
	@FXML
	private Button deactivate;
	

	@FXML
	public void initialize() throws DatabaseException, ParseException {
		loadInfo();
		loadPermissions();
		initDoneButton();
		initAdminOptions();
		initDeactivateButton();
	}

	private void loadInfo() {
		UserInformations info = EditUserController.getToEditUser().getInformations();
		username.setText(EditUserController.getToEditUser().getUsername());
		name.setText(info.getName());
		surname.setText(info.getSurname());
		email.setText(info.getMail().getEmail());
	}

	private void initDeactivateButton() {
		deactivate.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Look, a Confirmation Dialog");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				
				User editedUser = EditUserController.getToEditUser();	
				AdministrationController.modifyUserStatus(editedUser.getID(), false);
				SceneController.loadPreviousScene();
				
			}
		});
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
		boolean adminEditing = !EditUserController.isSelfEditing();
		setEditableUserData(!adminEditing);
		setInactivable(!adminEditing);
		setRolesVisible(adminEditing);

		discard.setVisible(adminEditing);

		if (adminEditing) {
			done.setText("Accept");
			discard.setOnMouseClicked(event -> {
				SceneController.loadPreviousScene();
			});
		}
	}
	
	private void setInactivable(boolean value) {
		deactivate.setVisible(!value);
	}
	
	private void setEditableUserData(boolean value) {
		name.setDisable(!value);
		surname.setDisable(!value);
		email.setDisable(!value);
	}

	private void setRolesVisible(boolean value) {
		if (!value) {
			roleList.setVisible(false);
		} else {
			LinkedList<String> roles = new LinkedList<String>();
			roles.add("Uploader");
			roles.add("Transcriber");
			roles.add("Upload reviser");
			roles.add("Transcription reviser");
			roles.add("Coordinator");
			
			for (String str : roles) {
				CheckMenuItem item = new CheckMenuItem();
				item.setText(str);
				item.onActionProperty().set(event -> {
					if (!item.isSelected()) {
						revokeRole(str);
					}
					else {
						grantRole(str);
					}
				});
				roleList.getItems().add(item);
			}
			
			roleList.setText("Roles");
			roleList.setAlignment(Pos.CENTER);
			
		}
	}

	private void grantRole(String str) {
		switch (str) {
		case "Uploader":
			grantUploader();
			break;
		case "Transcriber":
			grantTranscriber();
			break;
		case "Upload reviser":
			grantUploadReviser();
			break;
		case "Transcription reviser":
			grantTranscriptionReviser();
			break;
		case "Coordinator":
			grantCoordinator();
			break;

		default:
			break;
		}
	}
	
	private void revokeRole(String str) {
		switch (str) {
		case "Uploader":
			revokeUploader();
			break;
		case "Transcriber":
			revokeTranscriber();
			break;
		case "Upload reviser":
			revokeUploadReviser();
			break;
		case "Transcription reviser":
			revokeTranscriptionReviser();
			break;
		case "Coordinator":
			revokeCoordinator();
			break;

		default:
			break;
		}
	}

	private void grantUploader() {
		upload.setSelected(true);
	}

	private void grantTranscriber() {
		modifyTranscription.setSelected(true);
	}

	private void grantUploadReviser() {
		reviewPage.setSelected(true);
	}

	private void grantTranscriptionReviser() {
		reviewTranscription.setSelected(true);
	}

	private void grantCoordinator() {
		addNewProject.setSelected(true);
		;
		assignDigitalizationTask.setSelected(true);
		assignTranscriptionTask.setSelected(true);
		editMetaData.setSelected(true);
		publishDocument.setSelected(true);
	}
	
	private void revokeUploader() {
		upload.setSelected(false);
	}

	private void revokeTranscriber() {
		modifyTranscription.setSelected(false);
	}

	private void revokeUploadReviser() {
		reviewPage.setSelected(false);
	}

	private void revokeTranscriptionReviser() {
		reviewTranscription.setSelected(false);
	}

	private void revokeCoordinator() {
		addNewProject.setSelected(false);
		;
		assignDigitalizationTask.setSelected(false);
		assignTranscriptionTask.setSelected(false);
		editMetaData.setSelected(false);
		publishDocument.setSelected(false);
	}

}
