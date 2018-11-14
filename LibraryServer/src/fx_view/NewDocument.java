package fx_view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class NewDocument {

    @FXML
    private Label label;
    
    @FXML
    private TextField intext;
   
    @FXML
    private Button next;
    
    @FXML
    private Pane container;
    
    @FXML
    Button definita;
    
    @FXML
	TextArea inarea;
    
    @FXML
    Button indefinita;
    
    
    public static int c;
    
    String titolo,autori,description;
    
    @FXML
	public void initialize() {
    	c=0;
    	initNextButton();
    }
    
    @FXML
    public void initNextButton() {
    	next.setDisable(false);
    	
    	next.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			switch(NewDocument.c) {
				case 0:
				caseZero();
					break;
					
				case 1:
					caseOne();
					break;
			
				case 2:
					caseTwo();
					break;
					
			}
				
			c++;
	        event.consume();
	        });
    }
    
    @FXML
    public void caseZero() {
    	titolo=intext.getText();
    	label.setText("Inserisci autore/i");
    	intext.setPromptText("Autore/i");
    }
    
    @FXML
    public void caseOne() {
    	TextArea inarea=new TextArea();
    	autori=intext.getText();
    	label.setText("Inserisci descrizione");
    	container.getChildren().clear();
    	container.getChildren().add(inarea);
    }
    
    @FXML
    public void caseTwo() {
    	label.setText("Riguardo la composizione dell'opera si conosce");
    	System.out.println(c);
    	next.setVisible(false);
    	container.getChildren().clear();
    	definita=new Button("la data precisa");
    	indefinita=new Button("il periodo storico");
    	HBox hb= new HBox();
    	container.getChildren().add(hb);
    	hb.getChildren().add(new Label("                  "));
    	hb.getChildren().add(definita);
    	hb.getChildren().add(new Label("     /     "));
    	hb.getChildren().add(indefinita);
    }
    
}
