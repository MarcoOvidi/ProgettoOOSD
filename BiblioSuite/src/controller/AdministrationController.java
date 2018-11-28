package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.AdministratorQuerySet;
import dao.DatabaseException;
import dao.EditProfileQuerySet;
import model.User;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;

public class AdministrationController {

	public static LinkedList<Request> getRequest(Integer i){
		LinkedList<Request> req = null;
		try {
			req = AdministratorQuerySet.loadRequestsList(i);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return req;
	}

	public static Request loadRequest(UUIDRequest id) {
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
	
	public static String loadUsername(UUIDUser id) {
		String u = null;
		try {
			u = EditProfileQuerySet.loadUserProfile(id).getUsername();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return u;
	}
	
	public static void ignoreUserRequest(UUIDRequest id) {
		try{
			AdministratorQuerySet.ignoreRequest(id);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static void answerUserRequest(UUIDRequest id, String answer) {
		try{
			AdministratorQuerySet.answerRequest(id, answer);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
