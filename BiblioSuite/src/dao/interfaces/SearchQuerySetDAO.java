package dao.interfaces;

import java.util.LinkedList;

import dao.DatabaseException;
import vo.UUIDDocument;

public interface SearchQuerySetDAO {
	public LinkedList<UUIDDocument> searchByAuthorTitle(String match) throws DatabaseException;
	public LinkedList<UUIDDocument> searchByTag(String match) throws DatabaseException;
	
}
