package fx_view;

import java.util.HashMap;
import java.util.Map.Entry;

import controller.HomePageController;
import dao.DatabaseException;
import dao.ScanningWorkProjectQuerySet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import vo.DocumentRow;
import vo.UUIDDocument;
import vo.UUIDScanningWorkProject;

public class ManageProject {

	@FXML
	private AnchorPane topbar;

	@FXML
	private TableView<DocumentRow> documentTable;

	@FXML
	private TableColumn<DocumentRow, String> Document;

	@FXML
	private TableColumn<DocumentRow, String> Transcription_PRJ;

	@FXML
	private TableColumn<DocumentRow, String> Scanning_PRJ;

	@FXML
	private TabPane pane;

	@FXML
	private Tab transcriptionTab;

	@FXML
	private Tab scanningTab;

	@FXML
	private AnchorPane transcriptionTabPane;

	@FXML
	private AnchorPane scanningTabPane;

	@FXML
	private ObservableList<DocumentRow> rows;

	public void initialize() {
		try {
			tableDocumentFiller();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void tableDocumentFiller() throws NullPointerException, DatabaseException {
		Document.setCellValueFactory(new PropertyValueFactory<DocumentRow, String>("Document"));
		Transcription_PRJ.setCellValueFactory(new PropertyValueFactory<DocumentRow, String>("Transcription_PRJ"));
		Scanning_PRJ.setCellValueFactory(new PropertyValueFactory<DocumentRow, String>("Scanning_PRJ"));

		rows = FXCollections.observableArrayList();

		HomePageController.loadMyScanningProjects();
		HomePageController.loadMyTranscriptionProjects();

		HashMap<UUIDDocument, DocumentRow> document = new HashMap<UUIDDocument, DocumentRow>();

		for (Entry<UUIDScanningWorkProject, String[]> entry : HomePageController.getMySPrj().entrySet()) {
			String[] array = entry.getValue();
			document.put(ScanningWorkProjectQuerySet.loadUUIDDocument(entry.getKey()),
					new DocumentRow(array[0], array[1]));
		}
		for (DocumentRow docRow : document.values()) {
			rows.add(docRow);
		}

		documentTable.setItems(rows);

	}

	/*
	 * @FXML private GridPane grid;
	 * 
	 * @FXML public void initialize(){
	 * 
	 * HomePageController.loadMyTranscriptionProjects();
	 * HomePageController.loadMyScanningProjects();
	 * 
	 * int i=1,j=0; for(Entry<UUIDScanningWorkProject, String> entry :
	 * HomePageController.getMySPrj().entrySet()) {
	 * 
	 * if(j==6){ i++; j=0; } Button dd = new Button(entry.getValue());
	 * //dd.setPrefSize(20,50); dd.setMinSize(60, 45); grid.add(dd, j, i, 1, 1);
	 * j++; } Button dd = new Button("+"); dd.setFont(new Font(24));
	 * //dd.setPrefSize(20,50); dd.setMinSize(60, 45); dd.setMaxSize(60, 45);
	 * dd.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	 * SceneController.loadScene("newDocument"); }); grid.add(dd,0,0,1,1);
	 * 
	 * grid.setHgap(60.0); grid.setVgap(60.0);
	 * 
	 * loadProjects();
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * public void loadProjects() {
	 * 
	 * /* Image pageIcon=new Image(
	 * "http://www.clker.com/cliparts/3/6/2/6/1348002494474708155New%20Page%20Icon.svg.med.png"
	 * ); ImageView miniature=new ImageView(pageIcon); miniature.setFitWidth(100);
	 * miniature.setFitHeight(140);
	 *
	 * 
	 * int i = 0, j = 0; for (String curr : progetti) { if (j == 6) { i++; j = 0; }
	 * Button dd = new Button(curr); // dd.setPrefSize(20,50); dd.setMinSize(60,
	 * 45); grid.add(dd, j, i, 1, 1); j++; }
	 * 
	 * }
	 */

}
