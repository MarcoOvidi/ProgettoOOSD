package view;


import java.util.Collections;
import java.util.LinkedList;
import java.util.Map.Entry;

import controller.LocalSession;
import controller.PageScanController;
import controller.ScanningProjectController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.Page;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.view.Rows;

public class ScanRevisor {

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
	private TableColumn<Rows, Image> image;

	@FXML
	private TableColumn<Rows, String> revisioned;

	@FXML
	private TableColumn<Rows, String> validated;

	@FXML
	private ObservableList<Rows> pages;
	
	@FXML
	private Pane pageContainer;
	
	@FXML
	private Button closePageContainerButton;
	
	@FXML
	private ImageView pageImg;
	
	@FXML
	private Button acceptButton;
	
	@FXML
	private Button rejectButton;
	
	@FXML
	private Button confirmButton;
	
	@FXML
	private TextArea commentArea;
	
	
	private UUIDPage currentPage;
	
	private boolean isValidated;
	
	@FXML
	public void initialize() {
		initPageContainer();
		insertDocument();
		initLoadDocumentButton();
		initRowClick();
		initClosePageContainerButton();
		initAcceptButton();
		initRejectButton();
		initConfirmButton();
		initCommentArea();
	}
	
	public void initCommentArea(){
		commentArea.setVisible(true);
	}
	
	public void initAcceptButton(){
		acceptButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			isValidated=true;
			commentArea.setVisible(false);
	      });	
	};
	
	public void initRejectButton(){
		rejectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			isValidated=false;
			commentArea.setVisible(true);
		});	
	};
	
	public void initConfirmButton() {
		confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			pageContainer.setVisible(false);
			ScanningProjectController.validatePage(currentPage, isValidated);
			ScanningProjectController.revisedPage(currentPage, true);
			
			Event.fireEvent(loadDocumentButton, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
	                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
	                true, true, true, true, true, true, null));		});	
	}
	
	
	
	public void initClosePageContainerButton(){
		closePageContainerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			pageContainer.setVisible(false);
	      });	
	}
	
	public void initPageContainer() {
		pageContainer.setVisible(false);
	}

	@FXML
	public void insertDocument() {
		
		//TODO grande dubbio su quale dei due metodi!!!!!!!!
		PageScanController.loadUncompletedDocumentForReviser(LocalSession.getLocalUser().getID());

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
	public void initLoadDocumentButton() {
		LinkedList<Page> pL = new LinkedList<Page>();
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

		loadDocumentButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			pageTable.setEditable(true);
			PageScanController.loadUncompletedDocumentPagesFilters(documentList.getSelectionModel().getSelectedItem().getKey(), false, false);
			//PageScanController.loadNewDocumentPages(documentList.getSelectionModel().getSelectedItem().getKey());

			LinkedList<Page> p = PageScanController.getCurrentDocumentPages();
			pL.addAll(p);

			Collections.sort(p);

			pages.clear();
			pageTable.refresh();


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

				Image img = LocalSession.loadImage(page.getScan().getImage().getUrl());
				//ImageView imgView = new ImageView();
				//imgView.setImage(img);
				
				pages.add(new Rows(page.getPageNumber().toString(), rev, val, page.getID(), img));

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

	
	
	public void initRowClick() {
		pageTable.setRowFactory(tv -> {
			TableRow<Rows> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					pageContainer.setVisible(true);
					pageImg.setImage(row.getItem().getImage());
					currentPage=row.getItem().getId();
				}
			});
			return row;
		});
	}

}
