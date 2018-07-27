package controller;

import java.util.HashMap;

//import vo.UUID;
import vo.UUIDUser;
import vo.UUIDDocument;
import vo.UUIDDocumentCollection;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;

import model.*;
import model.TranscriptionWorkProject;

import dao.*;

//FIXME verificare metodi e classi mancanti nel dao (o da rinominare), sia qui che lì
public class SessionDataHandler {
		//variabili istanza
	//HashMap<UUID, Object> map;
	HashMap<UUIDUser, User> mapUser;
	HashMap<UUIDDocument, Document> mapDocument;
	HashMap<UUIDDocumentCollection, DocumentCollection> mapDocumentCollection;
	HashMap<UUIDPage, Page> mapPage;
	HashMap<UUIDScanningWorkProject, ScanningWorkProject> mapScanningWorkProject;
	HashMap<UUIDTranscriptionWorkProject, TranscriptionWorkProject> mapTranscriptionWorkProject;
	
	//TODO error handling
	
	public SessionDataHandler(/*DB reference?*/) {
		
	}
	
	public void addDocumentCollection(UUIDDocumentCollection id) {
		
	}
	
	public void removeCollection(UUIDDocumentCollection id) {
		
	}
	
	public Page getPage(UUIDPage id) {
		if (!mapPage.containsKey(id)) {
			Page page=null;
			//page = PageQuerySet.loadPage(id);
			return page;
		}
		else 
		return mapPage.get(id);
	}
	
	public Document getDocument(UUIDDocument id) {
		if (!mapDocument.containsKey(id)) {
			Document doc;
			doc = DocumentQuerySet.loadDocument(id);
			return doc;
		}
		else 
			return mapDocument.get(id);
	}
	
	//FIXME dove verrà gestita l'eccezione?
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
	
	//TODO gestione eccezioni
	public ScanningWorkProject getScanningWorkProject(UUIDScanningWorkProject id) throws NullPointerException, DatabaseException {
		if (!mapScanningWorkProject.containsKey(id)) {
			ScanningWorkProject swp;
			swp = ScanningWorkProjectQuerySet.loadScanningWorkProject(id);
			return swp;
		}
		return mapScanningWorkProject.get(id);
	}
	
	//TODO gestione eccezioni
	public TranscriptionWorkProject getTranscriptionWorkProject(UUIDTranscriptionWorkProject id) throws NullPointerException, DatabaseException {
		if (!mapTranscriptionWorkProject.containsKey(id)) {
			TranscriptionWorkProject twp;
			twp = TranscriptionWorkProjectQuerySet.loadTranscriptionWorkProject(id);
			return twp;
		}
		return mapTranscriptionWorkProject.get(id);
	}

}