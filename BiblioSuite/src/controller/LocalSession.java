package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import model.Document;
import model.User;
import vo.UserInformations;

public final class LocalSession {
	static User localUser;
	static ArrayList<String> topBarButtons = new ArrayList<String>();
	static Document openedDocumet = null;
	
	public static Image loadImage(String url) {
		Image image;
		try {
			image = new Image ("file:"+url);
			System.out.println(image.impl_getUrl());
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
		// TODO admin?
		/*
		 * if (permissions.admin) not show Contact Admin
		 */
		topBarButtons.add("Home");
		topBarButtons.add("My Profile");

		if (localUser.isCoordinator()) {
			topBarButtons.add("Manage Projects");
			if (localUser.isUploader())
				topBarButtons.add("Upload");
			if (localUser.isTranscriber())
				topBarButtons.add("Transcription");
			if (localUser.isUploadReviser() || localUser.isTranscriptionReviser())
				topBarButtons.add("Review");
			if (localUser.isAdmin())
				topBarButtons.add("Admin");

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