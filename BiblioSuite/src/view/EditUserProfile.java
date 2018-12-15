package view;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import controller.AdministrationController;
import controller.EditUserController;
import controller.LocalSession;
import controller.RoleController;
import dao.DatabaseException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.User;
import vo.Email;
import vo.Level;
import vo.UUIDUser;
import vo.UserInformations;
import vo.UserPermissions;
import vo.view.Formatter;

public class EditUserProfile {
	private int transcriberLevel = 0;

	@FXML
	private Label username;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private TextField email;
	@FXML
	private Label level;
	@FXML
	private Label levelT;

	@FXML
	private ChoiceBox<Integer> levelChoose;
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

		if (LocalSession.getLocalUser().isTranscriber()) {
			level.setText(
					String.valueOf(AdministrationController.getTranscriberLevel(LocalSession.getLocalUser().getID())));
		} else {
			levelT.setVisible(false);
			level.setVisible(false);
		}
	}

	private void initDeactivateButton() {
		deactivate.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

			User editedUser = EditUserController.getToEditUser();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("User Status");
			alert.setHeaderText("User status management");
			alert.setContentText("Are you sure that you want deactivate user: " + editedUser.getUsername() + " ?");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Error");
				alert1.setHeaderText("User " + editedUser.getUsername() + " can be involved in some projects !");
				alert1.setContentText(
						"Is it eventually ok to remove him from all projects and choose a new coordinator?");

				String exceptionText = "";

				for (Entry<String, Integer> entry : AdministrationController.getInvolvedUser(editedUser.getID())
						.entrySet()) {

					if (entry.getKey().equals("ScanningProjectCoordinator") && entry.getValue() != 0) {
						exceptionText = exceptionText + "\n " + "Scanning Project Coordinator: " + entry.getValue();
					}

					if (entry.getKey().equals("ScanningProjectDigitalizer") && entry.getValue() != 0) {
						exceptionText = exceptionText + "\n " + "Scanning Project Digitalizer: " + entry.getValue();

					}
					if (entry.getKey().equals("ScanningProjectReviser") && entry.getValue() != 0) {
						exceptionText = exceptionText + "\n " + "Scanning Project Reviser: " + entry.getValue();

					}
					if (entry.getKey().equals("TranscriptionProjectCoordinator") && entry.getValue() != 0) {
						exceptionText = exceptionText + "\n " + "Transcription Project Coordinator: "
								+ entry.getValue();

					}
					if (entry.getKey().equals("TranscriptionProjectTranscriber") && entry.getValue() != 0) {
						exceptionText = exceptionText + "\n " + "Transcription Project Transcriber: "
								+ entry.getValue();

					}
					if (entry.getKey().equals("TranscriptionProjectReviser") && entry.getValue() != 0) {
						exceptionText = exceptionText + "\n " + "Transcription Project Reviser: " + entry.getValue();

					}

				}

				Label label = new Label("User in involved in:");

				TextArea textArea = new TextArea(exceptionText);
				textArea.setEditable(false);
				textArea.setWrapText(true);

				textArea.setMaxWidth(Double.MAX_VALUE);
				textArea.setMaxHeight(Double.MAX_VALUE);
				GridPane.setVgrow(textArea, Priority.ALWAYS);
				GridPane.setHgrow(textArea, Priority.ALWAYS);

				GridPane expContent = new GridPane();
				expContent.setMaxWidth(Double.MAX_VALUE);
				expContent.add(label, 0, 0);
				expContent.add(textArea, 0, 1);

				// Set expandable Exception into the dialog pane.
				alert1.getDialogPane().setExpandableContent(expContent);
				alert1.getButtonTypes().add(ButtonType.NO);

				Optional<ButtonType> result1 = alert1.showAndWait();

				if (result1.get() == ButtonType.OK) {
					HashMap<String, Integer> map = AdministrationController.getInvolvedUser(editedUser.getID());

					if ((map.containsKey("ScanningProjectCoordinator") && map.get("ScanningProjectCoordinator") != 0)
							|| (map.containsKey("TrascriptionProjectCoordinator")
									&& map.get("TranscriptionProjectCoordinator") != 0)) {

						List<Formatter> scelta = new LinkedList<Formatter>();

						for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
							if (e.getKey().getValue() != editedUser.getID().getValue()) {
								scelta.add(new Formatter(e.getKey(), e.getValue()));
							}
						}

						if (!(scelta.isEmpty())) {

							ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
							dialogo.setTitle("User Role Management");
							dialogo.setHeaderText("You must choose a new Coordinator");
							dialogo.setContentText("Available Coordinators:");

							Optional<Formatter> risultato = dialogo.showAndWait();

							if (risultato.isPresent()) {

								// cambia a cascata inserendo il nuovo coordinatore
								RoleController.replaceCoordinator(editedUser.getID(), dialogo.getResult().getIdUser());

								// cancellalo come trascrittore/digitalizzatore da tutti i prj in cui è
								// coinvolto
								RoleController.removeUserFromAllProjects(editedUser.getID());
								AdministrationController.modifyUserStatus(editedUser.getID(), false);
								SceneController.loadPreviousScene();

							}
						} else {
							Alert alert3 = new Alert(AlertType.WARNING);
							alert3.setTitle("Warning");
							alert3.setHeaderText("No Staff");
							alert3.setContentText(
									"Add coordinators to your staff, you can't deactivate " + editedUser.getUsername());

							alert3.showAndWait();
							SceneController.loadPreviousScene();
						}
					} else {
						RoleController.removeUserFromAllProjects(editedUser.getID());
						AdministrationController.modifyUserStatus(editedUser.getID(), false);
						SceneController.loadPreviousScene();
					}

				} else if (result1.get() == ButtonType.NO) {
					SceneController.loadPreviousScene();

				}

			}

			/*
			 * Alert avviso = new Alert(AlertType.CONFIRMATION);
			 * avviso.setTitle("User Status");
			 * avviso.setHeaderText("User status management");
			 * avviso.setContentText("User: " + editedUser.getUsername() +
			 * " is involved like Coordinator in a scanning project do you want to pick up a new Admin ?"
			 * );
			 * 
			 * Optional<ButtonType> risultato = avviso.showAndWait();
			 * 
			 * if (result.get() == ButtonType.OK){ //scegli un nuovo admin }else { //mostra
			 * avviso operazione annullata //torna indietro }
			 */
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
			editedUser.setLevel(new Level(transcriberLevel));
			
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
			if (EditUserController.getToEditUser().isAdmin())
				deactivate.setDisable(true);

			//transcriber level
			for (int i = 1; i < 6; ++i) {
				levelChoose.getItems().add(i);
			}
			levelChoose.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number entry, Number entryNew) {
					transcriberLevel = levelChoose.getSelectionModel().getSelectedIndex()+1;
				}
			});
			levelChoose.getSelectionModel().select(AdministrationController.getTranscriberLevel(LocalSession.getLocalUser().getID()));
			levelChoose.setVisible(true);
			levelT.setVisible(true);
			level.setVisible(false);
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
					} else {
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
