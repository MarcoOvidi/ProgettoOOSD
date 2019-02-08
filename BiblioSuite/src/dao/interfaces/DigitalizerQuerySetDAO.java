package dao.interfaces;

import java.util.LinkedList;

import dao.concrete.DatabaseException;
import model.Page;
import vo.Image;
import vo.UUIDDocument;
import vo.UUIDPage;

public interface DigitalizerQuerySetDAO {
	public LinkedList<Page> loadDocument(UUIDDocument id, Boolean revision, Boolean validation)
			throws DatabaseException;
	public void updatePage(UUIDPage id, Image img) throws DatabaseException;
	public void updatePageNumber(UUIDPage id, Integer number) throws DatabaseException;
	
}
