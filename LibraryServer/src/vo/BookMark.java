package vo;

public class BookMark {
	UUIDUser uid ;
	UUIDDocument did;
	UUIDPage pid;
	
	public BookMark(UUIDUser uid, UUIDDocument did, UUIDPage pid) {
		super();
		this.uid = uid;
		this.did = did;
		this.pid = pid;
	}
	
	public UUIDUser getUID() {
		return this.uid;
	}
	
	public UUIDDocument getDID() {
		return this.did;
	}
	
	public UUIDPage getPID() {
		return this.pid;
	}

	public void setUid(UUIDUser uid) {
		this.uid = uid;
	}

	public void setDid(UUIDDocument did) {
		this.did = did;
	}

	public void setPid(UUIDPage pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "BookMark [uid=" + uid + ", did=" + did + ", pid=" + pid + "]";
	}
	
	
	
	
}
