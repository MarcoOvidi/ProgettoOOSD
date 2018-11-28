package controller;

import java.util.HashMap;

import dao.DatabaseException;
import dao.DocumentQuerySet;
import dao.EditProfileQuerySet;
import dao.ScanningWorkProjectQuerySet;
import dao.TranscriptionWorkProjectQuerySet;
import javafx.fxml.LoadException;
import model.Document;
import model.DocumentCollection;
import model.ScanningWorkProject;
import model.TranscriptionWorkProject;
import model.User;
import vo.UUIDDocument;
import vo.UUIDDocumentCollection;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
//import vo.UUID;
import vo.UUIDUser;

//FIXME verificare metodi e classi mancanti nel dao (o da rinominare), sia qui che lì
public final class SessionDataHandler {
		//variabili istanza
	//HashMap<UUID, Object> map;
	HashMap<UUIDUser, User> mapUser;
	HashMap<UUIDDocument, Document> mapDocument;
	HashMap<UUIDDocumentCollection, DocumentCollection> mapDocumentCollection;
	HashMap<UUIDScanningWorkProject, ScanningWorkProject> mapScanningWorkProject;
	HashMap<UUIDTranscriptionWorkProject, TranscriptionWorkProject> mapTranscriptionWorkProject;
	
	//TODO error handling
	private static SessionDataHandler instance = new SessionDataHandler();
	
	private SessionDataHandler() {		
	}
	
	public static final SessionDataHandler getInstance() {
		return instance;
	}
	
	/**
	 * Restituisce il numero di documenti in memoria
	 * @return
	 */
	public int numberOfDocuments () {
		return mapDocument.size();
	}
	
	public void addDocumentCollection(UUIDDocumentCollection id) {
		
	}
	
	public void removeCollection(UUIDDocumentCollection id) {
		
	}
		
	public Document getDocument(UUIDDocument id) throws DatabaseException {
		if (!mapDocument.containsKey(id)) {
			Document doc = null;
			try {
				doc = DocumentQuerySet.loadDocument(id);
			} catch (LoadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return doc;
		}
		else 
			return mapDocument.get(id);
	}
	
	public User getUser(UUIDUser id) throws DatabaseException {
		if (!mapUser.containsKey(id)) {
			//TODO il nome della classe non è affatto chiaro qui 
			User user = EditProfileQuerySet.loadUserProfile(id);
			return user;
		}
		return mapUser.get(id);
	}
	
	public DocumentCollection getCollection(UUIDDocumentCollection id) {
		if (!mapDocumentCollection.containsKey(id)) {
			DocumentCollection docCol=null;
			//FIXME quale classe del dao?
		//	docCol = DocumentCollectionQuerySet.loadDocumentCollection(id);
			return docCol;
		}
		return mapDocumentCollection.get(id);
	}
	
	public ScanningWorkProject getScanningWorkProject(UUIDScanningWorkProject id) throws NullPointerException, DatabaseException {
		if (!mapScanningWorkProject.containsKey(id)) {
			ScanningWorkProject swp;
			swp = ScanningWorkProjectQuerySet.loadScanningWorkProject(id);
			return swp;
		}
		return mapScanningWorkProject.get(id);
	}
	
	public TranscriptionWorkProject getTranscriptionWorkProject(UUIDTranscriptionWorkProject id) throws NullPointerException, DatabaseException {
		if (!mapTranscriptionWorkProject.containsKey(id)) {
			TranscriptionWorkProject twp;
			twp = TranscriptionWorkProjectQuerySet.loadTranscriptionWorkProject(id);
			return twp;
		}
		return mapTranscriptionWorkProject.get(id);
	}
}
