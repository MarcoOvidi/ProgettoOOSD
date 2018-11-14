package fx_view;

import java.io.IOException;
import java.text.ParseException;

import dao.DatabaseException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Container {
	
	private static BorderPane newContent;

	@FXML
	private AnchorPane topbar;
	
	@FXML
	private BorderPane content;
	
	
	public static void loadScene(String name) {
			try {
				newContent = (BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/fx_view/"+name+".fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	@FXML
	public void initialize() throws DatabaseException, ParseException {
		//content = newContent;
		content.getChildren().setAll(newContent);
	}
	
}
