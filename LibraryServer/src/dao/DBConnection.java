package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static void main(String[] args) {
		getConnection();
	}
	
	public static Connection getConnection() {
	   
    	Connection connection = null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    		return connection;
    	}

    	try {
    		connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/biblioteca?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","bibliotecario", "libriantichi");

    	} catch (SQLException e) {
    		return connection;
    	}

		return connection;
		
	}

}

