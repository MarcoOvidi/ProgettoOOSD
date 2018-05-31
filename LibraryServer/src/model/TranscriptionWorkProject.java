package model;

import vo.UUIDUser;
import vo.UUIDDocument;
import vo.UUIDTranscriptionWorkProject;
import java.util.LinkedList;




public class TranscriptionWorkProject extends WorkProject{
	
	//variabili istanza
	private LinkedList<UUIDUser> transcribers;
	private LinkedList<UUIDUser> revisers;
	private UUIDTranscriptionWorkProject id;
	private Boolean completed;
	
	
	//costruttore
	public TranscriptionWorkProject(UUIDTranscriptionWorkProject id,String t) {
		this.documentTitle=t;
		this.id=id;
	}
	
	public TranscriptionWorkProject() {
		super();
	}
	
	public TranscriptionWorkProject(Integer prjID) {
		this.id= new UUIDTranscriptionWorkProject(prjID);
	}
	
	//metodi get e set
	public LinkedList<UUIDUser> getTranscribers(){
		return this.transcribers;
	}
	
	public LinkedList<UUIDUser> getRevisers(){
		return this.revisers;
	}
	
	//TODO implementare per inserimenti multipli 
	public void setTrascriber(UUIDUser u) {
		this.transcribers.addLast(u);
	}
	
	//TODO implementare per inserimenti multipli o con parametro UUID
	public void setRevisers(UUIDUser u) {
		this.revisers.addLast(u);
	}

	public void setComplete(Boolean com) {
		this.completed=com;
	}
	
	public Boolean getComplete() {
		return this.completed;
	}
	
	
}
