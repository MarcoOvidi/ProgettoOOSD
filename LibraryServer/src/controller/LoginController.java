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

import dao.UserAuthenticationQuerySet;
import fx_view.SceneController;


public class LoginController{
	public static void login(LoginScene lscene, String usr, String psw) {
		
		 try {
			
			 if(UserAuthenticationQuerySet.login(usr, psw));
		    		SceneController.loadScene("home");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();//
		}
		
		lscene.displayMessage("Username o/e password non validi");
	}
}


