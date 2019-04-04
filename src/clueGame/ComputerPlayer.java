package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char lastRoom;
	private BoardCell currentCell;

	public ComputerPlayer(String name, Color color, String colorString, String type, BoardCell startCell) {
		super(name, color, colorString, type, startCell);
		currentCell = startCell;
		// TODO Auto-generated constructor stub
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		//Should only choose a valid target (calculating targets already tested –yay)
				//If room in list and has not been visited last:
				for ( BoardCell cell : targets ) {
					if ( cell.isDoorway() && cell.getInitial() != lastRoom ) {
						//choose room
						lastRoom = cell.getInitial();
						currentCell = Board.getInstance().getCellAt(getRow(), getCol());
						return cell;
					}
				}
				
				// Else, choose random cell and return
				int size = targets.size();
				int num = new Random().nextInt(size);
				int i = 0;
				for ( BoardCell cell : targets ) {
					if ( i == num ) {
						currentCell = Board.getInstance().getCellAt(getRow(), getCol());
						return cell;
					}
					i++;
				}
				
				// Something has gone wrong
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

	public Solution getSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Card> getCardsAllreadySeen() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCardToSeen(Card card) {
		// TODO Auto-generated method stub
		
	}

}
