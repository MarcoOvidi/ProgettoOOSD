package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.UUIDUser;

public class UserAuthenticationQuerySet {
	
	public static boolean registration(String username , String password, String name, String surname, String email) throws DatabaseException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {			
			ps = con.prepareStatement("INSERT INTO user(username,password,status,name,surname,registration_date,email) "
					+ "VALUES (?,?,false,?,?,select DATE_FORMAT(now(),'%d-%m-%y');,?");
			
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, name);
			ps.setString(4, surname);
			ps.setString(5, email);
			
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
	
	public static boolean permissionsControl(Integer permNumb , UUIDUser id) throws DatabaseException, Exception{
		
		switch(permNumb) {
		case 1 :
			return control("download",id);
		case 2 :
			return control("upload",id);
		case 3 :
			return control("editMetadata",id);
		case 4 :
			return control("reviewPage",id);
		case 5 :
			return control("modifyTranscription",id);
		case 6 :
			return control("requestTranscriptionTask",id);
		case 7 :
			return control("reviewTranscription",id);
		case 8 :
			return control("addNewProject",id);
		case 9 :
			return control("assignDigitalizationTask",id);
		case 10 :
			return control("assignTranscriptionTask",id);
		case 11 :
			return control("publishDocument",id);
		default :
			throw new Exception("Permesso non riconosciuto");
		}
	}	
	
	private static boolean control(String permission, UUIDUser userID) throws DatabaseException {
			
			Connection con = null;
		
			try {
				con = DBConnection.connect();
			}catch(DatabaseException e) {
				throw new DatabaseException("Errore di connessione", e);
			}
		
			PreparedStatement ps = null;
			ResultSet rs = null;
		
			try {			
				ps = con.prepareStatement("SELECT ? FROM perm_authorization WHERE ID_user=? ");
		
				ps.setString(1, permission);
				ps.setInt(2, userID.getValue());
				
				rs = ps.executeQuery();
				
				if(rs.next()) 
					return rs.getBoolean(permission);
				else 
					throw new DatabaseException("Utente o permesso non presente");
			
			}catch(SQLException e) {
				
					throw new DatabaseException("Errore di esecuzione della query", e);
			
			}finally {
				try{
					if(ps != null)
						ps.close();
					if(con!=null)
						con.close();
				}
				catch(SQLException e) {
					DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
				}
			}		
		}
	}
