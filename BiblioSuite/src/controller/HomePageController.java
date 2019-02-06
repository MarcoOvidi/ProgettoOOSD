package controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import dao.DatabaseException;
import dao.DocumentQuerySet;
import dao.HomePageQuerySet;
import dao.UserAuthenticationQuerySet;
import vo.UUIDDocument;
import vo.UUIDDocumentCollection;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;

public class HomePageController {
	
	private static HashMap<UUIDDocument,String[]> news = new HashMap<UUIDDocument,String[]>();
	private static HashMap<UUIDDocumentCollection, String>  categories = new HashMap<UUIDDocumentCollection,String>();
	private static HashMap<UUIDDocument,String[]> myCollection = new HashMap<UUIDDocument,String[]>();
	private static HashMap<UUIDTranscriptionWorkProject, String[]> myTPrj = new HashMap<UUIDTranscriptionWorkProject,String[]>();
	private static HashMap<UUIDScanningWorkProject, String[]> mySPrj = new HashMap<UUIDScanningWorkProject,String[]>();
	private static LinkedList<String[]> listDocumentInCollections = new LinkedList<String[]>();
	
	public void loadHomePage() {
		
	}
	
	public static void loadDocumentInCollections(UUIDDocumentCollection doc) {
		try {
			listDocumentInCollections = HomePageQuerySet.loadLibraryCollectionListWithDocument(doc);
		}catch(DatabaseException | SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
		
	public static void loadCategories() {
		try {
			categories = HomePageQuerySet.loadLibraryCollectionList();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadMyCollection() {
		try {
			myCollection = HomePageQuerySet.loadMyCollectionList(LocalSession.getLocalUser().getID());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}
	}

	public static void loadMyTranscriptionProjects() {
		try {
			setMyTPrj(HomePageQuerySet.loadMyTranscriptionWorkProjectList(LocalSession.getLocalUser().getID()));
		} catch (DatabaseException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	public static void loadMyScanningProjects() {
		try {
			setMySPrj(HomePageQuerySet.loadMyScanningWorkProjectList(LocalSession.getLocalUser().getID()));
		} catch (DatabaseException e) {
			e.printStackTrace();
			e.getMessage();
		}
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
	
	public static HashMap<UUIDDocument, String[]> getAllDocuments() {
		HashMap<UUIDDocument, String[]> res = new HashMap<UUIDDocument, String[]>();
		try {
			res = DocumentQuerySet.getAllDocuments();
		}
		catch (Exception e) {
		
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return res;
	}

	public static HashMap<UUIDDocument,String[]> getNews() {
		return news;
	}

	public static HashMap<UUIDDocumentCollection, String> getCategories(){
		return categories;
	}
	
	public static HashMap<UUIDDocument, String[]> getMyCollection(){
		return myCollection;
	}
	public static void setNews(HashMap<UUIDDocument,String[]> news) {
		HomePageController.news = news;
	}

	public static HashMap<UUIDTranscriptionWorkProject, String[]> getMyTPrj() {
		return myTPrj;
	}

	public static void setMyTPrj(HashMap<UUIDTranscriptionWorkProject, String[]> myTPrj) {
		HomePageController.myTPrj = myTPrj;
	}

	public static HashMap<UUIDScanningWorkProject, String[]> getMySPrj() {
		return mySPrj;
	}

	public static void setMySPrj(HashMap<UUIDScanningWorkProject, String[]> mySPrj) {
		HomePageController.mySPrj = mySPrj;
	}	
	
	public static boolean checkIsDigitalizer() {
		//2 upload
		
		Boolean perm = false;
		try {
		
			perm = UserAuthenticationQuerySet.permissionsControl(2, LocalSession.getLocalUser().getID());
		
		}catch(DatabaseException e) {
			e.getMessage();
			e.printStackTrace();
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		return perm;
	}
	
	public static boolean checkIsTranscriber() {
		//2 upload
		
		Boolean perm = false;
		try {
		
			perm = UserAuthenticationQuerySet.permissionsControl(5, LocalSession.getLocalUser().getID());
		
		}catch(DatabaseException e) {
			e.getMessage();
			e.printStackTrace();
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		return perm;
	}
	//TODO load menu buttons (checking permissions)

	public static LinkedList<String[]> getListDocumentInCollections() {
		return listDocumentInCollections;
	}

	public static void setListDocumentInCollections(LinkedList<String[]> listDocumentInCollections) {
		HomePageController.listDocumentInCollections = listDocumentInCollections;
	}
	
	public static String getDocumentTitle(UUIDDocument documentID) {
		
		try {
			return DocumentQuerySet.getDocumentTitle(documentID);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
