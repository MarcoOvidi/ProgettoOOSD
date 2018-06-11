package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserAuthenticationQuerySet;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Working");
	}
    
    
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String usr=request.getParameter("username");
		String psw=request.getParameter("password");
	    usr.replaceAll("[^a-zA-Z0-9]","");
		psw.replaceAll("[^a-zA-Z0-9]","");
		UserAuthenticationQuerySet aut= new UserAuthenticationQuerySet();
		String result;
		try {
			//FIXME vedete voi, ignoro completamente cosa sta accadendo qui.
			result=aut.login(usr, psw);
		} catch (SQLException e) {
			response.getWriter().append("Errore");
			return;
		}
		
		HttpSession session=request.getSession();
		session.setAttribute("id", result);
		response.getWriter().append("Login. ID: "+result);
	}

}
