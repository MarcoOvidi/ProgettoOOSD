package fx_view;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Optional;

import controller.AdministrationController;
import controller.EditUserController;
import controller.LocalSession;
import dao.DatabaseException;
import dao.EditProfileQuerySet;
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
import vo.RequestRow;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.UserRow;

public class AdminPanel {

	@FXML
	private TableView<UserRow> users;
	@FXML
	private ObservableList<UserRow> userRows;
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
	private Tab pending;
	@FXML
	private Tab old;

	@FXML
	private TableView<RequestRow> requests;
	@FXML
	private TableView<RequestRow> requests2;
	@FXML
	private ObservableList<RequestRow> requestsRows;
	@FXML
	private TableColumn<RequestRow, UUIDRequest> requestID;
	@FXML
	private TableColumn<RequestRow, String> requestUsername;
	@FXML
	private TableColumn<RequestRow, String> RequestObject;

	@FXML
	public void initialize() throws DatabaseException, ParseException {
		initUserList();
		initUserRowClick();
		initRequests();
	}

	private void initUserList() {
		userID.setCellValueFactory(new PropertyValueFactory<UserRow, UUIDUser>("userID"));
		username.setCellValueFactory(new PropertyValueFactory<UserRow, String>("username"));
		name.setCellValueFactory(new PropertyValueFactory<UserRow, String>("name"));
		surname.setCellValueFactory(new PropertyValueFactory<UserRow, String>("surname"));
		email.setCellValueFactory(new PropertyValueFactory<UserRow, String>("email"));
		userID.setVisible(false);

		userRows = FXCollections.observableArrayList();

		for (User user : AdministrationController.loadUserList()) {
			UserRow row = new UserRow(user.getID(), user.getUsername(), user.getInformations().getName(),
					user.getInformations().getSurname(), user.getInformations().getMail());
			userRows.add(row);
		}

		users.setItems(userRows);
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

	private void refreshRow(TableRow<UserRow> row) throws DatabaseException {
		User user = EditProfileQuerySet.loadUserProfile(row.getItem().getId());
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
		requestsRows = FXCollections.observableArrayList();

		LinkedList<vo.Request> requestsList = AdministrationController.getRequest(1);

		for (Request request : requestsList) {
			RequestRow row = new RequestRow(request.getId(), AdministrationController.loadUsername(request.getUser()),
					request.getObject());
			requestsRows.add(row);
		}
		requests2.setItems(requestsRows);
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
			dialog.setHeaderText("Answer for the request " + requestID.getValue() + " commited by" + AdministrationController.loadUsername(request.getUser()) + ".");
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
