package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char lastRoom;

	public ComputerPlayer(String name, Color color, String colorString, String type, BoardCell startCell) {
		super(name, color, colorString, type, startCell);
		// TODO Auto-generated constructor stub
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
		// picks 3 random, unseen cards (room, person, weapon). calls board.testAccusation(Card, Card, Card)
	}
	
	public void createSuggestion() {
		
	}

	public void clearLastRoom() {
		lastRoom = '_';
		
	}

	public void setLastRoom(char c) {
		lastRoom = c;
		
	}

}
