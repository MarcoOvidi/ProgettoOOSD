package vo;

//TODO verificare se astrarre la classe 
public class UUID {
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

}
