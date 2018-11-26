package fx_view;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import controller.AdministrationController;
import controller.EditUserController;
import controller.LocalSession;
import dao.DatabaseException;
import dao.EditProfileQuerySet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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
		initRowClick();
		initRequestTabs();
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

	public void initRowClick() {
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
	
	private void initRequestTabs() {
		requestID.setCellValueFactory(new PropertyValueFactory<RequestRow, UUIDRequest>("requestID"));
		requestUsername.setCellValueFactory(new PropertyValueFactory<RequestRow, String>("requestUsername"));
		RequestObject.setCellValueFactory(new PropertyValueFactory<RequestRow, String>("RequestObject"));
		requestID.setVisible(false);
		
		requestsRows = FXCollections.observableArrayList();
		
		pending.getTabPane().setOnMouseClicked(event -> {
			if(pending.getTabPane().getSelectionModel().getSelectedItem() == pending) {
				LinkedList<vo.Request> requestsList = AdministrationController.getRequest(0);
				//LinkedList<Request> requestsList = new LinkedList<vo.Request>(); 
				for (Request request : requestsList) {
					//RequestRow row = new RequestRow(request.getId(), request.getUser(), request.getObject());
				}
			}
			else {
				//HashMap<UUIDRequest, String> requestsMap = AdministrationController.getReadRequests();				
			}
		});
	}

}
