


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class SceneController extends Application {
	
	private static Scene scene;
	private static Stage stage;

	
	@Override
	public void start(Stage primaryStage) {
		stage=primaryStage;
		//scene = new Scene(root,1400,800);
		
		loadScene("login");
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
 

	
	public static void loadScene(String name) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/view/"+name+".fxml"));
			
			scene = new Scene(root,1400,800);
			//scene.getStylesheets().add(new RootCss.getCss());

			scene.setRoot(root);
			stage.setScene(scene);
			//scene.getStylesheets().add(new Object().getClass().getResource("home.css").toExternalForm());
			stage.setResizable(false);
			//stage.initStyle(StageStyle.UNDECORATED);
			stage.show();			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Stage getStage() {
		return stage;
	}

}




