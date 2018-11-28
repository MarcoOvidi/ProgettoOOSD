package vo.view;

import vo.Email;
import vo.UUIDUser;

public class UserRow {
	private UUIDUser id ;
	private String username;
	private String name;
	private String surname;
	private String email;

	public UserRow(UUIDUser id, String username, String name, String surname, Email email) {
		this.setId(id);
		this.setUsername(username);
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public UUIDUser getId() {
		return id;
	}

	public void setId(UUIDUser id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email.getEmail();
	}

}
