package model;

import java.util.Date;
import java.util.LinkedList;
import vo.UUIDUser;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;

public class ScanningWorkProject extends WorkProject {
	
	

	//variabili istanza
	private LinkedList<UUIDUser> digitalizers;
	private LinkedList<UUIDUser> revisers;
	private UUIDScanningWorkProject id;
	
	public ScanningWorkProject(Date d,UUIDUser coord,LinkedList<UUIDUser> dg, LinkedList<UUIDUser> rev, UUIDScanningWorkProject id, Boolean b) {
		super(d,coord,b);
		this.digitalizers=dg;
		this.revisers=rev;
		this.id=id;
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
	public void setDigitalizers(UUIDUser u) {
		this.digitalizers.addLast(u);
	}
	
	
	public void setRevisers(UUIDUser u) {
		this.revisers.addLast(u);
	}



	
	
	
	
	
	
}
