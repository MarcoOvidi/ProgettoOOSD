package model;

import vo.UUID;
public class PageScanStaff {
	//variabili istanza
	
	private UUID digitalizer;
	private UUID reviser;
	
	//costruttore
	
	public PageScanStaff() {
		
	}
	
	//metodi get e set 
	
	public UUID getDigitalizer() {
		return this.digitalizer;
	}
	
	public UUID getReviser() {
		return this.reviser;
	}
	
	//TODO conviene passare in input direttamente l'UUID?
	public void setDigitalizer(User u) {
		this.digitalizer=u.getID();
	}
	
	public void setReviser(User u) {
		this.reviser=u.getID();
	}
}
