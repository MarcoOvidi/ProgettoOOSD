package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Page;
import model.PageTranscription;
import model.PageTranscriptionStaff;
import vo.TEItext;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDUser;

public class TranscriberQuerySet {

	/*
	 * Seleziona la trascrizione di una pagina di un'opera
	 * 
	 * @param UUIDPage Id della Page di cui si deve aggiornare la trascrizione
	 * 
	 * @return PageTranscription Transcrizione di una pagina con relative info sul
	 * suo stato
	 * 
	 * @exception DatabaseException In caso di errori connessione al DB o di
	 * esecuzione delle query
	 * 
	 * @exception NullPointerException in caso i parametri di input siano non validi
	 * 
	 */
	public static PageTranscription loadTranscription(UUIDPage id) throws DatabaseException, NullPointerException {
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
		PageTranscription pt = null;

		try {
			ps = con.prepareStatement(
					"SELECT transcription,transcription_convalidation as tconv,transcription_revised as tr, "
							+ "transcription_completed as tc " + "FROM page " + "WHERE ID = ?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			if (rs.next()) {
				pt = new PageTranscription(new TEItext(rs.getString("transcription")), rs.getBoolean("tr"),
						rs.getBoolean("tconv"), rs.getBoolean("tc"), null);
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

		return pt;
	}

	/*
	 * Aggiorna la trascrizione di una pagina di un'opera
	 * 
	 * @param TEItext Testo da inserire/aggiornare
	 * 
	 * @param UUIDPage Id della Page di cui si deve aggiornare la trascrizione
	 * 
	 * @return Boolan True se la modifica è andata a buon fine
	 * 
	 * @exception DatabaseException In caso di errori connessione al DB o di
	 * esecuzione delle query
	 * 
	 * @exception NullPointerException in caso i parametri di input siano non validi
	 */
	public static Boolean updateText(TEItext text, UUIDPage id) throws DatabaseException, NullPointerException {
		if (id == null)
			throw new NullPointerException("Id non valido");
		if (text == null)
			throw new NullPointerException("Testo non valido");

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		Integer linesAffected = 0;

		try {
			ps = con.prepareStatement("UPDATE page SET transcription = ? WHERE ID=?;");
			ps.setString(1, text.getText());
			ps.setInt(2, id.getValue());

			linesAffected = ps.executeUpdate();
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

		return (linesAffected == 1);

	}

	/**
	 * 
	 * @param id         id UUIDDocument dell'opera di cui si vuole visualizzare lo stato di trascrizione delle pagine
	 * @return LinkedList<Page> Elenco di pagine
	 * @throws DatabaseException
	 * @see UUIDDocument
	 */
	public static LinkedList<Page> loadDocument(UUIDDocument id)
			throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<Page> pages = new LinkedList<Page>();

		try {
			ps = con.prepareStatement("select ID,number,transcription_convalidation,transcription_revised,ID_transcription_reviser,"
					+ "ID_transcriber from page WHERE ID_document=?;");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				pages.add(
						new Page(
								new UUIDPage(rs.getInt("ID")), 
								rs.getInt("number"),
								null, 
								new PageTranscription(
										null, 
										rs.getBoolean("transcription_revised"), 
										rs.getBoolean("transcription_convalidation"), 
										new PageTranscriptionStaff(
												new UUIDUser(rs.getInt("ID_transcriber")), 
												new UUIDUser(rs.getInt("ID_tramscription_reviser"))
												)
										)
								)
						);
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
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}

		}
		return pages;
	}
}
