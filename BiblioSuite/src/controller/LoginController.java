package controller;

import java.sql.SQLException;

import dao.concrete.DatabaseException;
import dao.concrete.EditProfileQuerySet;
import dao.concrete.UserAuthenticationQuerySet;
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
				User user = new EditProfileQuerySet().loadUserProfile(id);
				//LocalSessionBridge.getInstance();
				//LocalSession.setLocalUser(user);
				LocalSessionBridge.setLocalUser(user);
				lscene.displayMessage("Done. Loading scene");
				SceneController.loadScene("home");
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
			id = new UserAuthenticationQuerySet().login(usr, psw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			id = null;
		}
		return id;
	}
	
	public static void logout () {
		//TODO ask for confirmation
		LocalSessionBridge.clear();
		//SceneController.loadScene("login");
		SceneController.loadLogin();
	}
}
