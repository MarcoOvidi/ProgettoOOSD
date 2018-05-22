package ServerApplication.vo;

import java.util.Date;
import java.util.ArrayList;

public class DocumentMetadata {
	
	//variabili istanza
	private String title;
	private String author;
	private String description;
	private Date composingDate;
	private Date composingPeriod;
	private Integer preservationState;
	private ArrayList<Tag> tags;
	
	//costruttore
	public DocumentMetadata() {
		
	}
	
	//metodi get e set
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Date getComposingDate() {
		return this.composingDate;
	}
	
	public Date getComposingPeriod() {
		return this.composingPeriod;
	}
	
	public Integer getPreservationState() {
		return this.preservationState;
	}
	
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	
	public void setTitle(String t) {
		this.title=t;
	}
	
	public void setAuthor(String a) {
		this.author=a;
	}
	
	public void setDescription(String d) {
		this.description=d;
	}
	
	public void setComposingDate(Date d) {
		this.composingDate=d;
	}
	
	public void setComposingPeriod(Date d) {
		this.composingPeriod=d;
	}
	//TODO ci serve un'eccezione per controllare il valore inserito...
	public void setPreservationState(Integer s) {
		this.preservationState=s;
	}
	//implementare inserimento con parametro una lista di tag
	public boolean setTags(Tag t){
		return this.tags.add(t);
	}
	
	//metodi ausiliari
	//TODO non è ripetitivo?
	public boolean addTag(Tag t){
		return this.tags.add(t);
	}
	
	public boolean removeTag(Tag t) {
		return this.tags.remove(t);
	}
}