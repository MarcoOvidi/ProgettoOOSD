package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.TreeMap;

import model.ScanningWorkProject;
import model.TranscriptionWorkProject;
import model.WorkProject;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public class HomePageQuerySet {
	
	
	
	//seleziona i 10 progetti pubblicati più di recente tra digitalizzazioni e trascrizioni di opere
	public void loadNewst() {
		
	}
	
	//seleziona i progetti ai quali lavoro (ovvero progetti che non sono stati ancora pubblicati)
	//id , nome document,
	public static TreeMap<Integer,String> loadMyWorkProjectsList(UUIDUser user) {
		
		TreeMap<Integer,String> trPrj = null;
		TreeMap<Integer,String> digPrj= null;
		TreeMap<Integer,String> myPrj = new TreeMap<Integer,String>();
		
		trPrj = loadMyTranscriptionWorkProjectList(user);
		digPrj = loadMyScanningWorkProjectList(user);
		
		myPrj.putAll(trPrj);
		myPrj.putAll(digPrj);
		return myPrj;
		
	}
	
	public static TreeMap<Integer,String> loadMyTranscriptionWorkProjectList(UUIDUser usr){
		//CONNESSIONE
  	  	//STEP 1 parametri di connessione   
  		// JDBC driver name and database URL
  		final String JDBC_DRIVER = DBConnection.getJdbcDriver();
  		final String DB_URL = DBConnection.getDbUrl();

  		//  Database credentials
  		final String USER = DBConnection.getUser();
  		final String PASS = DBConnection.getPassword();
		
		Connection conn = null;
		Statement stmt = null;
		
		//oggetti di ausilio
		TreeMap<Integer,String> twp = new TreeMap<Integer,String>();
		
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			//STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			stmt = conn.createStatement();
			String sql;
			sql = "select tp.ID as ID_progetto, d.title as Title "
				  + "from transcription_project as tp join transcription_project_transcriber_partecipant as tptp "
				  + "join document as d "
				  + "on tp.ID=tptp.ID_transcription_project and tp.ID_document=d.ID "
				  + "where ID_transcriber_user= " + usr.getValue() + ";"; 
			ResultSet rs = stmt.executeQuery(sql);
			
			//STEP 5: Extract data from result set
			
			//Retrieve by column name
			while(rs.next()) {
				twp.put(rs.getInt("ID_progetto"), rs.getString("Title"));
			}  
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
				stmt.close();
			}catch(SQLException se2){
			}// 	nothing we can do
			try{	
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try	
		}//end try	
		return twp;
		
	}
	
	public static TreeMap<Integer,String> loadMyScanningWorkProjectList(UUIDUser usr){
		//CONNESSIONE
  	  	//STEP 1 parametri di connessione   
  		// JDBC driver name and database URL
  		final String JDBC_DRIVER = DBConnection.getJdbcDriver();
  		final String DB_URL = DBConnection.getDbUrl();

  		//  Database credentials
  		final String USER = DBConnection.getUser();
  		final String PASS = DBConnection.getPassword();
				
  		Connection conn = null;
  		Statement stmt = null;
  		
  		//oggetti di ausilio
  		TreeMap<Integer,String> swp = new TreeMap<Integer,String>();
  		
  		try{
  			//STEP 2: Register JDBC driver
  			Class.forName(JDBC_DRIVER);
  			
  			//STEP 3: Open a connection
  			conn = DriverManager.getConnection(DB_URL,USER,PASS);
  			
  			//STEP 4: Execute a query
  			stmt = conn.createStatement();
  			String sql;
  			sql = "select sp.ID as ID_progetto, d.title as Title "
  					+ "from scanning_project as sp join scanning_project_digitalizer_partecipant as spdp "
  					+ "join document as d "
  					+ "on sp.ID=spdp.ID_scanning_project and sp.ID_document=d.ID "
  					+ "where ID_digitalizer_user= " + usr.getValue() + ";"; 
  			ResultSet rs = stmt.executeQuery(sql);
						
  			//STEP 5: Extract data from result set
						
  			//Retrieve by column name
  			while(rs.next()) {
  				
  				swp.put(rs.getInt("ID_progetto"), "Title");
  				
  			}  
  			//STEP 6: Clean-up environment
  			rs.close();
  			stmt.close();
  			conn.close();
  		}catch(SQLException se){
  			//Handle errors for JDBC
  			se.printStackTrace();
  		}catch(Exception e){
  			//Handle errors for Class.forName
  			e.printStackTrace();
  		}finally{
  			//finally block used to close resources
  			try{
  				if(stmt!=null)
  					stmt.close();
						}catch(SQLException se2){
						}//nothing we can do
  			try{	
  				if(conn!=null)
  					conn.close();
  			}catch(SQLException se){
  				se.printStackTrace();
  			}//end finally try	
  		}//end try	
  		return swp;
			
		
	}
	//TODO serve un metodo che recupera le singole pagine assegnate ?
	
	// !!! questione dei segnalibri !!!  id documento, url immagine, nome del testo,
	public void loadMyCollectionList() {
		
	}
	
	public void loadMyBookMarksList() {
		
	}

	//seleziona le categorie di opere della biblioteca
	public void loadLibraryCollectionList() {

	}
	
	//seleziona le opere(miniature) di una determinata categoria
	public void loadCollection() {
		
	}
	
	//seleziona una descrizione della biblioteca
	public void loadHomeInfo() {
		
	}

}
