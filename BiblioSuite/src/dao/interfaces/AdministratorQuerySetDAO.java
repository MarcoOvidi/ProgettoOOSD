package dao.interfaces;


import java.util.List;
import java.util.Map;

import dao.DatabaseException;
import model.User;
import vo.Request;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.UserPermissions;

public interface AdministratorQuerySetDAO {
	public Boolean modifyUserStatus(UUIDUser user, Boolean status) throws DatabaseException;
	public Boolean updateUserPermissions(UUIDUser user, UserPermissions up);
	public List<Request> loadRequestsList(int b);
	public Request loadRequest(UUIDRequest id);
	public Boolean answerRequest(UUIDRequest id, String answer);
	public Boolean ignoreRequest(UUIDRequest id);
	public List<User> loadUserList(Boolean b);
	public Map<UUIDUser, String> showCoordinatorList();
	public Map<String, Integer> userIsInvolved(UUIDUser id);
	public void replaceCoordinator(UUIDUser oldCoordinator, UUIDUser newCoordinator);
	public void removeUserFromAllProjects(UUIDUser id);
	public void changeTranscriberLevel(UUIDUser id, int level);
	public int getTranscriberLevel(UUIDUser id);	
}
