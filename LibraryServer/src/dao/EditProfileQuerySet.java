package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import vo.UUIDUser;
import vo.Email;
import vo.UserInformations;
import model.User;
import java.util.Date;
import java.lang.NullPointerException;

public class EditProfileQuerySet {
	
	//seleziona tutti i campi anagrafici di un determinato utente
	
	/*
	 * @param user un UUID dell'utente di cui si vogliono prelevare i dati dal database
	 * @return User un oggetto utente  
	 * @exception SQLException in caso di errori relativi al database
	 * @exception NullPointerException se non vienete trovato l'utente passato come parametro nel db
	 */
	
	public static User loadUserProfile(UUIDUser user) throws NullPointerException{
		//STEP 1 parametri di connessione   
		// JDBC driver name and database URL
		   final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   final String DB_URL = "jdbc:mysql://localhost/biblioteca";

		   //  Database credentials
		   final String USER = "bibliotecario";
		   final String PASS = "libriantichi";
		   
		   Connection conn = null;
		   Statement stmt = null;
		   User usr = null;
		   
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);

		      //STEP 3: Open a connection
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT * from user where id="+ user.getValue() ;
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      
		      //Retrieve by column name
		      if(rs.next()) {
		    	  String username = rs.getString("username");
		    	  String name = rs.getString("name");
		    	  String pass = rs.getString("password");
		    	  String surname = rs.getString("surname");
		    	  Boolean status = rs.getBoolean("status");
		    	  String email = rs.getString("email");
		    	  Date regDate = rs.getDate("registration_date");
		         
		    	  //object creation
		    	  Email em = new Email(email);
		    	  UserInformations ui = new UserInformations(name,surname,regDate,em,pass);   
		    	  //user creations
		    	  usr = new User(user,username,ui,status);
		      }
		      else throw new NullPointerException();
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
		   return usr;
	}

	
	//aggiorna tutti i campi di un determinato utente sovrascrivendoli con quelli passati dal controller
	// toChange è l'utente al quale devo aggiornare le informazioni anagrafiche
	// user toUpdate è un utente di input che contiene le modifiche da andare a fare sull'utente tiChange
	
	/*
	 * @param toChange utente sul quale effettuare lo modifiche anagrafiche
	 * @param toUpdate utente fittizio sul quale vengono riportate le informazioni da cambiare e quelle che persistono
	 * @return Boolean 0 se l'operazione non ha effettuato modifiche all'utente toChange, 1 altrimenti
	 */
	public static boolean updateUserProfile(UUIDUser toChange,User toUpdate) throws NullPointerException {
		Integer linesAffected = 0;
		//recupero i campi per la query
		Integer id = toChange.getValue();
		String username = toUpdate.getUsername();
  	  	UserInformations ui = toUpdate.getInformations();
  	  	Boolean status = toUpdate.getActive();

  	  	//recupero le user information
  	  	String name = ui.getName();
  	  	String surname = ui.getSurname();
  	  	String password = ui.getPassword();
  	  	Email email = ui.getMail();
  	  	
  	  	//converto la Mail in stringa per la query
  	  	String em = email.getEmail();
  	  	
  	  	//CONNESSIONE
  	  	//STEP 1 parametri di connessione   
  		// JDBC driver name and database URL
  		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  		final String DB_URL = "jdbc:mysql://localhost/biblioteca";

  		//  Database credentials
  		final String USER = "bibliotecario";
  		final String PASS = "libriantichi";
  			   
  		Connection conn = null;
  		Statement stmt = null;
  		
  		//controllo che gli user passati non siano vuoti 
  		if(toUpdate == null || toUpdate == null )
  			throw new NullPointerException();
  		
  		//TODO fare il compareTo

  		try{
  			//STEP 2: Register JDBC driver
  			Class.forName(JDBC_DRIVER);

  			//STEP 3: Open a connection
  			conn = DriverManager.getConnection(DB_URL,USER,PASS);

  			//STEP 4: Execute a query
  			stmt = conn.createStatement();
  			String sql;
  			sql = "UPDATE user "
  				+ "SET username = " + "\"" + username + "\"" + ", password = " + "\"" + password + "\"" + ", status = " + status + ", name = " + "\"" + name 
  				+  "\"" + ", surname = " + "\"" + surname + "\"" + ", email = " + "\"" + em 
  				+ "\"" + "WHERE ID = " + id + ";";
  			
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
	
	/* TODO non potrebbe essere troppo drastica ?  nel database cmq un utente
	 *  che ha contribuito non saprirà mai perchè senno si perderebbero i suoi 
	 *  lavori al massimo potremmo creare un tag disattivato
	 */
	
	
	//elimina il record di un determinato utente dalla tabella user
	public void deleteUserProfile() {
		
	}
	
	
}
