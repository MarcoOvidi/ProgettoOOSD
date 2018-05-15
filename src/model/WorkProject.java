package model;

import vo.UUIDUser;
import controller.SessionDataHandler;
import java.util.Date;

public class WorkProject {
	
	//variabili istanza
	//TODO come risolviamo ?private UUID id;
	private SessionDataHandler session;
	private UUIDUser coordinator;
	private Date publishingDate;
	
	// costruttore
	public WorkProject() {
		
	}
	
	//metodi get e set
	
	/*
	public UUID getID() {
		return this.id;
	} */
	
	public UUIDUser getCoordinator() {
		return this.coordinator;
	}
	
	public Date getPublishingDate() {
		return this.publishingDate;
	}
	
	/*
	public void setId(UUID id) {
		this.id=id;
	}*/
	
	//TODO implementare con imput UUID
	public void setCoordinator(User c) {
		this.coordinator=c.getID();
	}
	
	public void setPublishingDate(Date d) {
		this.publishingDate=d;
	}

}
