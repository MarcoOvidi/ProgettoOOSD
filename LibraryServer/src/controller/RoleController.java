package controller;

import dao.DatabaseException;
import dao.ScanningWorkProjectQuerySet;
import dao.TranscriptionWorkProjectQuerySet;
import vo.UUIDScanningWorkProject;
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
	
	
	//______
	
	public static void addDigitalizerUserInScanningProject(UUIDUser user , UUIDScanningWorkProject project) {
		try{
			ScanningWorkProjectQuerySet.insertDigitalizerUser(project, user);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}
	
	public static void addReviserUserInScanningProject(UUIDUser user , UUIDScanningWorkProject project) {
		try{
			ScanningWorkProjectQuerySet.insertReviserUser(user, project);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}
	
	public static void removeDigitalizerUserInScanningProject(UUIDUser user , UUIDScanningWorkProject project) {
		try{
			ScanningWorkProjectQuerySet.removeDigitalizerUser(user, project);
		}catch(DatabaseException e ) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}
	
	public static void removeReviserUserInScanningProject(UUIDUser user , UUIDScanningWorkProject project) {
		try{
			ScanningWorkProjectQuerySet.removeReviserUser(user, project);
		}catch(DatabaseException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
		}
	}	
}
