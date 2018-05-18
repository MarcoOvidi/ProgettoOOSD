package model;

import vo.UUIDUser;
import java.util.LinkedList;
import vo.UUIDTranscriptionWorkProject;



public class TranscriptionWorkProject extends WorkProject{
	
	//variabili istanza
	private LinkedList<UUIDUser> transcribers;
	private LinkedList<UUIDUser> revisers;
	private UUIDTranscriptionWorkProject id;
	
	//costruttore
	public TranscriptionWorkProject() {
		super();
	}
	
	//metodi get e set
	public LinkedList<UUIDUser> getTranscribers(){
		return this.transcribers;
	}
	
	public LinkedList<UUIDUser> getRevisers(){
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
