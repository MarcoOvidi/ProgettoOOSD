package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Document;
import vo.UUIDDocument;

public class DocumentQuerySet {
	
	/* Inserisce un nuovo documento
	 * @param String Titolo dell'opera
	 * @return Boolean: true se l'operazione è andata a buon fine , false altrimenti
	 */
	//TODO questo metodo carica solo il titolo del documento giusto ?
	public static UUIDDocument insertDocument(String title) throws DatabaseException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		UUIDDocument id = null;
		
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement("insert into document(title) value( ? );", new String[] {"ID"});
			ps.setString(1,title);
			ps.addBatch();
			ps.executeBatch();
			
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				id = new UUIDDocument(rs.getInt(1));
			}
			
			con.commit();
			
		}catch(SQLException e) {
			try {
				con.abort(null);
				
			}catch(SQLException f) {
				DBConnection.logDatabaseException(new DatabaseException("Duplicato", f));
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
	
	//seleziona un documento
	//TODO
	//FIXME urgente
	public static Document loadDocument(UUIDDocument id) {
		return null;		
	}
	
	//aggiorna un documento
	public void updateDocument() {
		
	}
	
	//cancella un documento
	public void deleteDocument() {
		
	}
	
	//scarica un documento
	public void downloadFileDocument() {
		
	}
}
