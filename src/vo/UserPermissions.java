package vo;

public class UserPermissions {
	//generic user
	private Boolean download;
	//uploader
	private Boolean upload;
	private Boolean editMetadata;
	//digitalizationReviser
	private Boolean reviewPage;
	//transcriber
	private Boolean modifyTranscription;
	private Boolean requestTranscriptionTask;
	//trasnscriptionReviser
	private Boolean reviewTranscription;
	//coordinator
	private Boolean addNewProject;
	private Boolean assignDigitalizationTask;
	private Boolean assignTranscriptionTask;
	private Boolean publishDocument;
}
