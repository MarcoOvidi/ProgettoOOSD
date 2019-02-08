package dao.interfaces;

import java.util.LinkedList;

import dao.concrete.DatabaseException;
import vo.BookMark;
import vo.UUIDBookMark;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDUser;

public interface PageViewQuerySetDAO {
	public LinkedList<BookMark> loadBookmarksDocument(UUIDUser ID_user, UUIDDocument ID_doc)
			throws DatabaseException ;
	public void removeBookmarksDocument(UUIDBookMark ID_bm) throws DatabaseException;
	public void addBookmarksDocument(BookMark bm) throws DatabaseException ;
	public void addToMyCollectins(UUIDDocument id, UUIDUser user) throws DatabaseException;
	public void removeFromMyCollectins(UUIDDocument id, UUIDUser user) throws DatabaseException;
	public boolean controlMyBookMarks(UUIDUser user, UUIDDocument id, int pageNumber) throws DatabaseException ;
	public UUIDPage getMyBookMarkPageID(UUIDDocument id, int pageNumber)
			throws DatabaseException;
	public void addPageToMyBookMarks(UUIDUser user, UUIDDocument id, UUIDPage pageNumber)
			throws DatabaseException;
	public void removePageFromMyBookMarks(UUIDUser user, UUIDDocument id, UUIDPage pageNumber)
			throws DatabaseException;
	
}
