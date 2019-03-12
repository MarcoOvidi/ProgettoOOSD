package controller;

import dao.concrete.AdministrationQuerySet;
import dao.concrete.DatabaseException;
import dao.concrete.EditProfileQuerySet;
import dao.concrete.UserAuthenticationQuerySet;
import fx_view.controller.SceneController;
import model.User;
import vo.UUIDUser;

public class EditUserController {
	private static UUIDUser editingUser;
	private static User toEditUser;

	public static UUIDUser getEditingUser() {
		return editingUser;
	}

	public static User getToEditUser() {
		return toEditUser;
	}

	public static boolean canEdit() {
		if (getEditingUser().equals(getToEditUser().getID()))
			return true;
		try {
			if (new UserAuthenticationQuerySet().permissionsControl(12, editingUser))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean canEditPermissions() {
		// TODO implementare amministratore
		if (editingUser.equals(toEditUser.getID()))
			return false;
		try {
			if (new UserAuthenticationQuerySet().permissionsControl(12, editingUser))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean setModifications(User editedUser) {
		if (editedUser.getID().equals(getToEditUser().getID())) {
			toEditUser = editedUser;
			return false;
		} else
			return true;
	}

	public static void commitModifications() {
		try {

			// FIXME il metodo updateUserProfile non aggiorna i permessi
			// per ora aggiungo qui la chiamata all'altro ma forse dovrebbe farlo il metodo
			// del dao?
			new EditProfileQuerySet().updateUserProfile(getToEditUser().getID(), getToEditUser());
			new AdministrationQuerySet().updateUserPermissions(getToEditUser().getID(), getToEditUser().getPermissions());

			//System.out.println(getToEditUser().getID());
			//System.out.println(getToEditUser().getLevel());
			AdministrationController.changeTranscriberLevel(getToEditUser().getID(), getToEditUser().getLevel().getValue());
			
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

	public static void setToEditUser(UUIDUser toEditUser) {
		try {
			EditUserController.toEditUser = new EditProfileQuerySet().loadUserProfile(toEditUser);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void startEditing() {
		SceneController.loadScene("editUserProfile");
	}
	
	public static boolean isSelfEditing() {
		if (editingUser == null || toEditUser == null)
			return false;
		else {
			return (editingUser.equals(toEditUser.getID()));
		}
	}
}
