package model;

import java.util.LinkedList;
import vo.UUID;
import controller.SessionDataHandler;

public class DocumentCollection {
	//variabili istanza
	
	private UUID id;
	private SessionDataHandler session;
	private String name;
	private LinkedList<UUID> documents;
	
	
	//Costruttore
	//TODO
	public DocumentCollection() {

	}
	
	// metodi get e set
	
	public UUID getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public LinkedList<UUID> getDocuments() {
		return this.documents;
	}
	
	public void setID(UUID id) {
		this.id=id;
	}
	
	public void setName(String n) {
		this.name=n;
	}
	
	public void setDocuments(UUID id) {
		this.documents.addLast(id);
	}
	
	// metodi ausiliari
	
	public boolean addDocuments(LinkedList<UUID> l) {
		return this.documents.addAll(l);
	}
	
	public boolean removeDocument(UUID id) {
		return this.documents.remove(id);
	}
	
}
