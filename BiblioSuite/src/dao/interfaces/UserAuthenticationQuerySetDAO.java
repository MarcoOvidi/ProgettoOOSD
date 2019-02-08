package dao.interfaces;

import java.sql.SQLException;

import dao.concrete.DatabaseException;
import vo.UUIDUser;
import vo.UserPermissions;

public interface UserAuthenticationQuerySetDAO {
	public boolean registration(String username , String password, String name, 
			String surname, String email) throws DatabaseException;
	public boolean checkIfUsernameExists(String username) throws SQLException, DatabaseException;
	public UUIDUser login(String usr, String psw) throws SQLException, DatabaseException;
	public UserPermissions getUSerPermission(UUIDUser usr) throws DatabaseException;
	public boolean permissionsControl(Integer permNumb , UUIDUser id) throws DatabaseException, Exception;
	
}
