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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UUID other = (UUID) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	


	
	

}
