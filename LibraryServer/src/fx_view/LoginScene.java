package fx_view;
import controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public class LoginScene {
	
	String controllerUrl="LoginController";
	
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
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;
    
    @FXML
    private Label message;

    @FXML
    void loginButtonPushed(MouseEvent event) throws Exception {
    	String usr=username.getText();
    	String psw=password.getText();
    	//String param="username="+user+"&"+"password="+pass;
    	
		LoginController.login(this,usr,psw);
    	/*
    	ServletConnection scon= new ServletConnection(controllerUrl);
    	System.out.println(scon.sendPost(param));    	
    */
    }
    
    public void displayMessage(String msg){
    	message.setText(msg);
    }
    
    

}

