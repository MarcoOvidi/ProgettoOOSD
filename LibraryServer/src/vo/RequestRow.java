package vo;

public class RequestRow {
	private UUIDUser userID ;
	private UUIDRequest requestID;
	private String username;
	private String Object;

	public RequestRow(UUIDUser id, UUIDRequest request, String username, String object) {
		setUserID(id);
		setRequestID(request);
		setUsername(username);
		setObject(object);		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UUIDUser getUserID() {
		return userID;
	}

	public void setUserID(UUIDUser userID) {
		this.userID = userID;
	}

	public UUIDRequest getRequestID() {
		return requestID;
	}

	public void setRequestID(UUIDRequest requestID) {
		this.requestID = requestID;
	}

	public String getObject() {
		return Object;
	}

	public void setObject(String object) {
		Object = object;
	}


}
