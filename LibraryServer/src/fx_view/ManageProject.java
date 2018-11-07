package fx_view;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ManageProject {

    @FXML
    private AnchorPane topbar;

    @FXML
    private GridPane grid;
    
    private List<String> progetti = new ArrayList<String>();
    
    
    @FXML
    public void initialize(){
    	for(int i=0; i<2000; i++)
    		progetti.add("dragone " + i);
    	
    	loadProjects();




    }
    
    
    public void loadProjects() {
    	
        /*Image pageIcon=new Image("http://www.clker.com/cliparts/3/6/2/6/1348002494474708155New%20Page%20Icon.svg.med.png");
        ImageView miniature=new ImageView(pageIcon);
		miniature.setFitWidth(100);
		miniature.setFitHeight(140);*/
        
        
        
    	int i=0,j=0;
    	for(String curr : progetti) {
    		if(j==7){	
    			i++;
    			j=0;
    		}
        grid.add(new Button(curr), j, i, 1, 1);	
    	j++;
    	}
    		
    	
    }
    
    

}
