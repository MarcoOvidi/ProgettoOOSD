package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.DatabaseException;
import dao.DigitalizerQuerySet;
import dao.ScanningWorkProjectQuerySet;
import model.Document;
import model.Page;
import model.User;
import vo.UUIDDocument;
import vo.UUIDUser;

public class PageScanController {
	private static HashMap<UUIDDocument, String> uncompletedDocument = new HashMap<UUIDDocument,String>();
	private static LinkedList<Page> currentDocumentPages;
	
	//costruttore
	public PageScanController(User u, Document d) {
	}
	
	public void openWorkSession() {
		
	}
	
	public void saveDigitalizerModifications(/*modifications*/){
		
	}
	
	public void saveRevisorModifications(/*modifications+*/){
	
	}
	
	public void closeWorkSession() {
		
	}
	
	public static void loadUncompletedDocument(UUIDUser usr) {
		try{
			uncompletedDocument=ScanningWorkProjectQuerySet.getScanningUncompletedDocument(usr);
		}catch(DatabaseException e) {
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
	
	public static void loadNewDocumentPages(UUIDDocument doc,Boolean revision,Boolean convalidation) {
		try {
			currentDocumentPages = DigitalizerQuerySet.loadDocument(doc, revision, convalidation);
		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

	public static LinkedList<Page> getCurrentDocumentPages() {
		return currentDocumentPages;
	}

	public static void setCurrentDocument(LinkedList<Page> currentDocumentPages) {
		PageScanController.currentDocumentPages = currentDocumentPages;
	}
	
	
}
