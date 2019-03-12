package fx_view.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;

import controller.HomePageController;
import controller.PageViewController;
import dao.concrete.DatabaseException;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vo.UUIDDocument;

public class News {
	
	
	@FXML
	VBox news;
	
	@FXML
	public void initialize() {
		try {
			loadNews();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void loadNews() throws DatabaseException{
		try {
			try {
				HomePageController.loadNews();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HashMap<UUIDDocument, String[]> newsMap = HomePageController.getNews();
			int c = 0;

			for (Entry<UUIDDocument, String[]> entry : newsMap.entrySet()) {

				Label title1 = new Label(entry.getValue()[0]);
				title1.setPrefWidth(150);
				Label title2 = new Label("( " + entry.getValue()[1] + " giorni fa )");
				title2.setPrefWidth(150);
				ImageView title3;
				if (entry.getValue()[2].equals("sn")) {
					title3 = new ImageView(new Image("file://resources/favicon/32/document-scanning.png"));
				} else {
					title3 = new ImageView(new Image("file://resources/favicon/32/xml.png"));
				}

				String s = title1.getText();
				char first = Character.toUpperCase(s.charAt(0));
				String s1 = first + s.substring(1);
				title1.setText(s1);

				HBox row = new HBox();

				if (c % 2 == 0)
					row.getStyleClass().add("title-row");
				else
					row.getStyleClass().add("title-row1");
				row.getChildren().add(title1);
				row.getChildren().add(title2);
				row.getChildren().add(title3);

				row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					try {
						PageViewController.showDocument(entry.getKey());
					} catch (LoadException e) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText(e.getMessage());
						alert.show();
						e.printStackTrace();
					}
					event.consume();
				});

				news.getChildren().add(row);
				c++;
			}
		} catch (DatabaseException e) {
			Label label = new Label(e.getMessage());
			news.getChildren().add(label);

		}
	}
}
