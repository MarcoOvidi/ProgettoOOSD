package fx_view;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

import controller.PageScanController;
import controller.PageTranscriptionController;
import controller.ScanningProjectController;
import controller.TranscriptionProjectController;
import dao.DatabaseException;
import dao.ScanningWorkProjectQuerySet;
import dao.TranscriptionWorkProjectQuerySet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import model.Page;
import model.User;
import vo.DocumentRow;
import vo.PageScanningLog;
import vo.PageTranscriptionLog;
import vo.StaffRow;
import vo.UUIDDocument;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

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
	private TabPane transcriptionTabPane;

	@FXML
	private TabPane scanningTabPane;

	@FXML
	private Tab transcriptionPagesTab;

	@FXML
	private Tab scanningPagesTab;

	@FXML
	private Tab transcriptionTranscribersTab;

	@FXML
	private Tab scanningDigitalizersTab;

	@FXML
	private AnchorPane transcriptionPagesAnchor;

	@FXML
	private AnchorPane scanningPagesAnchor;

	@FXML
	private AnchorPane transcriptionTranscriberAnchor;

	@FXML
	private AnchorPane scanningDigitalizersAnchor;

	@FXML
	private ObservableList<DocumentRow> rows;

	@FXML
	private TableView<StaffRow> transcriptionStaff;

	@FXML
	private TableColumn<StaffRow, String> usernameTranscriber;

	@FXML
	private TableColumn<StaffRow, String> roleTranscriber;

	@FXML
	private ObservableList<StaffRow> transcriptionProjectStaff;

	@FXML
	private TableView<PageTranscriptionLog> transcriptionLog;

	@FXML
	private TableColumn<PageTranscriptionLog, String> pageNumber;

	@FXML
	private TableColumn<PageTranscriptionLog, String> transcriptionReviser;

	@FXML
	private TableColumn<PageTranscriptionLog, String> transcriptionRevised;

	@FXML
	private TableColumn<PageTranscriptionLog, String> transcriptionValidated;

	@FXML
	private TableColumn<PageTranscriptionLog, String> transcriptionTranscriber;

	@FXML
	private ObservableList<PageTranscriptionLog> listLog;

	@FXML
	private TableView<StaffRow> scanningStaff;

	@FXML
	private TableColumn<StaffRow, String> usernameDigitalizer;

	@FXML
	private TableColumn<StaffRow, String> roleDigitalizer;

	@FXML
	private ObservableList<StaffRow> scanningProjectStaff;

	@FXML
	private TableView<PageScanningLog> scanningLog;

	@FXML
	private TableColumn<PageScanningLog, String> pageScanNumber;

	@FXML
	private TableColumn<PageScanningLog, String> scanningReviser;

	@FXML
	private TableColumn<PageScanningLog, String> scanningRevised;

	@FXML
	private TableColumn<PageScanningLog, String> scanningValidated;

	@FXML
	private TableColumn<PageScanningLog, String> scanningDigitalizer;

	@FXML
	private ObservableList<PageScanningLog> listScanLog;
	
	public void initialize() {
		try {
			tableDocumentFiller();
			rowClick();
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

		ScanningProjectController.loadCoordinatedScanningPRoject();
		TranscriptionProjectController.loadCoordinatedTranscriptionPRoject();

		TreeMap<UUIDDocument, DocumentRow> document = new TreeMap<UUIDDocument, DocumentRow>();

		// TODO credo che tutto ciò vada nel controller
		for (Entry<UUIDScanningWorkProject, String[]> entry : ScanningProjectController.getCoordinatedScanningProject()
				.entrySet()) {
			String[] array = entry.getValue();
			// TODO chiamare tramite controller
			String format = "";
			if (array[1].equalsIgnoreCase("true"))
				format = "\u2714";
			else if (array[1].equals("false"))
				format = "\u2718";
			DocumentRow dr = new DocumentRow(array[0], format, entry.getKey());

			document.put(ScanningWorkProjectQuerySet.loadUUIDDocument(entry.getKey()), dr);

		}

		for (Entry<UUIDTranscriptionWorkProject, String[]> entry : TranscriptionProjectController
				.getCoordinatedTranscriptionProject().entrySet()) {
			// TODO chiamare tramite controller
			String[] array = entry.getValue();
			// TODO questo cazzo di if non funziona va sempre nell'else
			if (document.containsKey(TranscriptionWorkProjectQuerySet.loadUUIDDocument(entry.getKey()))) {
				String format = "";
				if (array[1].equalsIgnoreCase("true"))
					format = "\u2714";
				else if (array[1].equals("false"))
					format = "\u2718";

				DocumentRow dr = document.get(TranscriptionWorkProjectQuerySet.loadUUIDDocument(entry.getKey()));
				dr.setTranscription_PRJ(format);
				dr.setIdTPrj(entry.getKey());
				document.put(TranscriptionWorkProjectQuerySet.loadUUIDDocument(entry.getKey()), dr);
			} else {
				String format = "";
				if (array[1].equalsIgnoreCase("true"))
					format = "\u2714";
				else if (array[1].equals("false"))
					format = "\u2718";
				DocumentRow d = new DocumentRow(array[0], format, entry.getKey());
				document.put(TranscriptionWorkProjectQuerySet.loadUUIDDocument(entry.getKey()), d);
			}
		}
		
		for(Entry<UUIDDocument , DocumentRow> entry : document.entrySet()) {
			if(entry.getValue().getIdTPrj() == null)
				if(TranscriptionWorkProjectQuerySet.ifExistTranscriptionProject(entry.getKey())) {
					DocumentRow transcription = entry.getValue();
					transcription.setTranscription_PRJ("Not allowed");
					entry.setValue(transcription);
				}else {
					DocumentRow transcription = entry.getValue();
					transcription.setTranscription_PRJ("\u2204");
					entry.setValue(transcription);
				}
			
			if(entry.getValue().getIdSPrj() == null)
				if(ScanningWorkProjectQuerySet.ifExistScanningProject(entry.getKey())) {
					DocumentRow scanning = entry.getValue();
					scanning.setScanning_PRJ("Not allowed");
					entry.setValue(scanning);
				}else {
					DocumentRow scanning = entry.getValue();
					scanning.setScanning_PRJ("\u2204");
					entry.setValue(scanning);
				}
			
			rows.add(entry.getValue());
		}

		documentTable.setItems(rows);
	}

	public void rowClick() {
		documentTable.setRowFactory(tv -> {
			TableRow<DocumentRow> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {

					DocumentRow clickedRow = row.getItem();

					if (transcriptionProjectStaff != null) {
						transcriptionProjectStaff.clear();
						transcriptionStaff.refresh();
					}

					if (scanningProjectStaff != null) {
						scanningProjectStaff.clear();
						scanningStaff.refresh();
					}

					if (listLog != null) {
						listLog.clear();
						transcriptionLog.refresh();
					}

					if (listScanLog != null) {
						listScanLog.clear();
						scanningLog.refresh();
					}

					if (clickedRow.getIdTPrj() != null) {
						loadTranscriptionPagesLog(clickedRow);
						loadTranscriptionProjectStaff(clickedRow);
					}

					if (clickedRow.getIdSPrj() != null) {
						loadScanningPagesLog(clickedRow);
						loadScanningProjectStaff(clickedRow);
					}

				}
			});
			return row;
		});

	}

	public void loadTranscriptionPagesLog(DocumentRow dR) {

		pageNumber.setCellValueFactory(new PropertyValueFactory<PageTranscriptionLog, String>("pageNumber"));
		transcriptionReviser
				.setCellValueFactory(new PropertyValueFactory<PageTranscriptionLog, String>("transcriptionReviser"));
		transcriptionRevised
				.setCellValueFactory(new PropertyValueFactory<PageTranscriptionLog, String>("transcriptionRevised"));
		transcriptionValidated
				.setCellValueFactory(new PropertyValueFactory<PageTranscriptionLog, String>("transcriptionValidated"));
		transcriptionTranscriber.setCellValueFactory(
				new PropertyValueFactory<PageTranscriptionLog, String>("transcriptionTranscriber"));

		listLog = FXCollections.observableArrayList();

		try {
			PageTranscriptionController
					.loadTranscriptionLog(TranscriptionWorkProjectQuerySet.loadUUIDDocument(dR.getIdTPrj()));

			if (listLog != null) {
				listLog.clear();
				transcriptionLog.refresh();
			}

			LinkedList<Page> pageList = PageTranscriptionController.getTranscriptionLog();

			Iterator<Page> itr = pageList.iterator();

			while (itr.hasNext()) {
				Page p = itr.next();

				String format1 = String.valueOf(p.getTranscription().getRevised());

				if (format1.equalsIgnoreCase("true"))
					format1 = "\u2714";

				else if (format1.equals("false"))
					format1 = "\u2718";

				String format2 = String.valueOf(p.getTranscription().getValidated());

				if (format2.equalsIgnoreCase("true"))
					format2 = "\u2714";

				else if (format2.equals("false"))
					format2 = "\u2718";

				listLog.add(new PageTranscriptionLog(String.valueOf(p.getPageNumber()),
						String.valueOf(p.getTranscription().getStaff().getReviser().getValue()), format1, format2,
						String.valueOf(p.getTranscription().getStaff().getLastTranscriber().getValue())));
			}

			transcriptionLog.setItems(listLog);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	public void loadScanningPagesLog(DocumentRow dR) {

		pageScanNumber.setCellValueFactory(new PropertyValueFactory<PageScanningLog, String>("pageNumber"));
		scanningReviser.setCellValueFactory(new PropertyValueFactory<PageScanningLog, String>("scanningReviser"));
		scanningRevised.setCellValueFactory(new PropertyValueFactory<PageScanningLog, String>("scanningRevised"));
		scanningValidated.setCellValueFactory(new PropertyValueFactory<PageScanningLog, String>("scanningValidated"));
		scanningDigitalizer
				.setCellValueFactory(new PropertyValueFactory<PageScanningLog, String>("scanningDigitalizer"));

		listScanLog = FXCollections.observableArrayList();

		try {
			PageScanController.loadScanningLog(ScanningWorkProjectQuerySet.loadUUIDDocument(dR.getIdSPrj()));

			if (listScanLog != null) {
				listScanLog.clear();
				scanningLog.refresh();
			}

			LinkedList<Page> pageList = PageScanController.getScanningLog();
			Iterator<Page> itr = pageList.iterator();

			while (itr.hasNext()) {
				Page p = itr.next();

				String format1 = String.valueOf(p.getScan().getRevised());

				if (format1.equalsIgnoreCase("true"))
					format1 = "\u2714";

				else if (format1.equals("false"))
					format1 = "\u2718";

				String format2 = String.valueOf(p.getScan().getValidated());

				if (format2.equalsIgnoreCase("true"))
					format2 = "\u2714";

				else if (format2.equals("false"))
					format2 = "\u2718";

				listScanLog.add(new PageScanningLog(String.valueOf(p.getPageNumber()),
						String.valueOf(p.getScan().getStaff().getReviser().getValue()),
						format1, format2,
						String.valueOf(p.getScan().getStaff().getDigitalizer().getValue())));

			}
			scanningLog.setItems(listScanLog);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	public void loadTranscriptionProjectStaff(DocumentRow dR) {
		TranscriptionProjectController.loadTranscriptionProject(dR.getIdTPrj());

		usernameTranscriber.setCellValueFactory(new PropertyValueFactory<StaffRow, String>("username"));
		roleTranscriber.setCellValueFactory(new PropertyValueFactory<StaffRow, String>("role"));

		transcriptionProjectStaff = FXCollections.observableArrayList();

		for (UUIDUser user : TranscriptionProjectController.getTPrj().getTranscribers()) {
			User u = TranscriptionProjectController.getUserProfile(user);
			transcriptionProjectStaff.add(new StaffRow(u.getUsername(), "Transcriber"));
		}

		for (UUIDUser user : TranscriptionProjectController.getTPrj().getRevisers()) {
			User u = TranscriptionProjectController.getUserProfile(user);
			transcriptionProjectStaff.add(new StaffRow(u.getUsername(), "Reviser"));
		}

		User coordinator = TranscriptionProjectController
				.getUserProfile((TranscriptionProjectController.getTPrj().getCoordinator()));

		transcriptionProjectStaff.add(new StaffRow(coordinator.getUsername(), "Coordinator"));

		transcriptionStaff.setItems(transcriptionProjectStaff);
	}

	public void loadScanningProjectStaff(DocumentRow dR) {
		ScanningProjectController.loadScanningProject(dR.getIdSPrj());

		usernameDigitalizer.setCellValueFactory(new PropertyValueFactory<StaffRow, String>("username"));
		roleDigitalizer.setCellValueFactory(new PropertyValueFactory<StaffRow, String>("role"));

		scanningProjectStaff = FXCollections.observableArrayList();

		for (UUIDUser user : ScanningProjectController.getSPrj().getDigitalizers()) {
			User u = ScanningProjectController.getUserProfile(user);
			scanningProjectStaff.add(new StaffRow(u.getUsername(), "Digitalizer"));
		}

		for (UUIDUser user : ScanningProjectController.getSPrj().getRevisers()) {
			User u = ScanningProjectController.getUserProfile(user);
			scanningProjectStaff.add(new StaffRow(u.getUsername(), "Reviser"));
		}

		User coordinator = ScanningProjectController
				.getUserProfile((ScanningProjectController.getSPrj().getCoordinator()));

		scanningProjectStaff.add(new StaffRow(coordinator.getUsername(), "Coordinator"));

		scanningStaff.setItems(scanningProjectStaff);
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
