package controller;

import dao.DatabaseException;
import dao.EditProfileQuerySet;
import model.User;
import vo.UUIDUser;

public class EditUserController {
	private static UUIDUser editingUser;
	private static User toEditUser;
	
	public EditUserController(UUIDUser editingUser, User toEditUser) {
		EditUserController.setEditingUser(editingUser);
		EditUserController.setToEditUser(toEditUser);
	}
	
	public static UUIDUser getEditingUser() {
		return editingUser;
	}
		
	public static User getToEditUser() {
		return toEditUser;
	}
	
	public static boolean canEdit () {
		if(getEditingUser().equals(getToEditUser().getID()))
			return true;
		return false;
	}
	
	public static boolean canEditPermissions () {
		//TODO implementare amministratore
		if (editingUser.equals(toEditUser.getID()))
			return false;
		/*if (editingUser.isAdmin())
			return true;*/
		return false;
	}
	
	public static boolean setModifications(User editedUser) {
		if(!editedUser.getID().equals(getToEditUser().getID()))
			return false;
		else {
			return true;
		}
	}
	
	public static void commitModifications() {
		try {
			EditProfileQuerySet.updateUserProfile(getToEditUser().getID(), getToEditUser());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setEditingUser(UUIDUser editingUser) {
		EditUserController.editingUser = editingUser;
	}

	public static void setToEditUser(User toEditUser) {
		EditUserController.toEditUser = toEditUser;
	}
}
