package vo;

public class Rows {

	private Integer number;
	private String image;
	private String revisioned;
	private String validated;

	public Rows(Integer n, String i,String rev,String val) {
		this.number=n;
		this.image=i;
		this.revisioned=rev;
		this.validated=val;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
	
	
}

