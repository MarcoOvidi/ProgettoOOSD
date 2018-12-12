package controller;

import java.sql.SQLException;

import dao.DatabaseException;
import dao.UserAuthenticationQuerySet;

public class RegistrationController {

	public static String register(String username, String password, String email, String name, String surname) {
		if(!checkValidUsername(username))
			return "Invalid username.";
		
		try {
			if(!checkUsername(username))
				return "Username already exists.";
		} catch (SQLException | DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		boolean res = false;
		try {
			res = UserAuthenticationQuerySet.registration(username, password, name, surname, email);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (res)
			return "Success.";
		else
			return "Error.";
	}

	public static boolean checkValidUsername(String username){
		// TODO more check on string content
		if (username.length() < 5)
			return false;
		return true;
	}

	/*
	 * Checks if the inserted username is valid and not already taken
	 * @return true
	 */
	public static boolean checkUsername(String username) throws SQLException, DatabaseException {
		//TODO da completare
		if (checkValidUsername(username))
			return false;
		return (!UserAuthenticationQuerySet.checkIfUsernameExists(username));
	}

}
