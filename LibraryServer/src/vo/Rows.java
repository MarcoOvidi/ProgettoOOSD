package vo;

import javafx.scene.image.ImageView;

public class Rows {

	private String number;
	private ImageView ImageView;
	private String revisioned;
	private String validated;
	private UUIDPage id;

	public Rows(String n, ImageView i,String rev,String val) {
		this.number=n;
		this.ImageView=i;
		this.revisioned=rev;
		this.validated=val;
	}
	
	public Rows(String n, ImageView i,String rev,String val, UUIDPage id) {
		this.number=n;
		this.ImageView=i;
		this.revisioned=rev;
		this.validated=val;
		this.setId(id);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public ImageView getImageView() {
		return ImageView;
	}

	public void setImageView(ImageView ImageView) {
		this.ImageView = ImageView;
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
	
	
}

