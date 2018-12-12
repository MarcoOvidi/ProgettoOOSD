package vo.view;

import vo.UUIDUser;

public class Formatter {
	UUIDUser idUser;
	String username;

	public UUIDUser getIdUser() {
		return idUser;
	}

	public void setIdUser(UUIDUser idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Formatter(UUIDUser idUser, String username) {
		super();
		this.idUser = idUser;
		this.username = username;
	}

	@Override
	public String toString() {
		return username;
	}

}