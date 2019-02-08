package dao.concrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import dao.interfaces.SearchQuerySetDAO;
import vo.UUIDDocument;

public class SearchQuerySet implements SearchQuerySetDAO{

	/**
	 * Cerca un'opera per autore o titolo
	 * 
	 * @param match
	 * @return
	 * @throws DatabaseException
	 */
	public  LinkedList<UUIDDocument> searchByAuthorTitle(String match) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<UUIDDocument> documents = new LinkedList<UUIDDocument>();

		try {

			ps = con.prepareStatement("select doc.ID " + "from document as doc join document_metadata as dm "
					+ "on doc.ID=dm.ID_document " + "where doc.title LIKE (CONCAT('%' , ? , '%')) OR "
					+ "dm.author LIKE (CONCAT('%' , ? , '%')) " + "group by doc.ID;");
			ps.setString(1, match);
			ps.setString(2, match);

			rs = ps.executeQuery();

			while (rs.next()) {
				documents.add(new UUIDDocument(rs.getInt("doc.ID")));
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

		return documents;
	}

	/**
	 * Cerca un'opera per tag
	 * 
	 * @param match
	 * @return
	 * @throws DatabaseException
	 */
	public LinkedList<UUIDDocument> searchByTag(String match) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<UUIDDocument> documents = new LinkedList<UUIDDocument>();

		try {

			ps = con.prepareStatement("SELECT ID_document FROM document_metadata AS dm JOIN tag_metadata AS tm "
					+ "JOIN tag ON tm.ID_tag=tag.ID AND tm.ID_document_metadata=dm.ID "
					+ "WHERE tag.name LIKE (CONCAT('%' ,?, '%')) group by ID_document;");

			ps.setString(1, match);

			rs = ps.executeQuery();

			while (rs.next()) {
				documents.add(new UUIDDocument(rs.getInt("ID_document")));
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

		return documents;
	}

}
