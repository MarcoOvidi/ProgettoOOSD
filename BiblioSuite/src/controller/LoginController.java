package controller;

import java.sql.SQLException;

import dao.DatabaseException;
import dao.EditProfileQuerySet;
import dao.UserAuthenticationQuerySet;
import model.User;
import view.LoginScene;
import view.SceneController;
import view.UserID;
import vo.UUIDUser;

public class LoginController {
	
	/**
	 * 
	 * @param lscene
	 * @param usr nome utente
	 * @param psw password
	 * @return 
	 */
	public static void login(LoginScene lscene, String usr, String psw) throws DatabaseException {
		lscene.displayMessage("Checking identity...");

		try {
			UUIDUser id = loginDB(usr, psw);
			lscene.displayMessage("Connected. Loading user...");
			if (id != null) {
				UserID.setId(id);				
				User user = EditProfileQuerySet.loadUserProfile(id);
				LocalSession.getInstance();
				//LocalSession.setLocalUser(user);
				LocalSession.setLocalUser(user);
				lscene.displayMessage("Done. Loading scene");
				SceneController.loadScene("home2");
			} else {
				lscene.displayMessage("Invalid username or password");
			}
		} catch (DatabaseException e) {
			lscene.displayMessage("Invalid username or password");
			throw e;
		}

	}

	public static UUIDUser loginDB(String usr, String psw) throws DatabaseException {
		UUIDUser id;
		try {
			id = UserAuthenticationQuerySet.login(usr, psw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			id = null;
		}
		return id;
	}
	
	public static void logout () {
		//TODO ask for confirmation
		LocalSession.clear();
		//SceneController.loadScene("login");
		SceneController.loadLogin();
	}
}
