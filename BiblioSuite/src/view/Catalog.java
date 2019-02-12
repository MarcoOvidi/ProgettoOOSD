package view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;

import controller.HomePageController;
import controller.LocalSession;
import controller.PageViewController;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import vo.UUIDDocument;
import vo.UUIDDocumentCollection;
import vo.view.DocumentRow;

public class Catalog extends Application{
	@FXML
	private Accordion documentCollections;
	
	@FXML
	private AnchorPane categoriesPane;

	@FXML
	private JFXButton catalogo;
	
	@FXML
	public void initialize() {
		initCategoriesPane();
		loadCollections();
		catalog();
	}
	
	@FXML
	public void initCategoriesPane() {
		GridPane gridPane = new GridPane();
		
		HomePageController.loadCategories();
		
		
		HashMap<UUIDDocumentCollection, String> categories = HomePageController.getCategories();
		Set<Entry<UUIDDocumentCollection, String>> set = categories.entrySet();
		Iterator<Entry<UUIDDocumentCollection, String>> itr = set.iterator();
		
		/*
		int column = 0;
		int row=0;
		int pos=0;
		for (Entry<UUIDDocumentCollection, String> title : HomePageController.getCategories().entrySet()) {
			switch (pos) {
			case 0:
				Button button = new Button(title.getValue());
				gridPane.add(button, column, row);
				column++;
				pos++;
				break;
			case 1:
				Button button1 = new Button(title.getValue());
				gridPane.add(button1, column, row);
				column++;
				pos++;
				break;
			case 2:
				Button button2 = new Button(title.getValue());
				gridPane.add(button2, column, row);
				column++;
				pos++;
				break;
			case 3:
				Button button3 = new Button(title.getValue());
				gridPane.add(button3, column, row);
				column=0;
				row++;
				pos=0;
				break;
			default:
				break;
			}
		}*/
		
		
		int nr=0;
		if(categories.size()%4!=0)
			nr=1;
		
		for(int i=0; i<categories.size()/4+nr; i++) {
			for(int j=0; j<4; j++) {
				if (!itr.hasNext())
					break;
				ImageView img = new ImageView(new Image("file:resources/tails/tail"+(((i%3)+1)*4+j+1)+".png"));
				StackPane stackPane = new StackPane();
				img.maxHeight(100);
				img.maxHeight(100);
				//img.minHeight(200);
				//img.maxWidth(200);
				stackPane.setMaxWidth(100);
				stackPane.setMaxHeight(100);
				stackPane.getChildren().add(img);
				Label label = new Label(itr.next().getValue());
				//button.setGraphic(img);
				stackPane.getChildren().add(label);
				gridPane.add(stackPane, j, i);
			}
			if (!itr.hasNext())
				break;
		}
		
		categoriesPane.getChildren().add(gridPane);

		
	}

	
		
	@FXML
	public void loadCollections() {
		// carico le collezioni
		HomePageController.loadCategories();

		for (Entry<UUIDDocumentCollection, String> title : HomePageController.getCategories().entrySet()) {
			// creo il nome della collezione
			TitledPane tp = new TitledPane();
			System.out.println(title.getValue());
			// imposto il titolo della collezione
			tp.setText(title.getValue());
			// carico i documenti della collezione
			HomePageController.loadDocumentInCollections(title.getKey());
			// creo il contenitore per i documenti
			ListView<DocumentRow> lista = new ListView<DocumentRow>();
			ObservableList<DocumentRow> oss = FXCollections.observableArrayList();
			// imposto la visualizzazione della cella
			lista.setCellFactory(lv -> new ListCell<DocumentRow>() {
				@Override
				protected void updateItem(DocumentRow item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty || item == null ? "" : item.getDocument());
				}
			});

			lista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DocumentRow>() {

				@Override
				public void changed(ObservableValue<? extends DocumentRow> observable, DocumentRow oldValue,
						DocumentRow newValue) {

					try {
						PageViewController.showDocument(newValue.getId());
					} catch (LoadException e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}

				}

			});
			/*
			 * leaderListView.getSelectionModel().selectedItemProperty() .addListener(new
			 * ChangeListener<Person>() { public void changed(ObservableValue<? extends
			 * Person> observable, Person oldValue, Person newValue) {
			 * System.out.println("selection changed"); } });
			 */

			for (String[] document : HomePageController.getListDocumentInCollections()) {

				oss.add(new DocumentRow(document[1], new UUIDDocument(Integer.parseInt(document[0]))));

			}
			// aggiungo la lista di documenti al listview
			lista.setItems(oss);
			// inserisco la listview nel pane
			tp.setContent(lista);
			// aggiungo il pane nell'accordion
			documentCollections.getPanes().add(tp);
		}
	}
	
	public void start(Stage stage) throws Exception {
		//HostServices hs = getHostServices();
		//String folder = hs.resolveURI(hs.getDocumentBase(), "imgs/animals/");

		//int[] index = { 0 };
		
		HashMap<UUIDDocument, String[]> map = HomePageController.getAllDocuments();

		Unit[] images = new Unit[map.size()];
		
		int i=0;
		for (Map.Entry<UUIDDocument, String[]> entry : map.entrySet()) {
			//create unit
			Image image = LocalSession.loadImage(entry.getValue()[1]);
			images[i] = new Unit(image,i,entry.getKey());
			
			//event handler
			images[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
							try {
								PageViewController.showDocument(entry.getKey());
							} catch (LoadException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			});
			
			//increment counter
			i++;
		}
				
				/*Stream.of(
				new File(
						new URI(folder).getPath()
						).list()
				).map(
						name -> hs.resolveURI(folder, name)
						).map(
								url -> new Unit(url, index[0]++)
								).toArray(Unit[]::new);
								*/

//		for (int i = 0; i < images.length; i++) {
//			images[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
//				@Override
//				public void handle(MouseEvent mouseEvent) {
//					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//						if (mouseEvent.getClickCount() == 2) {
//							System.out.println("Double clicked");
//						}
//					}
//				}
//			});
//		}

		Group container = new Group();
		container.setStyle("-fx-background-color:derive(black, 20%)");
		container.getChildren().addAll(images);

		Slider slider = new Slider(0, images.length - 1, 0);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setBlockIncrement(1);
		slider.setSnapToTicks(true);

		container.getChildren().add(slider);

		Scene scene = new Scene(container, 1000, 400, true);
		scene.setFill(Color.rgb(33, 33, 33));

		stage.setScene(scene);
		stage.getScene().setCamera(new PerspectiveCamera());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();

		slider.translateXProperty().bind(stage.widthProperty().divide(2).subtract(slider.widthProperty().divide(2)));
		slider.setTranslateY(10);

		// FxTransformer.sliders(new DoubleProperty[] {x, z, rotation}, new String[]
		// {"x", "z", "rotation"}, stage, -360, 360).show();
		// new FxTransformer(images, IntStream.range(0, images.length).mapToObj(i ->
		// "images["+i+"]").toArray(String[]::new), stage, -500, 1000).show();

		slider.valueProperty().addListener((p, o, n) -> {
			if (n.doubleValue() == n.intValue())
				Stream.of(images).forEach(u -> u.update(n.intValue(), stage.getWidth(), stage.getHeight()));
		});

		container.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if (e.getTarget() instanceof Unit)
				slider.setValue(((Unit) e.getTarget()).index);
		});

		Button close = new Button("X");
		close.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 1) {
						stage.close();
					}
				}
			}
		});

		// close.setOnAction(e -> System.exit(0));
		close.getStyleClass().clear();
		close.setStyle("-fx-text-fill:white;-fx-font-size:15;-fx-font-weight:bold;-fx-font-family:'Comic Sans MS';");

		container.getChildren().add(close);
		close.translateXProperty().bind(stage.widthProperty().subtract(15));

		slider.setValue(5);
	}

	private static class Unit extends ImageView {
		final static Reflection reflection = new Reflection();
		final static Point3D rotationAxis = new Point3D(0, 90, 1);

		static {
			reflection.setFraction(0.5);
		}

		@SuppressWarnings("unused")
		final UUIDDocument id;
		final int index;
		final Rotate rotate = new Rotate(0, rotationAxis);
		final TranslateTransition transition = new TranslateTransition(Duration.millis(300), this);

		public Unit(Image image, int index, UUIDDocument id) {
			//super(imageUrl);
			setEffect(reflection);
			setUserData(index);
			
			setImage(image);
			
			setFitHeight(200);
			setFitWidth(100);
			
			this.id = id;
			this.index = index;
			getTransforms().add(rotate);
		}

		public void update(int currentIndex, double width, double height) {
			int ef = index - currentIndex;
			double middle = width / 2 - 100;
			boolean b = ef < 0;

			setTranslateY(height / 2 - getImage().getHeight() / 2);
			double x, z, theta, pivot;

			if (ef == 0) {
				z = -300;
				x = middle;
				theta = 0;
				pivot = b ? 200 : 0;
			} else {
				x = middle + ef * 82 + (b ? -147 : 147);
				z = -78.588;
				pivot = b ? 200 : 0;
				theta = b ? 46 : -46;
			}
			rotate.setPivotX(pivot);
			rotate.setAngle(theta);

			transition.pause();
			transition.setToX(x);
			transition.setToZ(z);
			transition.play();
		}

	}
	
	public void catalog() {
		catalogo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			try {
				start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			event.consume();
		});
	}

	
	
}
