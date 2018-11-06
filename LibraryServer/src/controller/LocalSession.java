package controller;

import model.User;

public final class LocalSession {
static
User localUser;
	
//TODO error handling
private static LocalSession instance = new LocalSession();

private LocalSession() {		
}

public static final LocalSession getInstance() {
	return instance;
}

public static User getLocalUser() {
	return localUser;
}

public static void setLocalUser(User lUser) {
	localUser = lUser;
}


}