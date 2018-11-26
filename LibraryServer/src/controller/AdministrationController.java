package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.AdministratorQuerySet;
import dao.DatabaseException;
import model.User;
import vo.UUIDUser;

public class AdministrationController {
	
	private HashMap<Integer,String> pendingRequests = new HashMap<Integer,String>();
	private HashMap<Integer,String> readRequests = new HashMap<Integer,String>();
	
	public void loadPendingRequestList() {
		try {
			pendingRequests = AdministratorQuerySet.loadRequestsList(0);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void loadReadRequestsList() {
		try {
			readRequests = AdministratorQuerySet.loadRequestsList(1);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public HashMap<Integer, String> getRequestList() {
		return pendingRequests;
	}

		
	
	public HashMap<Integer, String> getPendingRequests() {
		return pendingRequests;
	}
	public void setPendingRequests(HashMap<Integer, String> pendingRequests) {
		this.pendingRequests = pendingRequests;
	}
	public HashMap<Integer, String> getReadRequests() {
		return readRequests;
	}
	public void setReadRequests(HashMap<Integer, String> readRequests) {
		this.readRequests = readRequests;
	}
	public void setRequestList(HashMap<Integer, String> requestList) {
		this.pendingRequests = requestList;
	}

	public static HashMap<UUIDUser, String[]> loadAllUsers () {
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

}
