package vo;

import java.util.ArrayList;

public class DocumentMetadata {
	
	//variabili istanza
	private String author;
	private String description;
	private Integer composingDate;
	private VagueDate composingPeriod;
	private Integer preservationState;
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	
	//costruttore
	public DocumentMetadata(String auth, String descr,Integer compDate,VagueDate period,Integer preserv) {
		this.author=auth;
		this.description=descr;
		this.composingDate=compDate;
		this.composingPeriod=period;
		this.preservationState=preserv;
	}
	
	//metodi get e set
	
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Integer getComposingDate() {
		return this.composingDate;
	}
	

	public Integer getPreservationState() {
		return this.preservationState;
	}
	
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	
	public void setAuthor(String a) {
		this.author=a;
	}
	
	public void setDescription(String d) {
		this.description=d;
	}
	
	public void setComposingDate(Integer d) {
		this.composingDate=d;
	}
	
	public void setComposingPeriod(VagueDate d) {
		this.composingPeriod=d;
	}
	//TODO ci serve un'eccezione per controllare il valore inserito...
	public void setPreservationState(Integer s) {
		this.preservationState=s;
	}
	//implementare inserimento con parametro una lista di tag
	public boolean setTag(Tag t){
		return this.tags.add(t);
	}
	
	public boolean setTags(ArrayList<Tag> list) {
		return this.tags.addAll(list);
	}
	
	//metodi ausiliari
	//TODO non è ripetitivo?
	public boolean addTag(Tag t){
		return this.tags.add(t);
	}
	
	public boolean removeTag(Tag t) {
		return this.tags.remove(t);
	}

	@Override
	public String toString() {
		return "DocumentMetadata [author=" + author + ", description=" + description + ", composingDate="
				+ composingDate + ", composingPeriod=" + composingPeriod + ", preservationState=" + preservationState
				+ ", tags=" + tags + "]";
	}
	
	
}