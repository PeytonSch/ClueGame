/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

public class Player {


	private String name;
	private Color color;
	private String type;
	private BoardCell startCell;
	private String colorString;
	protected Set<Card> hand;
	private int row;
	private int col;
	protected Set<Card> seen;
	private Boolean isAlive;
	private boolean currentlyInRoom;

	public Player(String name, Color color, String colorString, String type, BoardCell startCell) {
		this.name = name;
		this.color = color;
		this.type = type;
		this.startCell = startCell;
		this.colorString = colorString;
		hand = new HashSet<Card>();
		seen = new HashSet<Card>();
		isAlive = true;

		row = startCell.getRow();
		col = startCell.getCol();
		
		currentlyInRoom = false;

	}

	//for testing just creating a simple player with the needed data structures
	public Player() {
		seen = new HashSet<Card>();
		hand = new HashSet<Card>();
		isAlive = true;
	}

	//give player card
	public void addCardToHand(Card card) {
		hand.add(card);
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

	//get cards player has seen
	public Set<Card> getCardsAllreadySeen(){
		return seen;
	}

	//add cards to list of cards player has seen
	public void addCardToListOfCardsAllreadySeen(Card card) {
		seen.add(card);
	}
	private Color convertColor(String string) {
		Color color; 
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(string.trim()); 
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}

	//draw player ovals and set colors to player colors
	public void drawPlayer(Graphics g) {
		int scale = 20;
		if (!isAlive) {
			return;
		}
		g.setColor(color);
		g.drawOval(this.col * scale, this.row * scale, scale, scale);
		g.fillOval(this.col*scale, this.row*scale, scale, scale);
		g.setColor(Color.BLACK);
		g.drawOval(this.col*scale, this.row*scale, scale, scale);
	}
	
	public void makeMove(BoardCell cell) {
		//moves player to given cell
		setLocation(cell.getRow(), cell.getCol());
		
		//update room status
		if (cell.isRoom()) {
			currentlyInRoom = true;
		}
		else {
			currentlyInRoom = false;
		}
	}
	
	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public boolean getCurrentlyInRoom() {
		return currentlyInRoom;
	}
	


	public BoardCell pickLocation(Set<BoardCell> targets) {
		// TODO Auto-generated method stub
		int target = new Random().nextInt(targets.size());
		int i = 0;
		for(BoardCell cell : targets) {
			if (i == target) {
				return cell;
				
			}
			i++;
		}
		System.out.println("pickLocation returning null");
		return null;
	}


}
