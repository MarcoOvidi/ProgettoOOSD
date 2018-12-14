package vo;

public class VagueDate {
	
	//variabili istanza
	
	private Integer from;
	private Integer to;
	
	public VagueDate(Integer from, Integer to) {
		this.setFrom(from);
		this.setTo(to);
	}

	@Override
	public String toString() {
		return "VagueDate [from=" + getFrom() + ", to=" + getTo() + "]";
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}
	
	

}
