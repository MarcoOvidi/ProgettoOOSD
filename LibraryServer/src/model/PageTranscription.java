package model;
import vo.TEItext;


public class PageTranscription {
	
	//variabili istanza
	
	private TEItext text;
	private Boolean revised;
	private Boolean validated;
	private Boolean completed;
	private PageTranscriptionStaff staff;
	
	
	//costruttore
	public PageTranscription(TEItext text, Boolean revised, Boolean validated,Boolean comp,
			PageTranscriptionStaff staff) {
		super();
		this.text = text;
		this.revised = revised;
		this.validated = validated;
		this.completed = comp;
		this.staff = staff;
	}
	
	//metodi get e set
		public TEItext getText() {
			return this.text;
		}

	public boolean getCompleted() {
		return this.completed;
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

	public void setCompleted(Boolean c) {
		this.completed=c;
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
