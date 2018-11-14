package fx_view;

import java.util.Map.Entry;

import controller.HomePageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;

public class ManageProject {

	@FXML
	private AnchorPane topbar;

	@FXML
	private GridPane grid;
    
    @FXML
    public void initialize(){
    	
    	HomePageController.loadMyTranscriptionProjects();
    	HomePageController.loadMyScanningProjects();
    	
    	int i=1,j=0;
    	for(Entry<UUIDScanningWorkProject, String> entry : HomePageController.getMySPrj().entrySet()) {

        		if(j==6){	
        			i++;
        			j=0;
        		}
        		Button dd = new Button(entry.getValue());
        		//dd.setPrefSize(20,50);
        		dd.setMinSize(60, 45);
    			grid.add(dd, j, i, 1, 1);
        	j++;
        	}
    	Button dd = new Button("+");
    	dd.setFont(new Font(24));
		//dd.setPrefSize(20,50);
		dd.setMinSize(60, 45);
		dd.setMaxSize(60, 45);
		dd.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("newDocument");
		});
		grid.add(dd,0,0,1,1);
    	
		grid.setHgap(60.0);
		grid.setVgap(60.0);
    	
    	loadProjects();




    }

	public void loadProjects() {

		/*
		 * Image pageIcon=new Image(
		 * "http://www.clker.com/cliparts/3/6/2/6/1348002494474708155New%20Page%20Icon.svg.med.png"
		 * ); ImageView miniature=new ImageView(pageIcon); miniature.setFitWidth(100);
		 * miniature.setFitHeight(140);
		 *

		int i = 0, j = 0;
		for (String curr : progetti) {
			if (j == 6) {
				i++;
				j = 0;
			}
			Button dd = new Button(curr);
			// dd.setPrefSize(20,50);
			dd.setMinSize(60, 45);
			grid.add(dd, j, i, 1, 1);
			j++;
		}*/

	}

}
