package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.DatabaseException;
import dao.concrete.DigitalizerQuerySet;
import dao.concrete.DocumentQuerySet;
import dao.concrete.TranscriberQuerySet;
import dao.concrete.TranscriptionReviserQuerySet;
import dao.concrete.TranscriptionWorkProjectQuerySet;
import javafx.fxml.LoadException;
import model.Document;
import model.Page;
import view.SceneController;
import vo.TEItext;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDUser;

public class PageTranscriptionController {
	private static HashMap<UUIDDocument, String> uncompletedDocument = new HashMap<UUIDDocument, String>();
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

	public static void loadUncompletedDocumentForTranscriber(UUIDUser usr) {
		try {
			uncompletedDocument = new TranscriptionWorkProjectQuerySet().getTranscriptionUncompletedDocumentTranscriber(usr);		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void loadUncompletedDocumentForReviser(UUIDUser usr) {
		try {
			uncompletedDocument = new TranscriptionWorkProjectQuerySet().getTranscriptionUncompletedDocumentReviser(usr);
		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	

	public static HashMap<UUIDDocument, String> getUncompletedDocument() {
		return uncompletedDocument;
	}


	public static void setTranscriptionLog(LinkedList<Page> transcriptionLog) {
		PageTranscriptionController.transcriptionLog = transcriptionLog;
	}
	
	public static void saveTranscription(UUIDPage page, String transcription) {
		try {
			new TranscriberQuerySet().updateText(new TEItext(transcription), page);
		} catch (NullPointerException | DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static void reviewDocumentTranscription(UUIDDocument document) throws LoadException {
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
			System.out.println("PIPPO");
			SceneController.loadScene("pageTranscriptionReview", false);
		}
	}
	
	

	public static void setPageRevised(UUIDPage page, boolean b) {
		try {
			new TranscriptionReviserQuerySet().revised(page, b);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setPageValidated(UUIDPage page, boolean b) {
		try {
			new TranscriptionReviserQuerySet().validated(page, b);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
