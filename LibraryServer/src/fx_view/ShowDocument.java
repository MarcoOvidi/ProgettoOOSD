package fx_view;

import java.util.LinkedList;

import controller.LocalSession;
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
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import model.Page;

public class ShowDocument {
	private static int currentPage;

	@FXML
	private Button backButton;

	@FXML
	private ScrollPane scrollPage;
	
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
		//backButton = new Button();
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadPreviousScene();
	        event.consume();
	        });
		
		/*scanList.addEventHandler(ScrollEvent.SCROLL, event -> {
			 System.out.println(scanList.getHeight());
			 System.out.println(event.getDeltaY());
			 System.out.println(event.getDeltaY() / scanList.getHeight());
		});*/
		
		scrollPage.vvalueProperty().bind(scanList.heightProperty());
	}
	

	public void loadPageList(){
        LinkedList<Page> pages = LocalSession.getOpenedDocumet().getPageList();
        //System.out.println("1");
	   
        for(Page page : pages) {
        	Image pageIcon = new Image("images/libricino.png");
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
			miniature.setFitWidth(90);
			miniature.setFitHeight(130);
			hbox.getChildren().add(miniature);
			hbox.getChildren().add(new Label(page.getPageNumber().toString()));
			hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				Node nodeOut = hbox.getChildren().get(1);
		        if(nodeOut instanceof Label){
		        			currentPage=Integer.parseInt(((Label) nodeOut).getText());
		    				loadPage();
		    				System.out.println(scanListScroll.getViewportBounds().getHeight());
		           }
		        else {System.out.println("Not");}
		        event.consume();
		        });
			pageList.getChildren().add(hbox);
			
			ImageView scan = new ImageView(pageIcon);
			scan.setFitWidth(360);
			scan.setFitHeight(520);
			//hbox.getChildren().add(new Label(page.getPageNumber().toString()));
			scan.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				Node nodeOut = hbox.getChildren().get(1);
		        if(nodeOut instanceof Label){
		        			currentPage=Integer.parseInt(((Label) nodeOut).getText());
		    				loadPage();
		    				System.out.println(scanListScroll.getViewportBounds().getHeight());
		           }
		        else {System.out.println("Not");}
		        event.consume();
		        });
			scanList.getChildren().add(scan);
	    }
	}


	@FXML
	public void loadPage() {
		/*if(p instanceof String) {
			Double page=Double.parseDouble(p);
			scanList.getChildren().add(null);
		//scanListScroll.setVvalue((page-1)*0.0101010101010);
		}*/
	}
	
	

}
