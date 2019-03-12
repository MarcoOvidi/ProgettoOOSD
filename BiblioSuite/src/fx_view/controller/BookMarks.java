package fx_view.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jfoenix.controls.JFXButton;

import controller.HomePageController;
import controller.PageViewController;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import vo.UUIDDocument;

public class BookMarks {
	@FXML
	private Pane categoriesPane;

	@FXML
	private JFXButton catalogo;

	@FXML
	GridPane gridPane = new GridPane();

	@FXML
	public void initialize() {
		initCategoriesPane();
	}

	@FXML
	public void initCategoriesPane() {
		gridPane.getChildren().clear();
	
	

		HashMap<UUIDDocument, String> categories = HomePageController.getMyPersonalCollection();
		Set<Entry<UUIDDocument, String>> set = categories.entrySet();
		Iterator<Entry<UUIDDocument, String>> itr = set.iterator();
		
		System.out.println(categories);

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
				

				if (!itr.hasNext())
					break;
				Map.Entry<UUIDDocument, String> currentCat = itr.next();
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
				label.setWrapText(true);
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
					try {
						PageViewController.showDocument(currentCat.getKey());
					} catch (LoadException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				});

				gridPane.add(stackPane, j, i);
				GridPane.setMargin(stackPane, new Insets(8, 8, 8, 8));

			}

			if (!itr.hasNext())
				break;
		}

		categoriesPane.getChildren().add(gridPane);

	}

}
