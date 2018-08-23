package dao;

import vo.BookMark;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.UserPermissions;

public class prova {

	public static void main(String[] args) {
		//BookMark bm = new BookMark(new UUIDUser(1), new UUIDDocument(1), new UUIDPage(1));
		
		try{
			
			System.out.println(AdministratorQuerySet.answerRequest(new UUIDRequest(1), "vaffanculoooo"));
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

}
