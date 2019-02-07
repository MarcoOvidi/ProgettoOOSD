package dao.interfaces;

import java.util.LinkedList;

import dao.DatabaseException;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;

public interface RequestToAdminQuerySetDAO {
	public UUIDRequest sendRequestToAdmin(Request req) throws NullPointerException,DatabaseException;
	public LinkedList<Request> loadAllRequestToAdmin(UUIDUser id) throws DatabaseException,NullPointerException;
	public Boolean undoRequestToAdmin(UUIDUser idu,UUIDRequest idr) throws NullPointerException,DatabaseException;
	
}
