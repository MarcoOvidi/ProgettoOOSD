package view;

import application.ServerAddress;
import application.ServletConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.json.*;
public class Login {
	
	String controllerUrl="LoginController";
	
	public Login() {
		controllerUrl=ServerAddress.getAddress()+controllerUrl;
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
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    void loginButtonPushed(MouseEvent event) throws Exception {
    	String user=username.getText();
    	String pass=password.getText();
    	String param="username="+user+"&"+"password="+pass;
    	
    	//temporaneo
    	SceneController.loadScene("home");
    	/*
    	ServletConnection scon= new ServletConnection(controllerUrl);
    	System.out.println(scon.sendPost(param));    	
    */
    }

}

