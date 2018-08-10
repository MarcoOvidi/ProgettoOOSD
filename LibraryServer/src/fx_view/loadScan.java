package fx_view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class loadScan {
	
	@FXML
	private Button filechooser;
	
	@FXML
	private VBox scanList;
	
	
	@FXML
	public void initialize(){
		filechooser.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			FileChooser fileChooser = new FileChooser();
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png","*.jpg");
	        fileChooser.getExtensionFilters().add(extFilter);
	        File file = fileChooser.showOpenDialog(new Stage());
	        
	        
	        
	        
			VBox hbox= new VBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setPadding(new Insets(40));
			hbox.focusedProperty().addListener((ov, oldV, newV) -> {
			      if (!newV) { // focus lost
		              // Your code 
			    		hbox.setStyle("-fx-background: #444;");
		           }
		        });

	        Image pic = new Image(file.toURI().toString());
	        ImageView newscan= new ImageView(pic);
	        newscan.setFitWidth(600);
	        newscan.setFitHeight(350);

			hbox.getChildren().add(newscan);
			hbox.getChildren().add(new TextField("1"));
	        scanList.getChildren().add(hbox);
	        
	        event.consume();
	        });
	}
	
	
	@FXML
	public void fileShoser() {

		
	};
}
