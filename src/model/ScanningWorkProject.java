package model;

import java.util.LinkedList;
import vo.UUID;

public class ScanningWorkProject extends WorkProject {
	
	//variabili istanza
	private LinkedList<UUID> digitalizers;
	private LinkedList<UUID> revisers;
	private Boolean completed;
	
	//costruttore
	public ScanningWorkProject() {
		super();
	}
	
	//metodi get e set
	
	public Boolean getCompleted() {
		return this.completed;
	}
	
	public LinkedList<UUID> getDigitalizers(){
		return this.digitalizers;
	}
	
	public LinkedList<UUID> getRevisers(){
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
