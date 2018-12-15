package vo;

public class Level {
	private Integer value;
	
	public Level(Integer value) {
		setValue(value);
	}
	
	@Override
	public String toString() {
		return this.getValue().toString();
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		if (value<1)
			this.setValue(1);
		else if (value>5)
			this.setValue(5);
		else
			this.value = value;
	}
	
	
}
