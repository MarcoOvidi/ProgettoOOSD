package vo;

public class Tag {
	private String name;
	
	public Tag(String n) {
		this.name=n;
	}
	
	public String getTag() {
		return this.name;
	}
	
	public void setTag(String name) {
		this.name=name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
