package fx_view.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneController extends Application {

	private static Scene scene;
	private static Stage stage;
	private static String sceneName;
	
	private static String previousSceneName;
	private static Scene previousScene;
	
	private static boolean loaded = false;
	
	private static FXMLLoader fxmlLoader;
	private static Container container;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;

		// scene = new Scene(root,1400,800);
		// loadScene("login");
		loadLogin();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void loadScene(String name) {
		loadScene(name, true);
	}

	public static void loadScene(String name, boolean showTopBar) {
		previousScene = scene;
		previousSceneName = sceneName; 
		sceneName = name;
		if (showTopBar) {
			try {
				// BorderPane root = (BorderPane)FXMLLoader.load(new
				// Object(){}.getClass().getResource("/fx_view/"+name+".fxml"));
				
				
				
				Container.loadScene(name);
				
				
				if(!loaded) {
					fxmlLoader = new FXMLLoader(new Object() {
					}.getClass().getResource("/fx_view/container.fxml"));
					BorderPane root = (BorderPane) fxmlLoader.load();
					container = (Container) fxmlLoader.getController();
				loaded=true;

				// scene = new Scene(root,1400,800);
				scene = new Scene(root);

				scene.setRoot(root);
				stage.setScene(scene);
				}
				else {
					container.partialInit();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				loaded=false;
				BorderPane root = (BorderPane)FXMLLoader.load(new
				Object(){}.getClass().getResource("/fx_view/"+name+".fxml"));
				//Container.loadScene(name);
				//BorderPane root = (BorderPane) FXMLLoader.load(new Object() {
				//}.getClass().getResource("/fx_view/container.fxml"));

				// scene = new Scene(root,1400,800);
				scene = new Scene(root);

				scene.setRoot(root);
				stage.setScene(scene);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		scene.getStylesheets().add(new RootCss().getCss());
		stage.getIcons().add(new Image("file:resources/icon.png"));
		System.out.println(stage.getIcons().get(0));
		stage.setResizable(true);

		// stage.setFullScreen(true);

		stage.setMinHeight(720);
		stage.setMinWidth(1280);

		stage.setTitle("Library - " + name);

		// stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		
		


	}

	public static void loadLogin() {
		loadScene("login", false);
		/*try {00
			previousScene = scene;
			BorderPane root = (BorderPane) FXMLLoader.load(new Object() {
			}.getClass().getResource("/fx_view/login.fxml"));

			scene = new Scene(root, 1400, 800);
			scene.getStylesheets().add(new RootCss().getCss());

			scene.setRoot(root);
			stage.setScene(scene);

			// scene.getStylesheets().add(new
			// Object().getClass().getResource("home.css").toExternalForm());
			stage.setResizable(true);

			// stage.setFullScreen(true);

			stage.setMinHeight(480);
			stage.setMinWidth(720);

			stage.setTitle("Library - login");

			// stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	public static void loadPreviousScene() {

		Container.loadScene(previousSceneName);
		container.partialInit();
		sceneName = previousSceneName; 

		scene = previousScene;
		stage.setScene(previousScene);

		// scene.getStylesheets().add(new
		// Object().getClass().getResource("home.css").toExternalForm());
		stage.setResizable(true);

		// stage.setFullScreen(true);

		stage.setMinHeight(560);
		stage.setMinWidth(900);

		stage.setTitle("Library - previous");

		// stage.initStyle(StageStyle.UNDECORATED);
		stage.show();

	}

	public static Stage getStage() {
		return stage;
	}

	public static String getSceneName() {
		return sceneName;
	}

}
