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
	
	public static boolean login(String usr, String psw) throws SQLException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<Integer,String> twpl = new HashMap<Integer,String>();
		
		try {
			
			ps = con.prepareStatement("SELECT tp.ID as ID_progetto, d.title as Title "
					  + "FROM transcription_project as tp JOIN transcription_project_transcriber_partecipant as tptp "
					  + "JOIN document as d "
					  + "ON tp.ID=tptp.ID_transcription_project and tp.ID_document=d.ID "
					  + "WHERE ID_transcriber_user=? ;"); 
		
			ps.setInt(1, usr.getValue());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				twpl.put(rs.getInt("ID_progetto"), rs.getString("Title"));
			}
			
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
		
		
		
		
		
		
		return false;
	}
}

