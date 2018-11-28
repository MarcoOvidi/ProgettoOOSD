package vo;

public class StaffRow {
	private String username;
	private String role;
	private UUIDUser id ;

	public StaffRow(String u, String r, UUIDUser id) {
		this.username = u;
		this.role = r;
		this.setId(id);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UUIDUser getId() {
		return id;
	}

	public void setId(UUIDUser id) {
		this.id = id;
	}

}
