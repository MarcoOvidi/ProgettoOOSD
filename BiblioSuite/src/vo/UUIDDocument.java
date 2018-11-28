package vo;

import java.util.Objects;

public class UUIDDocument extends UUID {

	public UUIDDocument(Integer i) {
		super(i);
	}

	@Override
	public int hashCode(){
	    return Objects.hash(this.getValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof UUID))
				return false;
		else
			return this.getValue() == ((UUID) obj).getValue();
	}
}
