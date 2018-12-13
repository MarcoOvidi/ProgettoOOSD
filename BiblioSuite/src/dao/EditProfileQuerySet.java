package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import vo.UUIDUser;
import vo.Email;
import vo.UserInformations;
import model.User;
import java.util.Date;

public class EditProfileQuerySet {
	
	/*Seleziona tutti i campi anagrafici di un determinato utente
	 * @param user un UUID dell'utente di cui si vogliono prelevare i dati dal database
	 * @return User un oggetto utente  
	 * @exception SQLException in caso di errori relativi al database
	 * @exception NullPointerException se non vienete trovato l'utente passato come parametro nel db
	 */
	
	public static User loadUserProfile(UUIDUser user) throws DatabaseException{
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		User usr = null;
		
		try {
			ps = con.prepareStatement("SELECT * FROM user WHERE id=?");
			ps.setInt(1, user.getValue());	
			
			rs = ps.executeQuery();
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
		    	  usr = new User(user,username,ui,status,UserAuthenticationQuerySet.getUSerPermission(user));
			}
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
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
		
		return usr;
	
		     		      
	}

	
	/*aggiorna tutti i campi di un determinato utente sovrascrivendoli con quelli passati dal controller
	 * @param toChange utente sul quale effettuare lo modifiche anagrafiche
	 * @param toUpdate utente fittizio sul quale vengono riportate le informazioni da cambiare e quelle che persistono
	 * @return Boolean 0 se l'operazione non ha effettuato modifiche all'utente toChange, 1 altrimenti
	 */
	public static boolean updateUserProfile(UUIDUser toChange,User toUpdate) throws DatabaseException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		}catch(DatabaseException e) {
			throw new DatabaseException("Errore di connessione", e);
		}
		
		PreparedStatement ps = null;
		Integer result = null;
		
		try {
			ps = con.prepareStatement("UPDATE user "
	  				+ "SET username = ? , password = ? , status = ? , name = ? , surname = ? , email = ? "
	  				+ " WHERE ID = ? ;");
			ps.setString(1, toUpdate.getUsername());
			ps.setString(2, toUpdate.getInformations().getPassword());
			ps.setBoolean(3, toUpdate.getActive());
			ps.setString(4, toUpdate.getInformations().getName());
			ps.setString(5, toUpdate.getInformations().getSurname());
			ps.setString(6, toUpdate.getInformations().getMail().getEmail());
			ps.setInt(7, toChange.getValue());
			
			
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			throw new DatabaseException("Errore di esecuzione della query", e);
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
		
		if(result == 1)
			return true;
		else
			return false;
	
	}//end method
	
	/* TODO non potrebbe essere troppo drastica ?  nel database cmq un utente
	 *  che ha contribuito non saprirà mai perchè senno si perderebbero i suoi 
	 *  lavori al massimo potremmo creare un tag disattivato
	 */
	
	public void deleteUserProfile() {
	}
	
	
}
