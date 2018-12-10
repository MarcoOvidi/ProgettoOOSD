package view;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.TreeMap;

import controller.EditUserController;
import controller.LocalSession;
import controller.LoginController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Container {

	@FXML
	private static BorderPane newContent;

	@FXML
	private TabPane topbar;
	
	@FXML
	private BorderPane content;
	
	@FXML
	private Button logout;

	private static TreeMap<String, String> map = new TreeMap<String, String>();

	static {
		map.put("Home", "home2");
		map.put("My Profile", "userProfile");
		map.put("Manage Projects", "manageProject");
		map.put("Upload", "loadScan");
		map.put("Transcription", "");
		map.put("Review", "scanRevisor");
		map.put("Admin", "adminPanel");
	}

	public void initButtonLink(String label, String link) {
		Tab tab = new Tab(label);
		topbar.getTabs().add(tab);
		if (link.equals(SceneController.getSceneName())) {
			topbar.getSelectionModel().select(tab);
		}
		else if(label.equals("My Profile") && SceneController.getSceneName().equals("editUserProfile") &&
				EditUserController.isSelfEditing()) {
				topbar.getSelectionModel().select(tab);
		}
		else if(label.equals("Admin") && SceneController.getSceneName().equals("editUserProfile") &&
				!EditUserController.isSelfEditing()) {
				topbar.getSelectionModel().select(tab);
		}
			
	}

	public void initLogoutLink() {

		logout.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

			// SceneController.loadScene("login");
			LoginController.logout();
			event.consume();
		});
	}

	public static void loadScene(String name) {
			try {
				//newContent = (BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/fx_view/"+name+".fxml"));
				newContent = ((BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/fx_view/"+name+".fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	@FXML
	public void initialize() throws DatabaseException, ParseException {// container.setSpacing(5);
		List<String> buttons = LocalSession.getTopBarButtons();

		for (String str : buttons) {
			initButtonLink(str, map.get(str));
		}
		

		topbar.setOnMouseClicked(event -> {
			SceneController.loadScene(map.get(topbar.getSelectionModel().getSelectedItem().getText()));
		});

		initLogoutLink();
		
		/*for(Tab tab : topbar.getTabs()) {
			try {
				if(tab.getText().equals("Transcription"))
					break;
				tab.setContent((BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/fx_view/"+map.get(tab.getText())+".fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

		//content = newContent;
		content.getChildren().setAll(newContent);
	}
	
}