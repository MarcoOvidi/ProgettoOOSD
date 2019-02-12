package dao.interfaces;

import dao.concrete.DatabaseException;
import vo.UUIDPage;
import vo.UUIDTranscriptionWorkProject;

public interface TranscriptionReviserQuerySetDAO {
	public Boolean completed(UUIDTranscriptionWorkProject id) throws DatabaseException;
	public Boolean validated(UUIDPage id, boolean b) throws DatabaseException;
	public Boolean revised(UUIDPage id, boolean b) throws DatabaseException;
	public void addTranscriptionRevisionComment(UUIDPage id, String comment) throws DatabaseException;
	public String getTranscriptionRevisionComment(UUIDPage id) throws DatabaseException ;
	
}
