package dao;

public class DBConnection {
	
	private static String jdbcDriver = "com.mysql.jdbc.Driver";
	private static String dbUrl = "jdbc:mysql://localhost/biblioteca";
	private static String user = "bibliotecario";
	private static String password = "libriantichi";
	
	
	public static String getJdbcDriver() {
		return jdbcDriver;
	}
	public static String getDbUrl() {
		return dbUrl;
	}
	public static String getUser() {
		return user;
	}
	public static String getPassword() {
		return password;
	}
	
	

}

