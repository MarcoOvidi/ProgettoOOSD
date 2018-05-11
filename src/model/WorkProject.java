package model;

import vo.UUID;
import controller.SessionDataHandler;
import java.util.Date;

public class WorkProject {
	
	//variabili istanza
	private UUID id;
	private SessionDataHandler session;
	private UUID coordinator;
	private Date publishingDate;
	
	// costruttore
	public WorkProject() {
		
	}
	
	//metodi get e set
	
	public UUID getID() {
		return this.id;
	}
	
	public UUID getCoordinator() {
		return this.coordinator;
	}
	
	public Date getPublishingDate() {
		return this.publishingDate;
	}
	
	public void setId(UUID id) {
		this.id=id;
	}
	//TODO implementare con imput UUID
	public void setCoordinator(User c) {
		this.coordinator=c.getID();
	}
	
	public void setPublishingDate(Date d) {
		this.publishingDate=d;
	}

}
