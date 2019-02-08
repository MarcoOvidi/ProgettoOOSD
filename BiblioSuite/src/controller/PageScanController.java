package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.DatabaseException;
import dao.concrete.DigitalizerQuerySet;
import dao.concrete.ScanningWorkProjectQuerySet;
import javafx.scene.image.Image;
import model.Document;
import model.Page;
import model.User;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDUser;

public class PageScanController {
	private static HashMap<UUIDDocument, String> uncompletedDocument = new HashMap<UUIDDocument, String>();
	private static UUIDDocument currentDocument;
	private static LinkedList<Page> currentDocumentPages;
	private static LinkedList<Page> scanningLog;

	// costruttore
	public PageScanController(User u, Document d) {
	}

	public void openWorkSession() {

	}
	
	public static void loadScanningLog(UUIDDocument doc) {
		try{
			setScanningLog(new DigitalizerQuerySet().loadDocument(doc, false, false));
			scanningLog.addAll(new DigitalizerQuerySet().loadDocument(doc, false, true));
			scanningLog.addAll(new DigitalizerQuerySet().loadDocument(doc, true, false));
			scanningLog.addAll(new DigitalizerQuerySet().loadDocument(doc, true, true));
		}catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	public void saveDigitalizerModifications(/* modifications */) {

	}

	public void saveRevisorModifications(/* modifications+ */) {

	}

	public void closeWorkSession() {

	}

	public static void loadUncompletedDocumentForDigitalizer(UUIDUser usr) {
		try {
			uncompletedDocument = new ScanningWorkProjectQuerySet().getScanningUncompletedDocumentDigitalizer(usr);
		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void loadUncompletedDocumentForReviser(UUIDUser usr) {
		try {
			uncompletedDocument = new ScanningWorkProjectQuerySet().getScanningUncompletedDocumentReviser(usr);
		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	

	public static HashMap<UUIDDocument, String> getUncompletedDocument() {
		return uncompletedDocument;
	}

	public static void setUncompletedDocument(HashMap<UUIDDocument, String> uncompleted_Document) {
		uncompletedDocument = uncompleted_Document;
	}

	public static void loadNewDocumentPages(UUIDDocument doc) {
		try {
			currentDocumentPages = new DigitalizerQuerySet().loadDocument(doc, false, false);
			currentDocumentPages.addAll(new DigitalizerQuerySet().loadDocument(doc, false, true));
			currentDocumentPages.addAll(new DigitalizerQuerySet().loadDocument(doc, true, false));
			currentDocumentPages.addAll(new DigitalizerQuerySet().loadDocument(doc, true, true));
			currentDocument = doc;
		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void loadNewDocumentPagesOnlyToRevise(UUIDDocument doc) {
		try {
			currentDocumentPages = new DigitalizerQuerySet().loadDocument(doc, false, false);
			currentDocumentPages.addAll(new DigitalizerQuerySet().loadDocument(doc, true, true));
			currentDocumentPages.addAll(new DigitalizerQuerySet().loadDocument(doc, false, true));
			currentDocumentPages.addAll(new DigitalizerQuerySet().loadDocument(doc, true, false));
			currentDocument = doc;
		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void loadUncompletedDocumentPagesFilters(UUIDDocument doc, Boolean revised, Boolean validated) {
		try {
			currentDocumentPages = new DigitalizerQuerySet().loadDocument(doc, revised, validated);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static LinkedList<Page> getCurrentDocumentPages() {
		return currentDocumentPages;
	}

	public static void updatePageNumber(UUIDPage id, Integer number) {
		try {
			new DigitalizerQuerySet().updatePageNumber(id, number);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void setCurrentDocument(LinkedList<Page> currentDocumentPages) {
		PageScanController.currentDocumentPages = currentDocumentPages;
	}
		
	public static UUIDPage createNewPage(Image image, int num) {
		UUIDPage page = new UUIDPage(-1);
		vo.Image img = new vo.Image("temp");
		
		try {
			page = new DigitalizerQuerySet().createPage(num, img, LocalSession.localUser.getID(), currentDocument);
			
			String URL;
			URL = ImageUploader.uploadImage(image, page.getValue().toString());
			img = new vo.Image(URL);
			
			new DigitalizerQuerySet().updatePage(page, img);
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(">>> Error creating file: Reverting changes to database...");
			try {
				new DigitalizerQuerySet().deletePage(page);
				System.err.println("Done.");
			} catch (DatabaseException e1) {
				e1.printStackTrace();
				System.err.println(">>> Cannot revert changes, leaving database in an unsafe state!");
			}
		}
		return page;
	}

	public static LinkedList<Page> getScanningLog() {
		return scanningLog;
	}

	public static void setScanningLog(LinkedList<Page> scanningLog) {
		PageScanController.scanningLog = scanningLog;
	}

}
