package controller;

import java.sql.SQLException;

import dao.DatabaseException;
import dao.EditProfileQuerySet;
import dao.UserAuthenticationQuerySet;
import fx_view.LoginScene;
import fx_view.SceneController;
import fx_view.UserID;
import model.User;
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
		lscene.displayMessage("Verifico identit�...");

		try {
			UUIDUser id = loginDB(usr, psw);			
			if (id != null) {
				SceneController.loadScene("home");
				UserID.setId(id);				
				User user = EditProfileQuerySet.loadUserProfile(id);
				LocalSession.setLocalUser(user);
			} else {
				lscene.displayMessage("Username o/e password non validi");
			}
		} catch (DatabaseException e) {
			lscene.displayMessage("Ops qualcosa � andato storto");
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
}
