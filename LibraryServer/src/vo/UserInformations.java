package ServerApplication.vo;

import java.util.Date;

public class UserInformations {
	
	//variabili istanza
	private String name;
	private String surname;
	private Date registrationDate;
	private Email email;
	
	//costruttore
	public UserInformations() {
		
	}
	
	//metodi get e set
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public Date getRegistrationDate() {
		return this.registrationDate;
	}
	
	public Email getMail() {
		return this.email;
	}
	
	public void setName(String n) {
		this.name=n;
	}

	public void setSurname(String s) {
		this.surname=s;
	}
	
	public void setRegistrationDate(Date d) {
		this.registrationDate=d;
	}
	
	public void setEmail(Email e) {
		this.email=e;
	}
}
