package vo.view;

import vo.UUIDRequest;

public class RequestRow {
	private UUIDRequest requestID;
	private String username;
	private String Object;

	public RequestRow(UUIDRequest request, String username, String object) {
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
