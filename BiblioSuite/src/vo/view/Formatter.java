package vo.view;

import vo.UUIDUser;

public class Formatter {
	private UUIDUser idUser;
	private String username;
	private int id;  // serve per i tag
	private String Tag;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return Tag;
	}

	public void setTag(String tag) {
		Tag = tag;
	}

}