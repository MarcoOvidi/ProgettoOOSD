package vo;


//TODO mettere un controllo che verifica la correttezza sintattica delle mail 
public class Email {
	String email;
	
	public Email(String e) {
		this.email = e;
	}
	
	public String getEmail() {
		return this.email;
	}
}
