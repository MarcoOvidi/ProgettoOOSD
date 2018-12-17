package controller;

import dao.DatabaseException;
import dao.DocumentQuerySet;
import dao.PageViewQuerySet;
import javafx.fxml.LoadException;
import model.Document;
import view.SceneController;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDUser;

public class PageViewController {
	
	public static void showDocument(UUIDDocument document) throws LoadException {
		Document doc = null;
		try{
			//doc = DocumentQuerySet.loadDocument(document);
			//FIXME potrebbe causare problemi questa cosa?
			doc = DocumentQuerySet.loadDocumentToView(document);
		}catch(DatabaseException e) {
			e.getMessage();
			e.printStackTrace();
		}
		if(doc != null) {
			LocalSession.setOpenedDocumet(doc);
			SceneController.loadScene("showDocumentNEW", false);
		}
		
	}
	
	public static void addDocumentFromMyCollection(UUIDDocument doc, UUIDUser user) {
		try {
			PageViewQuerySet.addToMyCollectins(doc, user);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeDocumentFromMyCollection(UUIDDocument doc, UUIDUser user) {
		try {
			PageViewQuerySet.removeFromMyCollectins(doc, user);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean controlBookMark(UUIDUser user,UUIDDocument doc, int num) {
		try {
			return PageViewQuerySet.controlMyBookMarks(user, doc, num);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static void addBookMark(UUIDUser user,UUIDDocument doc, UUIDPage num) {
		try {
			PageViewQuerySet.addPageToMyBookMarks(user, doc, num);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeBookMark(UUIDUser user,UUIDDocument doc, UUIDPage num) {
		try {
			PageViewQuerySet.removePageFromMyBookMarks(user, doc, num);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static UUIDPage getBookMarkPageID(UUIDDocument doc, int num) {
		try {
			return PageViewQuerySet.getMyBookMarkPageID(doc, num);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
