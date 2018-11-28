package vo;

public class PageTranscriptionLog {
	private String pageNumber;
	private String transcriptionReviser;
	private String transcriptionRevised;
	private String transcriptionValidated;
	private String transcriptionTranscriber;
	
	public PageTranscriptionLog(String pageNumber, String transcriptionReviser, String transcriptionRevised,
			String transcriptionValidated, String transcriptionTranscriber) {
		super();
		this.pageNumber = pageNumber;
		this.transcriptionReviser = transcriptionReviser;
		this.transcriptionRevised = transcriptionRevised;
		this.transcriptionValidated = transcriptionValidated;
		this.transcriptionTranscriber = transcriptionTranscriber;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getTranscriptionReviser() {
		return transcriptionReviser;
	}

	public void setTranscriptionReviser(String transcriptionReviser) {
		this.transcriptionReviser = transcriptionReviser;
	}

	public String getTranscriptionRevised() {
		return transcriptionRevised;
	}

	public void setTranscriptionRevised(String transcriptionRevised) {
		this.transcriptionRevised = transcriptionRevised;
	}

	public String getTranscriptionValidated() {
		return transcriptionValidated;
	}

	public void setTranscriptionValidated(String transcriptionValidated) {
		this.transcriptionValidated = transcriptionValidated;
	}

	public String getTranscriptionTranscriber() {
		return transcriptionTranscriber;
	}

	public void setTranscriptionTranscriber(String transcriptionTranscriber) {
		this.transcriptionTranscriber = transcriptionTranscriber;
	}
	
	
	
	
}
