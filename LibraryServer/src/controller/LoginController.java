package controller;

import fx_view.LoginScene;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DatabaseException;
import dao.UserAuthenticationQuerySet;
import fx_view.SceneController;
<<<<<<< HEAD
import vo.UUIDUser;
import fx_view.UserID;
=======
import model.User;
import vo.UUIDUser;
import controller.SessionDataHandler;

public class LoginController {
	
	/**
	 * 
	 * @param lscene
	 * @param usr nome utente
	 * @param psw password
	 * @return instanza User corrispondente alle credenziali valide di login, null se invalide
	 */
	public static User login(LoginScene lscene, String usr, String psw) throws DatabaseException {
		lscene.displayMessage("Verifico identitï¿½...");

public class LoginController{
	public static void login(LoginScene lscene, String usr, String psw) {
		lscene.displayMessage("Verifico identità...");
		 try {
			
			 try {
				Object uuiduser=UserAuthenticationQuerySet.login(usr, psw);
				if(uuiduser instanceof UUIDUser) {
				UserID.setId(((UUIDUser) uuiduser).getValue());
				SceneController.loadScene("home"); 
				//aaa
				}
				else
				{
					lscene.displayMessage("Username o/e password non validi");
				}
			 } catch (DatabaseException e) {
				// TODO Auto-generated catch block
					lscene.displayMessage("Ops qualcosa è andato storto");
				e.printStackTrace();
		try {
			UUIDUser id = loginDB(usr, psw);
			if (id != null) {
				SceneController.loadScene("home");
				return SessionDataHandler.GetInstance().getUser(id);
			} else {
				lscene.displayMessage("Username o/e password non validi");
				return null;
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			lscene.displayMessage("Ops qualcosa ï¿½ andato storto");
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
