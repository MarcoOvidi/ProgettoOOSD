package fx_view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class NewDocument {

    @FXML
    private AnchorPane topbar;

    @FXML
    private TextField title;

    @FXML
    private TextField author;

    @FXML
    private Button addDocument;

    @FXML
    private TextArea description;

    @FXML
    private DatePicker compositionPeriodFrom;

    @FXML
    private DatePicker compositionPeriodForm;

    @FXML
    private ChoiceBox<?> preservationState;
    
    @FXML
	public void initialize() {
	}
	

	
   
    
    

}
