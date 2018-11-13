package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import vo.UUIDUser;
import model.TranscriptionWorkProject;
import vo.UUIDDocument;
import vo.UUIDTranscriptionWorkProject;

public class TranscriptionWorkProjectQuerySet {
	//TODO Cosa manca?
	public static UUIDTranscriptionWorkProject insertTranscriptionWorkProject(LinkedList<UUIDUser> transcribers , 
			LinkedList<UUIDUser> revisers, UUIDUser coordinator, Boolean completed, UUIDDocument doc) throws DatabaseException {
			Connection con = null;
			
			try {
				con = DBConnection.connect();
			}catch(DatabaseException e) {
				throw new DatabaseException("Errore di connessione", e);
			}
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			UUIDTranscriptionWorkProject id = null;
			
			try {
				con.setAutoCommit(false);
				ps = con.prepareStatement("insert into transcription_project(ID_coordinator, ID_document,transcription_complete) values (?,?,?);", new String[] {"ID"});
				ps.setInt(1, coordinator.getValue());
				ps.setInt(2, doc.getValue());
				ps.setBoolean(3, completed);
			
				ps.addBatch();
				ps.executeBatch();
				
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					id = new UUIDTranscriptionWorkProject(rs.getInt(1));
				}
				con.commit();
				con.setAutoCommit(true);
				
				if (rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				
				Iterator<UUIDUser> itr = transcribers.iterator();
				
				while(itr.hasNext()) {
					UUIDUser usr = itr.next();
					ps = con.prepareStatement("insert into "
							+ "transcription_project_transcriber_partecipant(ID_transcription_project, ID_transcriber_user) "
							+ "values (?,?);");
					ps.setInt(1, id.getValue());
					ps.setInt(2, usr.getValue());
					rs = ps.executeQuery();
				}
				
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				
				itr = revisers.iterator();
				
				while(itr.hasNext()) {
					UUIDUser usr = itr.next();
					ps = con.prepareStatement("insert into "
							+ "transcription_project_reviser_partecipant(ID_transcription_project, ID_reviser_user) "
							+ "values (?,?);");
					ps.setInt(1, id.getValue());
					ps.setInt(2, usr.getValue());
				
				}
				
			}catch(SQLException e) {
				try {
					con.abort(null);	
				}catch(SQLException f) {
					DBConnection.logDatabaseException(new DatabaseException("Duplicato", f));
				}
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
			
			return id;
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
	
	/* Aggiunge un utente Trascrittore ad un progetto di Trascrizione
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si deve inserire l'utente
	 * @param UUIDUser ID dell'utente da inserire
	 * @return Boolean true se l'utente è stato inserito correttamente nel progetto di trascrizione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Boolean insertTranscriberUser(UUIDTranscriptionWorkProject ids, UUIDUser id) throws DatabaseException,NullPointerException {
    	if(ids == null)
    		throw new NullPointerException("Id Progetto non valido");
    	
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
			ps = con.prepareStatement("INSERT INTO transcription_project_transcriber_partecipant "
					+ "(ID_transcription_project,ID_transcriber_user) VALUE (?,?);");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, id.getValue());
			
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
    
    /* Aggiungi un utente Revisore ad un progetto di Trascrizione
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si deve inserire l'utente
	 * @param UUIDUser ID dell'utente da inserire
	 * @return Boolean true se l'utente è stato inserito correttamente nel progetto di trascrizione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Boolean insertReviserUser(UUIDUser id,UUIDTranscriptionWorkProject ids) throws DatabaseException,NullPointerException{
    	if(ids == null)
    		throw new NullPointerException("Id Progetto non valido");
    	
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
			ps = con.prepareStatement("INSERT INTO transcription_project_reviser_partecipant "
					+ "(ID_transcription_project,ID_reviser_user) VALUE (?,?);");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, id.getValue());
			
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
    
    /* Rimuove un utente Trascrittore da un progetto di Trascrizione
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si deve inserire l'utente
	 * @param UUIDUser ID dell'utente da inserire
	 * @return Boolean true se l'utente è stato eliminato correttamente nel progetto di trascrizione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Boolean removeTranscriberUser(UUIDUser id, UUIDTranscriptionWorkProject ids) throws DatabaseException,NullPointerException {
    	if(ids == null)
    		throw new NullPointerException("Id Progetto non valido");
    	
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
			ps = con.prepareStatement("DELETE FROM transcription_project_transcriber_partecipant "
					+ "WHERE ID_transcription_project = ? AND ID_transcriber_user =?;");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, id.getValue());
			
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

    /* Rimuove un utente Revisore da un progetto di Trascrizione
	 * @param UUIDTrnascriptionWorkProject ID del progetto in cui si deve inserire l'utente
	 * @param UUIDUser ID dell'utente da inserire
	 * @return Boolean true se l'utente è stato eliminato correttamente nel progetto di trascrizione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Boolean removeReviserUser(UUIDUser id, UUIDTranscriptionWorkProject ids) throws DatabaseException,NullPointerException {
    	if(ids == null)
    		throw new NullPointerException("Id Progetto non valido");
    	
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
			ps = con.prepareStatement("DELETE FROM transcription_project_reviser_partecipant "
					+ "WHERE ID_transcription_project = ? AND ID_reviser_user =?;");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, id.getValue());
			
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
    

    /* Inserisce una lista di utenti Trascrittori in un progetto di Trascrizione
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si devono inserire gli utenti
	 * @param LinkedList<UUIDUser> ID degli utenti da inserire
	 * @return Integer numero di Trascrittori correttamente inseriti nel progetto di trascrizione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Integer insertTranscriberUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids) throws DatabaseException,NullPointerException{
    	if(idl == null)
    		throw new NullPointerException("Lista di utenti non valida");
		if(ids == null)
			throw new NullPointerException("Id Progetto non valido");
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		
		Iterator<UUIDUser> itr = idl.iterator();
		
		Integer linesAffected = 0;
		
		try {
			ps = con.prepareStatement("INSERT INTO transcription_project_transcriber_partecipant"
					+ "(ID_transcription_project,ID_transcriber_user) "
					+ "VALUE (?,?);");
			ps.setInt(1, ids.getValue());
			
			while(itr.hasNext()) {
				Integer user = itr.next().getValue();
				ps.setInt(2, user);
				
				linesAffected += ps.executeUpdate();
			}
			
			return linesAffected;
			
			
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
    
    /* Inserisce una lista di utenti Revisori in un progetto di Transcrizione
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si devono inserire gli utenti
	 * @param LinkedList<UUIDUser> ID degli utenti da inserire
	 * @return Integer numero di Revisori correttamente inseriti nel progetto di trascrizione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Integer insertReviserUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids) throws DatabaseException,NullPointerException{
    	if(idl == null)
    		throw new NullPointerException("Lista di utenti non valida");
		if(ids == null)
			throw new NullPointerException("Id Progetto non valido");
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		
		Iterator<UUIDUser> itr = idl.iterator();
		
		Integer linesAffected = 0;
		
		try {
			ps = con.prepareStatement("INSERT INTO transcription_project_reviser_partecipant"
					+ "(ID_transcription_project,ID_reviser_user) "
					+ "VALUE (?,?);");
			ps.setInt(1, ids.getValue());
			
			while(itr.hasNext()) {
				Integer user = itr.next().getValue();
				ps.setInt(2, user);
				
				linesAffected += ps.executeUpdate();
			}
			
			return linesAffected;
			
			
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
    
    /* Rimuove una lista di utenti Trascrittori da un progetto di Trascrizione
	 * @param UUIDTranscriptionWorkProject ID del progetto da cui si devono eliminare gli utenti
	 * @param LinkedList<UUIDUser> ID degli utenti da eliminare
	 * @return Integer numero di Trascrittori correttamente rimossi dal progetto di trascrizione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Integer removeTranscriberUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids) throws DatabaseException,NullPointerException{
    	if(idl == null)
    		throw new NullPointerException("Lista di utenti non valida");
		if(ids == null)
			throw new NullPointerException("Id Progetto non valido");
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		
		Iterator<UUIDUser> itr = idl.iterator();
		
		Integer linesAffected = 0;
		
		try {
			ps = con.prepareStatement("DELETE FROM transcription_project_transcriber_partecipant "
					+ "WHERE ID_transcription_project=? AND ID_transcriber_user=?");
			ps.setInt(1, ids.getValue());
			
			while(itr.hasNext()) {
				Integer user = itr.next().getValue();
				ps.setInt(2, user);
				
				linesAffected += ps.executeUpdate();
			}
			
			return linesAffected;
			
			
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
    
    /* Rimuoveuna lista di utenti Revisori da un progetto di Trascrizione
	 * @param UUIDTranscriptionWorkProject ID del progetto da cui si devono eliminare gli utenti
	 * @param LinkedList<UUIDUser> ID degli utenti da eliminare
	 * @return Integer numero di Revisori correttamente rimossi dal progetto di trascrizione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Integer removeReviserUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids) throws DatabaseException,NullPointerException{
    	if(idl == null)
    		throw new NullPointerException("Lista di utenti non valida");
		if(ids == null)
			throw new NullPointerException("Id Progetto non valido");
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		
		Iterator<UUIDUser> itr = idl.iterator();
		
		Integer linesAffected = 0;
		
		try {
			ps = con.prepareStatement("DELETE FROM transcription_project_reviser_partecipant "
					+ "WHERE ID_transcription_project=? AND ID_reviser_user=?");
			ps.setInt(1, ids.getValue());
			
			while(itr.hasNext()) {
				Integer user = itr.next().getValue();
				ps.setInt(2, user);
				
				linesAffected += ps.executeUpdate();
			}
			
			return linesAffected;
			
			
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
