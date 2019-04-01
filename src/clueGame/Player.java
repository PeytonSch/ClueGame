package clueGame;

import java.awt.Color;

public class Player {
	

	private String name;
	private Color color;
	private String type;
	private BoardCell startCell;
	private String colorString;

	public Player(String name, Color color, String colorString, String type, BoardCell startCell) {
		this.name = name;
		this.color = color;
		this.type = type;
		this.startCell = startCell;
		this.colorString = colorString;
	}

	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}
	
	public String getColorString() {
		return colorString;
	}

	public String getType() {
		return type;
	}
	public BoardCell getStartLocation() {
		return startCell;
	}

}
