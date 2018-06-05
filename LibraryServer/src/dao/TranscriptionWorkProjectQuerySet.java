package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import vo.UUIDUser;

import model.TranscriptionWorkProject;
import vo.UUIDTranscriptionWorkProject;

public class TranscriptionWorkProjectQuerySet {
	//TODO
	public static void insertTranscriptionWorkProject() {
		
	}
	
	/* Seleziona un transcriptionWorkProject con il relativo personale
	 * @param UUIDTranscriptionProject id del progetto da caricare
	 * @return TranscriptionWorkProject oggetto completo
	 */
	public static TranscriptionWorkProject loadTranscriptionWorkProject(UUIDTranscriptionWorkProject id) throws DatabaseException, NullPointerException {
		if (id == null)
			throw new NullPointerException("Id non vallido");
		
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		TranscriptionWorkProject twp = null;
		LinkedList<UUIDUser> transcribers = new LinkedList<UUIDUser>();
		LinkedList<UUIDUser> revisers = new LinkedList<UUIDUser>();
		
		try {
			ps = con.prepareStatement("SELECT ID_transcriber_user FROM transcription_project_transcriber_partecipant "
					+ "WHERE ID_transcription_project = ?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				transcribers.add(new UUIDUser(rs.getInt("ID_transcriber_user")));
			}
			
			
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
			
			ps = con.prepareStatement("SELECT ID_reviser_user FROM transcription_project_reviser_partecipant "
					+ "WHERE ID_transcription_project=?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			
			while(rs.next()) {
				revisers.add(new UUIDUser(rs.getInt("ID_reviser_user")));
			}
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
			
			
			ps = con.prepareStatement("SELECT ID_coordinator,date,transcription_complete "
					+ "FROM transcription_project WHERE ID=?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getDate("date") != null) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = rs.getString("date");
				Date d = sdf.parse(date);
				
				twp = new TranscriptionWorkProject(d, new UUIDUser(rs.getInt("ID_coordinator")), 
					transcribers, revisers, id, rs.getBoolean("transcription_complete"));
			
				}else {
					twp = new TranscriptionWorkProject(null, new UUIDUser(rs.getInt("ID_coordinator")), 
							transcribers, revisers, id, rs.getBoolean("transcription_complete"));
				
				}
			}
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		}catch(ParseException pe){
			throw new DatabaseException("Errore nel parsing della data", pe);
		}finally {
	
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();
			}catch(SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
		}
		
		return twp;
	
		
		
	}
	
	//aggiorna un transcriptionWorkProject con il relativo personale
    public void updateTranscriptionWorkProject() {
		
	}
    
    //cancella un TranscriptionWorkProject con il relativo personale
    public static Boolean deleteTranscriptionWorkProject(UUIDTranscriptionWorkProject id) throws DatabaseException,NullPointerException {
    	if (id == null)
			throw new NullPointerException("Id non vallido");
		
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement("DELETE FROM transcription_project WHERE ID=?;");
			ps.setInt(1, id.getValue());
			
			return (ps.executeUpdate()==1);
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		}finally{
	
			try {
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();
			}catch(SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
		}
	}
}
