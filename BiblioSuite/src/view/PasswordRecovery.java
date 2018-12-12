package view;

import java.text.ParseException;

import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PasswordRecovery {

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
			if (!validEmail(email.getText())) {
				alert("Invalid email.");
				return;
			}
			alert("Mail with password recover instructions sent to " + email.getText());
		});
	}

	private void initBackButton() {
		back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadPreviousScene();
		});
	}
	
	private boolean validEmail (String mail) {
		//TODO
		if (mail.contains("@"))
			return true;
		else
			return false;
	}

	private void alert(String text) {
		Alert al = new Alert(AlertType.INFORMATION);
		al.setContentText(text);
		al.setTitle("Attention");
		al.show();
	}

}
