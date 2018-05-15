package model;

import java.util.LinkedList;
import vo.UUIDDocumentCollection;
import vo.UUIDDocument;
import controller.SessionDataHandler;

public class DocumentCollection {
	//variabili istanza
	
	private UUIDDocumentCollection id;
	private SessionDataHandler session;
	private String name;
	private LinkedList<UUIDDocument> documents;
	
	
	//Costruttore
	//TODO
	public DocumentCollection() {

	}
	
	// metodi get e set
	
	public UUIDDocumentCollection getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public LinkedList<UUIDDocument> getDocuments() {
		return this.documents;
	}
	
	public void setID(UUIDDocumentCollection id) {
		this.id=id;
	}
	
	public void setName(String n) {
		this.name=n;
	}
	
	public void setDocuments(UUIDDocument id) {
		this.documents.addLast(id);
	}
	
	// metodi ausiliari
	
	public boolean addDocuments(LinkedList<UUIDDocument> l) {
		return this.documents.addAll(l);
	}
	
	public boolean removeDocument(UUIDDocument id) {
		return this.documents.remove(id);
	}
	
}
