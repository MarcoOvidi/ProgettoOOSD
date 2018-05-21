package ServerApplication.model;

import java.util.LinkedList;
import ServerApplication.vo.UUIDUser;
import ServerApplication.vo.UUIDTranscriptionWorkProject;
import ServerApplication.vo.UUIDBookMark;
import ServerApplication.vo.UUIDScanningWorkProject;
import ServerApplication.vo.UserInformations;
import ServerApplication.vo.UserPermissions;
import ServerApplication.controller.SessionDataHandler;



public class User {

	//variabili istanza
	
	private UUIDUser id;
	private SessionDataHandler session;
	private String username;
	private UserInformations informations;
	private UserPermissions permissions;
	private LinkedList<UUIDTranscriptionWorkProject> transcriptionProjects;
	private LinkedList<UUIDScanningWorkProject> scanningProjects;
	private LinkedList<UUIDBookMark> bookMarks;
	private Boolean active;
	
	/**
	 * Costruttore 
	 * @param
	 */
	//TODO
	public User() {
		
	}
	
	//Metodi get e set
	
	public UUIDUser getID() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public UserInformations getInformations() {
		return this.informations;
	}
	
	public UserPermissions getPermissions() {
		return this.permissions;
	}
	
	public LinkedList<UUIDTranscriptionWorkProject> getTranscriptionProjects() {
		return this.transcriptionProjects;
	}
	
	public LinkedList<UUIDScanningWorkProject> getScanningrojects() {
		return this.scanningProjects;
	}
	
	public Boolean getActive() {
		return this.active;
	}
	
	public LinkedList<UUIDBookMark> getBookMarks(){
		return this.bookMarks;
	}
	
	public void setId(UUIDUser id) {
		this.id=id;
	}
	
	public void setUsername(String u) {
		this.username=u;
	}
	
	public void setInformations(UserInformations ui) {
		this.informations=ui;
	}
	
	public void setPermissions(Boolean[] p) throws RuntimeException{
		if(p.length != 11)
			throw new RuntimeException("Numero di permessi incompatibili");
		else {
			this.permissions.updatePermission(p);
		}
	}
	
	public void setTranscriptionProject(UUIDTranscriptionWorkProject p) {
		this.transcriptionProjects.addLast(p);
	}
	
	public void setScanningWorkProject(UUIDScanningWorkProject p) {
		this.scanningProjects.addLast(p);
	}
	
	public void setActive(Boolean s) {
		this.active=s;
	}
	
	public void setBookMarks(UUIDBookMark bm) {
		this.bookMarks.addLast(bm);
	}
	
	public void setBookMarks(LinkedList<UUIDBookMark> bm) {
		this.bookMarks.addAll(bm);
	}
	

	
	
	
}
