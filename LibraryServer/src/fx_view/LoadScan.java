package fx_view;

import java.io.File;
import java.util.Map.Entry;

import controller.LocalSession;
import controller.PageScanController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class LoadScan {

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
	private TableColumn<Rows, Integer> number;

	@FXML
	private TableColumn<Rows, String> image;

	@FXML
	private TableColumn<Rows, String> revisioned;

	@FXML
	private TableColumn<Rows, String> validated;

	@FXML
	private ObservableList<Rows> pages;

	@FXML
	public void initialize() {
		documentList();
		insertDocument();
		initButtonChoice();
		initLoadDocumentButton();
	}

	public void documentList() {
		filechooser.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png", "*.jpg");
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
			scanList.getChildren().add(hbox);

			event.consume();
		});
	}

	@FXML
	public void fileChooser() {

	};

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
		pages = FXCollections.observableArrayList();

		number.setCellValueFactory(new PropertyValueFactory<Rows, Integer>("Number"));
		image.setCellValueFactory(new PropertyValueFactory<Rows, String>("Image"));
		revisioned.setCellValueFactory(new PropertyValueFactory<Rows, String>("Revisioned"));
		validated.setCellValueFactory(new PropertyValueFactory<Rows, String>("Validated"));

		loadDocumentButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			PageScanController.loadNewDocumentPages(documentList.getSelectionModel().getSelectedItem().getKey());
			
			pageTable.getItems().clear();
			
			for (Page page : PageScanController.getCurrentDocumentPages()) {
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

				pages.add(new Rows(page.getPageNumber(), page.getScan().getImage().getUrl(), val, rev));

			}

			pageTable.setItems(pages);

			event.consume();
		});
	}
}
