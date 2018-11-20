package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import model.Document;
import model.User;
import vo.UserInformations;
import vo.UserPermissions;

public final class LocalSession {
	static User localUser;
	static ArrayList<String> topBarButtons = new ArrayList<String>();
	static Document openedDocumet = null;
	
	public static Image loadImage(String url) {
		Image image;
		try {
			image = new Image (url);
		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage() + "\n" + url + "\nCheck you database, motherfucker");
			alert.show();
			e.printStackTrace();
			
			return image = new Image ("images/missing.png");
		}
		
		return image;
		
	}
	
	public static Document getOpenedDocumet() {
		return openedDocumet;
	}

	public static void setOpenedDocumet(Document openedDocumet) {
		LocalSession.openedDocumet = openedDocumet;
	}

	// TODO error handling
	private static LocalSession instance = new LocalSession();

	private LocalSession() {

	}

	public static final LocalSession getInstance() {
		return instance;
	}

	public static User getLocalUser() {
		return localUser;
	}
	
	public static UserInformations getLocalUserInfo() {
		return localUser.getInformations();
	}

	private static void loadTopBarButtons() {
		UserPermissions permissions = localUser.getPermissions();
		// TODO admin?
		/*
		 * if (permissions.admin) not show Contact Admin
		 */
		topBarButtons.add("Home");
		topBarButtons.add("My Profile");

		if (permissions.getPublishDocumentPerm() || permissions.getAssignDigitalizationTaskPerm()
				|| permissions.getAssignTranscriptionTaskPerm() || permissions.getEditMetaDataPerm()) {
			topBarButtons.add("Manage Projects");
			if (permissions.getUploadPerm())
				topBarButtons.add("Upload");
			if (permissions.getModifyTranscriptionPerm())
				topBarButtons.add("Transcription");
			if (permissions.getReviewPagePerm() || permissions.getReviewPagePerm())
				topBarButtons.add("Review");

		}
	}

	public static void setLocalUser(User lUser) {
		localUser = lUser;
		loadTopBarButtons();
	}

	public static List<String> getTopBarButtons() {
		return topBarButtons;
	}

	public static void clear() {
		localUser = null;
		topBarButtons = new ArrayList<String>();
	}
}