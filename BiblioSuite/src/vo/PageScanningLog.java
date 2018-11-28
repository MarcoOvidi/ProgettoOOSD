package vo;

public class PageScanningLog {
	private String pageNumber;
	private String scanningReviser;
	private String scanningRevised;
	private String scanningValidated;
	private String scanningDigitalizer;
	
	public PageScanningLog(String pageNumber, String scanningReviser, String scanningRevised,
			String scanningValidated, String scanningDigitalizer) {
		super();
		this.pageNumber = pageNumber;
		this.scanningReviser = scanningReviser;
		this.scanningRevised = scanningRevised;
		this.scanningValidated = scanningValidated;
		this.scanningDigitalizer = scanningDigitalizer;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getScanningReviser() {
		return scanningReviser;
	}

	public void setScanningReviser(String scanningReviser) {
		this.scanningReviser = scanningReviser;
	}

	public String getScanningRevised() {
		return scanningRevised;
	}

	public void setScanningRevised(String scanningRevised) {
		this.scanningRevised = scanningRevised;
	}

	public String getScanningValidated() {
		return scanningValidated;
	}

	public void setScanningValidated(String scanningValidated) {
		this.scanningValidated = scanningValidated;
	}

	public String getScanningDigitalizer() {
		return scanningDigitalizer;
	}

	public void setScanningTranscriber(String scanningDigitalizer) {
		this.scanningDigitalizer = scanningDigitalizer;
	}
	
	
	
}
