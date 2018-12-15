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

public class AdministrationQuerySet { // tutto ok

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
	public static LinkedList<Request> loadRequestsList(int b) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		LinkedList<Request> req = new LinkedList<Request>();
		ResultSet rs = null;

		try {

			if (b == 0) {
				ps = con.prepareStatement("SELECT * from request WHERE status=?;");
				ps.setBoolean(1, false);
			} else {
				ps = con.prepareStatement("SELECT * from request WHERE status=?;");
				if (b == 1) {
					ps.setBoolean(1, true);
				} else {
					ps = con.prepareStatement("SELECT * from request WHERE status=? or status=?;");
					ps.setBoolean(1, false);
					ps.setBoolean(2, true);
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				req.add(new Request(new UUIDRequest(rs.getInt("ID")), new UUIDUser(rs.getInt("ID_user")),
						new UUIDUser(rs.getInt("ID_user")), rs.getBoolean("status"), rs.getString("object"),
						rs.getString("message"), rs.getString("answer_message")));
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

	/**
	 * Aggiorna a non pending lo stato di una richiesta che è stata scartata
	 * 
	 * @param id UUIDRequest della richiesta a cui rispondere
	 *
	 * @return Boolean true se l'operazione è andata a buon fine , false altrimenti
	 * 
	 * @see vo.UUIDRequest
	 */
	public static Boolean ignoreRequest(UUIDRequest id) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		Integer result = null;

		try {
			ps = con.prepareStatement("UPDATE request SET status=true WHERE ID=?;");
			ps.setInt(1, id.getValue());

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

	/**
	 * Seleziona tutti i campi anagrafici di tutti gli utenti
	 * 
	 * @return LinkedList di User
	 * 
	 * @exception SQLException in caso di errori relativi al database
	 */

	public static LinkedList<User> loadUserList(Boolean b) throws DatabaseException {
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
			ps = con.prepareStatement("SELECT * FROM user WHERE status=?");
			ps.setBoolean(1, b);

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

				result.add(new User(id, username, ui, status, UserAuthenticationQuerySet.getUSerPermission(id)));
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

	/**
	 * lista degli utenti che hanno il permessi di creare progetti := Coordinator
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public static HashMap<UUIDUser, String> showCoordinatorList() throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<UUIDUser, String> coordinators = new HashMap<UUIDUser, String>();

		try {
			ps = con.prepareStatement(
					"select u.id,u.username from user as u join perm_authorization as pA on u.ID=pA.ID_user WHERE addNewProject=1 and status=1;");

			rs = ps.executeQuery();

			while (rs.next()) {

				coordinators.put(new UUIDUser(rs.getInt("u.id")), rs.getString("u.username"));

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

		return coordinators;
	}

	/**
	 * Controlla se un utente fa parte in qualche modo di un progetto di
	 * digitalizzazione e/o trascrizione
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public static HashMap<String, Integer> userIsInvolved(UUIDUser id) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Integer> envolved = new HashMap<String, Integer>();

		try {

			ps = con.prepareStatement("select count(ID) as number from scanning_project where ID_coordinator=?;");
			ps.setInt(1, id.getValue());
			rs = ps.executeQuery();

			if (rs.next()) {
				envolved.put("ScanningProjectCoordinator", rs.getInt("number"));
			}

			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();

			ps = con.prepareStatement(
					"select count(ID) as number from scanning_project_digitalizer_partecipant where ID_digitalizer_user=?;");
			ps.setInt(1, id.getValue());
			rs = ps.executeQuery();

			if (rs.next()) {
				envolved.put("ScanningProjectDigitalizer", rs.getInt("number"));
			}

			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();

			ps = con.prepareStatement(
					"select count(ID) as number from scanning_project_reviser_partecipant where ID_reviser_user=?;");
			ps.setInt(1, id.getValue());
			rs = ps.executeQuery();

			if (rs.next()) {
				envolved.put("ScanningProjectReviser", rs.getInt("number"));
			}

			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();

			ps = con.prepareStatement("select count(ID) as number from transcription_project where ID_coordinator=?;");
			ps.setInt(1, id.getValue());
			rs = ps.executeQuery();

			if (rs.next()) {
				envolved.put("TranscriptionProjectCoordinator", rs.getInt("number"));
			}

			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();

			ps = con.prepareStatement(
					"select count(ID) as number from transcription_project_transcriber_partecipant where ID_transcriber_user=?;");
			ps.setInt(1, id.getValue());
			rs = ps.executeQuery();

			if (rs.next()) {
				envolved.put("TranscriptionProjectTranscriber", rs.getInt("number"));
			}

			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();

			ps = con.prepareStatement(
					"select count(ID) as number from transcription_project_reviser_partecipant where ID_reviser_user=?;");
			ps.setInt(1, id.getValue());
			rs = ps.executeQuery();

			if (rs.next()) {
				envolved.put("TranscriptionProjectReviser", rs.getInt("number"));
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

		return envolved;
	}

	/**
	 * Sostituisce un coordinatore in tutti i progetti in cui è convolto
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public static void replaceCoordinator(UUIDUser oldCoordinator, UUIDUser newCoordinator) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("update scanning_project set ID_coordinator=? where ID_coordinator=?;");

			ps.setInt(1, newCoordinator.getValue());
			ps.setInt(2, oldCoordinator.getValue());

			ps.executeUpdate();

			if (ps != null)
				ps.close();

			ps = con.prepareStatement("update transcription_project set ID_coordinator=? where ID_coordinator=?;");

			ps.setInt(1, newCoordinator.getValue());
			ps.setInt(2, oldCoordinator.getValue());

			ps.executeUpdate();

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

	}

	/**
	 * Elimina un utente da tutti i progetti in cui è coinvolto come
	 * digitalizzatore/revisore/trascrittore
	 * 
	 * @param id
	 * @throws DatabaseException
	 */
	public static void removeUserFromAllProjects(UUIDUser id) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;

		try {

			ps = con.prepareStatement("DELETE FROM scanning_project_reviser_partecipant WHERE ID_reviser_user=?;");
			ps.setInt(1, id.getValue());

			ps.executeUpdate();

			if (ps != null)
				ps.close();

			ps = con.prepareStatement(
					"DELETE FROM scanning_project_digitalizer_partecipant WHERE ID_digitalizer_user=?;");
			ps.setInt(1, id.getValue());
			ps.executeUpdate();

			if (ps != null)
				ps.close();

			ps = con.prepareStatement("DELETE FROM transcription_project_reviser_partecipant WHERE ID_reviser_user=?;");
			ps.setInt(1, id.getValue());

			ps.executeUpdate();

			if (ps != null)
				ps.close();

			ps = con.prepareStatement(
					"DELETE FROM transcription_project_transcriber_partecipant WHERE ID_transcriber_user=?;");
			ps.setInt(1, id.getValue());

			ps.executeUpdate();

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
	}

	/**
	 * Modifica il livello del trascrittore
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public static void changeTranscriberLevel(UUIDUser id, int level) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		if (level < 0 || level > 5)
			throw new DatabaseException("Inserire livello da 0 a 5 !");

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("UPDATE user SET level=? WHERE ID=?;");
			ps.setString(1, String.valueOf(level));
			ps.setInt(2, id.getValue());

			ps.executeUpdate();

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

	}
	
	/**
	 * Recupera il livello del trascrittore
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public static int getTranscriberLevel(UUIDUser id) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}


		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer result ;
		
		try {
			ps = con.prepareStatement("SELECT level FROM user WHERE ID=?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();
			
			if(rs.next())
				result = rs.getInt("level");
			else
				result = -1;
		} catch (SQLException e) {
			throw new DatabaseException("Errore nella query", e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException ex) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", ex));
			}
		}
		
		return result;

	}	

}
