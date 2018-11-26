package controller;

import java.util.HashMap;

import dao.DatabaseException;
import dao.DigitalizationRevisorQuerySet;
import dao.EditProfileQuerySet;
import dao.ScanningWorkProjectQuerySet;
import model.ScanningWorkProject;
import model.User;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;
import vo.UUIDUser;

public class ScanningProjectController {

	private static ScanningWorkProject sPrj;
	private static HashMap<UUIDScanningWorkProject, String[]> coordinatedScanningProject;
	
	
	public static void validatePage(UUIDPage p , Boolean b) {
		try {
			DigitalizationRevisorQuerySet.validated(p, b);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void revisedPage(UUIDPage p , Boolean b) {
		try {
			DigitalizationRevisorQuerySet.revised(p, b);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static HashMap<UUIDScanningWorkProject, String[]> getCoordinatedScanningProject() {
		return coordinatedScanningProject;
	}

	public static void setCoordinatedScanningProject(
			HashMap<UUIDScanningWorkProject, String[]> coordinated_ScanningProject) {
		coordinatedScanningProject = coordinated_ScanningProject;
	}

	public static void loadCoordinatedScanningPRoject() {

		try {
			coordinatedScanningProject = ScanningWorkProjectQuerySet
					.loadMyCoordinatedScanningWorkProjectList(LocalSession.getLocalUser().getID());
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static ScanningWorkProject getSPrj() {
		return sPrj;
	}

	public static void setSPrj(ScanningWorkProject s_Prj) {
		sPrj = s_Prj;
	}

	public static void loadScanningProject(UUIDScanningWorkProject idSPrj) {
		try {
			sPrj = ScanningWorkProjectQuerySet.loadScanningWorkProject(idSPrj);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// FIXME questo non dovrebbe essere qui
	public static User getUserProfile(UUIDUser user) {
		User u = null;
		try {
			u = EditProfileQuerySet.loadUserProfile(user);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return u;

	}

}
