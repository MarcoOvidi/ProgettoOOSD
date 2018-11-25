package vo;

public class DocumentRow {
	private String document;
	private String transcription_PRJ;
	private String scanning_PRJ;
	private UUIDTranscriptionWorkProject idTPrj;
	private UUIDScanningWorkProject idSPrj;
	private UUIDDocument id;
	
	public UUIDDocument getId() {
		return id;
	}


	public void setId(UUIDDocument id) {
		this.id = id;
	}


	public DocumentRow(String title, UUIDDocument id) {
		this.document=title;
		this.id=id;
	}

	
	public DocumentRow(String document, String transcription_PRJ, String scanning_PRJ,
			UUIDTranscriptionWorkProject idTPrj, UUIDScanningWorkProject idSPrj) {
		this.document = document;
		this.transcription_PRJ = transcription_PRJ;
		this.scanning_PRJ = scanning_PRJ;
		this.idTPrj = idTPrj;
		this.idSPrj = idSPrj;
	}

	public DocumentRow(String document, String scanning_PRJ, UUIDScanningWorkProject idSPrj) {
		this.document = document;
		this.scanning_PRJ = scanning_PRJ;
		this.transcription_PRJ = "";
		this.idSPrj = idSPrj;
	}
	
	public DocumentRow(String document,String transcription_prj, UUIDTranscriptionWorkProject idTPrj) {
		this.document = document;
		this.scanning_PRJ = "";
		this.transcription_PRJ = transcription_prj;
		this.idTPrj = idTPrj;
	}


		
	
	public UUIDTranscriptionWorkProject getIdTPrj() {
		return idTPrj;
	}

	public void setIdTPrj(UUIDTranscriptionWorkProject idTPrj) {
		this.idTPrj = idTPrj;
	}

	public UUIDScanningWorkProject getIdSPrj() {
		return idSPrj;
	}

	public void setIdSPrj(UUIDScanningWorkProject idSPrj) {
		this.idSPrj = idSPrj;
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

	
	@Override
	public String toString() {
		return "DocumentRow [document=" + document + ", transcription_PRJ=" + transcription_PRJ + ", scanning_PRJ="
				+ scanning_PRJ + ", idTPrj=" + idTPrj + ", idSPrj=" + idSPrj + "]";
	}

	

}
