package fx_view;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import controller.LocalSession;
import controller.PageScanController;
import controller.PageTranscriptionController;
import controller.RoleController;
import controller.ScanningProjectController;
import controller.TranscriptionProjectController;
import dao.DatabaseException;
import dao.ScanningWorkProjectQuerySet;
import dao.TranscriptionWorkProjectQuerySet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
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
	private Button newDocumentButton;

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

	private UUIDScanningWorkProject selectedDocumentScanningProject;

	private UUIDTranscriptionWorkProject selectedDocumentTranscriptionProject;

	private DocumentRow clickedDocument;

	public void initialize() {
		initNewDocumentButton();

		try {
			tableDocumentFiller();
			rowClick();
			userRowClick();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void initNewDocumentButton() {
		newDocumentButton.setFont(new Font(24));
		// dd.setPrefSize(20,50);
		newDocumentButton.setMinSize(60, 45);
		newDocumentButton.setMaxSize(60, 45);
		newDocumentButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			SceneController.loadScene("newDocument");
		});
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

		for (Entry<UUIDDocument, DocumentRow> entry : document.entrySet()) {
			if (entry.getValue().getIdTPrj() == null)
				if (TranscriptionWorkProjectQuerySet.ifExistTranscriptionProject(entry.getKey())) {
					DocumentRow transcription = entry.getValue();
					transcription.setTranscription_PRJ("Not allowed");
					entry.setValue(transcription);
				} else {
					DocumentRow transcription = entry.getValue();
					transcription.setTranscription_PRJ("\u2204");
					entry.setValue(transcription);
				}

			if (entry.getValue().getIdSPrj() == null)
				if (ScanningWorkProjectQuerySet.ifExistScanningProject(entry.getKey())) {
					DocumentRow scanning = entry.getValue();
					scanning.setScanning_PRJ("Not allowed");
					entry.setValue(scanning);
				} else {
					DocumentRow scanning = entry.getValue();
					scanning.setScanning_PRJ("\u2204");
					entry.setValue(scanning);
				}

			rows.add(entry.getValue());
		}

		documentTable.setItems(rows);
	}

	// TODO controllare gli eventconsume
	public void rowClick() {
		documentTable.setRowFactory(tv -> {
			TableRow<DocumentRow> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					clickedDocument = row.getItem();
					loadClickedDocumentProjects();

				}
			});
			return row;
		});
	}

	public void loadClickedDocumentProjects() {

		selectedDocumentScanningProject = clickedDocument.getIdSPrj();
		selectedDocumentTranscriptionProject = clickedDocument.getIdTPrj();

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

		if (clickedDocument.getIdTPrj() != null) {
			loadTranscriptionPagesLog(clickedDocument);
			loadTranscriptionProjectStaff(clickedDocument);
		}

		if (clickedDocument.getIdSPrj() != null) {
			loadScanningPagesLog(clickedDocument);
			loadScanningProjectStaff(clickedDocument);
		}

	}

	public void userRowClick() {
		transcriptionStaff.setRowFactory(tv -> {
			TableRow<StaffRow> row = new TableRow<StaffRow>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					// se la riga cliccata non è di un utente coordinatore e chi clicca è un
					// coordinatore o admin
					if (!(row.getItem().getRole().equals("Coordinator"))
							&& (RoleController.controlUserPermission(8, LocalSession.getLocalUser().getID())
									|| RoleController.controlUserPermission(12, LocalSession.getLocalUser().getID()))) {

						List<String> choices = new ArrayList<>();
						choices.add("Transcriber");
						choices.add("Reviser");
						choices.add("Remove user from project");
						if (RoleController.controlUserPermission(12, LocalSession.getLocalUser().getID()) &&
								RoleController.controlUserPermission(8, row.getItem().getId())) {
							choices.add("Coordinator");
						}

						ChoiceDialog<String> dialog = new ChoiceDialog<>(row.getItem().getRole(), choices);
						dialog.setTitle("User role management");
						dialog.setHeaderText("Role for the user: " + row.getItem().getUsername());
						dialog.setContentText("Choose new role:");

						Optional<String> result = dialog.showAndWait();

						if (result.isPresent()) {
							if (result.get().equalsIgnoreCase(row.getItem().getRole())) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning");
								alert.setHeaderText("No Change");
								alert.setContentText("You haven't changed user's role");

								alert.showAndWait();
							} else if (result.get().equalsIgnoreCase("Transcriber")) {
								if (row.getItem().getRole().equalsIgnoreCase("Reviser")) {
									// toglilo dai reviser
									RoleController.removeReviserUserInTrascriptionProject(row.getItem().getId(),
											selectedDocumentTranscriptionProject);
									// mettilo nei trascriber
									RoleController.addTranscriberUserInTrascriptionProject(row.getItem().getId(),
											selectedDocumentTranscriptionProject);
								} else if (row.getItem().getRole().equalsIgnoreCase("Coordinator")) {
									// toglilo da coordinatore
									// scegli un nuovo coordinatore
									// mettilo fra i transcriber
									List<Formatter> scelta = new LinkedList<Formatter>();

									for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers()
											.entrySet()) {
										scelta.add(new Formatter(e.getKey(), e.getValue()));
									}

									if (!(scelta.isEmpty())) {

										ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
										dialogo.setTitle("User Role Management");
										dialogo.setHeaderText("You must choose a new Coordinator");
										dialogo.setContentText("Available Coordinators:");
										Optional<Formatter> risultato = dialogo.showAndWait();
										if (risultato.isPresent()) {
											Formatter f = dialogo.getResult();
											RoleController.changeTransriptionProjectCoordinator(f.getIdUser(),
													selectedDocumentTranscriptionProject);
											RoleController.addTranscriberUserInTrascriptionProject(
													row.getItem().getId(), selectedDocumentTranscriptionProject);
										}
									} else {
										Alert alert = new Alert(AlertType.WARNING);
										alert.setTitle("Warning");
										alert.setHeaderText("No Staff");
										alert.setContentText("Add coordinators to your staff");

										alert.showAndWait();
									}

								}

							} else if (result.get().equalsIgnoreCase("Reviser")) {
								if (row.getItem().getRole().equalsIgnoreCase("Transcriber")) {
									// toglilo dai transcriber

									RoleController.removeTranscriberUserInTrascriptionProject(row.getItem().getId(),
											selectedDocumentTranscriptionProject);
									// mettilo nei reviser
									RoleController.addReviserUserInTrascriptionProject(row.getItem().getId(),
											selectedDocumentTranscriptionProject);
								} else if (row.getItem().getRole().equalsIgnoreCase("Coordinator")) {
									// toglilo da coordinatore
									// scegli un nuovo coordinatore
									// mettilo nei reviser
									List<Formatter> scelta = new LinkedList<Formatter>();

									for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers()
											.entrySet()) {
										scelta.add(new Formatter(e.getKey(), e.getValue()));
									}

									if (!(scelta.isEmpty())) {

										ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
										dialogo.setTitle("User Role Management");
										dialogo.setHeaderText("You must choose a new Coordinator");
										dialogo.setContentText("Available Coordinators:");
										Optional<Formatter> risultato = dialogo.showAndWait();
										if (risultato.isPresent()) {
											Formatter f = dialogo.getResult();
											RoleController.changeTransriptionProjectCoordinator(f.getIdUser(),
													selectedDocumentTranscriptionProject);
											RoleController.addReviserUserInTrascriptionProject(row.getItem().getId(),
													selectedDocumentTranscriptionProject);
										}
									} else {
										Alert alert = new Alert(AlertType.WARNING);
										alert.setTitle("Warning");
										alert.setHeaderText("No Staff");
										alert.setContentText("Add coordinators to your staff");

										alert.showAndWait();
									}

								} // parentesi
							} else if (result.get().equalsIgnoreCase("Remove user from project")) {
								if (row.getItem().getRole().equalsIgnoreCase("Reviser")) {
									// toglilo dai revisori
									RoleController.removeReviserUserInTrascriptionProject(row.getItem().getId(),
											selectedDocumentTranscriptionProject);
								} else if (row.getItem().getRole().equalsIgnoreCase("Transcriber")) {
									// toglilo dai trascrittori
									RoleController.removeTranscriberUserInTrascriptionProject(row.getItem().getId(),
											selectedDocumentTranscriptionProject);
								}

							} else if (result.get().equalsIgnoreCase("Coordinator")) {

								// scegli un nuovo coordinatore
								List<Formatter> scelta = new LinkedList<Formatter>();

								for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
									scelta.add(new Formatter(e.getKey(), e.getValue()));
								}

								if (!(scelta.isEmpty())) {

									RoleController.changeTransriptionProjectCoordinator(row.getItem().getId() ,
											selectedDocumentTranscriptionProject);

								} else {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("No Staff");
									alert.setContentText("Add coordinators to your staff");

									alert.showAndWait();
								}
							}
						}

						// se la riga cliccata è di un utente coordinatore e chi clicca è un admin
					} else if (row.getItem().getRole().equals("Coordinator")
							&& RoleController.controlUserPermission(12, LocalSession.getLocalUser().getID())) {
						List<String> choices = new ArrayList<>();
						choices.add("Transcriber");
						choices.add("Reviser");
						choices.add("Coordinator");
						choices.add("Remove user from project");

						ChoiceDialog<String> dialog = new ChoiceDialog<>(row.getItem().getRole(), choices);
						dialog.setTitle("User role management");
						dialog.setHeaderText("Role for the user: " + row.getItem().getUsername());
						dialog.setContentText("Choose new role:");

						Optional<String> result = dialog.showAndWait();

						if (result.isPresent()) {
							if (result.get().equalsIgnoreCase(row.getItem().getRole())) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning");
								alert.setHeaderText("No Change");
								alert.setContentText("You haven't changed user's role");

								alert.showAndWait();
							} else if (result.get().equalsIgnoreCase("Transcriber")) {
								// rimuovi da coordinatore
								// aggiungi nuovo coordinatore
								// aggiungilo nei transcriber

								List<Formatter> scelta = new LinkedList<Formatter>();

								for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
									scelta.add(new Formatter(e.getKey(), e.getValue()));
								}

								if (!(scelta.isEmpty())) {

									ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
									dialogo.setTitle("User Role Management");
									dialogo.setHeaderText("You must choose a new Coordinator");
									dialogo.setContentText("Available Coordinators:");
									Optional<Formatter> risultato = dialogo.showAndWait();
									if (risultato.isPresent()) {
										Formatter f = dialogo.getResult();
										RoleController.changeTransriptionProjectCoordinator(f.getIdUser(),
												selectedDocumentTranscriptionProject);
										RoleController.addTranscriberUserInTrascriptionProject(row.getItem().getId(),
												selectedDocumentTranscriptionProject);
									}
								} else {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("No Staff");
									alert.setContentText("Add coordinators to your staff");

									alert.showAndWait();
								}
							} else if (result.get().equalsIgnoreCase("Reviser")) {
								// rimuovilo da coordinatore
								// aggiungi un nuovo coordinatore
								// aggiungiglo ai revisori

								List<Formatter> scelta = new LinkedList<Formatter>();

								for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
									scelta.add(new Formatter(e.getKey(), e.getValue()));
								}

								if (!(scelta.isEmpty())) {

									ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
									dialogo.setTitle("User Role Management");
									dialogo.setHeaderText("You must choose a new Coordinator");
									dialogo.setContentText("Available Coordinators:");
									Optional<Formatter> risultato = dialogo.showAndWait();
									if (risultato.isPresent()) {
										Formatter f = dialogo.getResult();
										RoleController.changeTransriptionProjectCoordinator(f.getIdUser(),
												selectedDocumentTranscriptionProject);
										RoleController.addReviserUserInTrascriptionProject(row.getItem().getId(),
												selectedDocumentTranscriptionProject);
									}
								} else {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("No Staff");
									alert.setContentText(
											"Add coordinators to your staff, impossible to change Coordinator");

									alert.showAndWait();
								}
							} else if (result.get().equalsIgnoreCase("Remove user from project")) {
								// rimuovilo da coordinatore
								// scegli un nuovo coordinatore

								List<Formatter> scelta = new LinkedList<Formatter>();

								for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
									scelta.add(new Formatter(e.getKey(), e.getValue()));
								}

								if (!(scelta.isEmpty())) {

									ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
									dialogo.setTitle("User Role Management");
									dialogo.setHeaderText("You must choose a new Coordinator");
									dialogo.setContentText("Available Coordinators:");
									Optional<Formatter> risultato = dialogo.showAndWait();
									if (risultato.isPresent()) {
										Formatter f = dialogo.getResult();
										RoleController.changeTransriptionProjectCoordinator(f.getIdUser(),
												selectedDocumentTranscriptionProject);
									}
								} else {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("No Staff");
									alert.setContentText("Add coordinators to your staff");

									alert.showAndWait();
								}
							}

						}
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText("Not authorized operation");
						alert.setContentText("You haven't permission to do that !");
						Toolkit.getDefaultToolkit().beep();
						alert.showAndWait();
					}
				}
				loadClickedDocumentProjects();
			});
			return row;

		});

		scanningStaff.setRowFactory(tv -> {
			TableRow<StaffRow> row = new TableRow<StaffRow>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					// se la riga cliccata non è di un utente coordinatore e chi clicca è un
					// coordinatore o admin
					if (!(row.getItem().getRole().equals("Coordinator"))
							&& (RoleController.controlUserPermission(8, LocalSession.getLocalUser().getID())
									|| RoleController.controlUserPermission(12, LocalSession.getLocalUser().getID()))) {

						List<String> choices = new ArrayList<>();
						choices.add("Digitalizer");
						choices.add("Reviser");
						choices.add("Remove user from project");
						if (RoleController.controlUserPermission(12, LocalSession.getLocalUser().getID()) &&
								RoleController.controlUserPermission(8, row.getItem().getId())) {
							choices.add("Coordinator");
						}

						ChoiceDialog<String> dialog = new ChoiceDialog<>(row.getItem().getRole(), choices);
						dialog.setTitle("User role management");
						dialog.setHeaderText("Role for the user: " + row.getItem().getUsername());
						dialog.setContentText("Choose new role:");

						Optional<String> result = dialog.showAndWait();

						if (result.isPresent()) {
							if (result.get().equalsIgnoreCase(row.getItem().getRole())) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning");
								alert.setHeaderText("No Change");
								alert.setContentText("You haven't changed user's role");

								alert.showAndWait();
							} else if (result.get().equalsIgnoreCase("Digitalizer")) {
								if (row.getItem().getRole().equalsIgnoreCase("Reviser")) {
									// toglilo dai reviser
									RoleController.removeReviserUserInScanningProject(row.getItem().getId(),
											selectedDocumentScanningProject);
									// mettilo nei digitalizer
									RoleController.addDigitalizerUserInScanningProject(row.getItem().getId(),
											selectedDocumentScanningProject);
								} else if (row.getItem().getRole().equalsIgnoreCase("Coordinator")) {
									// toglilo da coordinatore
									// scegli un nuovo coordinatore
									// mettilo fra i digitalizer
									List<Formatter> scelta = new LinkedList<Formatter>();

									for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers()
											.entrySet()) {
										scelta.add(new Formatter(e.getKey(), e.getValue()));
									}

									if (!(scelta.isEmpty())) {

										ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
										dialogo.setTitle("User Role Management");
										dialogo.setHeaderText("You must choose a new Coordinator");
										dialogo.setContentText("Available Coordinators:");
										Optional<Formatter> risultato = dialogo.showAndWait();
										if (risultato.isPresent()) {
											Formatter f = dialogo.getResult();
											RoleController.changeScanningProjectCoordinator(f.getIdUser(),
													selectedDocumentScanningProject);
											RoleController.addDigitalizerUserInScanningProject(
													row.getItem().getId(), selectedDocumentScanningProject);
										}
									} else {
										Alert alert = new Alert(AlertType.WARNING);
										alert.setTitle("Warning");
										alert.setHeaderText("No Staff");
										alert.setContentText("Add coordinators to your staff");

										alert.showAndWait();
									}

								}

							} else if (result.get().equalsIgnoreCase("Reviser")) {
								if (row.getItem().getRole().equalsIgnoreCase("Digitalizer")) {
									// toglilo dai digitalizer

									RoleController.removeDigitalizerUserInScanningProject(row.getItem().getId(),
											selectedDocumentScanningProject);
									// mettilo nei reviser
									RoleController.addReviserUserInScanningProject(row.getItem().getId(),
											selectedDocumentScanningProject);
								} else if (row.getItem().getRole().equalsIgnoreCase("Coordinator")) {
									// toglilo da coordinatore
									// scegli un nuovo coordinatore
									// mettilo nei reviser
									List<Formatter> scelta = new LinkedList<Formatter>();

									for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers()
											.entrySet()) {
										scelta.add(new Formatter(e.getKey(), e.getValue()));
									}

									if (!(scelta.isEmpty())) {

										ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
										dialogo.setTitle("User Role Management");
										dialogo.setHeaderText("You must choose a new Coordinator");
										dialogo.setContentText("Available Coordinators:");
										Optional<Formatter> risultato = dialogo.showAndWait();
										if (risultato.isPresent()) {
											Formatter f = dialogo.getResult();
											RoleController.changeScanningProjectCoordinator(f.getIdUser(),
													selectedDocumentScanningProject);
											RoleController.addReviserUserInScanningProject(row.getItem().getId(),
													selectedDocumentScanningProject);
										}
									} else {
										Alert alert = new Alert(AlertType.WARNING);
										alert.setTitle("Warning");
										alert.setHeaderText("No Staff");
										alert.setContentText("Add coordinators to your staff");

										alert.showAndWait();
									}

								} // parentesi
							} else if (result.get().equalsIgnoreCase("Remove user from project")) {
								if (row.getItem().getRole().equalsIgnoreCase("Reviser")) {
									// toglilo dai revisori
									RoleController.removeReviserUserInScanningProject(row.getItem().getId(),
											selectedDocumentScanningProject);
								} else if (row.getItem().getRole().equalsIgnoreCase("Digitalizer")) {
									// toglilo dai digitalizzatori
									RoleController.removeDigitalizerUserInScanningProject(row.getItem().getId(),
											selectedDocumentScanningProject);
								}

							} else if (result.get().equalsIgnoreCase("Coordinator")) {

								// scegli un nuovo coordinatore
								List<Formatter> scelta = new LinkedList<Formatter>();

								for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
									scelta.add(new Formatter(e.getKey(), e.getValue()));
								}

								if (!(scelta.isEmpty())) {

									RoleController.changeScanningProjectCoordinator(row.getItem().getId() ,
											selectedDocumentScanningProject);

								} else {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("No Staff");
									alert.setContentText("Add coordinators to your staff");

									alert.showAndWait();
								}
							}
						}

						// se la riga cliccata è di un utente coordinatore e chi clicca è un admin
					} else if (row.getItem().getRole().equals("Coordinator")
							&& RoleController.controlUserPermission(12, LocalSession.getLocalUser().getID())) {
						List<String> choices = new ArrayList<>();
						choices.add("Digitalizer");
						choices.add("Reviser");
						choices.add("Coordinator");
						choices.add("Remove user from project");

						ChoiceDialog<String> dialog = new ChoiceDialog<>(row.getItem().getRole(), choices);
						dialog.setTitle("User role management");
						dialog.setHeaderText("Role for the user: " + row.getItem().getUsername());
						dialog.setContentText("Choose new role:");

						Optional<String> result = dialog.showAndWait();

						if (result.isPresent()) {
							if (result.get().equalsIgnoreCase(row.getItem().getRole())) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning");
								alert.setHeaderText("No Change");
								alert.setContentText("You haven't changed user's role");

								alert.showAndWait();
							} else if (result.get().equalsIgnoreCase("Digitalizer")) {
								// rimuovi da coordinatore
								// aggiungi nuovo coordinatore
								// aggiungilo nei digitalizer

								List<Formatter> scelta = new LinkedList<Formatter>();

								for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
									scelta.add(new Formatter(e.getKey(), e.getValue()));
								}

								if (!(scelta.isEmpty())) {

									ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
									dialogo.setTitle("User Role Management");
									dialogo.setHeaderText("You must choose a new Coordinator");
									dialogo.setContentText("Available Coordinators:");
									Optional<Formatter> risultato = dialogo.showAndWait();
									if (risultato.isPresent()) {
										Formatter f = dialogo.getResult();
										RoleController.changeScanningProjectCoordinator(f.getIdUser(),
												selectedDocumentScanningProject);
										RoleController.addDigitalizerUserInScanningProject(row.getItem().getId(),
												selectedDocumentScanningProject);
									}
								} else {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("No Staff");
									alert.setContentText("Add coordinators to your staff");

									alert.showAndWait();
								}
							} else if (result.get().equalsIgnoreCase("Reviser")) {
								// rimuovilo da coordinatore
								// aggiungi un nuovo coordinatore
								// aggiungiglo ai revisori

								List<Formatter> scelta = new LinkedList<Formatter>();

								for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
									scelta.add(new Formatter(e.getKey(), e.getValue()));
								}

								if (!(scelta.isEmpty())) {

									ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
									dialogo.setTitle("User Role Management");
									dialogo.setHeaderText("You must choose a new Coordinator");
									dialogo.setContentText("Available Coordinators:");
									Optional<Formatter> risultato = dialogo.showAndWait();
									if (risultato.isPresent()) {
										Formatter f = dialogo.getResult();
										RoleController.changeScanningProjectCoordinator(f.getIdUser(),
												selectedDocumentScanningProject);
										RoleController.addReviserUserInScanningProject(row.getItem().getId(),
												selectedDocumentScanningProject);
									}
								} else {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("No Staff");
									alert.setContentText(
											"Add coordinators to your staff, impossible to change Coordinator");

									alert.showAndWait();
								}
							} else if (result.get().equalsIgnoreCase("Remove user from project")) {
								// rimuovilo da coordinatore
								// scegli un nuovo coordinatore

								List<Formatter> scelta = new LinkedList<Formatter>();

								for (Map.Entry<UUIDUser, String> e : RoleController.showCoordinatorUsers().entrySet()) {
									scelta.add(new Formatter(e.getKey(), e.getValue()));
								}

								if (!(scelta.isEmpty())) {

									ChoiceDialog<Formatter> dialogo = new ChoiceDialog<>(scelta.get(0), scelta);
									dialogo.setTitle("User Role Management");
									dialogo.setHeaderText("You must choose a new Coordinator");
									dialogo.setContentText("Available Coordinators:");
									Optional<Formatter> risultato = dialogo.showAndWait();
									if (risultato.isPresent()) {
										Formatter f = dialogo.getResult();
										RoleController.changeScanningProjectCoordinator(f.getIdUser(),
												selectedDocumentScanningProject);
									}
								} else {
									Alert alert = new Alert(AlertType.WARNING);
									alert.setTitle("Warning");
									alert.setHeaderText("No Staff");
									alert.setContentText("Add coordinators to your staff");

									alert.showAndWait();
								}
							}

						}
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText("Not authorized operation");
						alert.setContentText("You haven't permission to do that !");
						Toolkit.getDefaultToolkit().beep();
						alert.showAndWait();
					}
				}
				loadClickedDocumentProjects();
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
						String.valueOf(p.getScan().getStaff().getReviser().getValue()), format1, format2,
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
			transcriptionProjectStaff.add(new StaffRow(u.getUsername(), "Transcriber", u.getID()));
		}

		for (UUIDUser user : TranscriptionProjectController.getTPrj().getRevisers()) {
			User u = TranscriptionProjectController.getUserProfile(user);
			transcriptionProjectStaff.add(new StaffRow(u.getUsername(), "Reviser", u.getID()));
		}

		User coordinator = TranscriptionProjectController
				.getUserProfile((TranscriptionProjectController.getTPrj().getCoordinator()));

		transcriptionProjectStaff.add(new StaffRow(coordinator.getUsername(), "Coordinator", coordinator.getID()));

		transcriptionStaff.setItems(transcriptionProjectStaff);
	}

	public void loadScanningProjectStaff(DocumentRow dR) {
		ScanningProjectController.loadScanningProject(dR.getIdSPrj());

		usernameDigitalizer.setCellValueFactory(new PropertyValueFactory<StaffRow, String>("username"));
		roleDigitalizer.setCellValueFactory(new PropertyValueFactory<StaffRow, String>("role"));

		scanningProjectStaff = FXCollections.observableArrayList();

		for (UUIDUser user : ScanningProjectController.getSPrj().getDigitalizers()) {
			User u = ScanningProjectController.getUserProfile(user);
			scanningProjectStaff.add(new StaffRow(u.getUsername(), "Digitalizer", u.getID()));
		}

		for (UUIDUser user : ScanningProjectController.getSPrj().getRevisers()) {
			User u = ScanningProjectController.getUserProfile(user);
			scanningProjectStaff.add(new StaffRow(u.getUsername(), "Reviser", u.getID()));
		}

		User coordinator = ScanningProjectController
				.getUserProfile((ScanningProjectController.getSPrj().getCoordinator()));

		scanningProjectStaff.add(new StaffRow(coordinator.getUsername(), "Coordinator", coordinator.getID()));

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

	// ___
	final class Formatter {
		UUIDUser idUser;
		String username;

		public UUIDUser getIdUser() {
			return idUser;
		}

		public void setIdUser(UUIDUser idUser) {
			this.idUser = idUser;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Formatter(UUIDUser idUser, String username) {
			super();
			this.idUser = idUser;
			this.username = username;
		}

		@Override
		public String toString() {
			return username;
		}

	}
}
