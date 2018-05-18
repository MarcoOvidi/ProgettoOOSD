package model;
import vo.TEItext;


public class PageTranscription {
	
	//variabili istanza
	
	private TEItext text;
	private Boolean complete;
	private Boolean revised;
	private Boolean validated;
	private PageTranscriptionStaff staff;
	
	
	//costruttore
	public PageTranscription() {
		
	}
	
	//metodi get e set
	public TEItext getText() {
		return this.text;
	}
	
	public boolean getComplete() {
		return this.complete;
	}
	
	public boolean getRevised() {
		return this.revised;
	}
	
	public boolean getValidated() {
		return this.validated;
	}
	
	public PageTranscriptionStaff getStaff() {
		return this.staff;
	}
	
	public void setText(TEItext txt) {
		this.text=txt;
	}

	public void setComplete(Boolean c) {
		this.complete=c;
	}
	
	public void setRevised(Boolean r) {
		this.revised=r;
	}
	
	public void setValidated(Boolean v) {
		this.validated=v;
	}
	
	public void setStaff(PageTranscriptionStaff staff) {
		this.staff=staff;
	}
}
