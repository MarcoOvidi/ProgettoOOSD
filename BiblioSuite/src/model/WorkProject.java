package model;

import vo.UUIDUser;
import java.util.Date;

public class WorkProject {
	private UUIDUser coordinator;
	private Date publishingDate;
	private Boolean completed;
	
	// costruttore
	public WorkProject(Date date,UUIDUser coordinator, Boolean completed) {
		this.publishingDate=date;
		this.coordinator=coordinator;
		this.completed=completed;
		
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

	public void setCoordinator(UUIDUser coordinator) {
		this.coordinator=coordinator;
	}
	
	public void setCoordinator(User coordinator) {
		this.coordinator=coordinator.getID();
	}
	
	public void setPublishingDate(Date date) {
		this.publishingDate=date;
	}

	@Override
	public String toString() {
		return "WorkProject [ coordinator=" + coordinator + ", publishingDate=" + publishingDate
				+ ", completed=" + completed + "]";
	}
	
	
}
