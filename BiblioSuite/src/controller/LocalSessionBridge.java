package controller;

import java.util.List;

import javafx.scene.image.Image;
import model.Document;
import model.User;
import vo.UserInformations;

public final class LocalSessionBridge {
	static LocalSession localSession = LocalSession.getInstance();
	
	
	private LocalSessionBridge() {}
	

	public static Image loadImage(String url) {
		return localSession.loadImage(url);
	}

	public static Document getOpenedDocumet() {
		return localSession.getOpenedDocumet();
	}

	public static void setOpenedDocumet(Document openedDocumet) {
		localSession.setOpenedDocumet(openedDocumet);
	}

	public static User getLocalUser() {
		return localSession.getLocalUser();
	}

	public static UserInformations getLocalUserInfo() {
		return localSession.getLocalUserInfo();
	}

	public static void setLocalUser(User lUser) {
		localSession.setLocalUser(lUser);
	}

	public static List<String> getTopBarButtons() {
		return localSession.getTopBarButtons();
	}

	public static void clear() {
		localSession.clear();
	}
}