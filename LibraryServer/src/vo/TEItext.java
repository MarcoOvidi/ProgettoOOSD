package vo;

public class TEItext {
	
	private String text;
	
	public  TEItext(String text) {
		this.text=text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String t) {
		this.text=t;
	}

	@Override
	public String toString() {
		return "TEItext [text=" + text + "]";
	}

	
}
