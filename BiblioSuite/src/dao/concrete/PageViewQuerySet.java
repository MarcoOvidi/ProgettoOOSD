package dao.concrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import dao.interfaces.PageViewQuerySetDAO;
import vo.BookMark;
import vo.UUIDBookMark;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDUser;

public class PageViewQuerySet implements PageViewQuerySetDAO{

	// seleziona tutti i segnalibri riguardanti un'opera che appartengono all'utente
	// che li ha creati
	public  LinkedList<BookMark> loadBookmarksDocument(UUIDUser ID_user, UUIDDocument ID_doc)
			throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<BookMark> pages = new LinkedList<BookMark>();

		try {
			ps = con.prepareStatement("SELECT * from book_marks WHERE ID_user=? AND ID_document=?");

			ps.setInt(1, ID_user.getValue());
			ps.setInt(2, ID_doc.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				pages.add(new BookMark(new UUIDUser(rs.getInt("ID_user")), new UUIDDocument(rs.getInt("ID_document")),
						new UUIDPage(rs.getInt("ID_page"))));
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

		return pages;
	}

	public  void removeBookmarksDocument(UUIDBookMark ID_bm) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("DELETE FROM book_marks WHERE ID=?");

			ps.setInt(1, ID_bm.getValue());

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

	public  void addBookmarksDocument(BookMark bm) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("INSERT INTO book_marks(ID_user,ID_document,ID_page) " + "VALUES (?,?,?)");

			ps.setInt(1, bm.getUID().getValue());
			ps.setInt(2, bm.getDID().getValue());
			ps.setInt(3, bm.getPID().getValue());

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

	public  void addToMyCollectins(UUIDDocument id, UUIDUser user) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("INSERT INTO personal_collection (ID_user, ID_document) VALUES (?,?);");
			ps.setInt(1, user.getValue());
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

	public  void removeFromMyCollectins(UUIDDocument id, UUIDUser user) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("DELETE FROM personal_collection WHERE ID_user = ? AND ID_document = ?;");
			ps.setInt(1, user.getValue());
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
	 * controlla se la pagina è presente nei segnalibri dell'utente
	 * 
	 * @param user
	 * @param id
	 * @param pageNumber
	 * @return
	 * @throws DatabaseException
	 */
	public  boolean controlMyBookMarks(UUIDUser user, UUIDDocument id, int pageNumber) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
					"Select b.ID from book_marks as b join page as p on b.ID_page=p.ID where b.ID_user = ? and b.ID_document=? and p.number=?;");

			ps.setInt(1, user.getValue());
			ps.setInt(2, id.getValue());
			ps.setInt(3, pageNumber);
			rs = ps.executeQuery();

			if (rs.next())
				return true;
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
		return false;
	}

	/**
	 * ID della pagina bookmark
	 * 
	 * @param user
	 * @param id
	 * @param pageNumber
	 * @return
	 * @throws DatabaseException
	 */
	public  UUIDPage getMyBookMarkPageID(UUIDDocument id, int pageNumber)
			throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
					"Select p.ID from page as p where ID_document=? and number=?;");

			ps.setInt(1, id.getValue());
			ps.setInt(2, pageNumber);
			rs = ps.executeQuery();

			if (rs.next())
				return new UUIDPage(rs.getInt("p.ID"));
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
		return null;
	}

	/**
	 * aggiunge una pagina ai segnalibri utente
	 * 
	 * @param user
	 * @param id
	 * @param pageNumber
	 * @return
	 * @throws DatabaseException
	 */
	public  void addPageToMyBookMarks(UUIDUser user, UUIDDocument id, UUIDPage pageNumber)
			throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("insert into book_marks (ID_user,ID_document,ID_page)  value (?,?,?);");

			ps.setInt(1, user.getValue());
			ps.setInt(2, id.getValue());
			ps.setInt(3, pageNumber.getValue());

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
	 * rimuove una pagina ai segnalibri utente
	 * 
	 * @param user
	 * @param id
	 * @param pageNumber
	 * @return
	 * @throws DatabaseException
	 */
	public  void removePageFromMyBookMarks(UUIDUser user, UUIDDocument id, UUIDPage pageNumber)
			throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("delete from book_marks where ID_user = ? and ID_document = ? and ID_page=?;");

			ps.setInt(1, user.getValue());
			ps.setInt(2, id.getValue());
			ps.setInt(3, pageNumber.getValue());

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

}
