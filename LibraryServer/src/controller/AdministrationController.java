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

	private HashMap<UUIDRequest, String> pendingRequests = new HashMap<UUIDRequest, String>();
	private HashMap<UUIDRequest, String> readRequests = new HashMap<UUIDRequest, String>();

	public HashMap<UUIDRequest, String> getPendingRequests() {
		return pendingRequests;
	}

	public void setPendingRequests(HashMap<UUIDRequest, String> pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

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
			setReadRequests(AdministratorQuerySet.loadRequestsList(1));
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public Request loadRequest(UUIDRequest id) {
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

	public HashMap<UUIDRequest, String> getReadRequests() {
		return readRequests;
	}

	public void setReadRequests(HashMap<UUIDRequest, String> readRequests) {
		this.readRequests = readRequests;
	}

}
