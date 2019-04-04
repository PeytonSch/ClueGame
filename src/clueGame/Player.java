/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Player {
	

	private String name;
	private Color color;
	private String type;
	private BoardCell startCell;
	private String colorString;
	private Set<Card> hand;
	private int row;
	private int col;

	public Player(String name, Color color, String colorString, String type, BoardCell startCell) {
		this.name = name;
		this.color = color;
		this.type = type;
		this.startCell = startCell;
		this.colorString = colorString;
		hand = new HashSet<Card>();
		
		row = startCell.getRow();
		col = startCell.getCol();
		
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}

	public Set<Card> getHand() {
		return hand;
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
	public void giveCard(Card card) {
		hand.add(card);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	

}
