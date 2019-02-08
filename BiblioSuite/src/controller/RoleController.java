package controller;

import java.util.HashMap;

import dao.concrete.AdministrationQuerySet;
import dao.concrete.DatabaseException;
import dao.concrete.ScanningWorkProjectQuerySet;
import dao.concrete.TranscriptionWorkProjectQuerySet;
import dao.concrete.UserAuthenticationQuerySet;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;
import vo.UserPermissions;

public class RoleController {

	public void sendRequestForm() {

	}

	public static boolean controlUserPermission(Integer permNumber, UUIDUser userID) {
		Boolean b = false;
		try {
			b = new UserAuthenticationQuerySet().permissionsControl(permNumber, userID);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return b;
	}

	public static void addTranscriberUserInTrascriptionProject(UUIDUser user, UUIDTranscriptionWorkProject project) {
		try {
			new TranscriptionWorkProjectQuerySet().insertTranscriberUser(project, user);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void addReviserUserInTrascriptionProject(UUIDUser user, UUIDTranscriptionWorkProject project) {
		try {
			new TranscriptionWorkProjectQuerySet().insertReviserUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void removeTranscriberUserInTrascriptionProject(UUIDUser user, UUIDTranscriptionWorkProject project) {
		try {
			new TranscriptionWorkProjectQuerySet().removeTranscriberUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void removeReviserUserInTrascriptionProject(UUIDUser user, UUIDTranscriptionWorkProject project) {
		try {
			new TranscriptionWorkProjectQuerySet().removeReviserUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void addDigitalizerUserInScanningProject(UUIDUser user, UUIDScanningWorkProject project) {
		try {
			new ScanningWorkProjectQuerySet().insertDigitalizerUser(project, user);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void addReviserUserInScanningProject(UUIDUser user, UUIDScanningWorkProject project) {
		try {
			new ScanningWorkProjectQuerySet().insertReviserUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void removeDigitalizerUserInScanningProject(UUIDUser user, UUIDScanningWorkProject project) {
		try {
			new ScanningWorkProjectQuerySet().removeDigitalizerUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void removeReviserUserInScanningProject(UUIDUser user, UUIDScanningWorkProject project) {
		try {
			new ScanningWorkProjectQuerySet().removeReviserUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void changeTransriptionProjectCoordinator(UUIDUser newCoord, UUIDTranscriptionWorkProject idPrj) {
		try {
			new TranscriptionWorkProjectQuerySet().changeTranscriptionProjectCoordinator(newCoord, idPrj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void changeScanningProjectCoordinator(UUIDUser newCoord, UUIDScanningWorkProject idPrj) {
		try {
			new ScanningWorkProjectQuerySet().changeScanningProjectCoordinator(newCoord, idPrj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static HashMap<UUIDUser, String> showCoordinatorUsers() {
		HashMap<UUIDUser,String> coord = new HashMap<UUIDUser , String>();
		
		try{
			coord = new AdministrationQuerySet().showCoordinatorList();
		}catch(Exception e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return coord;
	}
	
	public static UserPermissions getUserPermission(UUIDUser id) {
		try {
			return new UserAuthenticationQuerySet().getUSerPermission(id);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static void replaceCoordinator(UUIDUser oldCoordinator , UUIDUser newCoordinator) {
		try {
			new AdministrationQuerySet().replaceCoordinator(oldCoordinator, newCoordinator);
		}catch(DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static void removeUserFromAllProjects(UUIDUser id) {
		try {
			new AdministrationQuerySet().removeUserFromAllProjects(id);
		}catch(DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
