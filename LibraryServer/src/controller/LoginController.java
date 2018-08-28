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
import vo.UUIDUser;


public class LoginController{
	public static void login(LoginScene lscene, String usr, String psw) {
		lscene.displayMessage("Verifico identità...");
		 try {
			
			 try {
				Object uuiduser=UserAuthenticationQuerySet.login(usr, psw);
				if(uuiduser instanceof UUIDUser) {
				SceneController.loadScene("home");
				}
				else
				{
					lscene.displayMessage("Username o/e password non validi");
				}
			 } catch (DatabaseException e) {
				// TODO Auto-generated catch block
					lscene.displayMessage("Ops qualcosa è andato storto");
				e.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			lscene.displayMessage("Ops qualcosa è andato storto");
			e.printStackTrace();//
		}
		

	}
}


