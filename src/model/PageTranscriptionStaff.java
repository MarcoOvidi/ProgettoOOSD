package model;

import java.util.LinkedList;
import vo.UUID;

public class PageTranscriptionStaff {

	//variabili istanza
	private LinkedList<UUID> transcribers;
	private UUID reviser;
	
	//costruttore
	public PageTranscriptionStaff() {
		
	}
	
	// metodi get e set
	
	public LinkedList<UUID> getTranscribers(){
		return this.transcribers;
	}
	
	public UUID getReviser() {
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
