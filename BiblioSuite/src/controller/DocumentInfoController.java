package controller;

import java.util.ArrayList;

import dao.DatabaseException;
import dao.DocumentQuerySet;
import javafx.fxml.LoadException;
import model.Document;
import vo.Tag;
import vo.UUIDDocument;
import vo.VagueDate;

public class DocumentInfoController {
	private Document document;
	
	public DocumentInfoController(UUIDDocument document) throws LoadException, DatabaseException {
		loadDocument(document);
	}

	public void loadDocument(UUIDDocument id) throws LoadException, DatabaseException {
		document = DocumentQuerySet.loadDocument(id);
	}

	public String getTitle() {
		String res = "";
		if (document != null) {
			res += document.getTitle();
		}
		return res;
	}

	public String getAuthor() {
		String res = "";
		if (document != null) {
			res += document.getMetaData().getAuthor();
		}
		return res;
	}

	public String getComposition() {
		String res = "";
		if (document != null) {

			if (document.getMetaData().getCompositionDate() == null) {
				VagueDate vaguedate = document.getMetaData().getCompositionPeriod();
				res += "Between " + vaguedate.getFrom() + " and " + vaguedate.getTo();
			} else {
				res += document.getMetaData().getCompositionDate();
			}
		}
		
		return res;
	}
	

	public String getDescription() {
		String res = "";
		if (document != null) {
			res += document.getMetaData().getDescription();
		}
		return res;
	}

	public ArrayList<String> getTags() {
		ArrayList<String> res = new ArrayList<String>();
		if (document != null) {
			for (Tag tag : document.getMetaData().getTags()) {
				res.add(tag.toString());
			}
		}
		return res;
	}

}
