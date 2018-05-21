package ServerApplication.model;

import ServerApplication.vo.UUIDUser;
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
	
	public void setDigitalizer(UUIDUser u) {
		this.digitalizer=u;
	}
	
	public void setReviser(UUIDUser u) {
		this.reviser=u;
	}
}
