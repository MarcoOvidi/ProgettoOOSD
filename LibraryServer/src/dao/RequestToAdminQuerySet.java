package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;

public class RequestToAdminQuerySet {
	//query completate e funzionamento verificato
	
	/* Inserisce nel db una nuova richiesta fatta da un utente
	 * @param Request una richiesta specifica 
	 * @return UUIDRequest l'id che viene associato alla richiesta dal db
	 * @exception NullPointerException se la Request paramentro è null
	 * @exception DatabaseException per problemi di connessione o query sul DB
	 */
	public static UUIDRequest sendRequestToAdmin(Request req) throws NullPointerException,DatabaseException{
		if (req == null)
			throw new NullPointerException("La richiesta non ha un contenuto");
		
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		UUIDRequest id = null;
		
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement("INSERT INTO request(ID_user,object,message,status) "
					+ "VALUE(?,?,?,false);", new String[] {"ID"});
			ps.setInt(1, req.getUser().getValue());
			ps.setString(2, req.getObject());
			ps.setString(3, req.getMessage());
			
			ps.addBatch();
			ps.executeBatch();
			
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				id = new UUIDRequest(rs.getInt(1));
			}
			
			con.commit();
			
		}catch(SQLException e) {
			try {
				con.abort(null);
				
			}catch(SQLException f) {
				DBConnection.logDatabaseException(new DatabaseException("Richiesta già inserita", f));
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
	
	/* Seleziona tutte le richieste inviate da un determinato utente all'amministratore
	 * 
	 * @param UUIDUser utente di cui si devono caricare le richieste
	 * @return LinkedList<Request> richieste dell'utente parametro
	 * @exception NullPointerException se la Request paramentro è null
	 * @exception DatabaseException per problemi di connessione o query sul DB
	 */
	public static LinkedList<Request> loadAllRequestToAdmin(UUIDUser id) throws DatabaseException,NullPointerException{
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
		LinkedList<Request> req = new LinkedList<Request>();
		
		try {
			ps = con.prepareStatement("SELECT * FROM request WHERE ID_user=? ");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			
			while(rs.next()) {
				req.add(new Request(new UUIDRequest(rs.getInt("ID")), id, new UUIDUser(rs.getInt("ID_admin")), 
						rs.getBoolean("status"), rs.getString("object"), rs.getString("message"), 
						rs.getString("answer_message")));
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

	/* Cancella una richiesta inviata all'amministratore prima che venga letta/accettata(ovvero con status=false)
	 * @param UUIDUser utente che vuole cancellare una richiesta
	 * @param UUIDRequest id della richiesta da cancellare
	 * @return Boolean indica se la richiesta è andata a buon fine
	 */
	public static Boolean undoRequestToAdmin(UUIDUser idu,UUIDRequest idr) throws NullPointerException,DatabaseException {
		if(idu == null)
			throw new NullPointerException("Utente non valido");
		if(idr == null)
			throw new NullPointerException("Richiesta non valida");
		
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer linesAffected =0;
		
		try {
			ps = con.prepareStatement("SELECT status FROM request WHERE ID_user=? and ID=? ;");
			ps.setInt(1, idu.getValue());
			ps.setInt(2, idr.getValue());
			
			rs = ps.executeQuery();
			
			
			if(rs.next()) {
				if (rs.getBoolean("status") == true)
					throw new DatabaseException("Un amministratore ha già preso in carico la tua richiesta");
				else {
					if(rs != null)
						rs.close();
					if(ps != null)
						ps.close();
					
					ps = con.prepareStatement("DELETE FROM request WHERE ID=?");
					ps.setInt(1, idr.getValue());
					
					linesAffected = ps.executeUpdate();
					
				}
					
			}
			else {
				throw new NullPointerException("Errore: richiesta non presente o non appartenete all'utente specificato");
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
		
		return (linesAffected == 1);
	}
		
		
}
	
	

