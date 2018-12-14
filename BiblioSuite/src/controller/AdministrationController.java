package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.AdministrationQuerySet;
import dao.DatabaseException;
import dao.EditProfileQuerySet;
import model.User;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;

public class AdministrationController {

	/**
	 * Carica tutte le richieste inviate dagli utenti agli admin 
	 * @param i i=0 request not read ; i=1 read request
	 * @return LinkedList<Request> richieste presenti nel DB
	 */
	public static LinkedList<Request> getRequest(Integer i){
		LinkedList<Request> req = null;
		try {
			req = AdministrationQuerySet.loadRequestsList(i);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return req;
	}

	public static Request loadRequest(UUIDRequest id) {
		Request r = null;
		try {
			r = AdministrationQuerySet.loadRequest(id);
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

	public static LinkedList<User> loadUserList(Boolean status) {
		LinkedList<User> res = null;
		try {
			res = AdministrationQuerySet.loadUserList(status);
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
			AdministrationQuerySet.ignoreRequest(id);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static void answerUserRequest(UUIDRequest id, String answer) {
		try{
			AdministrationQuerySet.answerRequest(id, answer);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static void modifyUserStatus(UUIDUser id , Boolean status) {
		try {
			AdministrationQuerySet.modifyUserStatus(id, status);
		}catch(Exception e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static HashMap<String, Integer> getInvolvedUser(UUIDUser u){
		try {
			return AdministrationQuerySet.userIsInvolved(u);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
