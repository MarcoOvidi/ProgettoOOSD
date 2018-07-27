

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class loadScan {
	
	@FXML
	private Button filechooser;
	
	
	
	
	@FXML
	public void initialize(){
		filechooser.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			FileChooser fileChooser = new FileChooser();
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	        fileChooser.getExtensionFilters().add(extFilter);
	        File file = fileChooser.showOpenDialog(new Stage());
	         event.consume();
	        
	        });
	}
	
	
	@FXML
	public void fileShoser() {

		
	};
}
