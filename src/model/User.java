package model;

import java.util.LinkedList;

import vo.UserRoles;
import vo.UUIDUser;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDScanningWorkProject;
import vo.UserInformations;
import vo.UserPermissions;
import controller.SessionDataHandler;



public class User {

	//variabili istanza
	
	private UUIDUser id;
	private SessionDataHandler session;
	private String username;
	private UserInformations informations;
	private UserRoles role;
	private UserPermissions permissions;
	private LinkedList<UUIDTranscriptionWorkProject> transcriptionProject;
	private LinkedList<UUIDScanningWorkProject> digitalizationProject;
	//TODO bhooooo???!!!   private LinkedList<UUID> bookMarks;
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
	
	public UserRoles getRole() {
		return this.role;
	}
	
	public UserPermissions getPermissions() {
		return this.permissions;
	}
	
	public getProject() {
		return this.projects;
	}
	
	public getActive() {
		return this.active;
	}
	
	public setId(UUID id) {
		this.id=id;
	}
	
	public setUsername(String u) {
		this.username=u;
	}
	
	public setInformations(UserInformations ui) {
		this.informations=ui;
	}
	
	//TODO è necessario inserire più permessi o ruoli per volta? importando una lista? 
	public setRole(UserRoles r) {
		this.role.addLast(r);
	}
	
	public setPermissions(Boolean p) {
		this.permissions.addLast(p);
	}
	
	public setProject(Project p) {
		this.projects.addLast(p);
	}
	
	public setActive(Boolean s) {
		this.active=s;
	}
	
	
}
