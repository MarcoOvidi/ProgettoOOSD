package model; 
/**
 * 
 * @author Di Paolo, Ovidi, Venditti
 * @version 1.0
 *
 */

import vo.UUIDPage;
import vo.UUIDUser;

import java.util.LinkedList;

import controller.SessionDataHandler;


public class Page {
	
	//variabili istanza
	private UUIDPage id;
	private SessionDataHandler session;
	private Integer pageNumber;
	private PageScan scan;
	private PageTranscription transcription;
	
	public Page(UUIDPage id, Integer page_num, PageScan page_scan, PageTranscription page_trans) {
		this.id=id;
		this.pageNumber=page_num;
		this.scan=page_scan;
		this.transcription=page_trans;
	}
	
	
	//metodi get e set
	
	public UUIDPage getID() {
		return this.id;
	}
	
	public Integer getPageNumber() {
		return this.pageNumber;
	}
	
	public PageScan getScan() {
		return this.scan;
	}
	
	public PageTranscription getTranscription() {
		return this.transcription;
	}
	
	public void setID(UUIDPage id) {
		this.id=id;
	}
	
	public void setPageNumber(int n) {
		this.pageNumber=n;
	}
	
	public void setScan(PageScan ps) {
		this.scan=ps;
	}
	
	public void setTranscription(PageTranscription pt) {
		this.transcription=pt;
	}
	
	public void setPageTranscription(LinkedList<UUIDUser> transcribers) {
		transcription.setStaff(new PageTranscriptionStaff(transcribers));
	}


	@Override
	public String toString() {
		return "Page [id=" + id + ", session=" + session + ", pageNumber=" + pageNumber + ", scan=" + scan
				+ ", transcription=" + transcription + "]";
	}
	
	
	
}
