package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.DatabaseException;
import dao.DigitalizerQuerySet;
import dao.ScanningWorkProjectQuerySet;
import javafx.scene.image.Image;
import model.Document;
import model.Page;
import model.User;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;
import vo.UUIDUser;

public class PageScanController {
	private static HashMap<UUIDDocument, String> uncompletedDocument = new HashMap<UUIDDocument, String>();
	private static UUIDDocument currentDocument;
	private static LinkedList<Page> currentDocumentPages;

	// costruttore
	public PageScanController(User u, Document d) {
	}

	public void openWorkSession() {

	}

	public void saveDigitalizerModifications(/* modifications */) {

	}

	public void saveRevisorModifications(/* modifications+ */) {

	}

	public void closeWorkSession() {

	}

	public static void loadUncompletedDocument(UUIDUser usr) {
		try {
			uncompletedDocument = ScanningWorkProjectQuerySet.getScanningUncompletedDocument(usr);
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
			currentDocumentPages = DigitalizerQuerySet.loadDocument(doc, false, false);
			currentDocumentPages.addAll(DigitalizerQuerySet.loadDocument(doc, false, true));
			currentDocumentPages.addAll(DigitalizerQuerySet.loadDocument(doc, true, false));
			currentDocumentPages.addAll(DigitalizerQuerySet.loadDocument(doc, true, true));
			currentDocument = doc;
		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void setUncompletedDocumentPagesFilters(UUIDDocument doc, Boolean revised, Boolean validated) {
		try {
			currentDocumentPages = DigitalizerQuerySet.loadDocument(doc, revised, validated);
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
			DigitalizerQuerySet.updatePageNumber(id, number);
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
		vo.Image img = new vo.Image("");
		try {
			page = DigitalizerQuerySet.createPage(num, img , currentDocument);
			img = new vo.Image(currentDocument.toString() + page);
			ImageUploader.uploadImage(image, img.getUrl());
			DigitalizerQuerySet.updatePage(page, img);	
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return page;
	}

}
