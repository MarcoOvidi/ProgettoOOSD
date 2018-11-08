package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import vo.BookMark;
import vo.DocumentMetadata;
import vo.Tag;
import vo.UUIDBookMark;
import vo.UUIDDocument;
import vo.UUIDDocumentCollection;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;
import vo.VagueDate;

public class HomePageQuerySet {

	public static HashMap<UUIDDocument, String> loadNews(int newsNumb) throws DatabaseException {
		HashMap<UUIDDocument, String> trNews = loadTranscriptionNews(newsNumb / 2);
		HashMap<UUIDDocument, String> scNews = loadScanningNews(newsNumb / 2);

		trNews.putAll(scNews);

		return trNews;
		
	}

	/**
	 * Ritorna le ultime n Trascrizioni completate
	 * 
	 * @param newsNumber
	 * @return
	 * @throws DatabaseException
	 */
	private static HashMap<UUIDDocument, String> loadTranscriptionNews(int newsNumber) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<UUIDDocument, String> news = new HashMap<>();

		try {
			ps = con.prepareStatement("SELECT ID_document as doc, title , date FROM transcription_project AS tp JOIN document AS d ON tp.ID_document=d.ID ORDER BY date DESC LIMIT ?;");

			ps.setInt(1, newsNumber);
		

			rs = ps.executeQuery();

			while (rs.next()) {
				news.put(new UUIDDocument(rs.getInt("doc")), rs.getString("title") + " tn " + rs.getDate("date") );
			}

			return news;

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query" + e.getMessage(), e);
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
	 * Ritorna le ultime n opere scansionate
	 * 
	 * @param newsNumber
	 * @return
	 * @throws DatabaseException
	 */
	private static HashMap<UUIDDocument, String> loadScanningNews(int newsNumber) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<UUIDDocument, String> news = new HashMap<>();

		try {
			ps = con.prepareStatement("SELECT ID_document as doc,title, date  "
					+ " FROM scanning_project AS sp JOIN document AS d " + " ON sp.ID_document=d.ID "
					+ " WHERE sp.scanning_complete=1 " + " ORDER BY date DESC " + " LIMIT ?");

			ps.setInt(1, newsNumber);

			rs = ps.executeQuery();

			while (rs.next()) {
				news.put(new UUIDDocument(rs.getInt("doc")), rs.getString("title") + " sn " + rs.getDate("date"));
			}

			return news;

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
	 * seleziona i progetti di trascrizione ai quali lavoro (ovvero progetti che non
	 * sono stati ancora pubblicati)
	 * 
	 * @param UUIDUser
	 *            utente di cui si devono reperire i progetti di trascrizione
	 * @return HashMap<Integer,String> Mappa di Id progetti di trascrizione e titolo
	 *         dell'opera associata
	 */
	public static HashMap<UUIDTranscriptionWorkProject, String> loadMyTranscriptionWorkProjectList(UUIDUser usr) throws DatabaseException {

		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<UUIDTranscriptionWorkProject, String> twpl = new HashMap<UUIDTranscriptionWorkProject, String>();

		try {
			ps = con.prepareStatement("SELECT tp.ID as ID_progetto, d.title as Title "
					+ "FROM transcription_project as tp JOIN transcription_project_transcriber_partecipant as tptp "
					+ "JOIN document as d " + "ON tp.ID=tptp.ID_transcription_project and tp.ID_document=d.ID "
					+ "WHERE ID_transcriber_user=? ;");

			ps.setInt(1, usr.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				twpl.put(new UUIDTranscriptionWorkProject(rs.getInt("ID_progetto")), rs.getString("Title"));
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

	/**
	 * seleziona i progetti di digitalizzazione ai quali lavoro (ovvero progetti che
	 * non sono stati ancora pubblicati)
	 * 
	 * @param UUIDUser
	 *            utente di cui si devono reperire i progetti di digitalizzazione
	 * @return HashMap<Integer,String> Mappa di Id progetti di digitalizzazione e
	 *         titolo dell'opera associata
	 */
	public static HashMap<UUIDScanningWorkProject, String> loadMyScanningWorkProjectList(UUIDUser usr) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<UUIDScanningWorkProject, String> swpl = new HashMap<UUIDScanningWorkProject, String>();

		try {
			ps = con.prepareStatement("SELECT sp.ID as ID_progetto, d.title as Title "
					+ "FROM scanning_project as sp JOIN scanning_project_digitalizer_partecipant as spdp "
					+ "JOIN document as d " + "ON sp.ID=spdp.ID_scanning_project AND sp.ID_document=d.ID "
					+ "WHERE ID_digitalizer_user=?;");
			ps.setInt(1, usr.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				swpl.put(new UUIDScanningWorkProject(rs.getInt("ID_progetto")), rs.getString("Title"));
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

		return swpl;

	}

	/**
	 * Carica la lista dei Document preferiti di un utente
	 * 
	 * @param UUIdUser
	 *            id dell'utente di cui si vuole caricare la Collection personale
	 * @return HashMap<Integer,String[]> dove la chiave è l'Id del Document,
	 *         String[0] il titolo dell'opera e String[1] è l'immagine della prima
	 *         Page
	 * @exception DatabaseException
	 *                in caso di errori di connessione o esecuzione query nel DB
	 */
	public static HashMap<UUIDBookMark, String[]> loadMyCollectionList(UUIDUser usr) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		HashMap<UUIDBookMark, String[]> pc = new HashMap<UUIDBookMark, String[]>();

		try {
			ps = con.prepareStatement("SELECT p.ID_document as DocID, p.image PImage, d.title as Title "
					+ "FROM personal_collection as pc JOIN page as p JOIN document as d "
					+ "ON pc.ID_document=p.ID_document AND pc.ID_document=d.ID "
					+ "WHERE pc.ID_user=? and p.number=1;");
			ps.setInt(1, usr.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				String[] info = new String[2];
				info[0] = rs.getString("Title");
				info[1] = rs.getString("PImage");
				pc.put(new UUIDBookMark(rs.getInt("DocID")), info);
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

		return pc;

	}

	/**
	 * Recupera i metadati di tutti i Document presenti nella collezione personale
	 * di un User
	 * 
	 * @param UUIDUser
	 *            id dell'utente di cui carichiamo la PersonalDocumentCollection
	 * @return HashMap<Integer,DocumentMetadata> dove la chiave è l'Id del Document
	 *         e il valore il rispettivo DocumentMetadata
	 * @exception DatabaseException
	 *                in caso di mancata conessione o errori di query sul DB
	 */
	public static HashMap<Integer, DocumentMetadata> loadMyCollectionMetaData(UUIDUser usr) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<Integer, DocumentMetadata> dm = new HashMap<Integer, DocumentMetadata>();

		try {
			ps = con.prepareStatement(
					"SELECT dm.ID_document as ID, author as a, description as d,composition_date as cd, "
							+ "composition_period_from as cpf, composition_period_to as cpt, preservation_state as ps "
							+ "FROM personal_collection as pc join document_metadata as dm on pc.ID_document=dm.ID_document "
							+ "WHERE pc.ID_user=?;");
			ps.setInt(1, usr.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				Date comp = null;
				Date cpf = null;
				Date cpt = null;

				if (rs.getDate("cd") != null) {

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = rs.getString("cd");
					comp = sdf.parse(date);
				}

				if (rs.getDate("cpf") != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = rs.getString("cpf");
					cpf = sdf.parse(date);
				}

				if (rs.getDate("cpt") != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM_dd");
					String date = rs.getString("cpt");
					cpt = sdf.parse(date);
				}

				dm.put(rs.getInt("ID"), new DocumentMetadata(rs.getString("a"), rs.getString("d"), comp,
						new VagueDate(cpf, cpt), rs.getInt("ps")));
			}

			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();

			ps = con.prepareStatement("SELECT t.name FROM document d JOIN document_metadata dm JOIN tag_metadata tm "
					+ "JOIN tag t ON d.ID=dm.ID_document AND dm.ID=tm.ID_document_metadata AND tm.ID_tag=t.ID "
					+ "WHERE d.id=?;");

			for (Map.Entry<Integer, DocumentMetadata> docM : dm.entrySet()) {
				ps.setInt(1, docM.getKey());
				rs = ps.executeQuery();
				ArrayList<Tag> tag = new ArrayList<Tag>();
				while (rs.next()) {
					tag.add(new Tag(rs.getString("t.name")));
				}

				DocumentMetadata dmp = docM.getValue();
				dmp.setTags(tag);
				docM.setValue(dmp);

				if (rs != null)
					rs.close();
			}

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
		} catch (ParseException ex) {
			throw new DatabaseException("Impossibile effettuare il parsing della data", ex);
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

		return dm;

	}

	/**
	 * Recupera tutti i BookMarks di un utente
	 * 
	 * @param UUIDUser
	 *            id dell'utente che vuole caricare i BookMarks
	 * @return HashMap<Integer,BookMark> HashMap con chiave l'ID del BookMark e
	 *         value un BookMark
	 * @throw DatabaseException
	 */
	public static HashMap<Integer, BookMark> loadMyBookMarksList(UUIDUser usr) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<Integer, BookMark> bookM = new HashMap<Integer, BookMark>();

		try {
			ps = con.prepareStatement("SELECT b.ID,b.ID_document,b.ID_page,p.image FROM book_marks as b JOIN page as p "
					+ "ON b.ID_page=p.ID WHERE b.ID_user=?;");

			ps.setInt(1, usr.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				bookM.put(rs.getInt("b.ID"), new BookMark(usr, new UUIDDocument(rs.getInt("b.ID_document")),
						new UUIDPage(rs.getInt("b.ID_page"))));
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

		return bookM;

	}

	/**
	 * seleziona le categorie di opere della biblioteca
	 * @return
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static HashMap<UUIDDocumentCollection, String> loadLibraryCollectionList() throws DatabaseException, SQLException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		Statement s = con.createStatement();
		ResultSet rs = null;
		HashMap<UUIDDocumentCollection, String> cat = new HashMap<UUIDDocumentCollection,String>();

		try {
			rs = s.executeQuery("SELECT * FROM document_collection;");

			while (rs.next()) {
				cat.put(new UUIDDocumentCollection(rs.getInt("ID")), rs.getString("name"));

			}

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}

		}

		return cat;

	}

	/**
	 * Seleziona id,titolo e miniatura delle opere di una determinata categoria
	 * 
	 * @param UUIDDocumentCollection
	 *            Id della Collezione di cui si vogliono caricare le opere
	 * @return LinkedList<String[]> String[0] = Document ID, String[1] = Document
	 *         title , String[2] = link Imange della prima pagina
	 * @exception DatabaseException
	 *                Per errori di connessione al DB o di esecuzione delle query
	 */
	public static LinkedList<String[]> loadCollection(UUIDDocumentCollection idc) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<String[]> coll = new LinkedList<String[]>();

		try {
			ps = con.prepareStatement("SELECT dc.ID as IDcollection,d.ID as IDdocument,d.title,p.image "
					+ "FROM document_of_collection dc join document d join page p "
					+ "ON dc.ID_document=d.ID and d.ID=p.ID_document "
					+ "WHERE ID_document_collection = ? and p.number=1;");

			ps.setInt(1, idc.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				String[] info = new String[3];
				info[0] = Integer.toString(rs.getInt("IDdocument"));
				info[1] = rs.getString("d.title");
				info[2] = rs.getString("p.image");
				coll.add(info);
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

		return coll;

	}

	// seleziona una descrizione della biblioteca
	public void loadHomeInfo() {

	}

}
