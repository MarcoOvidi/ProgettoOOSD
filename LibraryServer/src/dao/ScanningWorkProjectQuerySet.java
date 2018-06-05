package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import model.ScanningWorkProject;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public class ScanningWorkProjectQuerySet {
	
	//iscerisce un nuovo ScanningWorkProject nel DB con il relativo personale
	public void insertScanningWorkProject() {
		
	}
	
	//seleziona uno ScanningWorkProject con il relativo personale
	/* Seleziona un transcriptionWorkProject con il relativo personale
	 * @param UUIDTranscriptionProject id del progetto da caricare
	 * @return TranscriptionWorkProject oggetto completo
	 */
	public static ScanningWorkProject loadScanningWorkProject(UUIDScanningWorkProject id) throws DatabaseException, NullPointerException {
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
		ScanningWorkProject swp = null;
		LinkedList<UUIDUser> digitalizers = new LinkedList<UUIDUser>();
		LinkedList<UUIDUser> revisers = new LinkedList<UUIDUser>();
		
		try {
			ps = con.prepareStatement("SELECT ID_digitalizer_user FROM scanning_project_digitalizer_partecipant "
					+ "WHERE ID_scanning_project = ?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				digitalizers.add(new UUIDUser(rs.getInt("ID_digitalizer_user")));
			}
			
			
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
			
			ps = con.prepareStatement("SELECT ID_reviser_user FROM scanning_project_reviser_partecipant "
					+ "WHERE ID_scanning_project=?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			
			while(rs.next()) {
				revisers.add(new UUIDUser(rs.getInt("ID_reviser_user")));
			}
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
			
			
			ps = con.prepareStatement("SELECT ID_coordinator,date,scanning_complete "
					+ "FROM scanning_project WHERE ID=?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getDate("date") != null) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = rs.getString("date");
				Date d = sdf.parse(date);
				
				swp = new ScanningWorkProject(d, new UUIDUser(rs.getInt("ID_coordinator")), 
					digitalizers, revisers, id, rs.getBoolean("scanning_complete"));
			
				}else {
					swp = new ScanningWorkProject(null, new UUIDUser(rs.getInt("ID_coordinator")), 
							digitalizers, revisers, id, rs.getBoolean("transcription_complete"));
				
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
		
		return swp;
	}
	


	//Aggiorna uno scanningWorkProject con il relativo personale
    public void updateScanningWorkProject() {
		
	}
    
    //cancella un TranscriptionWorkProject con il relativo personale
    public static Boolean deleteScanningWorkProject(UUIDScanningWorkProject id) throws DatabaseException,NullPointerException {
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
			ps = con.prepareStatement("DELETE FROM scanning_project WHERE ID=?;");
			ps.setInt(1, id.getValue());
			
			return (ps.executeUpdate() == 1);
			
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
