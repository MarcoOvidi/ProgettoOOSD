package fx_view;

import controller.CreateDocumentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    
    @FXML
	ChoiceBox<String> choiceBox = new ChoiceBox<String>();
    
    @FXML
	ChoiceBox<String> choiceBox1 = new ChoiceBox<String>();

    @FXML
    ChoiceBox<String> choiceBox2 = new ChoiceBox<String>();

    @FXML
	private TextField year= new TextField("");
	
    @FXML
	private TextField year1= new TextField("");
	
    @FXML
    private TextField year2= new TextField("");
    public static int c;
    private String title, author, description;
    private String yearvar, year1var, year2var;
    private String preservState;
    
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
					
				case 3:
					caseThree();
					break;
					
				case 4:
					caseFour();
					break;
					
				case 5:
					caseFive();
					break;
					
				case 6:
					caseSix();
					break;	
				
				default: finalCase();	
			}
				
			c++;
	        event.consume();
	        });
    }
    
    @FXML
    public void caseZero() {
    	title=intext.getText();
    	label.setText("Inserisci autore/i");
//    	intext.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//    		event.consume();
//    		c=c+2;
//    		caseThree();
//		});
    	intext.setText("");
    	intext.setPromptText("Autore/i");
    }
    
    @FXML
    public void caseOne() {
    	TextArea inarea=new TextArea();
    	author=intext.getText();
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
    	
    	definita.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		event.consume();
    		c=c+2;
    		caseThree();
		});
			
	   	indefinita.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	   		event.consume();
	   		c=c++;
	   		caseFour();
	   	});	
			
    	HBox hb= new HBox();
    	container.getChildren().add(hb);
    	hb.getChildren().add(new Label("                  "));
    	hb.getChildren().add(definita);
    	hb.getChildren().add(new Label("     /     "));
    	hb.getChildren().add(indefinita);
    }
    
    public void caseThree() {
    	HBox hbox=new HBox();
    	TextField year= new TextField("");
    	
    	choiceBox.getItems().add("d.C.");
        choiceBox.getItems().add("a.C.");
        
        choiceBox.getSelectionModel().select(0);
        
    	next.setVisible(false);
    	
    	label.setText("Digita l'anno in cui è stato composto il manoscritto");
    	container.getChildren().clear();
    	
    	year.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) {
    	        if (!newValue.matches("\\d*")) {
    	            year.setText(newValue.replaceAll("[^\\d]", ""));
    	        }
    	        if(year.getText().length()>4)
    	        	year.setText(year.getText().substring(0, year.getText().length()-1));
    	        if(year.getText().length()<1)
    	        	next.setVisible(false);
    	        if(year.getText().length()>1)
    	        	next.setVisible(true);
    	    }    	    
    	});
    	
    	hbox.getChildren().add(year);
        hbox.getChildren().add(choiceBox);
        hbox.setSpacing(40);
    	container.getChildren().add(hbox);
    	next.setVisible(true);
    	};
    
    public void caseFour() {
       	HBox hbox=new HBox();
	    next.setVisible(false);
       	
    	year1.setPromptText("Anno inizio");
    	year2.setPromptText("Anno fine");
    	
        choiceBox1.getItems().add("d.C.");
        choiceBox1.getItems().add("a.C.");
       
      	choiceBox2.getItems().add("d.C.");
      	choiceBox2.getItems().add("a.C.");
    	
        choiceBox1.getSelectionModel().select(0);
        choiceBox2.getSelectionModel().select(0);
        
    	label.setText("Inserisci il periodo di composizione dell'opera");
    	container.getChildren().clear();


    	year1.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) {
    	        if (!newValue.matches("\\d*")) {
    	            year1.setText(newValue.replaceAll("[^\\d]", ""));
    	        }
    	        if(year1.getText().length()>4)
    	        	year1.setText(year1.getText().substring(0, year1.getText().length()-1));
    	        if(year1.getText().length()<1)
    	        	next.setVisible(false);
    	        if(year1.getText().length()>1)
    	        	next.setVisible(true);
    	        
    	    }
    	});
    	

            
            
            
            
    	year2.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) {
    	        if (!newValue.matches("\\d*")) {
    	            year2.setText(newValue.replaceAll("[^\\d]", ""));
    	        }
    	        if(year2.getText().length()>4)
    	        	year2.setText(year2.getText().substring(0, year2.getText().length()-1));
    	        if(year2.getText().length()<1)
    	        	next.setVisible(false);
    	        if(year2.getText().length()>1)
    	        	next.setVisible(true);
    	    }
    	});

    	hbox.getChildren().add(year1);
        hbox.getChildren().add(choiceBox1);
    	hbox.getChildren().add(year2);
        hbox.getChildren().add(choiceBox2);
        hbox.setSpacing(20);
    	container.getChildren().add(hbox);
    	next.setVisible(true);
    }
    
    public void caseFive() {
       	
    	if(year.getText().equals("")) {
    		yearvar=year.getText();
    		String s=(String)choiceBox.getSelectionModel().getSelectedItem();
    		System.out.println(s);
    	}
    	else
    	{	
    		year1var=year1.getText();
    		year2var=year2.getText();
    		if(choiceBox1.getSelectionModel().getSelectedItem().equals("d.C."))
    			year1var="-"+year1var;

    		if(choiceBox2.getSelectionModel().getSelectedItem().equals("a.C."))
    			year2var="-"+year2var;
    		
    		System.out.println(year1var);
    		System.out.println(year2var);
    	}

    	label.setText("Indicare lo stato di conservazione");
    	
        container.getChildren().clear();

    	choiceBox = new ChoiceBox<String>();

    	choiceBox.getItems().add("1");
    	choiceBox.getItems().add("2");
    	choiceBox.getItems().add("3");
    	choiceBox.getItems().add("4");
    	choiceBox.getItems().add("5");
        
    	choiceBox.getSelectionModel().select(2);
        
        //choiceBox.
        
        container.getChildren().add(choiceBox);
        
    }
    
    public void caseSix() {
    	preservState = choiceBox.getSelectionModel().getSelectedItem();
    	
    	//TODO idee migliori per questi sono ben accette
    	if(title.isEmpty())
    		title = "Unknown";
    	if(author.isEmpty())
    		author = "Unknown";
    	
    	label.setText("Il tuo documento è stato creato");
    	next.setText(" Torna indietro.\n Dove sarà tutto nuovamente in inglese.\n Magico.");
        container.getChildren().clear();
        CreateDocumentController.createDocument(title, author, description, yearvar, year1var, year2var, preservState);
    }
    
    public void finalCase() {
    	SceneController.loadPreviousScene();
    }
    
    
}

