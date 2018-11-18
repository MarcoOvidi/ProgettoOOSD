package vo;

public class UserPermissions {
	//generic user
	private Boolean download; // like 1
	//uploader
	private Boolean upload;  //like 2
	private Boolean editMetadata; //like 3
	//digitalizationReviser
	private Boolean reviewPage;  //like 4
	//transcriber
	private Boolean modifyTranscription;  //like 5
	private Boolean requestTranscriptionTask; //like 6
	//trasnscriptionReviser
	private Boolean reviewTranscription; //like 7
	//coordinator
	private Boolean addNewProject; //like 8
	private Boolean assignDigitalizationTask; //like 9
	private Boolean assignTranscriptionTask; //like 10
	private Boolean publishDocument; //like 11
	
	//costruttore
	public UserPermissions(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f, Boolean g, Boolean h, Boolean i, Boolean l, Boolean m) {
		this.download = a;
		this.upload = b;
		this.editMetadata = c;
		this.reviewPage = d;
		this.modifyTranscription = e;
		this.requestTranscriptionTask = f;
		this.reviewTranscription = g;
		this.addNewProject = h;
		this.assignDigitalizationTask = i;
		this.assignTranscriptionTask = l;
		this.publishDocument = m;
	}
	
	public UserPermissions() {
		this.download =
		this.upload =
		this.editMetadata =
		this.reviewPage =
		this.modifyTranscription =
		this.requestTranscriptionTask =
		this.reviewTranscription =
		this.addNewProject =
		this.assignDigitalizationTask =
		this.assignTranscriptionTask =
		this.publishDocument = false; //WTF all'inizio l'avevamo messo a true. Corretta questa idea pessima. 
	}
	
	//metodi set
	public void setDownloadPerm(Boolean b) {
		this.download=b;
	}
	
	public void setUploadPerm(Boolean b) {
		this.upload=b;
	}
	
	public void setEditMetaDataPerm(Boolean b) {
		this.editMetadata=b;
	}
	
	public void setReviewPagePerm(Boolean b) {
		this.reviewPage=b;
	}
	
	public void setModifyTranscriptionPerm(Boolean b) {
		this.modifyTranscription=b;
	}
	
	public void setRequestTranscriptionTaskPerm(Boolean b) {
		this.requestTranscriptionTask=b;
	}
	
	public void setReviewTranscriptionPerm(Boolean b) {
		this.reviewTranscription=b;
	}
	
	public void setAddNewProjectPerm(Boolean b) {
		this.addNewProject=b;
	}
	
	public void setAssignDigitalizationTaskPerm(Boolean b) {
		this.assignDigitalizationTask=b;
	}
	
	public void setAssignTranscriptionTaskPerm(Boolean b) {
		this.assignTranscriptionTask=b;
	}
	
	public void setPublishDocumentPerm(Boolean b) {
		this.publishDocument=b;
	}
	 //metodi get
	
	public Boolean getDownloadPerm() {
		return this.download;
	}
	
	public Boolean getUploadPerm() {
		return this.upload;
	}
	
	public Boolean getEditMetaDataPerm() {
		return this.editMetadata;
	}
	
	public Boolean getReviewPagePerm() {
		return this.reviewPage;
	}
	
	public Boolean getModifyTranscriptionPerm() {
		return this.modifyTranscription;
	}
	
	public Boolean getRequestTranscriptionTaskPerm() {
		return this.requestTranscriptionTask;
	}
	
	public Boolean getReviewTranscriptionPerm() {
		return this.reviewTranscription;
	}
	
	public Boolean getAddNewProjectPerm() {
		return this.addNewProject;
	}
	
	public Boolean getAssignDigitalizationTaskPerm() {
		return this.assignDigitalizationTask;
	}
	
	public Boolean getAssignTranscriptionTaskPerm() {
		return this.assignTranscriptionTask;
	}
	
	public Boolean getPublishDocumentPerm() {
		return this.publishDocument;
	}
	
	public void updatePermission(Boolean[] p){
		this.download=p[1];
		this.upload=p[2];
		this.editMetadata=p[3];
		this.reviewPage=p[4];
		this.modifyTranscription=p[5];
		this.requestTranscriptionTask=p[6];
		this.reviewTranscription=p[7];
		this.addNewProject=p[8];
		this.assignDigitalizationTask=p[9];
		this.assignTranscriptionTask=p[10];
		this.publishDocument=p[11];
	}
	
	
}
