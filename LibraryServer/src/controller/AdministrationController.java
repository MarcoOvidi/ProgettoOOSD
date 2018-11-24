package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.AdministratorQuerySet;
import dao.DatabaseException;
import model.User;
import vo.UUIDUser;

public class AdministrationController {
	
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
