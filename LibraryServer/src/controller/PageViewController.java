package controller;

import dao.DatabaseException;
import dao.DocumentQuerySet;
import fx_view.SceneController;
import vo.UUIDDocument;

public class PageViewController {
	
	public static void showDocument(UUIDDocument doc) {
		try{
			LocalSession.setOpenedDocumet(DocumentQuerySet.loadDocument(doc));
			//System.out.println(LocalSession.getOpenedDocumet());
		}catch(DatabaseException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		SceneController.loadScene("showDocument");
		
	}
	
	public void downloadReadableDocument() {
		
	}

}
