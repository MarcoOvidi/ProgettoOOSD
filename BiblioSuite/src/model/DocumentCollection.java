package model;

import java.util.LinkedList;

import vo.UUIDDocument;
import vo.UUIDDocumentCollection;

public class DocumentCollection {
	//variabili istanza
	
	private UUIDDocumentCollection id;
	private String name;
	private LinkedList<UUIDDocument> documents;
	
	public DocumentCollection(UUIDDocumentCollection id, String name, LinkedList<UUIDDocument> documents) {
		this.id = id;
		this.name = name;
		this.documents = documents;
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
