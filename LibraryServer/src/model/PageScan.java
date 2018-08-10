package model;

import vo.Image;

public class PageScan {
	
	//variabili istanza
	private Image image;
	private Boolean validated;
	private Boolean revised;
	private PageScanStaff staff;
	
	// costruttore
	public PageScan(Image img, Boolean val, Boolean rev, PageScanStaff staff) {
		this.image=img;
		this.validated=val;
		this.revised=rev;
		this.staff=staff;
	}

	// metodi get e set
	
	public Image getImage() {
		return this.image;
	}
	
	public Boolean getValidated() {
		return this.validated;
	}
	
	public Boolean getRevised() {
		return this.revised;
	}
	
	public PageScanStaff getStaff() {
		return this.staff;
	}
}
