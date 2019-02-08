package dao.concrete;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public class DBConnection {
	
	
	
	  //connessione al database con DriverManager
    public static Connection connect() throws DatabaseException {
    	Properties prop = ReadProperties.getConnectionProperties();
    	try {
    		
    		//caricamento dinamico della classe driver
            
    		Class.forName(prop.getProperty("driver"));
            
    		//connessione al database locale
            
    		return DriverManager.getConnection(prop.getProperty("path"),prop.getProperty("user"),prop.getProperty("password"));
        } catch (ClassNotFoundException ex) {
           
        	//Usiamo un'eccezione user-defined per trasportare e gestire più
            //agevolmente tutte le eccezioni lagate all'uso del database
            
        	throw new DatabaseException("Driver non trovato", ex);
        
        } catch (SQLException ex) {
           
        	throw new DatabaseException("Errore di connessione", ex);
        }
    }
    
    public static void logDatabaseException(DatabaseException e) {
        Throwable cause = e.getCause();
        System.err.println("ERRORE DATABASE: " + e.getMessage());
        if (cause != null) {
            if (cause instanceof SQLException) {
                System.err.println("* SQLState: " + ((SQLException) cause).getSQLState());
                System.err.println("* Codice errore DBMS: " + ((SQLException) cause).getErrorCode());
                System.err.println("* Messaggio errore DBMS: " + ((SQLException) cause).getMessage());
            } else {
                System.err.println("* Causa: " + cause.getMessage());
            }
        }
    }
	
}

