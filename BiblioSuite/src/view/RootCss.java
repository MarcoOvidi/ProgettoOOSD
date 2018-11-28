package view;

public class RootCss{
	private String css=getClass().getResource("/fx_view/application.css").toExternalForm();

	public String getCss() {
		return css;
	}
	
}