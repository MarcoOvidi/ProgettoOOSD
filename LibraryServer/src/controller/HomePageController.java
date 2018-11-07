package controller;

import java.util.HashMap;

import dao.DatabaseException;
import dao.HomePageQuerySet;
import vo.UUIDDocument;

public class HomePageController {
	
	private static HashMap<UUIDDocument,String> news = new HashMap<UUIDDocument,String>();
	
	
	public void loadHomePage() {
		
	}
		
	
	public static void loadNews() throws DatabaseException {
		setNews(HomePageQuerySet.loadNews(20));
	}

	public static HashMap<UUIDDocument,String> getNews() {
		return news;
	}


	public static void setNews(HashMap<UUIDDocument,String> news) {
		HomePageController.news = news;
	}	
	
	//TODO load menu buttons (checking permissions)
}
