package dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.DBConnection;

public class UserAuthenticationQuerySet {
	
	private Connection con;
	
	public UserAuthenticationQuerySet(){
        con = DBConnection.getConnection();
	}
	
	
	public void registration() {
		
	}
	
	public String login(String usr, String psw) throws SQLException {

		    Statement stmt = null;
			
		   String query = "select id from user where username='"+usr+"' and passwd='"+psw+"'";
			try {
		        stmt=con.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        	if(rs.next()) {
		            String id = rs.getString("id");
		           
		        	return id;
		        	}else return "Authentication Failed";
		       
		    } catch (SQLException e ) {
		    	
		    } finally {
		        if (stmt != null) { stmt.close(); }
		    }
			return null;
		
	}

}
