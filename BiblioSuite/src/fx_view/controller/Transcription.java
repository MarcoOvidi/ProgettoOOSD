package fx_view.controller;

import java.util.Map.Entry;

import controller.LocalSessionBridge;
import controller.PageTranscriptionController;
import dao.concrete.DatabaseException;
import dao.concrete.TranscriptionWorkProjectQuerySet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import vo.UUIDDocument;
import vo.UUIDTranscriptionWorkProject;
import vo.view.Rows;

public class Transcription {

	private static UUIDDocument toOpenDocument = null;

	@FXML
	private VBox scanList;

	@FXML
	private ChoiceBox<Entry<UUIDDocument, String>> documentList;

	@FXML
	private TableView<Rows> pageTable;

	@FXML
	private TableColumn<Rows, String> number;

	@FXML
	private TableColumn<Rows, Image> image;

	@FXML
	private TableColumn<Rows, String> revisioned;

	@FXML
	private TableColumn<Rows, String> validated;

	@FXML
	private ObservableList<Rows> pages;

	//@FXML
	//private CheckBox checkConvalidated;

	//@FXML
	//private CheckBox checkRevisioned;

	//@FXML
	//private Button filterPages;

	//@FXML
	//private Label filtersLabel;
	
	//@FXML
	//private Button clearFilters;

	private UUIDDocument currentDocument;
	
	@FXML
	public void initialize() {
		insertDocument();
		initLoadDocument();
		//initfilterButtons();
		initPageTable();
		prepareDocument();
	}
	
	
	private void prepareDocument() {
		if (toOpenDocument != null) {
			loadDocument(toOpenDocument);
		}
		toOpenDocument = null;
	}

	private void initPageTable() {
		
		//
		//checkConvalidated.setVisible(false);
		//checkRevisioned.setVisible(false);;
		//filterPages.setVisible(false);;
		pageTable.setVisible(false);
		//clearFilters.setVisible(false);
		//filtersLabel.setVisible(false);
		//
		
		
		number.setResizable(false);
		image.setResizable(true);
		revisioned.setResizable(false);
		validated.setResizable(false);

		number.setSortable(false); // Sorting as Strings. Not good. Nope.
	}

	public static void setToOpenDocumentFromTranscriptionProject(UUIDTranscriptionWorkProject swp)
			throws NullPointerException, DatabaseException {
		// FIXME andrebbe incapsulato in un metodo del controller
		Transcription.toOpenDocument = new TranscriptionWorkProjectQuerySet().loadUUIDDocument(swp);
	}

	public static void setToOpenDocument(UUIDDocument toOpenDocument) {
		Transcription.toOpenDocument = toOpenDocument;
	}

	@FXML
	public void insertDocument() {
		PageTranscriptionController.loadUncompletedDocumentForTranscriber(LocalSessionBridge.getLocalUser().getID());
		
		documentList.setConverter(new StringConverter<Entry<UUIDDocument, String>>() {
			@Override
			public String toString(Entry<UUIDDocument, String> a) {
				return a.getValue();
			}

			@Override
			public Entry<UUIDDocument, String> fromString(String string) {
				return null;
			}
		});

		for (Entry<UUIDDocument, String> e : PageTranscriptionController.getUncompletedDocument().entrySet()) {
			documentList.getItems().add(e);
			if (e.getKey().equals(toOpenDocument)) {
				documentList.getSelectionModel().select(e);
			}
		}
	}

	public void initLoadDocument() {
		pages = FXCollections.observableArrayList();
		number.setCellValueFactory(new PropertyValueFactory<Rows, String>("Number"));
		number.setCellFactory(TextFieldTableCell.<Rows>forTableColumn());

		image.setCellFactory(param -> {
			// Set up the ImageView
			final ImageView imageview = new ImageView();
			imageview.setFitHeight(160);
			imageview.setFitWidth(90);

			// Set up the Table
			TableCell<Rows, Image> cell = new TableCell<Rows, Image>() {
				public void updateItem(Image item, boolean empty) {
					if (item != null) {
						imageview.setImage(item);
					}
				}
			};
			// Attach the imageview to the cell
			cell.setGraphic(imageview);
			return cell;
		});

		image.setCellValueFactory(new PropertyValueFactory<Rows, Image>("Image"));
		revisioned.setCellValueFactory(new PropertyValueFactory<Rows, String>("Revisioned"));
		validated.setCellValueFactory(new PropertyValueFactory<Rows, String>("Validated"));

		documentList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number entry, Number entryNew) {
				//
				//checkConvalidated.setVisible(true);
				//checkRevisioned.setVisible(true);
				//filterPages.setVisible(true);
				pageTable.setVisible(true);
				//clearFilters.setVisible(true);
				//filtersLabel.setVisible(true);
				//
				
				currentDocument=documentList.getItems().get((Integer) entryNew).getKey();
				loadDocument(currentDocument);
			}
		});

	}

	public void preLoadDocument(UUIDDocument document) {
		toOpenDocument = document;
	}

	public void loadDocument(UUIDDocument document) { // FIXME tutto da testare
		
		try {
			PageTranscriptionController.transcribeDocument(document);
		} catch (LoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		PageScanController.loadNewDocumentPages(document);

		LinkedList<Page> pL = PageScanController.getCurrentDocumentPages();
		pageTable.setEditable(true);

		Collections.sort(pL);

		pages.clear();
		pageTable.refresh();

		if (checkRevisioned.isSelected())
			checkRevisioned.fire();
		if (checkConvalidated.isSelected())
			checkConvalidated.fire();

		for (Page page : pL) {
			String rev = "";
			if (page.getScan().getRevised()) {
				rev = "\u2714";
			} else {
				rev = "\u2718";
			}

			String val = "";

			if (page.getScan().getValidated()) {
				val = "\u2714";
			} else {
				val = "\u2718";
			}

			Image img = LocalSession.loadImage(page.getScan().getImage().getUrl());

			ImageView imgView = new ImageView();
			imgView.setImage(img);

			Rows row = new Rows(page.getPageNumber().toString(), rev, val, page.getID(), img);
			pages.add(row);

		}

		pageTable.setItems(pages);

		number.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rows, String>>() {

			@Override
			public void handle(CellEditEvent<Rows, String> event) {
				if (event.getOldValue() == event.getNewValue()) {
					return;
				}
				for (Page p : pL) {
					if (p.getPageNumber() == Integer.parseInt(event.getNewValue())) {
						]
						event.getRowValue().setNumber(event.getOldValue());
						pageTable.refresh();
						Alert alt = new Alert(AlertType.INFORMATION);
						alt.setTitle("Not Valid Operation");
						alt.setHeaderText("Attention");
						alt.setContentText("Cannot change number to an existing Page!");
						alt.show();

						return;
					}

				}
				PageScanController.updatePageNumber(event.getRowValue().getId(), Integer.parseInt(event.getNewValue()));
			}
		});*/
	}
/*
	@FXML
	public void initfilterButtons() {
		filterPages.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			FilteredList<Rows> filteredList = new FilteredList<>(pages);

			pageTable.setItems(filteredList);

			filteredList.setPredicate(new Predicate<Rows>() {
				public boolean test(Rows t) {
					Boolean control = false;

					if (checkConvalidated.isSelected() && checkRevisioned.isSelected()) {
						if (t.getValidated().equalsIgnoreCase("\u2714") && t.getRevisioned().equalsIgnoreCase("\u2714"))
							control = true;
						else
							control = false;
					} else if (checkConvalidated.isSelected() && !(checkRevisioned.isSelected())) {
						if (t.getValidated().equalsIgnoreCase("\u2714") && t.getRevisioned().equalsIgnoreCase("\u2718"))
							control = true;
						else
							control = false;
					} else if (!(checkConvalidated.isSelected()) && checkRevisioned.isSelected()) {
						if (t.getValidated().equalsIgnoreCase("\u2718") && t.getRevisioned().equalsIgnoreCase("\u2714"))
							control = true;
						else
							control = false;
					} else if (!(checkConvalidated.isSelected()) && !(checkRevisioned.isSelected())) {
						if (t.getValidated().equalsIgnoreCase("\u2718") && t.getRevisioned().equalsIgnoreCase("\u2718"))
							control = true;
						else
							control = false;
					}

					return control;
				}
			});

			event.consume();
		});

		clearFilters.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			loadDocument(documentList.getSelectionModel().getSelectedItem().getKey());
			event.consume();
		});
	}*/

}
