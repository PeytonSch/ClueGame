package clueGame;

public enum DoorDirection {
	
	UP ("U"), DOWN ("D"), LEFT ("L"), RIGHT ("R"), NONE ("");
	
	private String value;
	
	DoorDirection(String aValue){
		value = aValue;
	}
	
	public String toString() {
		return value;
	}

}
