package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import vo.BookMark;
import vo.UUIDBookMark;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDUser;

public class PageViewQuerySet {
	
	//FIXME altro metodo ridondante? ??? Con quale?
	//seleziona tutte le informazioni dell'opera, tutte le pagine, tutte le scansioni
	public void loadOpera() {
		
	}
	
	//seleziona tutti i segnalibri riguardanti un'opera che appartengono all'utente che li ha creati
	public static LinkedList<BookMark> loadBookmarksDocument(UUIDUser ID_user, UUIDDocument ID_doc) throws DatabaseException{

		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
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
			
			while(rs.next()) {
				pages.add(new BookMark(new UUIDUser (rs.getInt("ID_user")), 
						new UUIDDocument(rs.getInt("ID_document")), new UUIDPage(rs.getInt("ID_page"))));
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
		
		return pages;
	}
	
	
	public static void removeBookmarksDocument(UUIDBookMark ID_bm) throws DatabaseException {

		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement("DELETE FROM book_marks WHERE ID=?");
			
			ps.setInt(1, ID_bm.getValue());
			
			
			ps.executeUpdate();
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		}finally {
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
	
	public static void addBookmarksDocument(BookMark bm) throws DatabaseException {

		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement("INSERT INTO book_marks(ID_user,ID_document,ID_page) "
					+ "VALUES (?,?,?)");
			
			ps.setInt(1, bm.getUID().getValue());
			ps.setInt(2, bm.getDID().getValue());
			ps.setInt(3, bm.getPID().getValue());
			
			ps.executeQuery();
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		}finally {
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
