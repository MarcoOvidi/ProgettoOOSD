package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import dao.DBConnection;
import dao.DatabaseException;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;




public class TranscriptionWorkProject extends WorkProject{
	
	//variabili istanza
	private LinkedList<UUIDUser> transcribers;
	private LinkedList<UUIDUser> revisers;
	private UUIDTranscriptionWorkProject id;
	
	
	
	public TranscriptionWorkProject(Date d,UUIDUser coord,LinkedList<UUIDUser> tr, LinkedList<UUIDUser> rev, UUIDTranscriptionWorkProject id, Boolean b) {
		super(d,coord,b);
		this.transcribers=tr;
		this.revisers=rev;
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
