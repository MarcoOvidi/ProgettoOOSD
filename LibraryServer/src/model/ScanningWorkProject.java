package model;

import java.util.Date;
import java.util.LinkedList;
import vo.UUIDUser;
import vo.UUIDScanningWorkProject;

public class ScanningWorkProject extends WorkProject {
	
	

	//variabili istanza
	private LinkedList<UUIDUser> digitalizers;
	private LinkedList<UUIDUser> revisers;
	private UUIDScanningWorkProject id;
	
	public ScanningWorkProject(Date date,UUIDUser coord,LinkedList<UUIDUser> digitalizers, LinkedList<UUIDUser> revisers, UUIDScanningWorkProject id, Boolean completed) {
		super(date,coord,completed);
		this.digitalizers=digitalizers;
		this.revisers=revisers;
		this.setId(id);
	}
	
	
	//costruttore
	
	
	
	//metodi get e set
	
	
	public LinkedList<UUIDUser> getDigitalizers(){
		return this.digitalizers;
	}
	
	public LinkedList<UUIDUser> getRevisers(){
		return this.revisers;
	}
	

	//TODO implementare a multiplo inserimento
	public void setDigitalizer(UUIDUser user) {
		this.digitalizers.addLast(user);
	}
	
	
	public void setReviser(UUIDUser user) {
		this.revisers.addLast(user);
	}


	public UUIDScanningWorkProject getId() {
		return id;
	}


	public void setId(UUIDScanningWorkProject id) {
		this.id = id;
	}



	
	
	
	
	
	
}
