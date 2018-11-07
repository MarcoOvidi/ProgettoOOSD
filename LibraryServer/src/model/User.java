package model;

import java.util.LinkedList;
import vo.UUIDUser;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDBookMark;
import vo.UUIDScanningWorkProject;
import vo.UserInformations;
import vo.UserPermissions;
import controller.SessionDataHandler;



public class User {

	//variabili istanza
	
	private UUIDUser id;
	//private SessionDataHandler session; //WTF //FIXME 
	private String username;
	private UserInformations informations;
	private UserPermissions permissions;
	private LinkedList<UUIDTranscriptionWorkProject> transcriptionProjects;
	private LinkedList<UUIDScanningWorkProject> scanningProjects;
	private LinkedList<UUIDBookMark> bookMarks;
	private Boolean active;
	
	//costruttore usato dalla query che recupera i soli dati anagrafici di un utente

	public User(UUIDUser id, String usern, UserInformations ui,Boolean status, UserPermissions perm) {
		this.id=id;
		this.username=usern;
		this.informations=ui;
		this.active=status;
		this.permissions = perm;
	}
	
	public User(UUIDUser id, String usern, UserInformations ui,Boolean status) {
		this.id=id;
		this.username=usern;
		this.informations=ui;
		this.active=status;
		
		this.permissions = new UserPermissions();
	}
	
	public User(String usern, UserInformations ui,Boolean status) {
		this.username=usern;
		this.informations=ui;
		this.active=status;
		
		this.permissions = new UserPermissions();
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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", informations=" + informations
				+ ", permissions=" + permissions + ", transcriptionProjects=" + transcriptionProjects
				+ ", scanningProjects=" + scanningProjects + ", bookMarks=" + bookMarks + ", active=" + active + "]";
	}	
}
