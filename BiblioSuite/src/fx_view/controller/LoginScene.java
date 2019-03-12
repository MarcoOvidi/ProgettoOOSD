package fx_view.controller;

import java.text.ParseException;

import controller.LoginController;
import dao.concrete.DatabaseException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LoginScene {

	private static boolean autoLogin = false;

	String controllerUrl = "LoginController";

	public LoginScene() {
	}

	@FXML
	private Font x31;

	@FXML
	private Color x41;

	@FXML
	private Font x1;

	@FXML
	private Color x2;

	@FXML
	private Label loginTitle;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Button login;

	@FXML
	private Button recoverButton;

	@FXML
	private Button registerButton;

	@FXML
	private Label message;

	@FXML
	void go() {
		String usr = username.getText();
		String psw = password.getText();
		if (usr.equals("") || psw.equals("")) {
			displayMessage("Username and password cannot be blank");
			return;
		}
		try {
			LoginController.login(this, usr, psw);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void loginButtonPushed(MouseEvent event) throws Exception {
		go();
	}

	@FXML
	public void initialize() throws DatabaseException, ParseException {
		if (autoLogin) {
			username.setText("Username");
			password.setText("Password");
			Platform.runLater(() -> go());
			autoLogin = false;
		}

		login.setFocusTraversable(true);
		recoverButton.setFocusTraversable(true);

		username.getParent().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				username.setText("");
				password.setText("");
				username.getParent().requestFocus();
			} else if (event.getCode() != KeyCode.SHIFT && event.getCode() != KeyCode.TAB) {
				username.setText(event.getCharacter());
				username.requestFocus();
			}
		});

		username.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			/*
			 * if (event.isShiftDown() && event.getCode() == KeyCode.TAB) {
			 * recover.requestFocus(); }
			 */
			message.setVisible(false);

			if (event.getCode() == KeyCode.ENTER) {
				password.requestFocus();
			} else if (event.getCode() == KeyCode.ESCAPE) {
				username.getParent().requestFocus();
			}
			if (event.getCode() != KeyCode.TAB && event.getCode() != KeyCode.BACK_SPACE
					&& event.getCode() != KeyCode.DELETE && event.getCode() != KeyCode.RIGHT
					&& event.getCode() != KeyCode.LEFT) {
				event.consume();
			}
		});

		password.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				go();
			} else if (event.getCode() == KeyCode.ESCAPE) {
				username.getParent().requestFocus();
			}
			if (event.getCode() != KeyCode.TAB && event.getCode() != KeyCode.BACK_SPACE
					&& event.getCode() != KeyCode.DELETE && event.getCode() != KeyCode.RIGHT
					&& event.getCode() != KeyCode.LEFT)
				event.consume();
		});

		login.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER)
				go();
		});

		recoverButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER)
				SceneController.loadScene("passwordRecovery", false);
		});

		recoverButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("passwordRecovery", false);
		});

		registerButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER)
				SceneController.loadScene("registration", false);
		});

		registerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("registration", false);
		});

		initEffects();

		Platform.runLater(() -> username.getParent().requestFocus());
	}

	private void initEffects() {
		Glow glow = new Glow();
		glow.setLevel(0.6);
		username.setEffect(glow);

		Glow glow2 = new Glow();
		glow2.setLevel(0.4);
		password.setEffect(glow2);

		Reflection reflection = new Reflection();
		reflection.setTopOffset(-10.1);
		reflection.setFraction(0.5);
		loginTitle.setEffect(reflection);

		TranslateTransition translate = new TranslateTransition();
		translate.setDuration(Duration.millis(800));
		translate.setNode(loginTitle);
		translate.setByY(10);
		translate.play();
		FadeTransition fade = new FadeTransition(Duration.millis(1000));
		fade.setNode(loginTitle);
		fade.setNode(username.getParent());
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();

	}

	public void displayMessage(String msg) {
		message.setText(msg);
		message.setVisible(true);
	}

}
