package dao;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

import javax.naming.spi.DirStateFactory.Result;

import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.UserPermissions;

public class AdministratorQuerySet {
	
	
	
	/* Questo metodo cambia lo stato di un utente da attivo a inattivo e viceversa
	 * 
	 * @param user Utente su cui fare l'aggiornamento di stato
	 * @param b boolean true se l'utente deve risultare attivo , false altrimenti
	 * 
	 * @return Boolean 1 se la modifica è stata effettuata, 0 altrimenti
	 * 
	 * @see vo.UUIDUser
	 * 
	 * @exception DatabaseException
	 */
	
	public static Boolean modifyUserStatus(UUIDUser user, Boolean b) throws DatabaseException {
		Connection con = null;
		
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		Integer result = null;
		
		try {
			ps = con.prepareStatement("UPDATE user SET status = ? WHERE id=?");
			ps.setBoolean(1, b);
			ps.setInt(2, user.getValue());	
			
			result = ps.executeUpdate();
			
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
		
		if(result == 1)
			return true;
		else
			return false;
	
	}//end method
		
	
	/* Aggiorna i permessi degli utenti
	 * 
	 * @param id , UUIDUser dell'utente a cui devono essere cambiati i permessi
	 * @param up , Oggetto UserPermission istanziato per avere una maschera permessi da impostare
	 * 
	 * @return Boolean true se l'inserimento è andato a buon fine , false altrimenti
	 * @see vo.UUIDuser,vo.UserPermissions
	 * 
	 * @exception SQLException
	 * 
	 */
	
	public static boolean updateUserPermissions(UUIDUser user,UserPermissions up) throws DatabaseException{
			
		Connection con = null;
		
		try{
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
			
		PreparedStatement ps = null;
		Integer res = null;
		
		try {
			ps = con.prepareStatement("UPDATE perm_authorization "
					+ "SET  download = ?, upload = ?, editMetadata =  ?, "
					+ "reviewPage =  ?, modifyTranscription =  ?, "
					+ "requestTranscriptionTask =  ?, reviewTranscription =  ?, "
					+ "addNewProject =  ?, assignDigitalizationTask = ?, "
					+ "assignTranscriptionTask = ?, publishDocument =  ? "
					+ "WHERE ID_user = ? ;");
			ps.setBoolean(1, up.getDownloadPerm());
			ps.setBoolean(2, up.getUploadPerm());
			ps.setBoolean(3, up.getEditMetaDataPerm());
			ps.setBoolean(4, up.getReviewPagePerm());
			ps.setBoolean(5, up.getModifyTranscriptionPerm());
			ps.setBoolean(6, up.getRequestTranscriptionTaskPerm());
			ps.setBoolean(7, up.getReviewTranscriptionPerm());
			ps.setBoolean(8, up.getAddNewProjectPerm());
			ps.setBoolean(9, up.getAssignDigitalizationTaskPerm());
			ps.setBoolean(10, up.getAssignTranscriptionTaskPerm());
			ps.setBoolean(11, up.getPublishDocumentPerm());
			ps.setInt(12, user.getValue());
			
			res = ps.executeUpdate();
			
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		}finally {
			try {
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();
			}catch(SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errori sulle risorse", e));
			}
		}
		
		return (res == 1);
	}
	
	/* Seleziona il campo oggetto di tutte le richieste inviate dagli utenti 
	 * 
	 * @param b se 0 mostra soltanto gli oggetti delle richieste pending, se 1 delle non pending, se 2 tutte
	 * @return HashMap<Integer,String> l'intero(chiave) rappresenta l'UUIDRequest e la String l'oogetto della request  
	 */
	
	public static HashMap<Integer,String> loadRequestsList(int b) throws DatabaseException {
		
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		HashMap<Integer,String> req = new HashMap<Integer,String>();
		ResultSet rs = null;
		
		try {
			
			if(b == 0) {
				ps = con.prepareStatement("SELECT ID,object from request WHERE status=?;");
				ps.setBoolean(1, false);
			}else {
				ps = con.prepareStatement("SELECT ID,object from request WHERE status=?;");
				if(b ==1) {
					ps.setBoolean(1, true);
				}
				else {
					ps = con.prepareStatement("SELECT ID,object from request WHERE status=? or status=?;");
					ps.setBoolean(1, false);
					ps.setBoolean(2, true);
				}
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				req.put(rs.getInt("ID"), rs.getString("object"));
			}
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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
		
		return req;
		
	}
	
	
	/* Seleziona tutti gli attributi di una richiesta
	 * @param id UUIDRequest di una richiesta
	 * @return Request un oggetto richiesta completo
	 * @throw NullPointerException in caso l'id parametro non sia presente nel db
	 */
	public static Request loadRequest(UUIDRequest id) throws NullPointerException,DatabaseException {
		
		if(id == null)
			throw new NullPointerException("Errore, id richiesta non valido");
		
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		Request r = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("SELECT * from request WHERE id=?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				r = new Request(new UUIDRequest(rs.getInt("ID")), new UUIDUser(rs.getInt("ID_user")),
						new UUIDUser(rs.getInt("ID_admin")), rs.getBoolean("status"), rs.getString("object"), 
						rs.getString("Message"), rs.getString("answer_message"));
			}
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
		}finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();
			}catch(SQLException ex) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", ex));
			}
		}
		return r;
	}
	
	/* Aggiorna l'attributo answer di una richiesta e imposta la request non pending
	 * @param id UUIDRequest della richiesta a cui rispondere
	 * @param answer Corpo del messaggio risposta
	 * @return Boolean true se l'operazione è andata a buon fine , false altrimenti
	 */
	//TODO per me se un admin mand la risposta il booleanno status viene messo a true nel senso che la richiesta  è stata analizzata 
	public static Boolean answerRequest(UUIDRequest id,String answer) throws DatabaseException{
		Connection con = null;
		
		try{
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		Integer result = null;
		
		try {
			ps = con.prepareStatement("UPDATE request SET answer_message=? , status=true WHERE ID=?;");
			ps.setString(1, answer);
			ps.setInt(2, id.getValue());
			
			result = ps.executeUpdate();
			
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore nella query", e);
		}finally {
			try {
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();
			}catch(SQLException ex) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", ex));
			}
		}
		
		return (result == 1);
		
	}//end method
	
		
}
	
	
	

