package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.DatabaseException;
import dao.EditProfileQuerySet;
import dao.TranscriptionReviserQuerySet;
import dao.TranscriptionWorkProjectQuerySet;
import model.TranscriptionWorkProject;
import model.User;
import vo.UUIDPage;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public class TranscriptionProjectController {
	
	private static TranscriptionWorkProject tPrj;
	
	private static HashMap<UUIDTranscriptionWorkProject, String[]> coordinatedTranscriptionProject;
	
	
	public static HashMap<UUIDTranscriptionWorkProject, String[]> getCoordinatedTranscriptionProject() {
		return coordinatedTranscriptionProject;
	}

	public static void setCoordinatedTranscriptionProject(
			HashMap<UUIDTranscriptionWorkProject, String[]> coordinatedTranscriptionProject) {
		TranscriptionProjectController.coordinatedTranscriptionProject = coordinatedTranscriptionProject;
	}
	
	public static void loadCoordinatedTranscriptionPRoject() {
		try{
			coordinatedTranscriptionProject = TranscriptionWorkProjectQuerySet.loadMyCoordinatedTranscriptionWorkProjectList(LocalSession.getLocalUser().getID());
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static TranscriptionWorkProject getTPrj() {
		return tPrj;
	}
	
	public static void setTPrj(TranscriptionWorkProject t_Prj) {
		tPrj = t_Prj;
	}
	
	public static void loadTranscriptionProject(UUIDTranscriptionWorkProject idTPrj) {
		try{
			tPrj = TranscriptionWorkProjectQuerySet.loadTranscriptionWorkProject(idTPrj);
		}catch(DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static User getUserProfile(UUIDUser user) {
		User u = null;
		try{
			u = EditProfileQuerySet.loadUserProfile(user);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return u;
		
	}
	
	public static void setTranscriptionComment(UUIDPage id , String comment) {
		try{
			TranscriptionReviserQuerySet.addTranscriptionRevisionComment(id, comment);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static LinkedList<UUIDUser> getAvailadbleTranscribers(UUIDTranscriptionWorkProject ids){
		try {
			return TranscriptionWorkProjectQuerySet.getAvailableDigitalizers(ids);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static LinkedList<UUIDUser> getAvailadbleRevisers(UUIDTranscriptionWorkProject ids){
		try {
			return TranscriptionWorkProjectQuerySet.getAvailableRevisers(ids);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

}
