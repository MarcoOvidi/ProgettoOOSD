package fx_view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Topbar {
	@FXML
	private Button profilelink;
	
	@FXML
	private Button homelink;
	
	
	public void initialize() {
		initProfilelink();
		initHomelink();
	}
	
	//inizializzo il bottone gotoprofile 
	@FXML
	public void initProfilelink() {
		profilelink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("userProfile");
			event.consume();
	        });
	}
	
	@FXML
	public void initHomelink() {
		homelink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("home");
			event.consume();
	        });
	}
	
	
}
