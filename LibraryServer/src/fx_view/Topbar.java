package fx_view;

import java.util.List;
import java.util.TreeMap;

import controller.LocalSession;
import controller.LoginController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

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
	private TabPane container;

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

		// container.setSpacing(5);
		List<String> buttons = LocalSession.getTopBarButtons();

		for (String str : buttons) {
			initButtonLink(str, map.get(str));
		}
		

		container.setOnMouseClicked(event -> {
			SceneController.loadScene(map.get(container.getSelectionModel().getSelectedItem().getText()));
		});

		initLogoutLink();

	}

	// inizializzo il bottone gotoprofile
	public void initButtonLink(String label, String link) {
		Tab tab = new Tab(label);
		container.getTabs().add(tab);
		if (link.equals(SceneController.getSceneName())) {
			container.getSelectionModel().select(tab);
		}
			
	}

	// inizializzo il bottone gotoprofile
	public void initLogoutLink() {

		logout.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

			// SceneController.loadScene("login");
			LoginController.logout();
			event.consume();
		});
	}

}
