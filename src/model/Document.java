package model;

import java.util.LinkedList;
import vo.UUID;
import controller.SessionDataHandler;
/**
 * 
 * @author Di Paolo, Ovidi, Venditti
 * @version 1.0
 *
 */

public class Document {
	//variabili istanza
	private UUID id;
	private SessionDataHandler session;
	private Metadata metadata;
	private LinkedList<Page> pageList;
	private UUID scanningWorkProject;
	private UUID transcriptionWorkProject;
	
	//TODO
	//costruttore
	
	public Document() {
		
	}
	
	//metodi get e set
	
	public Metadata getMetaData() {
		return this.metadata;
	}
	
	public LinkedList<Page> getPageList() {
		return this.pageList;
	}
	
	public UUID getScanningWorkProject() {
		return this.scanningWorkProject;
	}
	
	public UUID getTranscriptionWorkProject() {
		return this.transcriptionWorkProject;
	}
	
	public void setMetaData(Metadata m) {
		this.metadata=m;
	}
	
	//TODO setPageList con input una lista di pagine
	public void setPageList(Page p) {
		this.pageList.addLast(p);
	}
	
	public void setScanningProject(UUID id) {
		this.scanningWorkProject=id;
	}
	
	public void setTranscriptionWorkProject(UUID twp) {
		this.transcriptionWorkProject=id;
	}
	
	
	
	
}
