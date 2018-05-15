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
	
	public LinkedList<UUIDUSer> getTranscribers(){
		return this.transcribers;
	}
	
	public UUIDUser getReviser() {
		return this.reviser;
	}
	//TODO metodo che aggiunge una lista di utenti 
	public void setTranscribers(User u) {
		this.transcribers.add(u.getID());
	}
	//TODO vedere se impleentare con parametro UUID
	public void setReviser(User u) {
		this.reviser=u.getID();
	}
	
	
}
