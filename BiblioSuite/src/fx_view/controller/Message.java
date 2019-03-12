package fx_view.controller;

import controller.AdministrationController;
import controller.LocalSessionBridge;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Message {

	@FXML
	TextField object;
	@FXML
	TextArea messageText;
	@FXML
	Button send;
	
	@FXML
	public void initialize() {
		object.setOnAction(event -> {
			messageText.requestFocus();
		});
		send.setOnAction(event -> {
			if(object.getText().length() < 5) {
				
			}
			if(messageText.getText().length() < 5) {
				
			}
				
			
			AdministrationController.sendRequest(LocalSessionBridge.getLocalUser().getID(), 
					object.getText(), messageText.getText());
		});
	}
	

}
