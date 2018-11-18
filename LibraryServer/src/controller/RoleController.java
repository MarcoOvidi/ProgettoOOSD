package controller;

import java.util.HashMap;

import dao.AdministratorQuerySet;
import dao.DatabaseException;
import dao.ScanningWorkProjectQuerySet;
import dao.TranscriptionWorkProjectQuerySet;
import dao.UserAuthenticationQuerySet;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public class RoleController {

	public void sendRequestForm() {

	}

	public static boolean controlUserPermission(Integer permNumber, UUIDUser userID) {
		Boolean b = false;
		try {
			b = UserAuthenticationQuerySet.permissionsControl(permNumber, userID);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return b;
	}

	public static void addTranscriberUserInTrascriptionProject(UUIDUser user, UUIDTranscriptionWorkProject project) {
		try {
			TranscriptionWorkProjectQuerySet.insertTranscriberUser(project, user);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void addReviserUserInTrascriptionProject(UUIDUser user, UUIDTranscriptionWorkProject project) {
		try {
			TranscriptionWorkProjectQuerySet.insertReviserUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void removeTranscriberUserInTrascriptionProject(UUIDUser user, UUIDTranscriptionWorkProject project) {
		try {
			TranscriptionWorkProjectQuerySet.removeTranscriberUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void removeReviserUserInTrascriptionProject(UUIDUser user, UUIDTranscriptionWorkProject project) {
		try {
			TranscriptionWorkProjectQuerySet.removeReviserUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void addDigitalizerUserInScanningProject(UUIDUser user, UUIDScanningWorkProject project) {
		try {
			ScanningWorkProjectQuerySet.insertDigitalizerUser(project, user);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void addReviserUserInScanningProject(UUIDUser user, UUIDScanningWorkProject project) {
		try {
			ScanningWorkProjectQuerySet.insertReviserUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void removeDigitalizerUserInScanningProject(UUIDUser user, UUIDScanningWorkProject project) {
		try {
			ScanningWorkProjectQuerySet.removeDigitalizerUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void removeReviserUserInScanningProject(UUIDUser user, UUIDScanningWorkProject project) {
		try {
			ScanningWorkProjectQuerySet.removeReviserUser(user, project);
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}

	public static void changeTransriptionProjectCoordinator(UUIDUser newCoord, UUIDTranscriptionWorkProject idPrj) {
		try {
			TranscriptionWorkProjectQuerySet.changeTranscriptionProjectCoordinator(newCoord, idPrj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void changeScanningProjectCoordinator(UUIDUser newCoord, UUIDScanningWorkProject idPrj) {
		try {
			ScanningWorkProjectQuerySet.changeScanningProjectCoordinator(newCoord, idPrj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static HashMap<UUIDUser, String> showCoordinatorUsers() {
		HashMap<UUIDUser,String> coord = new HashMap<UUIDUser , String>();
		
		try{
			coord = AdministratorQuerySet.showCoordinatorList();
		}catch(Exception e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return coord;
	}
}
