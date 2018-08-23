package dao;

import vo.BookMark;
import vo.Image;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDRequest;
import vo.UUIDUser;
import vo.UserPermissions;

public class prova {

	public static void main(String[] args) {
		//BookMark bm = new BookMark(new UUIDUser(1), new UUIDDocument(1), new UUIDPage(1));
		
		try{
			
			DigitalizerQuerySet.updatePage(new UUIDPage(1), new Image("http:jfkrjfkrjfkrj"));
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

}
