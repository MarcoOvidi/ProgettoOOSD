package view;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.TreeMap;

import javax.swing.text.AbstractDocument.Content;

import controller.EditUserController;
import controller.LocalSession;
import controller.LoginController;
import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.util.Pair;

public class Container {

	@FXML
	private static BorderPane newContent;

	@FXML
	private TabPane topbar;
	
	@FXML
	private BorderPane content;
	
	@FXML
	private Tab logout;

	private static TreeMap<String, Pair<String,String>> map = new TreeMap<String, Pair<String,String>>();

	static {
		map.put("Home", new Pair<String,String>("home","home"));
		map.put("My Profile", new Pair<String,String>("userProfile","user-circle"));
		map.put("Manage Projects", new Pair<String,String>("manageProject","group"));
		map.put("Upload", new Pair<String,String>("loadScan","image"));
		map.put("Transcription", new Pair<String,String>("transcription","file-text"));
		map.put("Review", new Pair<String,String>("scanRevisor","edit"));
		map.put("Admin", new Pair<String,String>("adminPanel","briefcase"));
	}
	
	private String genFavIconURL(String str) {
		return ("file:resources/favicon/32/" + str + ".png");
	}

	public void initButtonLink(String label, String link) {

		javafx.scene.image.Image image = new javafx.scene.image.Image(genFavIconURL(map.get(label).getValue()));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(32);
		imageView.setFitWidth(32);
		Tooltip tooltip = new Tooltip(label);
		tooltip.setFont(new Font(14));
		
		Tab tab = new Tab();
		tab.setGraphic(imageView);
		tab.setTooltip(tooltip);
		
		topbar.getTabs().add(tab);
		
		if (link.equals(SceneController.getSceneName())) {
			topbar.getSelectionModel().select(tab);
			//tab.setText(tab.getTooltip().getText());
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
		

		logout.getTabPane().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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
			initButtonLink(str, map.get(str).getKey());
		}

		topbar.setOnMouseClicked(event -> {
			SceneController.loadScene(map.get(topbar.getSelectionModel().getSelectedItem().getTooltip().getText()).getKey());
		});
		
		//topbar.setTabMinWidth(100);
		
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
		//content.getChildren().get(0).setEffect(new BoxBlur());
	}
	
}