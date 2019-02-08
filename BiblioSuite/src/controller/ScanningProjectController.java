package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.DatabaseException;
import dao.concrete.DigitalizationRevisorQuerySet;
import dao.concrete.EditProfileQuerySet;
import dao.concrete.ScanningWorkProjectQuerySet;
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
			new DigitalizationRevisorQuerySet().validated(p, b);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void revisedPage(UUIDPage p , Boolean b) {
		try {
			new DigitalizationRevisorQuerySet().revised(p, b);
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
			coordinatedScanningProject = new ScanningWorkProjectQuerySet()
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
			sPrj = new ScanningWorkProjectQuerySet().loadScanningWorkProject(idSPrj);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void setScanningComment(UUIDPage id , String comment) {
		try{
			new DigitalizationRevisorQuerySet().addScanningRevisionComment(id, comment);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	// FIXME questo non dovrebbe essere qui
	public static User getUserProfile(UUIDUser user) {
		User u = null;
		try {
			u = new EditProfileQuerySet().loadUserProfile(user);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return u;

	}
	
	public static LinkedList<UUIDUser> getAvailadbleDigitalizers(UUIDScanningWorkProject ids){
		try {
			return new ScanningWorkProjectQuerySet().getAvailableDigitalizers(ids);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static LinkedList<UUIDUser> getAvailadbleRevisers(UUIDScanningWorkProject ids){
		try {
			return new ScanningWorkProjectQuerySet().getAvailableRevisers(ids);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public String getDigitalizationComment(UUIDPage id) {
		try {
			return new DigitalizationRevisorQuerySet().getScanningRevisionComment(id);
		}catch(DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "No Comment available";
		}
	}
	
	public static boolean isCompleted() {
		return sPrj.getCompleted();
	}

}
