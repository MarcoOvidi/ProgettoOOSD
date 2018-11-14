package fx_view;

import java.util.List;
import java.util.TreeMap;

import controller.LocalSession;
import controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Topbar {
	/*
	 * @FXML private Button profileLink;
	 * 
	 * @FXML private Button homeLink;
	 * 
	 * @FXML private Button newDocumentLink;
	 * 
	 * @FXML private Button loadScanLink;
	 */

	@FXML
	private HBox container;
	
	@FXML
	private Button logout;

	private static TreeMap<String, String> map = new TreeMap<String, String>();

	static {
		map.put("Home", "home");
		map.put("My Profile", "userProfile");
		map.put("Manage Projects", "manageProject");
		map.put("Upload", "loadScan");
		map.put("Transcription", "");
		map.put("Review", "scanRevisor");
	}

	public void initialize() {
		
		container.setSpacing(5);
		List<String> buttons = LocalSession.getTopBarButtons();

		for (String str : buttons) {
			initButtonLink(str, map.get(str));
		}

		initLogoutLink();

	}

	// inizializzo il bottone gotoprofile
	public void initButtonLink(String label, String link) {
		Button button = new Button(label);
		container.getChildren().add(button);
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene(link);
			event.consume();
		});
	}
	
	// inizializzo il bottone gotoprofile
	public void initLogoutLink() {
		
		
		logout.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
		
			
			//SceneController.loadScene("login");
			LoginController.logout();
			event.consume();
		});
	}

}
