package view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;

import controller.HomePageController;
import controller.LocalSessionBridge;
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
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import vo.UUIDDocument;
import vo.UUIDDocumentCollection;
import vo.view.DocumentRow;

public class Catalog extends Application {
	@FXML
	private Pane categoriesPane;

	@FXML
	private JFXButton catalogo;

	@FXML
	GridPane gridPane = new GridPane();

	@FXML
	public void initialize() {
		initCategoriesPane();
		loadCollections();
		catalog();
	}

	@FXML
	public void initCategoriesPane() {
		gridPane.getChildren().clear();
		HomePageController.loadCategories();

		HashMap<UUIDDocumentCollection, String> categories = HomePageController.getCategories();
		Set<Entry<UUIDDocumentCollection, String>> set = categories.entrySet();
		Iterator<Entry<UUIDDocumentCollection, String>> itr = set.iterator();

		/*
		 * int column = 0; int row=0; int pos=0; for (Entry<UUIDDocumentCollection,
		 * String> title : HomePageController.getCategories().entrySet()) { switch (pos)
		 * { case 0: Button button = new Button(title.getValue()); gridPane.add(button,
		 * column, row); column++; pos++; break; case 1: Button button1 = new
		 * Button(title.getValue()); gridPane.add(button1, column, row); column++;
		 * pos++; break; case 2: Button button2 = new Button(title.getValue());
		 * gridPane.add(button2, column, row); column++; pos++; break; case 3: Button
		 * button3 = new Button(title.getValue()); gridPane.add(button3, column, row);
		 * column=0; row++; pos=0; break; default: break; } }
		 */

		LinkedList<String> colors = new LinkedList<String>();

		colors.add("orange");
		colors.add("green");
		colors.add("violet");
		colors.add("red");
		colors.add("yellow");
		colors.add("pink");
		colors.add("brown");

		System.out.println("size " + categories.size());
		int nr = 0;
		if (categories.size() % 4 != 0)
			nr = 1;

		Iterator<String> colorsIterator = colors.iterator();

		// ScaleTransition transition = new ScaleTransition();
		Glow glow = new Glow();
		glow.setLevel(0.7);
		
		
		
		for (int i = 0; i < categories.size() / 4 + nr; i++) {
			for (int j = 0; j < 4; j++) {
				
				Map.Entry<UUIDDocumentCollection, String> currentCat = itr.next();

				if (!itr.hasNext())
					break;
				ImageView img = new ImageView(
						new Image("file:resources/tails/tail" + (((4 * i + j + 12) % 12) + 1) + ".png"));
				StackPane stackPane = new StackPane();
				img.setFitHeight(200);
				img.setFitWidth(200);
				// img.minHeight(200);
				// img.maxWidth(200);
				img.setStyle("-fx-opacity: 0.5;");

				StackPane pane = new StackPane();
				Label label = new Label(currentCat.getValue().toUpperCase());
				label.setStyle("-fx-text-fill:white; -fx-font-size: 16; -fx-font-weight: bold;");
				if (!colorsIterator.hasNext()) {
					colorsIterator = null;
					colorsIterator = colors.iterator();
				}

				StackPane layer1 = new StackPane();
				StackPane layer2 = new StackPane();
				StackPane layer3 = new StackPane();

				String currColor = colorsIterator.next();
				layer1.setStyle("-fx-opacity:0.4; -fx-pref-width:200px; -fx-pref-height:200px; -fx-background-color:"
						+ currColor + ";");
				layer2.setStyle("-fx-opacity:0.5; -fx-background-color:#555;");
				layer3.setStyle(
						"-fx-opacity:1; -fx-background-color:transparent; -fx-border-weight: 1px; -fx-border-color: white;");

				stackPane.getChildren().add(img);
				pane.getChildren().add(layer1);
				pane.getChildren().add(layer2);
				pane.getChildren().add(layer3);
				pane.getChildren().add(label);
				// button.setGraphic(img);
				stackPane.getChildren().add(pane);
				stackPane.setStyle("-fx-pref-width:100px; -fx-pref-height:100px");


				stackPane.setOnMouseEntered(event -> {
					label.setEffect(glow);
					stackPane.setEffect(glow);
					/*
					 * transition.setDuration(Duration.millis(200)); transition.setNode(stackPane);
					 * transition.setByX(0.04); transition.setByY(0.04);
					 * transition.setAutoReverse(true);
					 * 
					 * transition.play();
					 */
					// transition.playFrom(Duration.millis(200));
				});
				stackPane.setOnMouseExited(event -> {
					label.setEffect(null);
					stackPane.setEffect(null);
					// transition.setDuration(Duration.millis(200));
					// transition.setDelay(Duration.millis(100));
					// transition.setNode(stackPane);
					// transition.setByX(-0.03846153846154);
					// transition.setByY(-0.03846153846154);

					// transition.play();

					// transition.playFrom(Duration.millis(200));
				});

				stackPane.setOnMouseClicked(event -> {
					caricaCategoria(currentCat.getKey(), currColor, glow);
				});

				gridPane.add(stackPane, j, i);
				GridPane.setMargin(stackPane, new Insets(8, 8, 8, 8));

			}

			if (!itr.hasNext())
				break;
		}

		categoriesPane.getChildren().add(gridPane);

	}

	public void caricaCategoria(UUIDDocumentCollection currentCollection, String color, Glow glow) {
		HomePageController.loadDocumentInCollections(currentCollection);

		LinkedList<String[]> documentOfCurrentCollection = HomePageController.getListDocumentInCollections();
		String[] array = new String[2];
		array[0] = "";
		array[1] = "indietro";

		documentOfCurrentCollection.addFirst(array);
		gridPane.getChildren().clear();

		Iterator<String[]> itr = documentOfCurrentCollection.iterator();

		// ____________

		int nr = 0;
		if (documentOfCurrentCollection.size() % 4 != 0)
			nr = 1;

		for (int i = 0; i < documentOfCurrentCollection.size() / 4 + nr; i++) {
			for (int j = 0; j < 4; j++) {

				if (!itr.hasNext())
					break;

				String[] currentCat = itr.next();

				ImageView img = new ImageView(
						new Image("file:resources/tails/tail" + (((4 * i + j + 12) % 12) + 1) + ".png"));
				StackPane stackPane = new StackPane();
				img.setFitHeight(200);
				img.setFitWidth(200);
				// img.minHeight(200);
				// img.maxWidth(200);
				img.setStyle("-fx-opacity: 0.5;");

				StackPane pane = new StackPane();

				Label label = new Label();

				if (!currentCat[1].equalsIgnoreCase("indietro")) {
					label.setText(currentCat[1].toUpperCase());
				}

				label.setStyle("-fx-text-fill:white; -fx-font-size: 16; -fx-font-weight: bold;");

				StackPane layer1 = new StackPane();
				StackPane layer2 = new StackPane();
				StackPane layer3 = new StackPane();

				layer1.setStyle("-fx-opacity:0.4; -fx-pref-width:200px; -fx-pref-height:200px; -fx-background-color:"
						+ color + ";");
				layer2.setStyle("-fx-opacity:0.5; -fx-background-color:#555;");
				layer3.setStyle(
						"-fx-opacity:1; -fx-background-color:transparent; -fx-border-weight: 1px; -fx-border-color: white;");

				stackPane.getChildren().add(img);
				pane.getChildren().add(layer1);
				pane.getChildren().add(layer2);
				pane.getChildren().add(layer3);
				if (currentCat[1].equalsIgnoreCase("indietro")) {
					ImageView img1 = new ImageView(new Image("file:resources/favicon/128/angle-left.png"));
					pane.getChildren().add(img1);
				}
				pane.getChildren().add(label);
				// button.setGraphic(img);
				stackPane.getChildren().add(pane);
				stackPane.setStyle("-fx-pref-width:100px; -fx-pref-height:100px");

				stackPane.setOnMouseEntered(event -> {
					label.setEffect(glow);
					stackPane.setEffect(glow);
					/*
					 * transition.setDuration(Duration.millis(200)); transition.setNode(stackPane);
					 * transition.setByX(0.04); transition.setByY(0.04);
					 * transition.setAutoReverse(true);
					 * 
					 * transition.play();
					 */
					// transition.playFrom(Duration.millis(200));
				});
				stackPane.setOnMouseExited(event -> {
					label.setEffect(null);
					stackPane.setEffect(null);
					// transition.setDuration(Duration.millis(200));
					// transition.setDelay(Duration.millis(100));
					// transition.setNode(stackPane);
					// transition.setByX(-0.03846153846154);
					// transition.setByY(-0.03846153846154);

					// transition.play();

					// transition.playFrom(Duration.millis(200));
				});

				if (!currentCat[1].equalsIgnoreCase("indietro")) {

					stackPane.setOnMouseClicked(event -> {

						try {
							PageViewController.showDocument(new UUIDDocument(Integer.parseInt(currentCat[0])));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (LoadException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					});
				}else {
					stackPane.setOnMouseClicked(event -> {
						
						initCategoriesPane();

					});
				}
				
				

				gridPane.add(stackPane, j, i);
				GridPane.setMargin(stackPane, new Insets(8, 8, 8, 8));

			}

			if (!itr.hasNext())
				break;
		}

		// categoriesPane.getChildren().add(gridPane);
		// ____________

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
		}
	}

	public void start(Stage stage) throws Exception {
		// HostServices hs = getHostServices();
		// String folder = hs.resolveURI(hs.getDocumentBase(), "imgs/animals/");

		// int[] index = { 0 };

		HashMap<UUIDDocument, String[]> map = HomePageController.getAllDocuments();

		Unit[] images = new Unit[map.size()];

		int i = 0;
		for (Map.Entry<UUIDDocument, String[]> entry : map.entrySet()) {
			// create unit
			Image image = LocalSessionBridge.loadImage(entry.getValue()[1]);
			images[i] = new Unit(image, i, entry.getKey());

			// event handler
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

			// increment counter
			i++;
		}

		/*
		 * Stream.of( new File( new URI(folder).getPath() ).list() ).map( name ->
		 * hs.resolveURI(folder, name) ).map( url -> new Unit(url, index[0]++)
		 * ).toArray(Unit[]::new);
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
			// super(imageUrl);
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
