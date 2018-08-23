package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;

public class RevisorDigitalizationQuerySet {

	
	
	//aggiorna il flag di "validità" di uno ScanningWorkProject
	public void validated(UUIDPage id,Boolean validation) throws DatabaseException {

		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
	
		try {
			ps = con.prepareStatement("UPDATE page SET image_revised=true AND image_convalidation=? WHERE ID=?");
			ps.setBoolean(1, validation);
			ps.setInt(2, id.getValue());
			
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
	
	

	//dichiara completato un progetto di scansione opera
	public void scanningProcessCompleted(UUIDScanningWorkProject id) throws DatabaseException{

		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}
		
		PreparedStatement ps = null;
	
		try {
			ps = con.prepareStatement("UPDATE scanning_project SET scanning_complete=true WHERE ID=?");
			
			ps.setInt(1, id.getValue());
			
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
	
		//FIXME una volta che un revisore valida o invalida una scansione in automatico si mette il flag di revisione
	
}
