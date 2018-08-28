package model;

import java.util.LinkedList;

import controller.SessionDataHandler;
import vo.DocumentMetadata;
import vo.UUIDDocument;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
/**
 * 
 * @author Di Paolo, Ovidi, Venditti
 * @version 1.0
 *
 */

public class Document {
	//variabili istanza
	private String title;
	private UUIDDocument id;
	private SessionDataHandler session;
	private DocumentMetadata metadata;
	private LinkedList<Page> pageList;
	private UUIDScanningWorkProject scanningWorkProject;
	private UUIDTranscriptionWorkProject transcriptionWorkProject;
	
	//TODO
	//costruttore
	
	public Document(String title, UUIDDocument id, DocumentMetadata dm, LinkedList<Page> pagel,
			UUIDScanningWorkProject idsp, UUIDTranscriptionWorkProject idtp) {
		this.title = title;
		this.id = id;
		this.metadata = dm;
		this.pageList = pagel;
		this.scanningWorkProject = idsp;
		this.transcriptionWorkProject = idtp;
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
	
	public int getPagesNumber()
	{
		return this.pageList.size();
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
	
	
	public void setPageList(LinkedList<Page> page_list) {
		this.pageList.addAll(page_list);
	}
	
	public void setPage(Page p) {
		this.pageList.addLast(p);
	}
	
	public void setScanningProject(UUIDScanningWorkProject swp) {
		this.scanningWorkProject=swp;
	}
	
	public void setTranscriptionWorkProject(UUIDTranscriptionWorkProject twp) {
		this.transcriptionWorkProject=twp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setUUIDTranscriptionWorkProject(UUIDTranscriptionWorkProject id) {
		this.transcriptionWorkProject=id;
	}

	public void setUUIDScanningWorkProject(UUIDScanningWorkProject id) {
		this.scanningWorkProject=id;
	}

	@Override
	public String toString() {
		return "Document [title=" + title + ", id=" + id + ", session=" + session + ", metadata=" + metadata
				+ ", pageList=" + pageList + ", scanningWorkProject=" + scanningWorkProject
				+ ", transcriptionWorkProject=" + transcriptionWorkProject + "]";
	}
	
	
	
	
	
	
	
	
}
