package dao.interfaces;

import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.DatabaseException;
import model.ScanningWorkProject;
import vo.UUIDDocument;
import vo.UUIDScanningWorkProject;
import vo.UUIDUser;

public interface ScanningWorkProjectQuerySetDAO {
	public UUIDScanningWorkProject insertScanningWorkProject(UUIDUser coordinator, Boolean completed,
			LinkedList<UUIDUser> digitalizers, LinkedList<UUIDUser> revisers, UUIDDocument ID_doc)
			throws DatabaseException ;
	public ScanningWorkProject loadScanningWorkProject(UUIDScanningWorkProject id)
			throws DatabaseException, NullPointerException ;
	public UUIDDocument loadUUIDDocument(UUIDScanningWorkProject id)
			throws DatabaseException, NullPointerException ;
	public Boolean insertDigitalizerUser(UUIDScanningWorkProject ids, UUIDUser id)
			throws DatabaseException, NullPointerException ;
	public Boolean insertReviserUser(UUIDUser id, UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException ;
	public Boolean removeDigitalizerUser(UUIDUser id, UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException ;
	public Boolean removeReviserUser(UUIDUser id, UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Integer insertDigitalizerUser(LinkedList<UUIDUser> idl, UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Integer insertReviserUser(LinkedList<UUIDUser> idl, UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Integer removeDigitalizerUser(LinkedList<UUIDUser> idl, UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException ;
	public Integer removeReviserUser(LinkedList<UUIDUser> idl, UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException ;
	public Boolean deleteScanningWorkProject(UUIDScanningWorkProject id)
			throws DatabaseException, NullPointerException;
	public HashMap<UUIDDocument, String> getScanningUncompletedDocumentDigitalizer(UUIDUser id) throws DatabaseException;
	public HashMap<UUIDDocument, String> getScanningUncompletedDocumentReviser(UUIDUser id) throws DatabaseException;
	public HashMap<UUIDScanningWorkProject, String[]> loadMyCoordinatedScanningWorkProjectList(UUIDUser usr)
			throws DatabaseException;
	public boolean ifExistScanningProject(UUIDDocument doc) throws DatabaseException;
	public Boolean changeScanningProjectCoordinator(UUIDUser id, UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException ;
	public LinkedList<UUIDUser> getAvailableDigitalizers(UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException;
	public LinkedList<UUIDUser> getAvailableRevisers(UUIDScanningWorkProject ids)
			throws DatabaseException, NullPointerException;
			
}
