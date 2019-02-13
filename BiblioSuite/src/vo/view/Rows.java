package vo.view;

import vo.TEItext;
import vo.UUIDPage;
import vo.UUIDUser;

public class Rows {
	private javafx.scene.image.Image image;
	private TEItext transcription;
	private String number;
	private String revisioned;
	private String validated;
	private UUIDPage id;
	private UUIDUser transcriber;
	private UUIDUser digitalizer;
	
	
	public TEItext getTranscription() {
		return transcription;
	}

	public void setTranscription(TEItext transcription) {
		this.transcription = transcription;
	}

	public UUIDUser getTranscriber() {
		return transcriber;
	}

	public void setTranscriber(UUIDUser transcriber) {
		this.transcriber = transcriber;
	}

	public UUIDUser getDigitalizer() {
		return digitalizer;
	}

	public void setDigitalizer(UUIDUser digitalizer) {
		this.digitalizer = digitalizer;
	}

	
	public Rows(String n, UUIDUser transcriber, TEItext transcription) {
		this.number=n;
		this.transcriber=transcriber;
		this.transcription=transcription;
	}
	
	public Rows(String number, UUIDUser digitalizer, javafx.scene.image.Image image) {
		this.number=number;
		this.digitalizer=digitalizer;
		this.image=image;
	}

	public Rows(String n,String rev,String val) {
		this.number=n;
		this.revisioned=rev;
		this.validated=val;
	}
	
	public Rows(String number,String rev,String val, UUIDPage id,javafx.scene.image.Image image) {
		this.number=number;
		this.revisioned=rev;
		this.validated=val;
		this.setId(id);
		this.image=image;
	}
	
	public Rows(UUIDPage id) {
		this.id=id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRevisioned() {
		return revisioned;
	}

	public void setRevisioned(String revisioned) {
		this.revisioned = revisioned;
	}

	public String getValidated() {
		return validated;
	}

	public void setValidated(String validated) {
		this.validated = validated;
	}

	public UUIDPage getId() {
		return id;
	}

	public void setId(UUIDPage id) {
		this.id = id;
	}

	public javafx.scene.image.Image getImage() {
		return image;
	}

	public void setImage(javafx.scene.image.Image image) {
		this.image = image;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rows other = (Rows) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}

