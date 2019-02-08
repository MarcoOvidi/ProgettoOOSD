package dao.interfaces;

import java.util.LinkedList;

import dao.concrete.DatabaseException;
import model.Page;
import model.PageTranscription;
import vo.TEItext;
import vo.UUIDDocument;
import vo.UUIDPage;

public interface TranscriberQuerySetDAO {
	public PageTranscription loadTranscription(UUIDPage id) throws DatabaseException, NullPointerException;
	public Boolean updateText(TEItext text, UUIDPage id) throws DatabaseException, NullPointerException;
	public LinkedList<Page> loadDocument(UUIDDocument id)
			throws DatabaseException;
	
}
