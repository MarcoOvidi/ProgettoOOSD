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
import model.ScanningWorkProject;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public class ScanningWorkProjectQuerySet {
	
	//iscerisce un nuovo ScanningWorkProject nel DB con il relativo personale
	public void insertScanningWorkProject() {
		
	}
	
	
	/* Seleziona uno ScanningWorkProject con il relativo personale
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
	


	/* Aggiungi un utente Digitalizzatore ad un progetto di Digitalizzazione
	 * @param UUIDScanningWorkProject ID del progetto in cui si deve inserire l'utente
	 * @param UUIDUser ID dell'utente da inserire
	 * @return Boolean true se l'utente è stato inserito correttamente nel progetto di digitalizzazione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Boolean insertDigitalizerUser(UUIDScanningWorkProject ids, UUIDUser id) throws DatabaseException,NullPointerException {
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
			ps = con.prepareStatement("INSERT INTO scanning_project_digitalizer_partecipant "
					+ "(ID_scanning_project,ID_digitalizer_user) VALUE (?,?);");
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
    
    /* Aggiungi un utente Revisore ad un progetto di Digitalizzazione
	 * @param UUIDScanningWorkProject ID del progetto in cui si deve inserire l'utente
	 * @param UUIDUser ID dell'utente da inserire
	 * @return Boolean true se l'utente è stato inserito correttamente nel progetto di digitalizzazione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Boolean insertReviserUser(UUIDUser id,UUIDScanningWorkProject ids) throws DatabaseException,NullPointerException{
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
			ps = con.prepareStatement("INSERT INTO scanning_project_reviser_partecipant "
					+ "(ID_scanning_project,ID_reviser_user) VALUE (?,?);");
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
    
    /* Rimuove un utente Digitalizzatore da un progetto di Digitalizzazione
	 * @param UUIDScanningWorkProject ID del progetto in cui si deve inserire l'utente
	 * @param UUIDUser ID dell'utente da inserire
	 * @return Boolean true se l'utente è stato eliminato correttamente nel progetto di digitalizzazione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Boolean removeDigitalizerUser(UUIDUser id, UUIDScanningWorkProject ids) throws DatabaseException,NullPointerException {
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
			ps = con.prepareStatement("DELETE FROM scanning_project_digitalizer_partecipant "
					+ "WHERE ID_scanning_project = ? AND ID_digitalizer_user =?;");
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

    /* Rimuove un utente Revisore da un progetto di Digitalizzazione
	 * @param UUIDScanningWorkProject ID del progetto in cui si deve inserire l'utente
	 * @param UUIDUser ID dell'utente da inserire
	 * @return Boolean true se l'utente è stato eliminato correttamente nel progetto di digitalizzazione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Boolean removeReviserUser(UUIDUser id, UUIDScanningWorkProject ids) throws DatabaseException,NullPointerException {
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
			ps = con.prepareStatement("DELETE FROM scanning_project_reviser_partecipant "
					+ "WHERE ID_scanning_project = ? AND ID_reviser_user =?;");
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
    

    /* Inserisce una lista di utenti Digitalizzatori in un progetto di Digitalizzazione
	 * @param UUIDScanningWorkProject ID del progetto in cui si devono inserire gli utenti
	 * @param LinkedList<UUIDUser> ID degli utenti da inserire
	 * @return Integer numero di Digitalizzatori correttamente inseriti nel progetto di digitalizzazione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Integer insertDigitalizerUser(LinkedList<UUIDUser> idl, UUIDScanningWorkProject ids) throws DatabaseException,NullPointerException{
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
			ps = con.prepareStatement("INSERT INTO scanning_project_digitalizer_partecipant(ID_scanning_project,ID_digitalizer_user) "
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
    
    /* Inserisce una lista di utenti Revisori in un progetto di Digitalizzazione
	 * @param UUIDScanningWorkProject ID del progetto in cui si devono inserire gli utenti
	 * @param LinkedList<UUIDUser> ID degli utenti da inserire
	 * @return Integer numero di Revisori correttamente inseriti nel progetto di digitalizzazione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Integer insertReviserUser(LinkedList<UUIDUser> idl, UUIDScanningWorkProject ids) throws DatabaseException,NullPointerException{
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
			ps = con.prepareStatement("INSERT INTO scanning_project_reviser_partecipant(ID_scanning_project,ID_reviser_user) "
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
    
    /* Rimuoveuna lista di utenti Digitalizzatori da un progetto di Digitalizzazione
	 * @param UUIDScanningWorkProject ID del progetto da cui si devono eliminare gli utenti
	 * @param LinkedList<UUIDUser> ID degli utenti da eliminare
	 * @return Integer numero di Digitalizzatori correttamente rimossi dal progetto di digitalizzazione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Integer removeDigitalizerUser(LinkedList<UUIDUser> idl, UUIDScanningWorkProject ids) throws DatabaseException,NullPointerException{
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
			ps = con.prepareStatement("DELETE FROM scanning_project_digitalizer_partecipant "
					+ "WHERE ID_scanning_project=? AND ID_digitalizer_user=?");
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
    
    /* Rimuoveuna lista di utenti Revisori da un progetto di Digitalizzazione
	 * @param UUIDScanningWorkProject ID del progetto da cui si devono eliminare gli utenti
	 * @param LinkedList<UUIDUser> ID degli utenti da eliminare
	 * @return Integer numero di Revisori correttamente rimossi dal progetto di digitalizzazione
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
    public static Integer removeReviserUser(LinkedList<UUIDUser> idl, UUIDScanningWorkProject ids) throws DatabaseException,NullPointerException{
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
			ps = con.prepareStatement("DELETE FROM scanning_project_reviser_partecipant "
					+ "WHERE ID_scanning_project=? AND ID_reviser_user=?");
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
    
    
    /* Cancella un TranscriptionWorkProject con il relativo personale
     * @param UUIDScanningWorkProject ID del progetto da eliminare
     * @return Boolean true se il Progetto di Digitalizzazione è stato correttamente eliminato
     * @throw DatabaseException in caso di errori di connessione al DB o di esecuzione di query
     * @throw NullPointerException In caso il parametro di input sia null
     */
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
