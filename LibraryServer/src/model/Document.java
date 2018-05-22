package ServerApplication.model;

import java.util.LinkedList;

import ServerApplication.controller.SessionDataHandler;
import ServerApplication.vo.DocumentMetadata;
import ServerApplication.vo.UUIDDocument;
import ServerApplication.vo.UUIDScanningWorkProject;
import ServerApplication.vo.UUIDTranscriptionWorkProject;
/**
 * 
 * @author Di Paolo, Ovidi, Venditti
 * @version 1.0
 *
 */

public class Document {
	//variabili istanza
	private UUIDDocument id;
	private SessionDataHandler session;
	private DocumentMetadata metadata;
	private LinkedList<Page> pageList;
	private UUIDScanningWorkProject scanningWorkProject;
	private UUIDTranscriptionWorkProject transcriptionWorkProject;
	
	//TODO
	//costruttore
	
	public Document() {
		
	}
	
	//metodi get e set
	
	public UUIDDocument getUUID() {
		return this.id;
	}
	
	public DocumentMetadata getMetaData() {
		return this.metadata;
	}
	
	public LinkedList<Page> getPageList() {
		return this.pageList;
	}
	
	public UUIDScanningWorkProject getScanningWorkProject() {
		return this.scanningWorkProject;
	}
	
	public UUIDTranscriptionWorkProject getTranscriptionWorkProject() {
		return this.transcriptionWorkProject;
	}
	
	public void setUUID(UUIDDocument id) {
		this.id=id;
	}
	
	public void setMetaData(DocumentMetadata m) {
		this.metadata=m;
	}
	
	//TODO setPageList con input una lista di pagine
	public void setPageList(Page p) {
		this.pageList.addLast(p);
	}
	
	public void setScanningProject(UUIDScanningWorkProject swp) {
		this.scanningWorkProject=swp;
	}
	
	public void setTranscriptionWorkProject(UUIDTranscriptionWorkProject twp) {
		this.transcriptionWorkProject=twp;
	}
	
	
	
	
}
