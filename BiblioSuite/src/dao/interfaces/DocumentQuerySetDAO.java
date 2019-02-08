package dao.interfaces;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import dao.concrete.DatabaseException;
import javafx.fxml.LoadException;
import model.Document;
import vo.Tag;
import vo.UUIDDocument;
import vo.UUIDDocumentMetadata;

public interface DocumentQuerySetDAO {
	public UUIDDocument insertDocument(String title, String author, String description, String composition_date,
			String composition_period_from, String composition_period_to, String preservation_state)
			throws DatabaseException, ParseException;
	public Document loadDocument(UUIDDocument id) throws DatabaseException, LoadException ;
	public Document loadDocumentToView(UUIDDocument id) throws DatabaseException, LoadException;
	public HashMap<Integer, Tag> loadAvailableTag() throws DatabaseException;
	public void addTagToDocument(UUIDDocumentMetadata id, HashMap<Integer, Tag> tags) throws DatabaseException;
	public UUIDDocumentMetadata getDocumentMetadataOfDocument(UUIDDocument id) throws DatabaseException ;
	public Integer insertNewTag(Tag t) throws DatabaseException ;
	public ArrayList<Tag> loadDocumentTags(UUIDDocument document)
			throws DatabaseException;
	public String getDocumentTitle(UUIDDocument id) throws DatabaseException;
	public HashMap<UUIDDocument, String[]> getAllDocuments() throws DatabaseException;
	
}
