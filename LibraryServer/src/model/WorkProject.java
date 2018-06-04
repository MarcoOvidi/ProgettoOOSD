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
	private Boolean completed;
	
	// costruttore
	public WorkProject(Date d,UUIDUser coord, Boolean b) {
		this.publishingDate=d;
		this.coordinator=coord;
		this.completed=b;
		
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

	@Override
	public String toString() {
		return "WorkProject [session=" + session + ", coordinator=" + coordinator + ", publishingDate=" + publishingDate
				+ ", completed=" + completed + "]";
	}
	
	
}
