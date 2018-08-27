package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Document;
import model.Page;
import model.PageScan;
import model.PageScanStaff;
import model.PageTranscription;
import model.PageTranscriptionStaff;
import vo.DocumentMetadata;
import vo.Image;
import vo.TEItext;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;
import vo.VagueDate;

public class DocumentQuerySet {
	
	/* Inserisce un nuovo documento
	 * @param String Titolo dell'opera
	 * @return Boolean: true se l'operazione è andata a buon fine , false altrimenti
	 */
	//TODO questo metodo carica solo il titolo del documento giusto ?
	public static UUIDDocument insertDocument(String title, String author, String description, String composition_date, String composition_period_from, String composition_period_to, String preservation_state) throws DatabaseException {
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
	
	
	public static Document loadDocument(UUIDDocument id) throws DatabaseException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		DocumentMetadata dm = null;
		Document d = new Document(null,null, null, null, null, null);
		
		try {
			
			//recupero i metadati del documento
			
			ps = con.prepareStatement("SELECT d.ID AS ID_doc,d.title as doc_title ,dm.* "
					+ "FROM document AS d JOIN document_metadata AS dm ON d.ID=dm.ID_document WHERE d.ID=?;");
			
			
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			rs.last();
			
			if(rs.getRow() <= 1) {  //TODO controllare conversione date mysql to java
				d.setUUID(id);
				d.setTitle(rs.getString("doc_title"));
				dm = new DocumentMetadata(rs.getString("author"), rs.getString("description"), 
						rs.getDate("composition_date"), new VagueDate(rs.getDate("composition_period_from"), 
								rs.getDate("composition_period_to")), rs.getInt("preservation_state"));
				d.setMetaData(dm);
			}else {
				throw new DatabaseException("Errore: è stata rilevata più di una corrispondenza per l'id" + id.getValue());
			}
			
			
			//recupero le pagine del documento
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
			
			ps = con.prepareStatement("SELECT p.* FROM page AS p JOIN document AS d ON p.ID_document=d.ID WHERE d.ID=?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			LinkedList<Page> page_list = new LinkedList<Page>();
			
			while(rs.next()) {
				Page p = new Page(new UUIDPage(rs.getInt("ID")), rs.getInt("number"), 
						new PageScan(new Image(rs.getString("image")), rs.getBoolean("image_convalidation"), rs.getBoolean("image_revised"), 
						new PageScanStaff(new UUIDUser(rs.getInt("ID_digitalizer")), new UUIDUser(rs.getInt("ID_scanning_reviser")))), 
						new PageTranscription(new TEItext(rs.getString("transcription")), rs.getBoolean("transcription_revised"), rs.getBoolean("transcription_convalidation"), rs.getBoolean("transcription_completed"), 
						new PageTranscriptionStaff(null, new UUIDUser(rs.getInt("ID_transcription_reviser")))));
				
				ps1 = con.prepareStatement("SELECT ta.ID_transcriber_user FROM transcription_assigned AS ta JOIN document AS d "
						+ "JOIN page AS p ON d.ID=p.ID_document AND p.ID=ta.ID_page "
						+ "WHERE d.ID=? AND p.ID=?;");
				ps1.setInt(1, id.getValue());
				ps1.setInt(2, rs.getInt("ID"));
				
				rs1 = ps1.executeQuery();
				
				LinkedList<UUIDUser> transcriber_user = new LinkedList<UUIDUser>();
				
				while(rs1.next()) {
					transcriber_user.add(new UUIDUser(rs1.getInt("ID_transcriber_user")));
				}
				p.setPageTranscription(transcriber_user);
				page_list.add(p);
				
			}
			d.setPageList(page_list);
			
			// recupero l'ID dello scanning project e del transcription project
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
			
			ps = con.prepareStatement("SELECT sp.ID AS scan_prj_ID, tp.ID AS trans_prj_ID "
					+ "FROM document AS d JOIN scanning_project AS sp JOIN transcription_project AS tp "
					+ "ON d.ID=sp.ID_document and d.ID=tp.ID_document"
					+ "WHERE d.ID=?;");
			rs = ps.executeQuery();
			
			rs.last();
			if(rs.getRow() <= 1) {
				d.setScanningProject(new UUIDScanningWorkProject(rs.getInt("scan_prj_ID")));
				d.setTranscriptionWorkProject(new UUIDTranscriptionWorkProject(rs.getInt("trans_prj_ID")));
			}else {
				throw new DatabaseException("Errore: è stata trova più di una corrispondenza per il Documento con ID" + id.getValue());
			}
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
		}finally {
			try{
				if(rs1 != null)
					rs1.close();
				if(ps1 != null)
					ps1.close();
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
		
	return d;	
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
