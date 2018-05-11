package model;

import vo.UUID;
import java.util.LinkedList;



public class TranscriptionWorkProject extends WorkProject{
	
	//variabili istanza
	private LinkedList<UUID> transcribers;
	private LinkedList<UUID> revisers;
	
	//costruttore
	public TranscriptionWorkProject() {
		super();
	}
	
	//metodi get e set
	public LinkedList<UUID> getTranscribers(){
		return this.transcribers;
	}
	
	public LinkedList<UUID> getRevisers(){
		return this.revisers;
	}
	
	//TODO implementare per inserimenti multipli o con parametro UUID
	public void setTrascriber(User u) {
		this.transcribers.addLast(u.getID());
	}
	
	//TODO implementare per inserimenti multipli o con parametro UUID
	public void setRevisers(User u) {
		this.revisers.addLast(u.getID());
	}
	
}
