package controller;

import java.text.ParseException;

import dao.concrete.DatabaseException;
import dao.concrete.DocumentQuerySet;
import vo.UUIDDocument;

public class CreateDocumentController {

	// costruttore
	public static UUIDDocument createDocument(String title, String author, String description, String composition_date,
			String composition_period_from, String composition_period_to, String preservation_state) {
		UUIDDocument id = null;

		try {
			id = new DocumentQuerySet().insertDocument(title, author, description, composition_date, composition_period_from,
					composition_period_to, preservation_state);
		} catch (DatabaseException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*
		if (id != null) {
			attachToNewScanningWorkProject(id);
		}*/
		
		return id;
	}
/*
	private static void attachToNewScanningWorkProject(UUIDDocument id) {
		try {
			System.out.println("cane");
			System.out
					.println(ScanningWorkProjectQuerySet.insertScanningWorkProject(LocalSession.getLocalUser().getID(),
							false, new LinkedList<UUIDUser>(), new LinkedList<UUIDUser>(), id));

		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

}
