package controller;

import dao.DatabaseException;
import dao.TranscriptionWorkProjectQuerySet;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public class RoleController {

	public void sendRequestForm() {
		
	}
	
	public static void addTranscriberUserInTrascriptionProject(UUIDUser user , UUIDTranscriptionWorkProject project) {
		try{
			TranscriptionWorkProjectQuerySet.insertTranscriberUser(project, user);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}
	
	public static void addReviserUserInTrascriptionProject(UUIDUser user , UUIDTranscriptionWorkProject project) {
		try{
			TranscriptionWorkProjectQuerySet.insertReviserUser(user, project);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}
	
	public static void removeTranscriberUserInTrascriptionProject(UUIDUser user , UUIDTranscriptionWorkProject project) {
		try{
			TranscriptionWorkProjectQuerySet.removeTranscriberUser(user, project);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}
	
	public static void removeReviserUserInTrascriptionProject(UUIDUser user , UUIDTranscriptionWorkProject project) {
		try{
			TranscriptionWorkProjectQuerySet.removeReviserUser(user, project);
		}catch(DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}	
}
