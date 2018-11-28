package vo;

import java.util.Date;

public class UserInformations {
	
	//variabili istanza
	private String name;
	private String surname;
	private Date registrationDate;
	private Email email;
	private String password;
	
	//costruttore
	public UserInformations(String name, String surname, Date registrationDate, Email email, String password) {
		this.name=name;
		this.surname=surname;
		this.registrationDate=registrationDate;
		this.email=email;
		this.password=password;
		
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
	
	public String getPassword() {
		return this.password;
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
	//TODO inserire funzione hash di cifratura
	public void setPassword(String passwd) {
		this.password=passwd;
	}
}
