package model;

import java.util.LinkedList;
import vo.UUIDUser;

public class PageTranscriptionStaff {

	//variabili istanza
	private LinkedList<UUIDUser> transcribers;
	private UUIDUser lastTranscriber;
	private UUIDUser reviser;
	
	//costruttore
	public PageTranscriptionStaff(LinkedList<UUIDUser> trans, UUIDUser rev) {
		this.transcribers=trans;
		this.reviser=rev;
	}
	
	public PageTranscriptionStaff(UUIDUser lastTranscriber, UUIDUser reviser) {
		super();
		this.lastTranscriber = lastTranscriber;
		this.reviser = reviser;
	}

	public PageTranscriptionStaff(LinkedList<UUIDUser> trans) {
		this.transcribers=trans;
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
	public UUIDUser getLastTranscriber() {
		return lastTranscriber;
	}
	public void setLastTranscriber(UUIDUser lastTranscriber) {
		this.lastTranscriber = lastTranscriber;
	}
	
	
}
