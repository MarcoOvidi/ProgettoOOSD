package model;

import java.util.LinkedList;
import vo.UUIDUser;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;

public class ScanningWorkProject extends WorkProject {
	
	//variabili istanza
	private LinkedList<UUIDUser> digitalizers;
	private LinkedList<UUIDUser> revisers;
	private Boolean completed;
	private UUIDScanningWorkProject id;
	private String documentTitle;  //ho aggiunto questo campo perchè la query in HomePageQS che pesca tutti i prj e i loro nomi ha bisogno anche del titolo dell'opera
	
	//costruttore
	public ScanningWorkProject() {
		super();
	}
	
	public ScanningWorkProject(UUIDScanningWorkProject id,String t) {
		this.documentTitle=t;
		this.id=id;
	}
	
	//metodi get e set
	
	public Boolean getCompleted() {
		return this.completed;
	}
	
	public LinkedList<UUIDUser> getDigitalizers(){
		return this.digitalizers;
	}
	
	public LinkedList<UUIDUser> getRevisers(){
		return this.revisers;
	}
	
	public void setCompleted(Boolean s) {
		this.completed=s;
	}
	//TODO implementare a multiplo inserimento
	public void setDigitalizers(UUIDUser u) {
		this.digitalizers.addLast(u);
	}
	
	
	public void setRevisers(UUIDUser u) {
		this.revisers.addLast(u);
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	@Override
	public String toString() {
		return "ScanningWorkProject [digitalizers=" + digitalizers + ", revisers=" + revisers + ", completed="
				+ completed + ", id=" + id + ", documentTitle=" + documentTitle + "]";
	}
	
	
	
	
	
	
}
