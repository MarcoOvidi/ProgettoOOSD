package view;

import java.sql.SQLException;
import java.text.ParseException;

import controller.RegistrationController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Registration {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password1;
	@FXML
	private PasswordField password2;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private TextField email;

	@FXML
	private Button back;
	@FXML
	private Button register;

	@FXML
	public void initialize() throws DatabaseException, ParseException {
		initRegisterButton();
		initBackButton();
	}

	private void initRegisterButton() {
		register.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (username.getText().isEmpty() || password1.getText().isEmpty() || password2.getText().isEmpty()
					|| email.getText().isEmpty() || name.getText().isEmpty() || surname.getText().isEmpty()) {
				alert("No field can be left blank.");
				return;
			}
			try {
				if (!RegistrationController.checkValidUsername(username.getText())) {
					alert("Username already taken.");
					return;
				}
				if (!RegistrationController.checkUsername(username.getText())) {
					alert("Username already taken.");
					return;
				}
			} catch (SQLException | DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!password1.getText().equals(password2.getText())) {
				alert("Inserted passwords do not match.");
				event.consume();

				String res = RegistrationController.register(username.getText(), password1.getText(), email.getText(),
						name.getText(), surname.getText());

				alert(res);

			}
		});
	}

	private void initBackButton() {
		back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadPreviousScene();
		});
	}

	private void alert(String text) {
		Alert al = new Alert(AlertType.INFORMATION);
		al.setContentText(text);
		al.setTitle("Attention");
		al.show();
	}

}
