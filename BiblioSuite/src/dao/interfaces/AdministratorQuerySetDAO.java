package dao.interfaces;


import java.util.List;
import java.util.Map;

import dao.concrete.DatabaseException;
import model.User;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.UserPermissions;

public interface AdministratorQuerySetDAO {
	public Boolean modifyUserStatus(UUIDUser user, Boolean status) throws DatabaseException;
	public Boolean updateUserPermissions(UUIDUser user, UserPermissions up) throws DatabaseException;
	public List<Request> loadRequestsList(int b)throws DatabaseException;
	public Request loadRequest(UUIDRequest id)throws DatabaseException;
	public Boolean answerRequest(UUIDRequest id, String answer)throws DatabaseException;
	public Boolean ignoreRequest(UUIDRequest id)throws DatabaseException;
	public List<User> loadUserList(Boolean b)throws DatabaseException;
	public Map<UUIDUser, String> showCoordinatorList()throws DatabaseException;
	public Map<String, Integer> userIsInvolved(UUIDUser id)throws DatabaseException;
	public void replaceCoordinator(UUIDUser oldCoordinator, UUIDUser newCoordinator)throws DatabaseException;
	public void removeUserFromAllProjects(UUIDUser id)throws DatabaseException;
	public void changeTranscriberLevel(UUIDUser id, int level)throws DatabaseException;
	public int getTranscriberLevel(UUIDUser id)throws DatabaseException;	
}
