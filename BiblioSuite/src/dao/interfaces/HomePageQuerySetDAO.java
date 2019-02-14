package dao.interfaces;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.DatabaseException;
import vo.BookMark;
import vo.DocumentMetadata;
import vo.UUIDDocument;
import vo.UUIDDocumentCollection;
import vo.UUIDScanningWorkProject;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public interface HomePageQuerySetDAO {
	public HashMap<UUIDTranscriptionWorkProject, String[]> loadMyTranscriptionWorkProjectList(UUIDUser usr)
			throws DatabaseException ;
	public HashMap<UUIDScanningWorkProject, String[]> loadMyScanningWorkProjectList(UUIDUser usr)
			throws DatabaseException ;
	public HashMap<UUIDDocument, String[]> loadMyCollectionList(UUIDUser usr) throws DatabaseException;
	public HashMap<Integer, DocumentMetadata> loadMyCollectionMetaData(UUIDUser usr) throws DatabaseException ;
	public HashMap<Integer, BookMark> loadMyBookMarksList(UUIDUser usr) throws DatabaseException ;
	public HashMap<UUIDDocumentCollection, String> loadLibraryCollectionList()
			throws DatabaseException, SQLException ;
	public LinkedList<String[]> loadCollection(UUIDDocumentCollection idc) throws DatabaseException ;
	public LinkedList<String[]> loadLibraryCollectionListWithDocument(UUIDDocumentCollection docColl)
			throws DatabaseException, SQLException;
	public  HashMap<UUIDDocument, String> loadMyCollectionDocuments(UUIDUser usr) throws DatabaseException;
	
}
