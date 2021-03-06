package vo;

import java.util.ArrayList;

public class DocumentMetadata {
	
	//variabili istanza
	private String author;
	private String description;
	private Integer compositionDate;
	private VagueDate compositionPeriod;
	private Integer preservationState;
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	
	//costruttore
	public DocumentMetadata(String auth, String descr,Integer compDate,VagueDate period,Integer preserv) {
		this.author=auth;
		this.description=descr;
		this.compositionDate=compDate;
		this.compositionPeriod=period;
		this.preservationState=preserv;
	}
	
	//metodi get e set
	
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Integer getCompositionDate() {
		return this.compositionDate;
	}
	
	public VagueDate getCompositionPeriod() {
		return compositionPeriod;
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
	
	public void setCompositionDate(Integer d) {
		this.compositionDate=d;
	}
	
	public void setCompositionPeriod(VagueDate d) {
		this.compositionPeriod=d;
	}
	//TODO ci serve un'eccezione per controllare il valore inserito...
	public void setPreservationState(Integer s) {
		this.preservationState=s;
	}
	
	public void setTags(ArrayList<Tag> list) {
		this.tags = list;
	}
	
	public boolean addTag(Tag t){
		return this.tags.add(t);
	}
	
	public boolean removeTag(Tag t) {
		return this.tags.remove(t);
	}

	@Override
	public String toString() {
		return "DocumentMetadata [author=" + author + ", description=" + description + ", composingDate="
				+ compositionDate + ", composingPeriod=" + compositionPeriod + ", preservationState=" + preservationState
				+ ", tags=" + tags + "]";
	}
	
	
}