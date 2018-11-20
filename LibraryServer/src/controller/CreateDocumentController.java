package controller;

import java.text.ParseException;
import java.util.LinkedList;

import dao.DatabaseException;
import dao.DocumentQuerySet;
import dao.ScanningWorkProjectQuerySet;
import vo.UUIDDocument;
import vo.UUIDUser;

public class CreateDocumentController {

	// costruttore
	public static UUIDDocument createDocument(String title, String author, String description, String composition_date,
			String composition_period_from, String composition_period_to, String preservation_state) {
		UUIDDocument id = new UUIDDocument(-1);
		try {
			id = DocumentQuerySet.insertDocument(title, author, description, composition_date, composition_period_from,
					composition_period_to, preservation_state);
		} catch (DatabaseException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		attachToNewScanningWorkProject(id);

		return id;
	}

	private static void attachToNewScanningWorkProject(UUIDDocument id) {
		try {
			System.out.println("cane");
			System.out.println(ScanningWorkProjectQuerySet.insertScanningWorkProject(LocalSession.getLocalUser().getID(), false,
					new LinkedList<UUIDUser>(), new LinkedList<UUIDUser>(), id));
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
