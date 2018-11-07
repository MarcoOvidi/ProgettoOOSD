package fx_view;

import java.util.List;
import java.util.TreeMap;

import controller.LocalSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

	private static TreeMap<String, String> map = new TreeMap<String, String>();

	static {
		map.put("Home", "home");
		map.put("MyProfile", "userProfile");
		map.put("Manage Projects", "manageProject");
		map.put("Upload", "loadScan");
		map.put("Transcription", "");
		map.put("Review", "");
	}

	public void initialize() {

		
		container.setSpacing(5);
		List<String> buttons = LocalSession.getTopBarButtons();

		for (String str : buttons) {
			initButtonLink(str, map.get(str));
		}

	}

	// inizializzo il bottone gotoprofile
	@FXML
	public void initButtonLink(String label, String link) {
		Button button = new Button(label);
		container.getChildren().add(button);
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene(link);
			event.consume();
		});
	}

}
