package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import model.Document;

public class DocumentQuerySet {
	
	/* Inserisce un nuovo documento
	 * @param doc Un oggetto Documento
	 * @return Boolean: true se l'operazione è andata a buon fine , false altrimenti
	 */
	
	public void insertDocument(Document doc) {
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
  		
  		try{
  			//STEP 2: Register JDBC driver
  			Class.forName(JDBC_DRIVER);

  			//STEP 3: Open a connection
  			conn = DriverManager.getConnection(DB_URL,USER,PASS);

  			//STEP 4: Execute a query
  			stmt = conn.createStatement();
  			String sql = "";
  			sql = "INSERT into document(tilte,scanning_complete,transcription_complete) "
  					+ "Value  title = " + "\"" + doc.getTitle() + "\""  
  					+ ", scanning_complete = " + "\"" + doc.getScanningWorkProject(). 
  					+ ", editMetadata = " + editMetadata  
  					+ ", reviewPage = " + reviewPage  
  					+ ", modifyTranscription = " + modifyTranscription    
  					+ ", requestTranscriptionTask = " + requestTranscriptionTask    
  					+ ", reviewTranscription = " + reviewTranscription  
  					+ ", addNewProject = " + addNewProject  
  					+ ", assignDigitalizationTask = " + assignDigitalizationTask   
  					+ ", assignTranscriptionTask = " +assignTranscriptionTask
  					+ ", publishDocument = " + publishDocument 
  					+ " WHERE ID_user = " + id + ";";
  			
  			
  			linesAffected = stmt.executeUpdate(sql);
  			
   			
  			//STEP 5: Clean-up environment
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
  			}// nothing we can do
  			try{
  				if(conn!=null)
  					conn.close();
  			}catch(SQLException se){
  				se.printStackTrace();
  			}//end finally try
  		}//end try
  		
  		if (linesAffected == 1)
  			return true;
  		else
  			return false;
		
		
		
		
		
		
	}
	
	//seleziona un documento
	public void loadDocument() {
		
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
