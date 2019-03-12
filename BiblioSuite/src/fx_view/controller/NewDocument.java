package fx_view.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import controller.CreateDocumentController;
import controller.DocumentInfoController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import vo.Tag;
import vo.UUIDDocument;

public class NewDocument {

	@FXML
	private MenuButton roleList;

	@FXML
	private Label label;

	@FXML
	private TextField intext;

	@FXML
	private Button next;

	@FXML
	private Pane container;

	@FXML
	private Button definita;

	@FXML
	private TextArea inarea;

	@FXML
	private Button indefinita;	

	@FXML
	private ChoiceBox<String> choiceBox = new ChoiceBox<String>();

	@FXML
	private ChoiceBox<String> choiceBox1 = new ChoiceBox<String>();

	@FXML
	private ChoiceBox<String> choiceBox2 = new ChoiceBox<String>();

	@FXML
	private TextField year = new TextField("");

	@FXML
	private TextField year1 = new TextField("");

	@FXML
	private TextField year2 = new TextField("");

	public static int c;
	private String title, author, description;
	private String yearvar, year1var, year2var;
	private String preservState;
	HashMap<Integer, Tag> existingTags = new HashMap<Integer, Tag>();
	LinkedList<Tag> notExistingTags = new LinkedList<Tag>();

	@FXML
	public void initialize() {
		c = 0;
		next.setVisible(false);
		title = "";
		author = "";
		description = "";

		initNextButton();

		intext.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (intext.getText().length() > 5)
					next.setVisible(true);
				else
					next.setVisible(false);
			}
		});

	}

	@FXML
	public void initNextButton() {
		next.setDisable(false);

		next.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			switch (NewDocument.c) {
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

			case 7:
				caseSeven();
				break;
			case 8:
				caseEight();
				break;
			case 9:
				caseNine();
				break;
			case 10:
				caseTen();
				break;

			default:
				finalCase();
			}

			c++;
			event.consume();
		});
	}

	@FXML
	public void caseZero() {
		title = intext.getText();
		label.setText("Insert author/s");
		next.setVisible(false);
//    	intext.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//    		event.consume();
//    		c=c+2;
//    		caseThree();
//		});

		intext.setText("");
		intext.setPromptText("Author/s");
	}

	@FXML
	public void caseOne() {
		author = intext.getText();
		label.setText("Insert description");
		container.getChildren().clear();
		inarea = new TextArea();
		container.getChildren().add(inarea);

	}

	@FXML
	public void caseTwo() {
		description = inarea.getText();
		inarea.getText();
		label.setText("What do you know about the creation of the work");
		next.setVisible(false);
		container.getChildren().clear();
		definita = new Button("A date");
		indefinita = new Button("A period");

		definita.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			event.consume();
			c = c + 2;
			caseThree();
		});

		indefinita.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			event.consume();
			c = c + 2;
			caseFour();
		});

		HBox hb = new HBox();
		container.getChildren().add(hb);
		hb.getChildren().add(new Label("                  "));
		hb.getChildren().add(definita);
		hb.getChildren().add(new Label("     /     "));
		hb.getChildren().add(indefinita);
	}

	public void caseThree() {
		HBox hbox = new HBox();

		choiceBox.getItems().add("d.C.");
		choiceBox.getItems().add("a.C.");

		choiceBox.getSelectionModel().select(0);

		next.setVisible(false);

		label.setText("Enter the year in which the document was composed");
		container.getChildren().clear();

		year.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					year.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (year.getText().length() > 4)
					year.setText(year.getText().substring(0, year.getText().length() - 1));
				if (year.getText().length() < 1)
					next.setVisible(false);
				if (year.getText().length() > 1)
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
		HBox hbox = new HBox();
		next.setVisible(false);

		year1.setPromptText("From year");
		year2.setPromptText("To year");

		choiceBox1.getItems().add("d.C.");
		choiceBox1.getItems().add("a.C.");

		choiceBox2.getItems().add("d.C.");
		choiceBox2.getItems().add("a.C.");

		choiceBox1.getSelectionModel().select(0);
		choiceBox2.getSelectionModel().select(0);

		label.setText("Isert opera's composition period");
		container.getChildren().clear();

		year1.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					year1.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (year1.getText().length() > 4)
					year1.setText(year1.getText().substring(0, year1.getText().length() - 1));
				if (year1.getText().length() < 1)
					next.setVisible(false);
				if (year1.getText().length() > 1)
					next.setVisible(true);

			}
		});

		year2.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					year2.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (year2.getText().length() > 4)
					year2.setText(year2.getText().substring(0, year2.getText().length() - 1));
				if (year2.getText().length() < 1)
					next.setVisible(false);
				if (year2.getText().length() > 1)
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

		if (!(year.getText().equals(""))) {
			yearvar = year.getText();
		} else {
			year1var = year1.getText();
			year2var = year2.getText();
			if (choiceBox1.getSelectionModel().getSelectedItem().equals("a.C."))
				year1var = "-" + year1var;

			if (choiceBox2.getSelectionModel().getSelectedItem().equals("a.C."))
				year2var = "-" + year2var;
		}

		label.setText("Indicate the state of conservation");

		container.getChildren().clear();

		choiceBox = new ChoiceBox<String>();

		choiceBox.getItems().add("1");
		choiceBox.getItems().add("2");
		choiceBox.getItems().add("3");
		choiceBox.getItems().add("4");
		choiceBox.getItems().add("5");

		choiceBox.getSelectionModel().select(2);

		// choiceBox.

		container.getChildren().add(choiceBox);

	}

	public void caseSix() {

		preservState = choiceBox.getSelectionModel().getSelectedItem();
		label.setText("Choose tags that describe the Opera");

		container.getChildren().clear();
		/*
		 * 
		 * choiceBox = new ChoiceBox<String>();
		 * 
		 * choiceBox.getItems().add("1"); choiceBox.getItems().add("2");
		 * choiceBox.getItems().add("3"); choiceBox.getItems().add("4");
		 * choiceBox.getItems().add("5");
		 * 
		 * choiceBox.getSelectionModel().select(2);
		 * 
		 * // choiceBox.
		 * 
		 */
		////
		LinkedList<String> roles = new LinkedList<String>();
		roles.add("Uploader");
		roles.add("Transcriber");
		roles.add("Upload reviser");
		roles.add("Transcription reviser");
		roles.add("Coordinator");
		roleList = new MenuButton();
		existingTags = new HashMap<Integer, Tag>();

		for (Entry<Integer, Tag> tag : DocumentInfoController.getAvailableTags().entrySet()) {
			CheckMenuItem item = new CheckMenuItem();
			item.setText(tag.getValue().getTag());
			item.setId(String.valueOf(tag.getKey()));
			item.onActionProperty().set(event -> {
				if (!item.isSelected()) {
					existingTags.remove(Integer.valueOf(item.getId()));
				} else {
					existingTags.put(Integer.valueOf(item.getId()), new Tag(item.getText()));
				}
			});

			roleList.getItems().add(item);
		}

		roleList.setText("Available Tags");
		// roleList.setAlignment(Pos.CENTER);

		container.getChildren().add(roleList);

	}

	public void caseSeven() {
		c = 7 ; //loop finchè l'utente non vuole uscire
		label.setText("Do you need any other Tag?");
		next.setVisible(false);
		container.getChildren().clear();
		definita = new Button("Yes");
		indefinita = new Button("No");

		definita.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			event.consume();
			caseEight();
		
		});

		indefinita.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			event.consume();
			//non ne ha bisogno vado avanti e completo la creazione
			c=11;
			caseTen();
			
		});

		HBox hb = new HBox();
		container.getChildren().add(hb);
		hb.getChildren().add(new Label("                  "));
		hb.getChildren().add(definita);
		hb.getChildren().add(new Label("     /     "));
		hb.getChildren().add(indefinita);


	}
	
	public void caseEight() {
		c=9;	
		definita.setVisible(false);
		indefinita.setVisible(false);
		container.getChildren().clear();
		container.getChildren().add(intext);
		label.setText("Insert ONLY 1 tag (Remeber: not allowed spaces and commas!)");
		intext.setText("");
		intext.setPromptText("Tag");
	}
	
	public void caseNine() {
		notExistingTags.add(new Tag(intext.getText()));
		c=7;
		caseSeven();
		
	}

	public void caseTen() {
		if (title.isEmpty())
			title = "Unknown";
		if (author.isEmpty())
			author = "Unknown";
		
		UUIDDocument creato = CreateDocumentController.createDocument(title, author, description, yearvar, year1var,
				year2var, preservState);
		DocumentInfoController.setAvailableTagsToDocument(DocumentInfoController.getUUIDDocumentMetadata(creato),
				existingTags);
		
		if(!notExistingTags.isEmpty()) {
			HashMap<Integer, Tag> inserted = new HashMap<Integer, Tag>();
			
			//aggiungi i tag nuovi al DB
			for(Tag t : notExistingTags) {
				inserted.put(DocumentInfoController.insertNewTag(t), t);
			}
			
			//aggiungi i nuovi tag relazionati al document che stiamo creando
			
			DocumentInfoController.setAvailableTagsToDocument(DocumentInfoController.getUUIDDocumentMetadata(creato),inserted);
			
		}
		label.setText("Your document has been created");
		next.setVisible(true);
		next.setText("Go back");
		container.getChildren().clear();
		System.out.println(title + author + description + yearvar + year1var + year2var + preservState + existingTags + notExistingTags);

	}
	

	public void finalCase() {
		SceneController.loadPreviousScene();
	}

}
