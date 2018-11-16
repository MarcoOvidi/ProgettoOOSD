package vo;

public class DocumentRow {
	private String document;
	private String transcription_PRJ;
	private String scanning_PRJ;
	
	public DocumentRow(String document, String transcription_PRJ, String scanning_PRJ) {
		super();
		this.document = document;
		this.transcription_PRJ = transcription_PRJ;
		this.scanning_PRJ = scanning_PRJ;
	}

	public DocumentRow(String document, String scanning_PRJ) {
		super();
		this.document = document;
		this.scanning_PRJ = scanning_PRJ;
		this.transcription_PRJ = null;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getTranscription_PRJ() {
		return transcription_PRJ;
	}

	public void setTranscription_PRJ(String transcription_PRJ) {
		this.transcription_PRJ = transcription_PRJ;
	}

	public String getScanning_PRJ() {
		return scanning_PRJ;
	}

	public void setScanning_PRJ(String scanning_PRJ) {
		this.scanning_PRJ = scanning_PRJ;
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
