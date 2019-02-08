package dao.interfaces;

import dao.concrete.DatabaseException;
import vo.UUIDPage;
import vo.UUIDScanningWorkProject;

public interface DigitalizationRevisorQuerySetDAO {
	public void validated(UUIDPage id, Boolean validation) throws DatabaseException;
	public void revised(UUIDPage id, Boolean validation) throws DatabaseException;
	public void scanningProcessCompleted(UUIDScanningWorkProject id) throws DatabaseException;
	public void addScanningRevisionComment(UUIDPage id, String comment) throws DatabaseException;
	public String getScanningRevisionComment(UUIDPage id) throws DatabaseException ;	
}
