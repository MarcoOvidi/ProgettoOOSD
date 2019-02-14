package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.DatabaseException;
import dao.concrete.DigitalizationRevisorQuerySet;
import dao.concrete.DocumentQuerySet;
import dao.concrete.TranscriptionReviserQuerySet;
import dao.concrete.TranscriptionWorkProjectQuerySet;
import javafx.fxml.LoadException;
import model.Document;
import vo.Tag;
import vo.UUIDDocument;
import vo.UUIDDocumentMetadata;
import vo.UUIDPage;
import vo.VagueDate;

public class DocumentInfoController {
	private Document document;

	public DocumentInfoController(UUIDDocument document) throws LoadException, DatabaseException {
		loadDocument(document);
	}

	public void loadDocument(UUIDDocument id) throws LoadException, DatabaseException {
		document = new DocumentQuerySet().loadDocument(id);
	}
	
	public Document getDocument() {
		return this.document;
	}
	
	public String getPagesNumber() {
		return String.valueOf(document.getPagesNumber());
	}

	public String getTitle() {
		String res = "";
		if (document != null) {
			res += document.getTitle();
		}
		return res;
	}

	public String getAuthor() {
		String res = "";
		if (document != null) {
			res += document.getMetaData().getAuthor();
		}
		return res;
	}

	public String getComposition() {
		String res = "";
		if (document != null) {

			if (document.getMetaData().getCompositionDate() == null) {
				VagueDate vaguedate = document.getMetaData().getCompositionPeriod();
				res += "Between " + vaguedate.getFrom() + " and " + vaguedate.getTo();
			} else {
				res += document.getMetaData().getCompositionDate();
			}
		}

		return res;
	}

	public String getDescription() {
		String res = "";
		if (document != null) {
			res += document.getMetaData().getDescription();
		}
		return res;
	}

	public ArrayList<String> getTags() {
		ArrayList<String> res = new ArrayList<String>();
		if (document != null) {
			/*if (document.getMetaData().getTags() == null)
				return res;*/
			for (Tag tag : document.getMetaData().getTags()) {
				res.add(tag.toString());
			}
		}
		return res;
	}

	public boolean getScanningComplete() {
		if (document.getScanningWorkProject() != null) {
			ScanningProjectController.loadScanningProject(document.getScanningWorkProject());
			return ScanningProjectController.isCompleted();
		}
		return false;
	}

	public boolean getTranscriptionComplete() {
		if (document.getTranscriptionWorkProject() != null) {
			TranscriptionProjectController.loadTranscriptionProject(document.getTranscriptionWorkProject());
			return TranscriptionProjectController.isCompleted();
		}
		return false;
	}

	public void setScanningComplete() {
		setScanningComplete(true);
	}
	
	public void setScanningComplete(boolean b) {
		try {
			new DigitalizationRevisorQuerySet().scanningProcessCompleted(document.getScanningWorkProject(),b);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(b) {
			try {
				//TODO solo se non esiste già
				new TranscriptionWorkProjectQuerySet().insertTranscriptionWorkProject(LocalSessionBridge.getLocalUser().getID(),document.getUUID());
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setTranscriptionComplete() {
		setScanningComplete(true);
	}
	
	public void setTranscriptionComplete(boolean b) {
		try {
			new TranscriptionReviserQuerySet().completed(document.getTranscriptionWorkProject());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> res = new ArrayList<String>();
		if (document != null) {
			// FIXME manca
		}
		return res;
	}

	public static HashMap<Integer, Tag> getAvailableTags() {
		try {
			return new DocumentQuerySet().loadAvailableTag();
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static void setAvailableTagsToDocument(UUIDDocumentMetadata id, HashMap<Integer, Tag> tags) {
		try {
			new DocumentQuerySet().addTagToDocument(id, tags);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static UUIDDocumentMetadata getUUIDDocumentMetadata(UUIDDocument id) {
		try {
			return new DocumentQuerySet().getDocumentMetadataOfDocument(id);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static Integer insertNewTag(Tag tag) {
		try {
			return new DocumentQuerySet().insertNewTag(tag);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static UUIDPage getPageID(UUIDDocument id, int number) {
		try {
			return new DocumentQuerySet().getPageUUID(id, number);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getInvolvedCollections(UUIDDocument id){
		try {
			return new DocumentQuerySet().getInvolvedCollections(id);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<String> getInvolvedCollections(){
		return getInvolvedCollections(document.getUUID());
	}

}
