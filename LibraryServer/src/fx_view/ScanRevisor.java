package fx_view;


import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.function.Predicate;

import controller.LocalSession;
import controller.PageScanController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Page;
import vo.Rows;
import vo.UUIDDocument;

public class ScanRevisor {

	@FXML
	private Button filechooser;

	@FXML
	private VBox scanList;

	@FXML
	private ChoiceBox<Entry<UUIDDocument, String>> documentList;

	@FXML
	private Button loadDocumentButton;

	@FXML
	private TableView<Rows> pageTable;

	@FXML
	private TableColumn<Rows, String> number;

	@FXML
	private TableColumn<Rows, ImageView> image;

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
	public void initialize() {
		imageUpload();
		insertDocument();
		initButtonChoice();
		initLoadDocumentButton();
		filterButton();
	}

	public void imageUpload() {
		filechooser.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.jpg","*.png");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showOpenDialog(new Stage());

			VBox hbox = new VBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setPadding(new Insets(40));
			hbox.focusedProperty().addListener((ov, oldV, newV) -> {
				if (!newV) { // focus lost
					// Your code
					hbox.setStyle("-fx-background: #444;");
				}
			});

			Image pic = new Image(file.toURI().toString());
			ImageView newscan = new ImageView(pic);
			newscan.setFitWidth(600);
			newscan.setFitHeight(350);

			hbox.getChildren().add(newscan);
			hbox.getChildren().add(new TextField("1"));
			Button send = new Button("Upload Page");
			
			send.addEventHandler(MouseEvent.MOUSE_CLICKED, sending -> {
				//TODO chiama controller che chiama la query createPage di digitalizerQuerySET
				
				sending.consume();
			});
			scanList.getChildren().add(hbox);

			event.consume();
		});
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
		}
	}

	@FXML
	public void initButtonChoice() {
		filechooser.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			// TODO
			event.consume();
		});
	}

	@FXML
	public void initLoadDocumentButton() {
		LinkedList<Page> pL = new LinkedList<Page>();
		pages = FXCollections.observableArrayList();
		number.setCellValueFactory(new PropertyValueFactory<Rows, String>("Number"));
		number.setCellFactory(TextFieldTableCell.<Rows>forTableColumn());
		image.setCellValueFactory(new PropertyValueFactory<Rows, ImageView>("Image"));
		revisioned.setCellValueFactory(new PropertyValueFactory<Rows, String>("Revisioned"));
		validated.setCellValueFactory(new PropertyValueFactory<Rows, String>("Validated"));

		loadDocumentButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			pageTable.setEditable(true);
			PageScanController.loadNewDocumentPages(documentList.getSelectionModel().getSelectedItem().getKey());

			LinkedList<Page> p = PageScanController.getCurrentDocumentPages();
			pL.addAll(p);

			Collections.sort(p);

			pages.clear();
			pageTable.refresh();

			if (checkRevisioned.isSelected())
				checkRevisioned.fire();
			if (checkConvalidated.isSelected())
				checkConvalidated.fire();

			for (Page page : p) {
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

				Image img = new Image("file:" + page.getScan().getImage().getUrl());
				ImageView imgView = new ImageView();
				imgView.setImage(img);
				
				pages.add(new Rows(page.getPageNumber().toString(), rev, val, page.getID(), new vo.Image("file:" + page.getScan().getImage().getUrl())));

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

			event.consume();
		});

	}

	@FXML
	public void filterButton() {
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

			pages.clear();
			pageTable.refresh();

			PageScanController.loadNewDocumentPages(documentList.getSelectionModel().getSelectedItem().getKey());

			LinkedList<Page> p = PageScanController.getCurrentDocumentPages();

			Collections.sort(p);

			if (checkRevisioned.isSelected())
				checkRevisioned.fire();
			if (checkConvalidated.isSelected())
				checkConvalidated.fire();

			for (Page page : p) {
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

				Image img = new Image("file:" + page.getScan().getImage().getUrl());

				ImageView imgView = new ImageView();
				imgView.setImage(img);

				pages.add(new Rows(page.getPageNumber().toString(), rev, val, page.getID(), new vo.Image("file:" + page.getScan().getImage().getUrl())));

			}

			pageTable.setItems(pages);

			event.consume();
		});
	}

}
