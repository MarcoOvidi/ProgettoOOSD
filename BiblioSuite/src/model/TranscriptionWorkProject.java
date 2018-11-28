package model;

import java.util.Date;
import java.util.LinkedList;

import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;




public class TranscriptionWorkProject extends WorkProject{
	
	//variabili istanza
	private LinkedList<UUIDUser> transcribers;
	private LinkedList<UUIDUser> revisers;
	private UUIDTranscriptionWorkProject id;
	
	
	
	public TranscriptionWorkProject(Date date,UUIDUser coordinator,LinkedList<UUIDUser> transcribers, LinkedList<UUIDUser> revisers, UUIDTranscriptionWorkProject id, Boolean completed) {
		super(date,coordinator,completed);
		this.transcribers=transcribers;
		this.revisers=revisers;
		this.id=id;
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


	
	public UUIDTranscriptionWorkProject getUUID() {
		return this.id;
	}
	
	public void setUUID(UUIDTranscriptionWorkProject id) {
		this.id=id;
	}



	@Override
	public String toString() {
		return super.toString() + "TranscriptionWorkProject [transcribers=" + transcribers + ", revisers=" + revisers + ", id=" + id + "]";
	}
	
	
	
	
}
