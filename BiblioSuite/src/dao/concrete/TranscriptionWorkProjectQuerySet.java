package dao.concrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import dao.interfaces.TranscriptionWorkProjectQuerySetDAO;
import model.TranscriptionWorkProject;
import vo.UUIDDocument;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public class TranscriptionWorkProjectQuerySet implements TranscriptionWorkProjectQuerySetDAO {
	// TODO Cosa manca?
	public UUIDTranscriptionWorkProject insertTranscriptionWorkProject(LinkedList<UUIDUser> transcribers,
			LinkedList<UUIDUser> revisers, UUIDUser coordinator, Boolean completed, UUIDDocument doc)
			throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UUIDTranscriptionWorkProject id = null;

		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(
					"insert into transcription_project(ID_coordinator, ID_document,transcription_complete) values (?,?,?);",
					new String[] { "ID" });
			ps.setInt(1, coordinator.getValue());
			ps.setInt(2, doc.getValue());
			ps.setBoolean(3, completed);

			ps.addBatch();
			ps.executeBatch();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = new UUIDTranscriptionWorkProject(rs.getInt(1));
			}
			con.commit();
			con.setAutoCommit(true);

			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();

			Iterator<UUIDUser> itr = transcribers.iterator();

			while (itr.hasNext()) {
				UUIDUser usr = itr.next();
				ps = con.prepareStatement("insert into "
						+ "transcription_project_transcriber_partecipant(ID_transcription_project, ID_transcriber_user) "
						+ "values (?,?);");
				ps.setInt(1, id.getValue());
				ps.setInt(2, usr.getValue());
				rs = ps.executeQuery();
			}

			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();

			itr = revisers.iterator();

			while (itr.hasNext()) {
				UUIDUser usr = itr.next();
				ps = con.prepareStatement("insert into "
						+ "transcription_project_reviser_partecipant(ID_transcription_project, ID_reviser_user) "
						+ "values (?,?);");
				ps.setInt(1, id.getValue());
				ps.setInt(2, usr.getValue());

			}

		} catch (SQLException e) {
			try {
				con.abort(null);
			} catch (SQLException f) {
				DBConnection.logDatabaseException(new DatabaseException("Duplicato", f));
			}
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

		return id;
	}

	public UUIDTranscriptionWorkProject insertTranscriptionWorkProject(UUIDUser coordinator, UUIDDocument doc)
			throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UUIDTranscriptionWorkProject id = null;

		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(
					"insert into transcription_project(ID_coordinator, ID_document,transcription_complete) values (?,?,false);",
					new String[] { "ID" });
			ps.setInt(1, coordinator.getValue());
			ps.setInt(2, doc.getValue());

			ps.addBatch();
			ps.executeBatch();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = new UUIDTranscriptionWorkProject(rs.getInt(1));
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				con.abort(null);
			} catch (SQLException f) {
				DBConnection.logDatabaseException(new DatabaseException("Duplicato", f));
			}
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

		return id;
	}

	/*
	 * Seleziona un transcriptionWorkProject con il relativo personale
	 * 
	 * @param UUIDTranscriptionProject id del progetto da caricare
	 * 
	 * @return TranscriptionWorkProject oggetto completo
	 */
	public TranscriptionWorkProject loadTranscriptionWorkProject(UUIDTranscriptionWorkProject id)
			throws DatabaseException, NullPointerException {
		if (id == null)
			throw new NullPointerException("Id non valido poichè nullo");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
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

			while (rs.next()) {
				transcribers.add(new UUIDUser(rs.getInt("ID_transcriber_user")));
			}

			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();

			ps = con.prepareStatement("SELECT ID_reviser_user FROM transcription_project_reviser_partecipant "
					+ "WHERE ID_transcription_project=?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				revisers.add(new UUIDUser(rs.getInt("ID_reviser_user")));
			}
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();

			ps = con.prepareStatement(
					"SELECT ID_coordinator,date,transcription_complete " + "FROM transcription_project WHERE ID=?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getDate("date") != null) {

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = rs.getString("date");
					Date d = sdf.parse(date);

					twp = new TranscriptionWorkProject(d, new UUIDUser(rs.getInt("ID_coordinator")), transcribers,
							revisers, id, rs.getBoolean("transcription_complete"));

				} else {
					twp = new TranscriptionWorkProject(null, new UUIDUser(rs.getInt("ID_coordinator")), transcribers,
							revisers, id, rs.getBoolean("transcription_complete"));

				}
			}

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		} catch (ParseException pe) {
			throw new DatabaseException("Errore nel parsing della data", pe);
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

		return twp;

	}

	/*
	 * Aggiunge un utente Trascrittore ad un progetto di Trascrizione
	 * 
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si deve inserire
	 * l'utente
	 * 
	 * @param UUIDUser ID dell'utente da inserire
	 * 
	 * @return Boolean true se l'utente è stato inserito correttamente nel progetto
	 * di trascrizione
	 * 
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * 
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
	public Boolean insertTranscriberUser(UUIDTranscriptionWorkProject ids, UUIDUser id)
			throws DatabaseException, NullPointerException {
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");

		if (id == null)
			throw new NullPointerException("Id non vallido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("INSERT INTO transcription_project_transcriber_partecipant "
					+ "(ID_transcription_project,ID_transcriber_user) VALUE (?,?);");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, id.getValue());

			return (ps.executeUpdate() == 1);

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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

	/*
	 * Aggiungi un utente Revisore ad un progetto di Trascrizione
	 * 
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si deve inserire
	 * l'utente
	 * 
	 * @param UUIDUser ID dell'utente da inserire
	 * 
	 * @return Boolean true se l'utente è stato inserito correttamente nel progetto
	 * di trascrizione
	 * 
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * 
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
	public Boolean insertReviserUser(UUIDUser id, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");

		if (id == null)
			throw new NullPointerException("Id non vallido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("INSERT INTO transcription_project_reviser_partecipant "
					+ "(ID_transcription_project,ID_reviser_user) VALUE (?,?);");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, id.getValue());

			return (ps.executeUpdate() == 1);

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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

	/*
	 * Rimuove un utente Trascrittore da un progetto di Trascrizione
	 * 
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si deve inserire
	 * l'utente
	 * 
	 * @param UUIDUser ID dell'utente da inserire
	 * 
	 * @return Boolean true se l'utente è stato eliminato correttamente nel progetto
	 * di trascrizione
	 * 
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * 
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
	public Boolean removeTranscriberUser(UUIDUser id, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");

		if (id == null)
			throw new NullPointerException("Id non vallido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("DELETE FROM transcription_project_transcriber_partecipant "
					+ "WHERE ID_transcription_project = ? AND ID_transcriber_user =?;");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, id.getValue());

			return (ps.executeUpdate() == 1);

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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

	/*
	 * Rimuove un utente Revisore da un progetto di Trascrizione
	 * 
	 * @param UUIDTrnascriptionWorkProject ID del progetto in cui si deve inserire
	 * l'utente
	 * 
	 * @param UUIDUser ID dell'utente da inserire
	 * 
	 * @return Boolean true se l'utente è stato eliminato correttamente nel progetto
	 * di trascrizione
	 * 
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * 
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
	public Boolean removeReviserUser(UUIDUser id, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");

		if (id == null)
			throw new NullPointerException("Id non vallido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("DELETE FROM transcription_project_reviser_partecipant "
					+ "WHERE ID_transcription_project = ? AND ID_reviser_user =?;");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, id.getValue());

			return (ps.executeUpdate() == 1);

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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

	/*
	 * Inserisce una lista di utenti Trascrittori in un progetto di Trascrizione
	 * 
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si devono inserire
	 * gli utenti
	 * 
	 * @param LinkedList<UUIDUser> ID degli utenti da inserire
	 * 
	 * @return Integer numero di Trascrittori correttamente inseriti nel progetto di
	 * trascrizione
	 * 
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * 
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
	public Integer insertTranscriberUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (idl == null)
			throw new NullPointerException("Lista di utenti non valida");
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		Iterator<UUIDUser> itr = idl.iterator();

		Integer linesAffected = 0;

		try {
			ps = con.prepareStatement("INSERT INTO transcription_project_transcriber_partecipant"
					+ "(ID_transcription_project,ID_transcriber_user) " + "VALUE (?,?);");
			ps.setInt(1, ids.getValue());

			while (itr.hasNext()) {
				Integer user = itr.next().getValue();
				ps.setInt(2, user);

				linesAffected += ps.executeUpdate();
			}

			return linesAffected;

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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

	/*
	 * Inserisce una lista di utenti Revisori in un progetto di Transcrizione
	 * 
	 * @param UUIDTranscriptionWorkProject ID del progetto in cui si devono inserire
	 * gli utenti
	 * 
	 * @param LinkedList<UUIDUser> ID degli utenti da inserire
	 * 
	 * @return Integer numero di Revisori correttamente inseriti nel progetto di
	 * trascrizione
	 * 
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * 
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
	public Integer insertReviserUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (idl == null)
			throw new NullPointerException("Lista di utenti non valida");
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		Iterator<UUIDUser> itr = idl.iterator();

		Integer linesAffected = 0;

		try {
			ps = con.prepareStatement("INSERT INTO transcription_project_reviser_partecipant"
					+ "(ID_transcription_project,ID_reviser_user) " + "VALUE (?,?);");
			ps.setInt(1, ids.getValue());

			while (itr.hasNext()) {
				Integer user = itr.next().getValue();
				ps.setInt(2, user);

				linesAffected += ps.executeUpdate();
			}

			return linesAffected;

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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

	/*
	 * Rimuove una lista di utenti Trascrittori da un progetto di Trascrizione
	 * 
	 * @param UUIDTranscriptionWorkProject ID del progetto da cui si devono
	 * eliminare gli utenti
	 * 
	 * @param LinkedList<UUIDUser> ID degli utenti da eliminare
	 * 
	 * @return Integer numero di Trascrittori correttamente rimossi dal progetto di
	 * trascrizione
	 * 
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * 
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
	public Integer removeTranscriberUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (idl == null)
			throw new NullPointerException("Lista di utenti non valida");
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		Iterator<UUIDUser> itr = idl.iterator();

		Integer linesAffected = 0;

		try {
			ps = con.prepareStatement("DELETE FROM transcription_project_transcriber_partecipant "
					+ "WHERE ID_transcription_project=? AND ID_transcriber_user=?");
			ps.setInt(1, ids.getValue());

			while (itr.hasNext()) {
				Integer user = itr.next().getValue();
				ps.setInt(2, user);

				linesAffected += ps.executeUpdate();
			}

			return linesAffected;

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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
	 * Rimuoveuna lista di utenti Revisori da un progetto di Trascrizione
	 * 
	 * @param UUIDTranscriptionWorkProject ID del progetto da cui si devono
	 *                                     eliminare gli utenti
	 * 
	 * @param                              LinkedList<UUIDUser> ID degli utenti da
	 *                                     eliminare
	 * 
	 * @return Integer numero di Revisori correttamente rimossi dal progetto di
	 *         trascrizione
	 * 
	 * @throw DatabaseException in caso di errori di connessione ad DB o sulle query
	 * 
	 * @throws NullPointerException in caso i parametri di input siano nulli
	 */
	public Integer removeReviserUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (idl == null)
			throw new NullPointerException("Lista di utenti non valida");
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		Iterator<UUIDUser> itr = idl.iterator();

		Integer linesAffected = 0;

		try {
			ps = con.prepareStatement("DELETE FROM transcription_project_reviser_partecipant "
					+ "WHERE ID_transcription_project=? AND ID_reviser_user=?");
			ps.setInt(1, ids.getValue());

			while (itr.hasNext()) {
				Integer user = itr.next().getValue();
				ps.setInt(2, user);

				linesAffected += ps.executeUpdate();
			}

			return linesAffected;

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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

	// cancella un TranscriptionWorkProject con il relativo personale
	public Boolean deleteTranscriptionWorkProject(UUIDTranscriptionWorkProject id)
			throws DatabaseException, NullPointerException {
		if (id == null)
			throw new NullPointerException("Id non vallido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("DELETE FROM transcription_project WHERE ID=?;");
			ps.setInt(1, id.getValue());

			return (ps.executeUpdate() == 1);

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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
	 * Selects the UUIDDocument of a specific UUIDTranscriptionWorkProject
	 * 
	 * @param UUIDScanningWorkProject id corresponding to the Document
	 * 
	 * @return UUIDDocument UUID of the Document
	 */

	public UUIDDocument loadUUIDDocument(UUIDTranscriptionWorkProject id)
			throws DatabaseException, NullPointerException {
		if (id == null)
			throw new NullPointerException("Id non valido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UUIDDocument doc = null;

		try {
			ps = con.prepareStatement("SELECT ID_document FROM transcription_project " + "WHERE ID = ?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			if (rs.next())
				doc = new UUIDDocument(rs.getInt("ID_document"));

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
		return doc;
	}

	/**
	 * seleziona i progetti di trascrizione di cui l'utente è Coordinatore
	 * 
	 * @param UUIDUser utente di cui si devono reperire i progetti di trascrizione
	 *                 come coordinatore
	 * @return HashMap<Integer,String> Mappa di Id progetti di trascrizione e titolo
	 *         dell'opera associata
	 */
	public HashMap<UUIDTranscriptionWorkProject, String[]> loadMyCoordinatedTranscriptionWorkProjectList(UUIDUser usr)
			throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<UUIDTranscriptionWorkProject, String[]> twpl = new HashMap<UUIDTranscriptionWorkProject, String[]>();

		try {
			ps = con.prepareStatement("SELECT tp.ID as ID_progetto, d.title as Title, transcription_complete "
					+ "FROM transcription_project as tp JOIN document as d ON tp.ID_document=d.ID "
					+ "WHERE ID_coordinator=? ;");

			ps.setInt(1, usr.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				String[] array = { rs.getString("Title"), String.valueOf(rs.getBoolean("transcription_complete")) };

				twpl.put(new UUIDTranscriptionWorkProject(rs.getInt("ID_progetto")), array);
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

		return twpl;
	}

	public boolean ifExistTranscriptionProject(UUIDDocument doc) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		Boolean exist = false;

		try {
			ps = con.prepareStatement(
					"SELECT IF( EXISTS(SELECT * FROM transcription_project WHERE ID_document = ?), 1, 0) as Exist;");
			ps.setInt(1, doc.getValue());

			rs = ps.executeQuery();

			if (rs.next())
				exist = rs.getBoolean("Exist");

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

		return exist;

	}

	/**
	 * Cambia il coordinatore di un progetto di trascrizione
	 * 
	 * @param id
	 * @param ids
	 * @return
	 * @throws DatabaseException
	 * @throws NullPointerException
	 */
	public Boolean changeTranscriptionProjectCoordinator(UUIDUser id, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");

		if (id == null)
			throw new NullPointerException("Id non vallido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("update transcription_project set ID_coordinator = ? where ID = ?;");
			ps.setInt(1, id.getValue());
			ps.setInt(2, ids.getValue());

			return (ps.executeUpdate() == 1);

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
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
	 * Recupera tutti gli utenti attivi, che soni transcrittori e che non fanno già
	 * parte del progetto sia come trascrittori che come revisori
	 * 
	 * @param ids
	 * @return
	 * @throws DatabaseException
	 * @throws NullPointerException
	 */
	public LinkedList<UUIDUser> getAvailableTranscribers(UUIDTranscriptionWorkProject ids, int level)
			throws DatabaseException, NullPointerException {
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");

		Connection con = null;
		LinkedList<UUIDUser> availableStaff = new LinkedList<UUIDUser>();

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select u.ID from user as u join perm_authorization as perm "
					+ "on u.ID=perm.ID_user " + "WHERE modifyTranscription=1 and status = 1 and level=? "
					+ "and u.ID not in( select ID_transcriber_user from transcription_project_transcriber_partecipant where ID_transcription_project=?) "
					+ "AND u.ID not in( select ID_reviser_user from transcription_project_reviser_partecipant where ID_transcription_project=?) "
					+ "AND u.ID not in( select ID_coordinator from transcription_project where ID=?);");
			ps.setString(1, String.valueOf(level));
			ps.setInt(2, ids.getValue());
			ps.setInt(3, ids.getValue());
			ps.setInt(4, ids.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				availableStaff.add(new UUIDUser(rs.getInt("ID")));
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
		return availableStaff;
	}

	/**
	 * Recupera tutti gli utenti attivi, che sono revisori e che non fanno già parte
	 * del progetto sia come trascrittori che come revisori e coordinatori
	 * 
	 * @param ids
	 * @return
	 * @throws DatabaseException
	 * @throws NullPointerException
	 */
	public LinkedList<UUIDUser> getAvailableRevisers(UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException {
		if (ids == null)
			throw new NullPointerException("Id Progetto non valido");

		Connection con = null;
		LinkedList<UUIDUser> availableStaff = new LinkedList<UUIDUser>();

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select u.ID from user as u join perm_authorization as perm "
					+ "on u.ID=perm.ID_user " + "WHERE reviewTranscription=1 and status = 1 and "
					+ "u.ID not in( select ID_transcriber_user from transcription_project_transcriber_partecipant where ID_transcription_project=?) "
					+ "AND u.ID not in( select ID_reviser_user from transcription_project_reviser_partecipant where ID_transcription_project=?) "
					+ "AND u.ID not in( select ID_coordinator from transcription_project where ID=?) ;");
			ps.setInt(1, ids.getValue());
			ps.setInt(2, ids.getValue());
			ps.setInt(3, ids.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				availableStaff.add(new UUIDUser(rs.getInt("ID")));
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
		return availableStaff;
	}

	/**
	 * Permette di estrarre tutti i documenti il cui scanning project non è
	 * completato e l'utente parametro ne è un digitalizzatore
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public HashMap<UUIDDocument, String> getTranscriptionUncompletedDocumentTranscriber(UUIDUser id)
			throws DatabaseException {

		HashMap<UUIDDocument, String> uncompletedDocument = new HashMap<UUIDDocument, String>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		try {
			ps = con.prepareStatement("select d.ID as ID ,d.title as T from document as d "
					+ "join transcription_project as sp join transcription_project_transcriber_partecipant  "
					+ "as spp on d.ID=sp.ID_document  AND spp.ID_transcription_project=sp.ID "
					+ "WHERE transcription_complete=0 AND spp.ID_transcriber_user = ?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				uncompletedDocument.put(new UUIDDocument(rs.getInt("ID")), rs.getString("T"));
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

		return uncompletedDocument;
	}

	/**
	 * Permette di estrarre tutti i documenti il cui scanning project non è
	 * completato e l'utente parametro ne è un revisore
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public HashMap<UUIDDocument, String> getTranscriptionUncompletedDocumentReviser(UUIDUser id)
			throws DatabaseException {

		HashMap<UUIDDocument, String> uncompletedDocument = new HashMap<UUIDDocument, String>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		try {
			ps = con.prepareStatement("select d.ID as ID ,d.title as T from document as d "
					+ "join transcription_project as sp join transcription_project_reviser_partecipant  "
					+ "as spp on d.ID=sp.ID_document  AND spp.ID_transcription_project=sp.ID "
					+ "WHERE transcriptiong_complete=0 AND spp.ID_reviser_user = ?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				uncompletedDocument.put(new UUIDDocument(rs.getInt("ID")), rs.getString("T"));
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

		return uncompletedDocument;
	}

}
