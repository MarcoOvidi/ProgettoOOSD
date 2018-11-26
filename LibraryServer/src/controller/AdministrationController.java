package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.AdministratorQuerySet;
import dao.DatabaseException;
import model.User;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;

public class AdministrationController {

<<<<<<< HEAD
	private static HashMap<UUIDRequest, String> pendingRequests = new HashMap<UUIDRequest, String>();
	private static HashMap<UUIDRequest, String> readRequests = new HashMap<UUIDRequest, String>();
||||||| merged common ancestors
	private HashMap<UUIDRequest, String> pendingRequests = new HashMap<UUIDRequest, String>();
	private HashMap<UUIDRequest, String> readRequests = new HashMap<UUIDRequest, String>();
=======
>>>>>>> b71ec88a56621de9953f2126281030e8b6328472

<<<<<<< HEAD
	public static HashMap<UUIDRequest, String> getPendingRequests() {
		return pendingRequests;
	}

	public static void setPendingRequests(HashMap<UUIDRequest, String> pendingRequests) {
		AdministrationController.pendingRequests = pendingRequests;
	}

	public static void loadPendingRequestList() {
||||||| merged common ancestors
	public HashMap<UUIDRequest, String> getPendingRequests() {
		return pendingRequests;
	}

	public void setPendingRequests(HashMap<UUIDRequest, String> pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

	public void loadPendingRequestList() {
=======
	public LinkedList<Request> getRequest(Integer i){
		LinkedList<Request> req = null;
>>>>>>> b71ec88a56621de9953f2126281030e8b6328472
		try {
			req = AdministratorQuerySet.loadRequestsList(i);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return req;
	}
<<<<<<< HEAD

	public static void loadReadRequestsList() {
		try {
			setReadRequests(AdministratorQuerySet.loadRequestsList(1));
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static Request loadRequest(UUIDRequest id) {
||||||| merged common ancestors

	public void loadReadRequestsList() {
		try {
			setReadRequests(AdministratorQuerySet.loadRequestsList(1));
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public Request loadRequest(UUIDRequest id) {
=======
	
	public Request loadRequest(UUIDRequest id) {
>>>>>>> b71ec88a56621de9953f2126281030e8b6328472
		Request r = null;
		try {
			r = AdministratorQuerySet.loadRequest(id);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	public static HashMap<UUIDUser, String[]> loadAllUsers() {
		HashMap<UUIDUser, String[]> users = new HashMap<UUIDUser, String[]>();
		return users;
	}

	public static LinkedList<User> loadUserList() {
		LinkedList<User> res = null;
		try {
			res = AdministratorQuerySet.loadUserList();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

<<<<<<< HEAD
	public static HashMap<UUIDRequest, String> getReadRequests() {
		return readRequests;
	}

	public static void setReadRequests(HashMap<UUIDRequest, String> readRequests) {
		AdministrationController.readRequests = readRequests;
	}

||||||| merged common ancestors
	public HashMap<UUIDRequest, String> getReadRequests() {
		return readRequests;
	}

	public void setReadRequests(HashMap<UUIDRequest, String> readRequests) {
		this.readRequests = readRequests;
	}

=======
>>>>>>> b71ec88a56621de9953f2126281030e8b6328472
}
