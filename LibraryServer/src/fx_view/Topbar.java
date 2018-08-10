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
	
	@FXML
	private Button loadScanLink;
	
	
	public void initialize() {
		initProfileLink();
		initHomeLink();
		initNewDocumentLink();
		initLoadScanLink();
	}
	
	//inizializzo il bottone gotoprofile 
	@FXML
	public void initProfileLink() {
		profileLink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("userProfile");
			event.consume();
	        });
	}
	
	@FXML
	public void initHomeLink() {
		homeLink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("home");
			event.consume();
	        });
	}
	
	@FXML
	public void initNewDocumentLink() {
		newDocumentLink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("newDocument");
			event.consume();
	        });
	}
	
	@FXML
	public void initLoadScanLink() {
		newDocumentLink.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("loadScan");
			event.consume();
	        });
	}
	
}
