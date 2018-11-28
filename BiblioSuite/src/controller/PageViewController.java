package controller;

import dao.DatabaseException;
import dao.DocumentQuerySet;
import javafx.fxml.LoadException;
import model.Document;
import view.SceneController;
import vo.UUIDDocument;

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
			SceneController.loadScene("showDocumentNEW");
		}
		
	}
	
	public void downloadReadableDocument() {
		
	}

}
