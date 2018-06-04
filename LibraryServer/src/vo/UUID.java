package vo;

//TODO verificare se astrarre la classe 
public class UUID implements Comparable<UUID> {
	private Integer value;
	
	public UUID(Integer i) {
		this.value=i;
	}
	
	public void setValue(Integer v) {
		this.value=v;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	@Override
	public int compareTo(UUID id) {
		if(this.value < id.getValue())
			return -1;
		else {
			if(this.value > id.getValue())
				return 1;
			else return 0;
		}
	}

	@Override
	public String toString() {
		return "UUID [value=" + value + "]";
	}


	
	

}
