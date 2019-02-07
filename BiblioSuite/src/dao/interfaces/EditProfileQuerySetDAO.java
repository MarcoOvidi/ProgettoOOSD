package dao.interfaces;

import dao.DatabaseException;
import model.User;
import vo.UUIDUser;

public interface EditProfileQuerySetDAO {
	public User loadUserProfile(UUIDUser user) throws DatabaseException;
	public boolean updateUserProfile(UUIDUser toChange,User toUpdate) throws DatabaseException ;
	
}
