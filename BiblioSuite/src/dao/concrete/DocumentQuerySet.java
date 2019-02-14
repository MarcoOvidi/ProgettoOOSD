package dao.concrete;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import controller.LocalSession;
import dao.interfaces.DocumentQuerySetDAO;
import javafx.fxml.LoadException;
import model.Document;
import model.Page;
import model.PageScan;
import model.PageScanStaff;
import model.PageTranscription;
import model.PageTranscriptionStaff;
import vo.DocumentMetadata;
import vo.Image;
import vo.TEItext;
import vo.Tag;
import vo.UUIDDocument;
import vo.UUIDDocumentMetadata;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;
import vo.VagueDate;

public class DocumentQuerySet implements DocumentQuerySetDAO {

	/**
	 * Inserisce un nuovo documento
	 * 
	 * @param String Titolo dell'opera
	 * 
	 * @return Boolean: true se l'operazione è andata a buon fine , false altrimenti
	 */
	// TODO questo metodo carica solo il titolo del documento giusto ?
	public  UUIDDocument insertDocument(String title, String author, String description, String composition_date,
			String composition_period_from, String composition_period_to, String preservation_state)
			throws DatabaseException, ParseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		if (composition_date != null && (composition_period_from != null || composition_period_to != null)) {
			throw new DatabaseException("Errore inserire la data certa o un periodo");
		} else if (composition_period_from != null && composition_period_to != null && composition_date != null)
			throw new DatabaseException("Errore inserire data certa o un periodo");
		else if (composition_date == null && (composition_period_from == null || composition_period_to == null))
			throw new DatabaseException("Inserire entrambe le dati per il periodo");
		else if (composition_date != null) {
			if (composition_date.length() > 4)

				throw new RuntimeException("Errore inserire anno nel formatto YYYY");

			PreparedStatement ps = null;
			ResultSet rs = null;
			UUIDDocument id = null;

			try {
				con.setAutoCommit(false);
				ps = con.prepareStatement("insert into document(title) value( ? );", new String[] { "ID" });
				ps.setString(1, title);

				ps.addBatch();
				ps.executeBatch();

				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					id = new UUIDDocument(rs.getInt(1));
				}

				con.commit();

				con.setAutoCommit(true);

				ps.close();

				ps = con.prepareStatement(
						"insert into document_metadata(author,description,composition_date,preservation_state,ID_document) "
								+ "values(?,?,?,?,?);");

				ps.setString(1, author);
				ps.setString(2, description);
				ps.setInt(3, Integer.parseInt(composition_date));
				ps.setInt(4, Integer.parseInt(preservation_state));
				ps.setInt(5, id.getValue());

				ps.executeUpdate();


				if (ps != null)
					ps.close();

				ps = con.prepareStatement("insert into scanning_project (ID_coordinator,ID_document,scanning_complete) "
						+ "values(?,?,0);");

				ps.setInt(1, LocalSession.getLocalUser().getID().getValue());
				ps.setInt(2, id.getValue());

				ps.executeUpdate();

			} catch (SQLException e) {
				try {
					con.abort(null);
				} catch (SQLException f) {
					DBConnection.logDatabaseException(new DatabaseException("Duplicato", f));
				}
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
			return id;
		} else {
			if (composition_period_from.length() > 4 || composition_period_to.length() > 4)
				throw new RuntimeException("Errore inserire anno nel formatto YYYY");
			if (Integer.parseInt(composition_period_from) > Integer.parseInt(composition_period_to))
				throw new RuntimeException("La data di fine periodo non può precedere quella di inizio");
			PreparedStatement ps = null;
			ResultSet rs = null;
			UUIDDocument id = null;

			try {
				con.setAutoCommit(false);

				ps = con.prepareStatement("insert into document(title) value( ? );", new String[] { "ID" });
				ps.setString(1, title);

				ps.addBatch();
				ps.executeBatch();

				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					id = new UUIDDocument(rs.getInt(1));
				}

				con.commit();

				con.setAutoCommit(true);

				ps.close();

				ps = con.prepareStatement("insert into document_metadata(author,description,"
						+ "composition_period_from,composition_period_to,preservation_state,ID_document) "
						+ "values(?,?,?,?,?,?);");

				ps.setString(1, author);
				ps.setString(2, description);
				ps.setInt(3, Integer.parseInt(composition_period_from));
				ps.setInt(4, Integer.parseInt(composition_period_to));
				ps.setInt(5, Integer.parseInt(preservation_state));
				ps.setInt(6, id.getValue());

				ps.executeUpdate();

				new File("resources/documents/" + id.getValue()).mkdirs();

				if (ps != null)
					ps.close();

				ps = con.prepareStatement("insert into scanning_project (ID_coordinator,ID_document,scanning_complete) "
						+ "values(?,?,0);");

				ps.setInt(1, LocalSession.getLocalUser().getID().getValue());
				ps.setInt(2, id.getValue());

				ps.executeUpdate();

			} catch (SQLException e) {
				try {
					con.abort(null);
				} catch (SQLException f) {
					DBConnection.logDatabaseException(new DatabaseException("Duplicato", f));
				}
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
			return id;
		}
	}

	public  Document loadDocument(UUIDDocument id) throws DatabaseException, LoadException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		DocumentMetadata dm = null;
		Document d = new Document();

		try {

			// recupero i metadati del documento

			ps = con.prepareStatement("SELECT d.ID AS ID_doc,d.title as doc_title ,dm.* "
					+ "FROM document AS d JOIN document_metadata AS dm ON d.ID=dm.ID_document WHERE d.ID=?;");

			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			rs.last();

			if (rs.getRow() == 1) {
				d.setUUID(id);
				d.setTitle(rs.getString("doc_title"));
				dm = new DocumentMetadata(rs.getString("author"), rs.getString("description"),
						rs.getInt("composition_date"),
						new VagueDate(rs.getInt("composition_period_from"), rs.getInt("composition_period_to")),
						rs.getInt("preservation_state"));
				
				dm.setTags(loadDocumentTags(id));

				d.setMetaData(dm);

				// recupero le pagine del documento
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

				ps = con.prepareStatement(
						"SELECT p.* FROM page AS p JOIN document AS d ON p.ID_document=d.ID WHERE d.ID=?;");
				ps.setInt(1, id.getValue());

				rs = ps.executeQuery();

				LinkedList<Page> page_list = new LinkedList<Page>();

				while (rs.next()) {
					Page p = new Page(new UUIDPage(rs.getInt("ID")), rs.getInt("number"),
							new PageScan(new Image(rs.getString("image")), rs.getBoolean("image_convalidation"),
									rs.getBoolean("image_revised"),
									new PageScanStaff(new UUIDUser(rs.getInt("ID_digitalizer")),
											new UUIDUser(rs.getInt("ID_scanning_reviser")))),
							new PageTranscription(new TEItext(rs.getString("transcription")),
									rs.getBoolean("transcription_revised"),
									rs.getBoolean("transcription_convalidation"),
									new PageTranscriptionStaff(new UUIDUser(rs.getInt("ID_transcriber")),
											new UUIDUser(rs.getInt("ID_transcription_reviser")))));
					ps1 = con.prepareStatement(
							"SELECT ta.ID_transcriber_user FROM transcription_assigned AS ta JOIN document AS d "
									+ "JOIN page AS p ON d.ID=p.ID_document AND p.ID=ta.ID_page "
									+ "WHERE d.ID=? AND p.ID=?;");
					ps1.setInt(1, id.getValue());
					ps1.setInt(2, rs.getInt("ID"));

					rs1 = ps1.executeQuery();

					LinkedList<UUIDUser> transcriber_user = new LinkedList<UUIDUser>();

					while (rs1.next()) {
						transcriber_user.add(new UUIDUser(rs1.getInt("ID_transcriber_user")));
					}
					p.setPageTranscription(transcriber_user);
					page_list.add(p);

					if (rs1 != null)
						rs1.close();
					if (ps1 != null)
						ps1.close();

				}
				d.setPageList(page_list);

				// recupero l'ID dello scanning project e del transcription project
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

				ps = con.prepareStatement("SELECT sp.ID AS scan_prj_ID "
						+ "FROM document AS d JOIN scanning_project AS sp  "
						+ "ON d.ID=sp.ID_document  WHERE d.ID=?;");

				ps.setInt(1, id.getValue());
				rs = ps.executeQuery();

				rs.last();

				if (rs.getRow() == 1) {
					d.setScanningProject(new UUIDScanningWorkProject(rs.getInt("scan_prj_ID")));
				} else if (rs.getRow() == 0) {
					d.setScanningProject(null);
				} else {
					throw new LoadException("Errore: è stata trova più di una corrispondenza per i project");
				}
				
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

				ps = con.prepareStatement("SELECT tp.ID AS trans_prj_ID "
						+ "FROM document AS d JOIN transcription_project AS tp  "
						+ "ON d.ID=tp.ID_document  WHERE d.ID=?;");

				ps.setInt(1, id.getValue());
				rs = ps.executeQuery();

				rs.last();

				if (rs.getRow() == 1) {
					d.setTranscriptionWorkProject(new UUIDTranscriptionWorkProject(rs.getInt("trans_prj_ID")));
				} else if (rs.getRow() == 0) {
					d.setTranscriptionWorkProject(null);
				} else {
					throw new LoadException("Errore: è stata trova più di una corrispondenza per i project");
				}
				
				

			} else if (rs.getRow() == 0) {
				throw new LoadException("Non è stato trovato un documento corrispondente\n" + id
						+ "\nCheck you database, motherfucker");
			} else {

				throw new LoadException("È stata rilevata più di una corrispondenza per l'id\n" + id.getValue()
						+ "\nCheck your database, motherfucker");
			}

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query: " + e.getMessage(), e);
		} finally {
			try {
				if (rs1 != null)
					rs1.close();
				if (ps1 != null)
					ps1.close();
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

		return d;
	}

	/*
	 * Come loadDocument ma ignora pagine non revisionate
	 */
	public  Document loadDocumentToView(UUIDDocument id) throws DatabaseException, LoadException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		DocumentMetadata dm = null;
		Document d = new Document();

		try {

			// recupero i metadati del documento

			ps = con.prepareStatement("SELECT d.ID AS ID_doc,d.title as doc_title ,dm.* "
					+ "FROM document AS d JOIN document_metadata AS dm ON d.ID=dm.ID_document WHERE d.ID=?;");

			ps.setInt(1, id.getValue());

			rs = ps.executeQuery();

			rs.last();

			if (rs.getRow() == 1) {
				d.setUUID(id);
				d.setTitle(rs.getString("doc_title"));
				dm = new DocumentMetadata(rs.getString("author"), rs.getString("description"),
						rs.getInt("composition_date"),
						new VagueDate(rs.getInt("composition_period_from"), rs.getInt("composition_period_to")),
						rs.getInt("preservation_state"));

				d.setMetaData(dm);

				// recupero le pagine del documento
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

				ps = con.prepareStatement(
						"SELECT p.* FROM page AS p JOIN document AS d ON p.ID_document=d.ID WHERE d.ID=? AND p.image_convalidation=1;");
				ps.setInt(1, id.getValue());

				rs = ps.executeQuery();

				LinkedList<Page> page_list = new LinkedList<Page>();

				while (rs.next()) {
					Page p = new Page(new UUIDPage(rs.getInt("ID")), rs.getInt("number"),
							new PageScan(new Image(rs.getString("image")), rs.getBoolean("image_convalidation"),
									rs.getBoolean("image_revised"),
									new PageScanStaff(new UUIDUser(rs.getInt("ID_digitalizer")),
											new UUIDUser(rs.getInt("ID_scanning_reviser")))),
							new PageTranscription(new TEItext(rs.getString("transcription")),
									rs.getBoolean("transcription_revised"),
									rs.getBoolean("transcription_convalidation"),
									new PageTranscriptionStaff(new UUIDUser(rs.getInt("ID_transcriber")),
											new UUIDUser(rs.getInt("ID_transcription_reviser"))),
									rs.getString("transcription_reviser_comment")));
					ps1 = con.prepareStatement(
							"SELECT ta.ID_transcriber_user FROM transcription_assigned AS ta JOIN document AS d "
									+ "JOIN page AS p ON d.ID=p.ID_document AND p.ID=ta.ID_page "
									+ "WHERE d.ID=? AND p.ID=?;");
					ps1.setInt(1, id.getValue());
					ps1.setInt(2, rs.getInt("ID"));

					rs1 = ps1.executeQuery();

					LinkedList<UUIDUser> transcriber_user = new LinkedList<UUIDUser>();

					while (rs1.next()) {
						transcriber_user.add(new UUIDUser(rs1.getInt("ID_transcriber_user")));
					}
					p.setPageTranscription(transcriber_user);
					page_list.add(p);

					if (rs1 != null)
						rs1.close();
					if (ps1 != null)
						ps1.close();

				}
				d.setPageList(page_list);

				// recupero l'ID dello scanning project e del transcription project
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

				ps = con.prepareStatement("SELECT sp.ID AS scan_prj_ID "
						+ "FROM document AS d JOIN scanning_project AS sp  "
						+ "ON d.ID=sp.ID_document  WHERE d.ID=?;");

				ps.setInt(1, id.getValue());
				rs = ps.executeQuery();

				rs.last();

				if (rs.getRow() == 1) {
					d.setScanningProject(new UUIDScanningWorkProject(rs.getInt("scan_prj_ID")));
				} else if (rs.getRow() == 0) {
					d.setScanningProject(null);
				} else {
					throw new LoadException("Errore: è stata trova più di una corrispondenza per i project");
				}
				
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

				ps = con.prepareStatement("SELECT tp.ID AS trans_prj_ID "
						+ "FROM document AS d JOIN transcription_project AS tp  "
						+ "ON d.ID=tp.ID_document  WHERE d.ID=?;");

				ps.setInt(1, id.getValue());
				rs = ps.executeQuery();

				rs.last();

				if (rs.getRow() == 1) {
					d.setTranscriptionWorkProject(new UUIDTranscriptionWorkProject(rs.getInt("trans_prj_ID")));
				} else if (rs.getRow() == 0) {
					d.setTranscriptionWorkProject(null);
				} else {
					throw new LoadException("Errore: è stata trova più di una corrispondenza per i project");
				}
				
			}

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query: " + e.getMessage(), e);
		} finally {
			try {
				if (rs1 != null)
					rs1.close();
				if (ps1 != null)
					ps1.close();
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

		return d;
	}

	// aggiorna un documento
	public void updateDocument() {

	}

	// cancella un documento
	public void deleteDocument() {

	}

	// scarica un documento
	public void downloadFileDocument() {

	}

	/**
	 * Carica tutti i tag disponibili in DB
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public  HashMap<Integer, Tag> loadAvailableTag() throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<Integer, Tag> tags = new HashMap<Integer, Tag>();

		try {
			ps = con.prepareStatement("SELECT * from tag;");

			rs = ps.executeQuery();

			while (rs.next()) {
				tags.put(rs.getInt("ID"), new Tag(rs.getString("name")));
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

		return tags;

	}

	/**
	 * Associa tags ad un document
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public  void addTagToDocument(UUIDDocumentMetadata id, HashMap<Integer, Tag> tags) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;

		try {

			ps = con.prepareStatement("INSERT INTO tag_metadata (ID_document_metadata ,ID_tag) VALUES (?,?);");

			for (Entry<Integer, Tag> tag : tags.entrySet()) {
				ps.setInt(1, id.getValue());
				ps.setInt(2, tag.getKey());

				ps.addBatch();

			}

			ps.executeBatch();

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
	 * Recupera UUIDDocumentMetadata da un UUIDDocument
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public  UUIDDocumentMetadata getDocumentMetadataOfDocument(UUIDDocument id) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UUIDDocumentMetadata idDm = null;

		try {
			ps = con.prepareStatement("SELECT ID FROM document_metadata WHERE ID_document = ?;");
			ps.setInt(1, id.getValue());
			rs = ps.executeQuery();

			if (rs.next())
				idDm = new UUIDDocumentMetadata(rs.getInt("ID"));

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
		return idDm;
	}

	/**
	 * Inserisce un nuovo tag nel db
	 * 
	 * @param t Tag
	 * @return
	 * @throws DatabaseException
	 */
	public  Integer insertNewTag(Tag t) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer tagID = null;

		try {
			con.setAutoCommit(false);

			ps = con.prepareStatement("insert into tag(name) value( ? );", new String[] { "ID" });
			ps.setString(1, t.getTag());

			ps.addBatch();
			ps.executeBatch();

			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				tagID = rs.getInt(1);
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
		return tagID;
	}

	/**
	 * Recupera i tag di un documento
	 * 
	 * @param UUIDDocument id del documento di cui carichiamo i tag
	 * @return ArrayList<Tag>
	 * @exception DatabaseException in caso di mancata conessione o errori di query
	 *                              sul DB
	 */
	public  ArrayList<Tag> loadDocumentTags(UUIDDocument document)
			throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;		
		ArrayList<Tag> tags = new ArrayList<Tag>();
		
		try {
			ps = con.prepareStatement("SELECT t.name FROM document d JOIN document_metadata dm JOIN tag_metadata tm "
					+ "JOIN tag t ON d.ID=dm.ID_document AND dm.ID=tm.ID_document_metadata AND tm.ID_tag=t.ID "
					+ "WHERE d.id=?;");
			
			ps.setInt(1, document.getValue());
			
			rs = ps.executeQuery();
			while (rs.next()) {
				tags.add(new Tag(rs.getString("t.name")));
			}
			if (rs != null)
				rs.close();

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

		return tags;

	}
	
	/**
	 * Mostra il titolo di un document
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public  String getDocumentTitle(UUIDDocument id) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String title = "";

		try {
			ps = con.prepareStatement("SELECT title from document where ID=?;");

			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();

			if (rs.next()) {
				title = rs.getString("title");
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

		return title;

	}
	
	
	/**
	 * Ritorna l'elenco completo di tutte le opere comprese le url delle copertine (pagina 1)
	 * @return
	 * @throws DatabaseException
	 */
	public  HashMap<UUIDDocument, String[]> getAllDocuments() throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<UUIDDocument, String[]> documents = new HashMap<UUIDDocument, String[]>();

		try {
			ps = con.prepareStatement("select d.ID,d.title,page.image "
					+ "from document d left join page "
					+ "on d.ID=page.ID_document and page.number=1;");

			
			rs = ps.executeQuery();

			while(rs.next()) {
				String[] info = new String[2];
				info[0] = rs.getString("title");
				info[1] = rs.getString("image");
				documents.put(new UUIDDocument(rs.getInt("ID")), info);
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

		return documents;

	}
	
	
	/**
	 * UUIDPage from UUIDDocument and page number
	 */
	public  UUIDPage getPageUUID(UUIDDocument doc, int pageNumber) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
	

		try {
			ps = con.prepareStatement("select ID from page where ID_document=? AND number=?");
			ps.setInt(1, doc.getValue());
			ps.setInt(2, pageNumber);
			
			rs = ps.executeQuery();

			if(rs.next()) {
				return new UUIDPage(rs.getInt("ID"));
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
		return null;

	}
	
	public static void main(String[] foo) {
		try {
			System.out.println(new DocumentQuerySet().getPageUUID(new UUIDDocument(193), 1));
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public LinkedList<String> getInvolvedCollections(UUIDDocument id) throws DatabaseException{
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<String> categories = new LinkedList<String>();
	

		try {
			ps = con.prepareStatement("select dc.name from document_collection as dc"
					+ " join document_of_collection as dof "
					+ "on dc.ID=dof.ID_document_collection where dof.ID_document=?;");
			ps.setInt(1, id.getValue());
			rs = ps.executeQuery();

			while(rs.next()) {
				categories.add(rs.getString("name"));
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
		return categories;
	}

}
