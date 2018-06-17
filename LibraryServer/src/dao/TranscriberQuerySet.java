package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.PageTranscription;
import vo.TEItext;
import vo.UUIDPage;

public class TranscriberQuerySet {

	/* Seleziona la trascrizione di una pagina di un'opera
	 * @param UUIDPage Id della Page di cui si deve aggiornare la trascrizione
	 * @return PageTranscription Transcrizione di una pagina con relative info sul suo stato
	 * @exception DatabaseException In caso di errori connessione al DB o di esecuzione delle query
	 * @exception NullPointerException in caso i parametri di input siano non validi
	 * 
	 */
	public static PageTranscription loadTranscription(UUIDPage id) throws DatabaseException,NullPointerException{
		if (id == null)
			throw new NullPointerException("Id non valido");
		
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PageTranscription pt = null;
		
		try {
			ps = con.prepareStatement("SELECT transcription,transcription_convalidation as tconv,transcription_revised as tr, "
					+ "transcription_completed as tc "
					+ "FROM page "
					+ "WHERE ID = ?;");
			ps.setInt(1, id.getValue());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				pt = new PageTranscription(new TEItext(rs.getString("transcription")), 
						rs.getBoolean("tr"), rs.getBoolean("tconv"),rs.getBoolean("tc"), null);
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
		
		return pt;
	}
	
	
	/* Aggiorna la trascrizione di una pagina di un'opera
	 * @param TEItext Testo da inserire/aggiornare
	 * @param UUIDPage Id della Page di cui si deve aggiornare la trascrizione
	 * @return Boolan True se la modifica è andata a buon fine
	 * @exception DatabaseException In caso di errori connessione al DB o di esecuzione delle query
	 * @exception NullPointerException in caso i parametri di input siano non validi
	 */
	public static Boolean updateText(TEItext text, UUIDPage id) throws DatabaseException,NullPointerException{
		if (id == null )
			throw new NullPointerException("Id non valido");
		if( text == null)
			throw new NullPointerException("Testo non valido");
		
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
		Integer linesAffected = 0;
		
		try {
			ps = con.prepareStatement("UPDATE page SET transcription = ? WHERE ID=?;");
			ps.setString(1, text.getText());
			ps.setInt(2, id.getValue());
			
			linesAffected = ps.executeUpdate();	
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
		
		return (linesAffected == 1);
	
		
		
	}
}
