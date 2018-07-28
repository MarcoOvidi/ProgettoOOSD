package fx_view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Topbar {
	@FXML
	private Button profileLink;
	
	@FXML
	private Button homeLink;
	
	@FXML
	private Button newDocumentLink;
	
	
	public void initialize() {
		initProfilelink();
		initHomelink();
	}
	
	//inizializzo il bottone gotoprofile 
	@FXML
	public void initProfilelink() {
		profileLink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("userProfile");
			event.consume();
	        });
	}
	
	@FXML
	public void initHomelink() {
		homeLink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("home");
			event.consume();
	        });
	}
	
	@FXML
	public void newdocumentlink() {
		newDocumentLink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("newDocumentScene");
			event.consume();
	        });
	}
}
