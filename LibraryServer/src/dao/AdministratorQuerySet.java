package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import model.User;
import vo.Email;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.UserInformations;
import vo.UserPermissions;

public class AdministratorQuerySet { // tutto ok

	/**
	 * Questo metodo cambia lo stato di un utente da attivo a inattivo e viceversa
	 * 
	 * @param user   Utente su cui fare l'aggiornamento di stato
	 * @param status boolean true se l'utente deve risultare attivo , false
	 *               altrimenti
	 * 
	 * @return Boolean 1 se la modifica è stata effettuata, 0 altrimenti
	 * 
	 * @see vo.UUIDUser
	 * 
	 * @exception DatabaseException
	 */
	public static Boolean modifyUserStatus(UUIDUser user, Boolean status) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		Integer result = null;

		try {
			ps = con.prepareStatement("UPDATE user SET status = ? WHERE id=?");
			ps.setBoolean(1, status);
			ps.setInt(2, user.getValue());

			result = ps.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}

		}

		if (result == 1)
			return true;
		else
			return false;

	}

	/**
	 * Aggiorna i permessi degli utenti
	 * 
	 * @param id , UUIDUser dell'utente a cui devono essere cambiati i permessi
	 * @param up , Oggetto UserPermission istanziato per avere una maschera permessi
	 *           da impostare
	 * 
	 * @return Boolean true se l'inserimento è andato a buon fine , false altrimenti
	 * @see vo.UUIDuser,vo.UserPermissions
	 * 
	 * @exception SQLException
	 * 
	 */
	public static boolean updateUserPermissions(UUIDUser user, UserPermissions up) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		Integer res = null;

		try {
			ps = con.prepareStatement(
					"UPDATE perm_authorization " + "SET  download = ?, upload = ?, editMetadata =  ?, "
							+ "reviewPage =  ?, modifyTranscription =  ?, "
							+ "requestTranscriptionTask =  ?, reviewTranscription =  ?, "
							+ "addNewProject =  ?, assignDigitalizationTask = ?, "
							+ "assignTranscriptionTask = ?, publishDocument =  ? " + "WHERE ID_user = ? ;");
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

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errori sulle risorse", e));
			}
		}

		return (res == 1);
	}

	/**
	 * Seleziona il campo oggetto di tutte le richieste inviate dagli utenti
	 * 
	 * @param b se 0 mostra soltanto gli oggetti delle richieste pending, se 1 delle
	 *          non pending, se 2 tutte
	 * @return HashMap<Integer,String> l'intero(chiave) rappresenta l'UUIDRequest e
	 *         la String l'oogetto della request
	 */
	public static HashMap<Integer, String> loadRequestsList(int b) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		HashMap<Integer, String> req = new HashMap<Integer, String>();
		ResultSet rs = null;

		try {

			if (b == 0) {
				ps = con.prepareStatement("SELECT ID,object from request WHERE status=?;");
				ps.setBoolean(1, false);
			} else {
				ps = con.prepareStatement("SELECT ID,object from request WHERE status=?;");
				if (b == 1) {
					ps.setBoolean(1, true);
				} else {
					ps = con.prepareStatement("SELECT ID,object from request WHERE status=? or status=?;");
					ps.setBoolean(1, false);
					ps.setBoolean(2, true);
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				req.put(rs.getInt("ID"), rs.getString("object"));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
		}

		return req;

	}

	/**
	 * Seleziona tutti gli attributi di una richiesta
	 * 
	 * @param id UUIDRequest di una richiesta
	 * @return Request un oggetto richiesta completo
	 * @throws NullPointerException In caso l'id parametro non sia presente nel db
	 * @see vo.UUIDRequest
	 */
	public static Request loadRequest(UUIDRequest id) throws NullPointerException, DatabaseException {

		if (id == null)
			throw new NullPointerException("Errore, id richiesta non valido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		Request r = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("SELECT * from request WHERE id=?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			if (rs.next()) {
				r = new Request(new UUIDRequest(rs.getInt("ID")), new UUIDUser(rs.getInt("ID_user")),
						new UUIDUser(rs.getInt("ID_admin")), rs.getBoolean("status"), rs.getString("object"),
						rs.getString("Message"), rs.getString("answer_message"));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException ex) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", ex));
			}
		}
		return r;
	}

	/**
	 * Aggiorna l'attributo answer di una richiesta e imposta la request non pending
	 * 
	 * @param id     UUIDRequest della richiesta a cui rispondere
	 * @param answer Corpo del messaggio risposta
	 * @return Boolean true se l'operazione è andata a buon fine , false altrimenti
	 * @see vo.UUIDRequest
	 */
	public static Boolean answerRequest(UUIDRequest id, String answer) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		Integer result = null;

		try {
			ps = con.prepareStatement("UPDATE request SET answer_message=? , status=true WHERE ID=?;");
			ps.setString(1, answer);
			ps.setInt(2, id.getValue());

			result = ps.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Errore nella query", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException ex) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", ex));
			}
		}

		return (result == 1);

	}

	/*
	 * Seleziona tutti i campi anagrafici di tutti gli utenti
	 * 
	 * @return LinkedList di User
	 * 
	 * @exception SQLException in caso di errori relativi al database
	 */

	public static LinkedList<User> loadUserList() throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<User> result = new LinkedList<User>();

		try {
			ps = con.prepareStatement("SELECT * FROM user");

			rs = ps.executeQuery();
			while (rs.next()) {
				UUIDUser id = new UUIDUser(rs.getInt("ID"));
				String username = rs.getString("username");
				String name = rs.getString("name");
				String pass = rs.getString("password");
				String surname = rs.getString("surname");
				Boolean status = rs.getBoolean("status");
				String email = rs.getString("email");
				Date regDate = rs.getDate("registration_date");

				// object creation
				Email em = new Email(email);
				UserInformations ui = new UserInformations(name, surname, regDate, em, pass);
				
				result.add(new User(id, username, ui, status,
						UserAuthenticationQuerySet.getUSerPermission(id)));
			}

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}

		}

		return result;

	}
}
