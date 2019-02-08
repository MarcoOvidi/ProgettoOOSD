package controller;

import java.util.LinkedList;

import dao.concrete.DatabaseException;
import dao.concrete.SearchQuerySet;
import vo.UUIDDocument;

public class SearchController {

	public static LinkedList<UUIDDocument> searchByAuthorTitle(String expr) {

		LinkedList<UUIDDocument> documents = null;

		try {
			documents = new SearchQuerySet().searchByAuthorTitle(expr);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return documents;
	}
	
	public static LinkedList<UUIDDocument> searchByTag(String expr) {

		LinkedList<UUIDDocument> documents = null;

		try {
			documents = new SearchQuerySet().searchByTag(expr);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return documents;
	}


}
