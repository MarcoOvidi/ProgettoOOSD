package controller;

import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.DatabaseException;
import dao.concrete.EditProfileQuerySet;
import dao.concrete.TranscriptionReviserQuerySet;
import dao.concrete.TranscriptionWorkProjectQuerySet;
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
			coordinatedTranscriptionProject = new TranscriptionWorkProjectQuerySet().loadMyCoordinatedTranscriptionWorkProjectList(LocalSessionBridge.getLocalUser().getID());
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
			tPrj = new TranscriptionWorkProjectQuerySet().loadTranscriptionWorkProject(idTPrj);
		}catch(DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static User getUserProfile(UUIDUser user) {
		User u = null;
		try{
			u = new EditProfileQuerySet().loadUserProfile(user);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return u;
		
	}
	
	public static void setTranscriptionComment(UUIDPage id , String comment) {
		try{
			new TranscriptionReviserQuerySet().addTranscriptionRevisionComment(id, comment);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static LinkedList<UUIDUser> getAvailableTranscribers(UUIDTranscriptionWorkProject ids , int level){
		try {
			return new TranscriptionWorkProjectQuerySet().getAvailableTranscribers(ids,level);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static LinkedList<UUIDUser> getAvailableRevisers(UUIDTranscriptionWorkProject ids){
		try {
			return new TranscriptionWorkProjectQuerySet().getAvailableRevisers(ids);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public String getTranscriptionComment(UUIDPage id) {
		try {
			return new TranscriptionReviserQuerySet().getTranscriptionRevisionComment(id);
		}catch(DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "No Comment available";
		}
	}

	public static boolean isCompleted() {
		return tPrj.getCompleted();
	}
	
}
