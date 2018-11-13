package fx_view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneController extends Application {
	
	private static Scene scene;
	private static Stage stage;
	
	private static Scene previousScene;

	
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
			previousScene = scene;
			BorderPane root = (BorderPane)FXMLLoader.load(new Object(){}.getClass().getResource("/fx_view/"+name+".fxml"));
			
			scene = new Scene(root,1400,800);
			scene.getStylesheets().add(new RootCss().getCss());

			scene.setRoot(root);
			stage.setScene(scene);

			//scene.getStylesheets().add(new Object().getClass().getResource("home.css").toExternalForm());
			stage.setResizable(true);

			//stage.setFullScreen(true);
			
			stage.setMinHeight(480);
			stage.setMinWidth(720);
			
			stage.setTitle("Library - " + name);
			
			//stage.initStyle(StageStyle.UNDECORATED);
			stage.show();			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void loadPreviousScene() {

		scene = previousScene;
		stage.setScene(previousScene);

		//scene.getStylesheets().add(new Object().getClass().getResource("home.css").toExternalForm());
		stage.setResizable(true);

		//stage.setFullScreen(true);
		
		stage.setMinHeight(480);
		stage.setMinWidth(720);
		
		stage.setTitle("Library - previous");
		
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.show();			

	}
	
	public static Stage getStage() {
		return stage;
	}

}




