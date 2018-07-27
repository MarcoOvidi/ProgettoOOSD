package view;


import java.beans.EventHandler;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ShowDocument {
	
	@FXML
	private VBox pageList;
	
	@FXML
	private VBox scanList;
	
	@FXML
	private VBox transcriptionList;

	@FXML
	private ScrollPane scanListScroll;
	
	@FXML
	public void initialize() {
		loadPageList();
/*   
	    pageIcon=new Image("http://www.savonanews.it/typo3temp/pics/M_8804605c2f.jpg");
	    for(int i=1; i<=100; i++) {
			HBox hbox= new HBox();
			hbox.setStyle("-fx-background: #333;");
			hbox.setPadding(new Insets(0));
			hbox.setPadding(new Insets(20));
			ImageView miniature = new ImageView(pageIcon);
			miniature.setFitWidth(600);
			miniature.setFitHeight(800);
			hbox.getChildren().add(miniature);
	       scanList.getChildren().add(hbox);
	    }
	*/    

	}
	

	public void loadPageList(){
        Image pageIcon=new Image("http://www.clker.com/cliparts/3/6/2/6/1348002494474708155New%20Page%20Icon.svg.med.png");
        //Carico le miniature nella pageList
	    for(int i=1; i<=100; i++) {
			VBox hbox= new VBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setPadding(new Insets(20));
			hbox.focusedProperty().addListener((ov, oldV, newV) -> {
			      if (!newV) { // focus lost
		              // Your code 
			    		hbox.setStyle("-fx-background: #444;");
		           }
		        });
			ImageView miniature = new ImageView(pageIcon);
			miniature.setFitWidth(100);
			miniature.setFitHeight(140);
			hbox.getChildren().add(miniature);
			hbox.getChildren().add(new Label(""+i));
			pageList.getChildren().add(hbox);
			hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				Node nodeOut = hbox.getChildren().get(1);
		        if(nodeOut instanceof Label){
		        			String p=((Label) nodeOut).getText();
		    				loadPage(p);
		    				System.out.println(scanListScroll.getViewportBounds().getHeight());
		           }
		        else {System.out.println("Not");}
		        event.consume();
		        
		        });
	        
	    }
	}



	@FXML
	public void loadPage(String p) {
		if(p instanceof String) {
			Double page=Double.parseDouble(p);
			scanList.getChildren().add(null);
		//scanListScroll.setVvalue((page-1)*0.0101010101010);
		}
	}
	
	

}
