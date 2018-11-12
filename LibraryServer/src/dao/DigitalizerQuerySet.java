 package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import model.Page;
import model.PageScan;
import vo.Image;
import vo.UUIDDocument;
import vo.UUIDPage;

public class DigitalizerQuerySet { //COMPLETATA E VERIFICATA DEF
	
	/**
	 * 
	 * @param id id UUIDDocument dell'opera di cui si vogliono visualizzare le pagine scansionate e il loro stato nel processo di validazione
	 * @param revision TRUE: mostra solo le pagine revisionate FALSE altrimenti
	 * @param validation TRUE: mostra le pagine validate FALSE altrimenti
	 * @return LinkedList<Page> Elenco di pagine
	 * @throws DatabaseException
	 * @see UUIDDocument
	 */
	public static LinkedList<Page> loadDocument(UUIDDocument id,Boolean revision,Boolean validation) throws DatabaseException{
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<Page> pages = new LinkedList<Page>();
		
		try {
			ps = con.prepareStatement("select ID,number,image,image_revised,"
					+ "image_convalidation from page WHERE image_convalidation=? "
					+ "AND image_revised=? AND ID_document=?");
			ps.setBoolean(1, validation);
			ps.setBoolean(2, revision);
			ps.setInt(3, id.getValue());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				pages.add(new Page(new UUIDPage(rs.getInt("ID")), rs.getInt("number"), 
						new PageScan(new Image(rs.getString("image")), 
								rs.getBoolean("image_convalidation"), 
								rs.getBoolean("image_revised"), null), null));
			}
			
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
		}finally {
			try{
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(con!=null)
					con.close();
			}catch(SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
			
			
		}
		return pages;
	}
	
	/**
	 * 
	 * @param id UUIDPage: id della pagina di cui si vuole aggiornare la scansione  
	 * @param img Image: path della scansione
	 * @throws DatabaseException
	 * @see vo.UUIDPage
	 * @see vo.Image
	 */
	public static void updatePage(UUIDPage id,Image img) throws DatabaseException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement("UPDATE page SET image=? WHERE ID=?");
			ps.setString(1, img.getUrl());
			ps.setInt(2, id.getValue());
			
			ps.executeUpdate();
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
	}
}
