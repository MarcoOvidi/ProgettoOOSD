package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;

public class DigitalizationRevisorQuerySet {

	// aggiorna il flag di "validità" di uno ScanningWorkProject
	public static void validated(UUIDPage id, Boolean validation) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("UPDATE page SET image_convalidation=? WHERE ID=?");
			ps.setBoolean(1, validation);
			ps.setInt(2, id.getValue());

			ps.executeUpdate();

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

	public static void revised(UUIDPage id, Boolean validation) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("UPDATE page SET image_revised=? WHERE ID=?");
			ps.setBoolean(1, validation);
			ps.setInt(2, id.getValue());

			ps.executeUpdate();

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

	// dichiara completato un progetto di scansione opera
	public static void scanningProcessCompleted(UUIDScanningWorkProject id) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("UPDATE scanning_project SET scanning_complete=true WHERE ID=?");

			ps.setInt(1, id.getValue());

			ps.executeUpdate();

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
	 * Permette di aggiungere un commento all'ultima digitalizzazione della pagina
	 * in sede di revisione
	 * 
	 * @param id
	 * @param comment
	 * @throws DatabaseException
	 */
	public static void addScanningRevisionComment(UUIDPage id, String comment) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("UPDATE page SET scanning_reviser_comment=? WHERE ID=?");
			ps.setString(1, comment);
			ps.setInt(2, id.getValue());

			ps.executeUpdate();

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
	 * Permette di accedere al commento dell'ultima digitalizzazione della pagina
	 * 
	 * @param id
	 * @param comment
	 * @throws DatabaseException
	 */
	public static String getScanningRevisionComment(UUIDPage id) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		String comment = "Comment Not Found";
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("SELECT scanning_reviser_comment FROM page WHERE ID=?");
			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();
			
			if(rs.next()) {
				comment = rs.getString("scanning_reviser_comment");
			}
			

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
		}
		return comment;
	}

}
