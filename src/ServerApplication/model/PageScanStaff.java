package model;

import vo.UUIDUser;
public class PageScanStaff {
	//variabili istanza
	
	private UUIDUser digitalizer;
	private UUIDUser reviser;
	
	//costruttore
	
	public PageScanStaff() {
		
	}
	
	//metodi get e set 
	
	public UUIDUser getDigitalizer() {
		return this.digitalizer;
	}
	
	public UUIDUser getReviser() {
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
