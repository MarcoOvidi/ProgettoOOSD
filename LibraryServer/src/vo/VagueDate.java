package vo;

public class VagueDate {
	
	//variabili istanza
	
	private Integer from;
	private Integer to;
	
	public VagueDate(Integer from, Integer to) {
		this.from=from;
		this.to= to;
	}

	@Override
	public String toString() {
		return "VagueDate [from=" + from + ", to=" + to + "]";
	}
	
	

}
