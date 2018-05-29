package vo;

public class Request {
	
	private UUIDRequest id;
	private UUIDUser user;
	private UUIDUser admin;
	private Boolean status;
	private String object;
	private String message;
	private String answer;
	
	public Request (UUIDRequest idr, UUIDUser idu, UUIDUser ida, Boolean s, String o, String m, String a) {
		this.id = idr;
		this.user = idu;
		this.admin = ida;
		this.status = s;
		this.object = o;
		this.message = m;
		this.answer=a;
	}
	
	public Request (UUIDRequest idr, String object) {
		this.id = idr;
		this.object = object;
	}

	public UUIDRequest getId() {
		return id;
	}

	public void setId(UUIDRequest id) {
		this.id = id;
	}

	public UUIDUser getUser() {
		return user;
	}

	public void setUser(UUIDUser user) {
		this.user = user;
	}

	public UUIDUser getAdmin() {
		return admin;
	}

	public void setAdmin(UUIDUser admin) {
		this.admin = admin;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", user=" + user + ", admin=" + admin + ", status=" + status + ", object=" + object
				+ ", message=" + message + ", answer=" + answer + "]";
	}
	
	
	
	
	
	
}
