package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import model.Document;
import model.Page;
import model.PageScan;
import model.PageScanStaff;
import model.PageTranscription;
import model.PageTranscriptionStaff;
import vo.DocumentMetadata;
import vo.Image;
import vo.TEItext;
import vo.UUIDDocument;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;
import vo.VagueDate;

public class DigitalizerQuerySet { //DA COMPLETARE
	
	//FIXME metodo troppo grande e ridondtante perchè già presente in DocumentQuerySet , secondo me il digitalizer ha bisogno solo delle pagges ed in particolare di vedere o meno se le immagini sono scannerizzate e/o validate 
	public Document loadDocument(UUIDDocument id){
	return null;
	}
	
	
	public void updatePage(UUIDPage id,Image img) {
		
	}








}
