package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.AdministrationQuerySet;
import dao.concrete.DatabaseException;
import dao.concrete.EditProfileQuerySet;
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
			req = new AdministrationQuerySet().loadRequestsList(i);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return req;
	}

	/**
	 * Carica integralmente una richiesta mandata da un utente
	 * @param id
	 * @return
	 */
	public static Request loadRequest(UUIDRequest id) {
		Request r = null;
		try {
			r = new AdministrationQuerySet().loadRequest(id);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	
	/**
	 * Carica tutti gli utenti registrati alla biblioteca
	 * @return HashMap<UUIDUser, String[]> 
	 */
	public static HashMap<UUIDUser, String[]> loadAllUsers() {
		HashMap<UUIDUser, String[]> users = new HashMap<UUIDUser, String[]>();
		return users;
	}

	
	/**
	 * Carica gli utenti con un determinato stato 
	 * @param status 1=attivi 0=non attivi
	 * @return LinkedList<User>
	 */
	public static LinkedList<User> loadUserList(Boolean status) {
		LinkedList<User> res = null;
		try {
			res = new AdministrationQuerySet().loadUserList(status);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}
	
	/**
	 * Ritorna l'usernamen di un utente
	 * @param id utente
	 * @return String username
	 */
	public static String loadUsername(UUIDUser id) {
		String u = null;
		try {
			u = new EditProfileQuerySet().loadUserProfile(id).getUsername();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return u;
	}
	
	/**
	 * Archivia una richiesta senza rispondere
	 * @param id richiesta
	 */
	public static void ignoreUserRequest(UUIDRequest id) {
		try{
			new AdministrationQuerySet().ignoreRequest(id);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Risponde alla richiesta di un utente
	 * @param id richiesta
	 * @param answer
	 */
	public static void answerUserRequest(UUIDRequest id, String answer) {
		try{
			new AdministrationQuerySet().answerRequest(id, answer);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Rende un utente inattivo attivo e viceversa
	 * @param id utente
	 * @param status 0=disattivo 1=attivo
	 */
	public static void modifyUserStatus(UUIDUser id , Boolean status) {
		try {
			new AdministrationQuerySet().modifyUserStatus(id, status);
		}catch(Exception e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Ritorna tutti i progetti in cui l'utente è partecipe in qualsiasi ruolo
	 * @param u id user
	 * @return HashMap<String, Integer>
	 */
	public static HashMap<String, Integer> getInvolvedUser(UUIDUser u){
		try {
			return new AdministrationQuerySet().userIsInvolved(u);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Cambia il livello di un trascrittore
	 * @param idUSer
	 * @param level
	 */
	public static void changeTranscriberLevel(UUIDUser idUSer, int level) {
		try {
			new AdministrationQuerySet().changeTranscriberLevel(idUSer, level);
		}catch(DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Seleziona il livello di un trascrittore
	 * @param idUSer
	 * @param level
	 */
	public static int getTranscriberLevel(UUIDUser idUSer) {
		try {
			return new AdministrationQuerySet().getTranscriberLevel(idUSer);
		}catch(DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return -1;
		}
	}
}
