package controller;

import java.util.LinkedList;

import dao.DatabaseException;
import dao.SearchQuerySet;
import vo.UUIDDocument;

public class SearchController {

	public static LinkedList<UUIDDocument> searchByTitleAuthor(String expr) {

		LinkedList<UUIDDocument> documents = null;

		try {
			documents = SearchQuerySet.searchByAuthorTitle(expr);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return documents;
	}
	
	public static LinkedList<UUIDDocument> searchByTag(String expr) {

		LinkedList<UUIDDocument> documents = null;

		try {
			documents = SearchQuerySet.searchByTag(expr);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return documents;
	}


}
