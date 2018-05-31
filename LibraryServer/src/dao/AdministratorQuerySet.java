package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.UserPermissions;

public class AdministratorQuerySet {
	
	
	
	/* Questo metodo cambia lo stato di un utente da attivo a inattivo e viceversa
	 * 
	 * @param user Utente su cui fare l'aggiornamento di stato
	 * @param b boolean true se l'utente deve risultare attivo , false altrimenti
	 * 
	 * @return Boolean 1 se la modifica è stata effettuata, 0 altrimenti
	 * 
	 * @see vo.UUIDUser
	 * 
	 * @exception SQLException
	 */
	
	public static Boolean acceptUserRegistration(UUIDUser user, Boolean b) {
		Integer linesAffected = 0;
		//recupero la chiave utente
		Integer id = user.getValue();
		
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
  			String sql;
  			sql = "UPDATE user "
  	  				+ "SET status = " + b + " WHERE ID = " + id + ";";
  			
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
  	}//end method
		
	
	/* Aggiorna i permessi degli utenti
	 * 
	 * @param id , UUIDUser dell'utente a cui devono essere cambiati i permessi
	 * @param up , Oggetto UserPermission istanziato per avere una maschera permessi da impostare
	 * 
	 * @return Boolean 1 se l'inserimento è andato a buon fine , 0 altrimenti
	 * @see vo.UUIDuser,vo.UserPermissions
	 * 
	 * @exception SQLException
	 * 
	 */
	
	public static boolean updateUserPermissions(UUIDUser user,UserPermissions up) {
		
		//recupero i dati necessari alla query dagli oggetti di input
		Integer linesAffected = 0;
		Integer id = user.getValue();
		Boolean download = up.getDownloadPerm();
		//uploader
		Boolean upload = up.getUploadPerm();
		Boolean editMetadata = up.getEditMetaDataPerm();
		//digitalizationReviser
		Boolean reviewPage = up.getReviewPagePerm();
		//transcriber
		Boolean modifyTranscription = up.getModifyTranscriptionPerm();
		Boolean requestTranscriptionTask = up.getRequestTranscriptionTaskPerm();
		//trasnscriptionReviser
		Boolean reviewTranscription = up.getReviewTranscriptionPerm();
		//coordinator
		Boolean addNewProject = up.getAddNewProjectPerm();
		Boolean assignDigitalizationTask = up.getAssignDigitalizationTaskPerm();
		Boolean assignTranscriptionTask = up.getAssignTranscriptionTaskPerm();
		Boolean publishDocument = up.getPublishDocumentPerm();
		
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
  			if(up.getDownloadPerm()==true) {
  				sql = "UPDATE perm_authorization "
  					+ "SET  download = " + download  
  					+ ", upload = " + upload  
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
  			}
  			
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
	
	/* Seleziona il campo oggetto di tutte le richieste inviate dagli utenti 
	 * 
	 * @param b se "FALSE" mostra soltanto gli oggetti delle richieste pending, se "TRUE" viceversa
	 * @return LinkedList<Request> con il solo UUIDRequest ed oggetto  
	 */
	
	public static LinkedList<Request> loadRrequestsList(Boolean b) {
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
		LinkedList<Request> req = new LinkedList<Request>();
		
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			//STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			stmt = conn.createStatement();
			String sql;
			if(b) {
				sql = "SELECT ID,object from request where status=true;";
			}
			else {
				sql = "SELECT ID,object from request where status=false;";
			}
			ResultSet rs = stmt.executeQuery(sql);
			
			//STEP 5: Extract data from result set
			
			//Retrieve by column name
			while(rs.next()) {
				Integer ident = rs.getInt("ID");
				String object = rs.getString("object");
				
				//object creation
				UUIDRequest idr = new UUIDRequest(ident);
				Request r = new Request(idr,object);  
				req.addLast(r);
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
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		
		return req;
			
	}
	
	/* Seleziona il campo oggetto di tutte le richieste inviate dagli utenti pending e non 
	 * 
	 * @return LinkedList<Request> con il solo UUIDRequest ed oggetto  
	 */
	
	public static LinkedList<Request> loadRrequestsList() {
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
		LinkedList<Request> req = new LinkedList<Request>();
		
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			//STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: Execute a query
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT ID,object from request ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			//STEP 5: Extract data from result set
			
			//Retrieve by column name
			while(rs.next()) {
				Integer id = rs.getInt("ID");
				String object = rs.getString("object");
				
				//object creation
				UUIDRequest idr = new UUIDRequest(id);
				Request r = new Request(idr,object);  
				req.addLast(r);
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
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		
		return req;
			
	}
	
	/* Seleziona tutti gli attributi di una richiesta
	 * @param id UUIDRequest di una richiesta
	 * @return Request un oggetto richiesta completo
	 * @throw NullPointerException in caso l'id parametro non sia presente nel db
	 */
	public static Request loadRequest(UUIDRequest id) throws NullPointerException {
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
				Request req = null;
				
				try{
					//STEP 2: Register JDBC driver
					Class.forName(JDBC_DRIVER);
					
					//STEP 3: Open a connection
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					
					//STEP 4: Execute a query
					stmt = conn.createStatement();
					String sql;
					sql = "SELECT * from request where id=" + id.getValue() + ";";
					
					ResultSet rs = stmt.executeQuery(sql);
					
					//STEP 5: Extract data from result set
					
					//Retrieve by column name
					if(rs.next()) {
						UUIDUser user = new UUIDUser(rs.getInt("ID_user"));
						UUIDUser admin = new UUIDUser(rs.getInt("ID_admin"));
						Boolean status = rs.getBoolean("status");
						String object = rs.getString("object");
						String message = rs.getString("message");
						String answer = rs.getString("answer_message");
						
						req = new Request(id,user,admin,status,object,message,answer);
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
					}// nothing we can do
					try{
						if(conn!=null)
							conn.close();
					}catch(SQLException se){
						se.printStackTrace();
					}//end finally try
				}//end try
				
				if(req == null)
					throw new NullPointerException();
				else
					return req;
		
	}
	
	/* Aggiorna l'attributo answer di una richiesta e imposta la request non pending
	 * @param id UUIDRequest della richiesta a cui rispondere
	 * @param answer Corpo del messaggio risposta
	 * @return Boolean true se l'operazione è andata a buon fine , false altrimenti
	 */
	//TODO per me se un admin mand la risposta il booleanno status viene messo a true nel senso che è stata analizzata 
	public static Boolean answerRequest(UUIDRequest id,String answer) {
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
  		
  		Integer linesAffected =0;
  		
  		try{
  			//STEP 2: Register JDBC driver
  			Class.forName(JDBC_DRIVER);

  			//STEP 3: Open a connection
  			conn = DriverManager.getConnection(DB_URL,USER,PASS);

  			//STEP 4: Execute a query
  			stmt = conn.createStatement();
  			String sql;
  			sql = "UPDATE request "
  				+ "SET answer_message = " + "\"" + answer + "\"" + " ,status = true " + "WHERE ID = " + id.getValue() + ";";
  			
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
  	}//end method
	
		
}
	
	
	

