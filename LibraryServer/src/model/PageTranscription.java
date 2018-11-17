package model;
import vo.TEItext;


public class PageTranscription {
	
	//variabili istanza
	
	private TEItext text;
	private Boolean revised;
	private Boolean validated;
	private PageTranscriptionStaff staff;
	
	
	//costruttore
	public PageTranscription(TEItext text, Boolean revised, Boolean validated,
			PageTranscriptionStaff staff) {
		super();
		this.text = text;
		this.revised = revised;
		this.validated = validated;
		this.staff = staff;
	}
	
	//metodi get e set
		public TEItext getText() {
			return this.text;
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
	
	public void setRevised(Boolean r) {
		this.revised=r;
	}
	
	public void setValidated(Boolean v) {
		this.validated=v;
	}
	
	public void setStaff(PageTranscriptionStaff staff) {
		this.staff=staff;
	}

	@Override
	public String toString() {
		return "PageTranscription [text=" + text + ", revised=" + revised + ", validated=" + validated + ", staff="
				+ staff + "]";
	}
	
	
}
