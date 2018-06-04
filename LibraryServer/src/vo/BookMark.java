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

	@Override
	public String toString() {
		return "BookMark [uid=" + uid + ", did=" + did + ", pid=" + pid + "]";
	}
	
	
	
	
}
