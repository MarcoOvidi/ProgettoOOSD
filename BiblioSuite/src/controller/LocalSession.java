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

	private static LocalSession instance = new LocalSession();
	
	private LocalSession() {
		
	}
	
	public static final LocalSession getInstance() {
		return instance;
	}
	
	
	@SuppressWarnings("deprecation")
	public Image loadImage(String url) {
		Image image;

		if (url == null)
			return image = new Image("images/missing.png");

		try {
			image = new Image("file:" + url);
			System.out.println(image.impl_getUrl());
		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage() + "\n" + url + "\nCheck you database, motherfucker");
			alert.show();
			e.printStackTrace();

			return image = new Image("images/missing.png");
		}

		return image;

	}

	public Document getOpenedDocumet() {
		return openedDocumet;
	}

	public void setOpenedDocumet(Document openedDocumet) {
		LocalSession.openedDocumet = openedDocumet;
	}

	public User getLocalUser() {
		return localUser;
	}

	public UserInformations getLocalUserInfo() {
		return localUser.getInformations();
	}

	private void loadTopBarButtons() {
		topBarButtons.add("Home");
		topBarButtons.add("My Profile");
		
		if (localUser.isAdmin())
			topBarButtons.add("Contact Admin");
		if (localUser.isCoordinator())
			topBarButtons.add("Manage Projects");
		if (localUser.isUploader())
			topBarButtons.add("Upload");
		if (localUser.isTranscriber())
			topBarButtons.add("Transcription");
		if (localUser.isUploadReviser())
			topBarButtons.add("Review");
		if (localUser.isTranscriptionReviser())
			topBarButtons.add("Transcription Review");
		if (localUser.isAdmin())
			topBarButtons.add("Admin");
	}

	public void setLocalUser(User lUser) {
		localUser = lUser;
		loadTopBarButtons();
	}

	public List<String> getTopBarButtons() {
		return topBarButtons;
	}

	public void clear() {
		localUser = null;
		topBarButtons = new ArrayList<String>();
	}
}