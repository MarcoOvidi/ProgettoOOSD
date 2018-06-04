package vo;

import java.util.Date;

public class VagueDate {
	
	//variabili istanza
	
	private Date from;
	private Date to;
	
	public VagueDate(Date from, Date to) {
		this.from=from;
		this.to= to;
	}

	@Override
	public String toString() {
		return "VagueDate [from=" + from + ", to=" + to + "]";
	}
	
	

}
