package vo;

public class Tag {
	private String name;
	
	public Tag(String n) {
		this.name=n;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
