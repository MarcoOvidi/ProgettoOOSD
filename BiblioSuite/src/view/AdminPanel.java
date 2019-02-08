package view;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Optional;

import controller.AdministrationController;
import controller.EditUserController;
import controller.LocalSession;
import dao.concrete.DatabaseException;
import dao.concrete.EditProfileQuerySet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import model.User;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.view.RequestRow;
import vo.view.UserRow;

public class AdminPanel {

	@FXML
	private TableView<UserRow> users;
	@FXML
	private TableView<UserRow> users1;
	@FXML
	private ObservableList<UserRow> userRows;
	@FXML
	private ObservableList<UserRow> userRows1;
	@FXML
	private TableColumn<UserRow, UUIDUser> userID;
	@FXML
	private TableColumn<UserRow, String> username;
	@FXML
	private TableColumn<UserRow, String> name;
	@FXML
	private TableColumn<UserRow, String> surname;
	@FXML
	private TableColumn<UserRow, String> email;

	@FXML
	private TableColumn<UserRow, UUIDUser> userID1;
	@FXML
	private TableColumn<UserRow, String> username1;
	@FXML
	private TableColumn<UserRow, String> name1;
	@FXML
	private TableColumn<UserRow, String> surname1;
	@FXML
	private TableColumn<UserRow, String> email1;

	@FXML
	private Tab pending;
	@FXML
	private Tab old;

	@FXML
	private TableView<RequestRow> requests;
	@FXML
	private ObservableList<RequestRow> requestsRows;
	@FXML
	private TableColumn<RequestRow, UUIDRequest> requestID;
	@FXML
	private TableColumn<RequestRow, String> requestUsername;
	@FXML
	private TableColumn<RequestRow, String> RequestObject;
	@FXML
	private TableView<RequestRow> requests2;
	@FXML
	private ObservableList<RequestRow> requestsRows2;
	@FXML
	private TableColumn<RequestRow, UUIDRequest> requestID2;
	@FXML
	private TableColumn<RequestRow, String> requestUsername2;
	@FXML
	private TableColumn<RequestRow, String> RequestObject2;

	@FXML
	public void initialize() throws DatabaseException, ParseException {
		initActiveUserList();
		initInactiveUserList();
		initUserRowClick();
		initRequests();
		initUserRowClick1();
	}

	private void initActiveUserList() {
		userID.setCellValueFactory(new PropertyValueFactory<UserRow, UUIDUser>("userID"));
		username.setCellValueFactory(new PropertyValueFactory<UserRow, String>("username"));
		name.setCellValueFactory(new PropertyValueFactory<UserRow, String>("name"));
		surname.setCellValueFactory(new PropertyValueFactory<UserRow, String>("surname"));
		email.setCellValueFactory(new PropertyValueFactory<UserRow, String>("email"));
		userID.setVisible(false);

		userRows = FXCollections.observableArrayList();

		for (User user : AdministrationController.loadUserList(true)) {
			UserRow row = new UserRow(user.getID(), user.getUsername(), user.getInformations().getName(),
					user.getInformations().getSurname(), user.getInformations().getMail());
			userRows.add(row);
		}

		users.setItems(userRows);
	}

	private void initInactiveUserList() {
		userID1.setCellValueFactory(new PropertyValueFactory<UserRow, UUIDUser>("userID"));
		username1.setCellValueFactory(new PropertyValueFactory<UserRow, String>("username"));
		name1.setCellValueFactory(new PropertyValueFactory<UserRow, String>("name"));
		surname1.setCellValueFactory(new PropertyValueFactory<UserRow, String>("surname"));
		email1.setCellValueFactory(new PropertyValueFactory<UserRow, String>("email"));
		userID1.setVisible(false);

		userRows1 = FXCollections.observableArrayList();

		for (User user : AdministrationController.loadUserList(false)) {
			UserRow row = new UserRow(user.getID(), user.getUsername(), user.getInformations().getName(),
					user.getInformations().getSurname(), user.getInformations().getMail());
			userRows1.add(row);
		}

		users1.setItems(userRows1);
	}

	public void initUserRowClick() {
		users.setRowFactory(tv -> {
			TableRow<UserRow> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					users.refresh();

					EditUserController.setEditingUser(LocalSession.getLocalUser().getID());
					EditUserController.setToEditUser(row.getItem().getId());
					EditUserController.startEditing();

					try {
						refreshRow(row);
					} catch (DatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
			return row;
		});
	}

	public void initUserRowClick1() {
		users1.setRowFactory(tv -> {
			TableRow<UserRow> row1 = new TableRow<>();
			row1.setOnMouseClicked(event -> {
				if (!row1.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					users.refresh();

					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("User Status");
					alert.setHeaderText("Gestione Stato Utente");
					alert.setContentText("Vuoi rendere attivo l'utente: " + row1.getItem().getUsername() + " ?");

					Optional<ButtonType> result = alert.showAndWait();
					
					if (result.get() == ButtonType.OK) {
						AdministrationController.modifyUserStatus(row1.getItem().getId(), true);
					}

					try {
						refreshRow(row1);
					} catch (DatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
			return row1;
		});
	}

	private void refreshRow(TableRow<UserRow> row) throws DatabaseException {
		User user = new EditProfileQuerySet().loadUserProfile(row.getItem().getId());
		UserRow newRow = new UserRow(user.getID(), user.getUsername(), user.getInformations().getName(),
				user.getInformations().getSurname(), user.getInformations().getMail());

		int index = row.getIndex();
		userRows.remove(index);
		userRows.add(index, newRow);

		users.refresh();

	}

	private void initRequests() {
		requestID.setCellValueFactory(new PropertyValueFactory<RequestRow, UUIDRequest>("requestID"));
		requestUsername.setCellValueFactory(new PropertyValueFactory<RequestRow, String>("Username"));
		RequestObject.setCellValueFactory(new PropertyValueFactory<RequestRow, String>("Object"));
		requestID.setVisible(false);
		requestID2.setCellValueFactory(new PropertyValueFactory<RequestRow, UUIDRequest>("requestID"));
		requestUsername2.setCellValueFactory(new PropertyValueFactory<RequestRow, String>("Username"));
		RequestObject2.setCellValueFactory(new PropertyValueFactory<RequestRow, String>("Object"));
		requestID2.setVisible(false);

		initRequestTabs();

		pending.getTabPane().setOnMouseClicked(event -> {
			initRequestTabs();
			initRequestTabs2();
		});

		initRequestRowClick();
	}

	public void initRequestTabs() {
		requestsRows = FXCollections.observableArrayList();

		LinkedList<vo.Request> requestsList = AdministrationController.getRequest(0);

		for (Request request : requestsList) {
			RequestRow row = new RequestRow(request.getId(), AdministrationController.loadUsername(request.getUser()),
					request.getObject());
			requestsRows.add(row);
		}
		requests.setItems(requestsRows);
		requests.refresh();

	}

	public void initRequestTabs2() {
		requestsRows2 = FXCollections.observableArrayList();

		LinkedList<vo.Request> requestsList = AdministrationController.getRequest(1);

		for (Request request : requestsList) {
			RequestRow row = new RequestRow(request.getId(), AdministrationController.loadUsername(request.getUser()),
					request.getObject());
			requestsRows2.add(row);
		}
		requests2.setItems(requestsRows2);
		requests2.refresh();
	}

	public void initRequestRowClick() {
		requests.setRowFactory(tv -> {
			TableRow<RequestRow> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					userRequestDialog(row.getItem().getRequestID());
				}

			});
			return row;
		});
	}

	public void userRequestDialog(UUIDRequest requestID) {
		Request request = AdministrationController.loadRequest(requestID);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Request from " + AdministrationController.loadUsername(request.getUser()));
		alert.setHeaderText("Object: \"" + request.getObject() + "\"");
		alert.setContentText("\"" + request.getMessage() + "\"");

		ButtonType buttonAnswer = new ButtonType("Answer");
		ButtonType buttonIgnore = new ButtonType("Ignore");
		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonAnswer, buttonIgnore, buttonCancel);
		alert.getDialogPane().setMinHeight(400);
		alert.getDialogPane().setMinWidth(700);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonAnswer) {

			TextInputDialog dialog = new TextInputDialog("answer");
			dialog.setTitle("Request Answer");
			dialog.setHeaderText("Answer for the request " + requestID.getValue() + " commited by"
					+ AdministrationController.loadUsername(request.getUser()) + ".");
			dialog.setContentText("Please enter answer:");

			Optional<String> result2 = dialog.showAndWait();
			if (result2.isPresent()) {
				AdministrationController.answerUserRequest(requestID, result2.get());
				initRequests();
			}

		} else if (result.get() == buttonIgnore) {
			AdministrationController.ignoreUserRequest(requestID);
			initRequests();
		} else {
			alert.close();
		}
	}

}
