package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserAuthenticationQuerySet {
	
	
	public UserAuthenticationQuerySet(){
	}
	
	public void registration() {
		
	}
	
	public static boolean login(String usr, String psw) throws SQLException, DatabaseException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {			
			ps = con.prepareStatement("SELECT id FROM user WHERE username=? AND passwd=?");
		
			ps.setString(1, usr);
			ps.setString(2, psw);
			rs = ps.executeQuery();
			
			if(rs.next()) 
				return true;
			else 
				return false;
			
		}catch(SQLException e) {
			
				throw new DatabaseException("Errore di esecuzione della query", e);
			
		}finally {
			try{
				if(ps != null)
					ps.close();
				if(con!=null)
					con.close();
			}catch(SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
			
			
		}			
	}
}

