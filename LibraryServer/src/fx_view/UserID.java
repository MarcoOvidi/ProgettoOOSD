package fx_view;

import vo.UUIDUser;

public class UserID {
	private static UUIDUser id;
	public static UUIDUser getId(){return id;}
	public static void setId(UUIDUser uid) {id=uid;}
}
