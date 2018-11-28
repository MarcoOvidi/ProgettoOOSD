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
	
	public static boolean checkValid(String mail) {
		//TODO controlla se la stringa è una email valida
		return true;
	}
}
