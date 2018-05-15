package model;

import java.util.LinkedList;
import vo.UUIDUser;
import vo.UUIDScanningWorkProject;

public class ScanningWorkProject extends WorkProject {
	
	//variabili istanza
	private LinkedList<UUIDUser> digitalizers;
	private LinkedList<UUIDUser> revisers;
	private Boolean completed;
	private UUIDScanningWorkProject id;
	
	//costruttore
	public ScanningWorkProject() {
		super();
	}
	
	//metodi get e set
	
	public Boolean getCompleted() {
		return this.completed;
	}
	
	public LinkedList<UUIDUser> getDigitalizers(){
		return this.digitalizers;
	}
	
	public LinkedList<UUIDUser> getRevisers(){
		return this.revisers;
	}
	
	public void setCompleted(Boolean s) {
		this.completed=s;
	}
	//TODO implementare con parametro diretto UUID e a multiplo inserimento
	public void setDigitalizers(User u) {
		this.digitalizers.addLast(u.getID());
	}
	
	//TODO implementare con parametro diretto UUID e a multiplo inserimento
	public void setRevisers(User u) {
		this.revisers.addLast(u.getID());
	}
	
	
}
