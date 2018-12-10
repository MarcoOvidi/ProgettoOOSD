package view;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.function.Predicate;

import controller.LocalSession;
import controller.PageScanController;
import dao.DatabaseException;
import dao.ScanningWorkProjectQuerySet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.Page;
import vo.UUIDDocument;
import vo.UUIDScanningWorkProject;
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

	@FXML
	private CheckBox checkConvalidated;

	@FXML
	private CheckBox checkRevisioned;

	@FXML
	private Button filterPages;

	@FXML
	private Button clearFilters;
	
	@FXML
	private Label loadScanTitle;

	private UUIDDocument currentDocument;
	
	@FXML
	public void initialize() {
		insertDocument();
		initLoadDocument();
		initfilterButtons();
		initPageTable();
		initLoadScanTitle();
		prepareDocument();
	}

	
	private void initLoadScanTitle(){
		loadScanTitle.setVisible(false);
		
	}
	
	
	private void prepareDocument() {
		if (toOpenDocument != null) {
			loadDocument(toOpenDocument);
		}
		toOpenDocument = null;
	}

	private void initPageTable() {
		number.setResizable(false);
		image.setResizable(true);
		revisioned.setResizable(false);
		validated.setResizable(false);

		number.setSortable(false); // Sorting as Strings. Not good. Nope.
	}

	public static void setToOpenDocumentFromScanningProject(UUIDScanningWorkProject swp)
			throws NullPointerException, DatabaseException {
		// FIXME andrebbe incapsulato in un metodo del controller
		Transcription.toOpenDocument = ScanningWorkProjectQuerySet.loadUUIDDocument(swp);
	}

	public static void setToOpenDocument(UUIDDocument toOpenDocument) {
		Transcription.toOpenDocument = toOpenDocument;
	}

	@FXML
	public void insertDocument() {
		PageScanController.loadUncompletedDocument(LocalSession.getLocalUser().getID());

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

		for (Entry<UUIDDocument, String> e : PageScanController.getUncompletedDocument().entrySet()) {
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
				loadScanTitle.setVisible(true);
			  currentDocument=documentList.getItems().get((Integer) entryNew).getKey();
				loadDocument(currentDocument);
			}
		});

	}

	public void preLoadDocument(UUIDDocument document) {
		toOpenDocument = document;
	}

	public void loadDocument(UUIDDocument document) { // FIXME tutto da testare
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
						/*
						 * if (p.getScan().getValidated()) { Alert alt = new
						 * Alert(AlertType.INFORMATION); alt.setTitle("Not Valid Operation");
						 * alt.setHeaderText("Attention");
						 * alt.setContentText("Cannot change number to a validated Page!"); alt.show();
						 * event.getRowValue().setNumber(event.getOldValue()); pageTable.refresh();
						 * return; } else {
						 */
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
		});
	}

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
	}

}