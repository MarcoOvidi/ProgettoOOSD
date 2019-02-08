package controller;

import java.util.LinkedList;

import dao.concrete.DatabaseException;
import dao.concrete.DigitalizerQuerySet;
import dao.concrete.DocumentQuerySet;
import dao.concrete.TranscriberQuerySet;
import javafx.fxml.LoadException;
import model.Document;
import model.Page;
import view.SceneController;
import vo.UUIDDocument;

public class PageTranscriptionController {
	
	private static LinkedList<Page> currentDocumentPages;
	private static UUIDDocument currentDocument;
	private static LinkedList<Page> transcriptionLog;
	//private static int toEditPageNumber;
	
	
	public static void loadTranscriptionLog(UUIDDocument doc) {
		try{
			transcriptionLog = new TranscriberQuerySet().loadDocument(doc);
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
	
	public static void loadNewDocumentPagesOnlyNotRevised(UUIDDocument doc) {
		try {
			currentDocumentPages = new DigitalizerQuerySet().loadDocument(doc, false, false); // c'è un errore 
			
			
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
			doc = new DocumentQuerySet().loadDocumentToView(document);
		}catch(DatabaseException e) {
			e.getMessage();
			e.printStackTrace();
		}
		if(doc != null) {
			LocalSession.setOpenedDocumet(doc);
			//PageTranscriptionController.setToEditPageNumber(0);
			SceneController.loadScene("pageTranscription", false);
		}
		
	}
	
	/*public static void transcribeDocument(UUIDDocument document, int page) throws LoadException {
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
			PageTranscriptionController.setToEditPageNumber(page);
			SceneController.loadScene("pageTranscription", false);
		}
		
	}

	public static int getToEditPageNumber() {
		return toEditPageNumber;
	}

	public static void setToEditPageNumber(int toEditPageNumber) {
		PageTranscriptionController.toEditPageNumber = toEditPageNumber;
	}*/
	
	
	
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
