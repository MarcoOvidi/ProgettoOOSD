package model;

import java.util.LinkedList;
import vo.UUIDUser;

public class PageTranscriptionStaff {

	//variabili istanza
	private LinkedList<UUIDUser> transcribers;
	private UUIDUser reviser;
	
	//costruttore
	public PageTranscriptionStaff() {
		
	}
	
	// metodi get e set
	
	public LinkedList<UUIDUser> getTranscribers(){
		return this.transcribers;
	}
	
	public UUIDUser getReviser() {
		return this.reviser;
	}
	//TODO metodo che aggiunge una lista di utenti 
	public void setTranscribers(UUIDUser u) {
		this.transcribers.add(u);
	}
	
	public void setReviser(UUIDUser u) {
		this.reviser=u;
	}
	
	
}
