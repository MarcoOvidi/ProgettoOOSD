

public class RootCss{
	private String css=getClass().getResource("application.css").toExternalForm();

	public String getCss() {
		return css;
	}
	
}