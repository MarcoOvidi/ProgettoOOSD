package vo;

public class StaffRow {
	private String username;
	private String role;

	public StaffRow(String u, String r) {
		this.username = u;
		this.role = r;
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

}
