package controller;

import java.util.LinkedList;

import dao.DatabaseException;
import dao.DigitalizerQuerySet;
import dao.DocumentQuerySet;
import dao.TranscriberQuerySet;
import javafx.fxml.LoadException;
import model.Document;
import model.Page;
import view.SceneController;
import vo.UUIDDocument;

public class PageTranscriptionController {
	
	private static LinkedList<Page> currentDocumentPages;
	private static UUIDDocument currentDocument;
	private static LinkedList<Page> transcriptionLog;
	
	
	public static void loadTranscriptionLog(UUIDDocument doc) {
		try{
			transcriptionLog = TranscriberQuerySet.loadDocument(doc);
		}catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	public static LinkedList<Page> getTranscriptionLog() {
		return transcriptionLog;
	}



	public static void setTranscriptionLog(LinkedList<Page> transcriptionLog) {
		PageTranscriptionController.transcriptionLog = transcriptionLog;
	}



	public static LinkedList<Page> getCurrentDocumentPages() {
		return currentDocumentPages;
	}



	public static void setCurrentDocumentPages(LinkedList<Page> currentDocumentPages) {
		PageTranscriptionController.currentDocumentPages = currentDocumentPages;
	}



	public static UUIDDocument getCurrentDocument() {
		return currentDocument;
	}



	public static void setCurrentDocument(UUIDDocument currentDocument) {
		PageTranscriptionController.currentDocument = currentDocument;
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
	
	public static void transcribeDocument(UUIDDocument document) throws LoadException {
		Document doc = null;
		try{
			//doc = DocumentQuerySet.loadDocument(document);
			//FIXME potrebbe causare problemi questa cosa?
			doc = DocumentQuerySet.loadDocumentToView(document);
		}catch(DatabaseException e) {
			e.getMessage();
			e.printStackTrace();
		}
		if(doc != null) {
			LocalSession.setOpenedDocumet(doc);
			SceneController.loadScene("pageTranscription", false);
		}
		
	}
	
	
	
	/*
	 * //costruttore public PageTranscriptionController(User u, Document d) {
	 * 
	 * }
	 * 
	 * public void openWorkSession() {
	 * 
	 * }
	 * 
	 * public void closeWorkSession() {
	 * 
	 * }
	 * 
	 * public void saveRevisorModifications(/*modifications+){
	 * 
	 * }
	 * 
	 * public void saveTranscriberModifications(/*modifications+){
	 * 
	 * }
	 */

}
