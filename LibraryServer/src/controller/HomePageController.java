package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import dao.DatabaseException;
import dao.HomePageQuerySet;
import vo.UUIDDocument;

public class HomePageController {
	
	private static HashMap<UUIDDocument,String[]> news = new HashMap<UUIDDocument,String[]>();
	
	
	public void loadHomePage() {
		
	}
		
	
	@SuppressWarnings("deprecation")
	public static void loadNews() throws DatabaseException, ParseException {
		
		HashMap<UUIDDocument,String[]> news = new HashMap<UUIDDocument,String[]>();
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(timeStamp);

		
		
		Iterator<Entry<UUIDDocument, String>> itr = HomePageQuerySet.loadNews(20).entrySet().iterator();
		
		while(itr.hasNext()) {
			Map.Entry<UUIDDocument, String> entry = itr.next();
			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getValue().substring(entry.getValue().length()-10, entry.getValue().length()));
			
			Calendar start = Calendar.getInstance();
			start.set(d.getYear(),d.getMonth(),d.getDay());
			
			Calendar end = Calendar.getInstance();
			end.set(d2.getYear(),d2.getMonth(),d2.getDay());
			Date startDate = start.getTime();
			Date endDate = end.getTime();
			long startTime = startDate.getTime();
			long endTime = endDate.getTime();
			long diffTime = endTime - startTime;
			long diffDays = diffTime / (1000 * 60 * 60 * 24);
			
			String[] titAgo = {entry.getValue().substring(0, entry.getValue().length()-14),Long.toString(diffDays),entry.getValue().substring(entry.getValue().length()-13,entry.getValue().length()-11)};
			
			news.put(entry.getKey(), titAgo);
		}
		
		setNews(news);
	}

	public static HashMap<UUIDDocument,String[]> getNews() {
		return news;
	}


	public static void setNews(HashMap<UUIDDocument,String[]> news) {
		HomePageController.news = news;
	}	
	
	//TODO load menu buttons (checking permissions)
}
