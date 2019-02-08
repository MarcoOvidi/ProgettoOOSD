package dao.interfaces;

import java.util.HashMap;
import java.util.LinkedList;

import dao.concrete.DatabaseException;
import model.TranscriptionWorkProject;
import vo.UUIDDocument;
import vo.UUIDTranscriptionWorkProject;
import vo.UUIDUser;

public interface TranscriptionWorkProjectQuerySetDAO {
	public UUIDTranscriptionWorkProject insertTranscriptionWorkProject(LinkedList<UUIDUser> transcribers,
			LinkedList<UUIDUser> revisers, UUIDUser coordinator, Boolean completed, UUIDDocument doc)
			throws DatabaseException ;
	public TranscriptionWorkProject loadTranscriptionWorkProject(UUIDTranscriptionWorkProject id)
			throws DatabaseException, NullPointerException;
	public Boolean insertTranscriberUser(UUIDTranscriptionWorkProject ids, UUIDUser id)
			throws DatabaseException, NullPointerException;
	public Boolean insertReviserUser(UUIDUser id, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Boolean removeTranscriberUser(UUIDUser id, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Boolean removeReviserUser(UUIDUser id, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Integer insertTranscriberUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Integer insertReviserUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Integer removeTranscriberUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Integer removeReviserUser(LinkedList<UUIDUser> idl, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	public Boolean deleteTranscriptionWorkProject(UUIDTranscriptionWorkProject id)
			throws DatabaseException, NullPointerException;
	public UUIDDocument loadUUIDDocument(UUIDTranscriptionWorkProject id)
			throws DatabaseException, NullPointerException;
	public HashMap<UUIDTranscriptionWorkProject, String[]> loadMyCoordinatedTranscriptionWorkProjectList(
			UUIDUser usr) throws DatabaseException;
	public boolean ifExistTranscriptionProject(UUIDDocument doc) throws DatabaseException;
	public Boolean changeTranscriptionProjectCoordinator(UUIDUser id, UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	public LinkedList<UUIDUser> getAvailableTranscribers(UUIDTranscriptionWorkProject ids, int level)
			throws DatabaseException, NullPointerException;
	public LinkedList<UUIDUser> getAvailableRevisers(UUIDTranscriptionWorkProject ids)
			throws DatabaseException, NullPointerException;
	
	
}
