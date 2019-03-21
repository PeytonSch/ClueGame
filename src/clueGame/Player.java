package clueGame;

import java.awt.Color;

public class Player {
	

	private String name;
	private Color color;
	private String type;
	private int startX;
	private int startY;

	public Player(String name, Color color, String type, int startX, int startY) {
		this.name = name;
		this.color = color;
		this.type = type;
		this.startX = startX;
		this.startY = startY;
	}

}
