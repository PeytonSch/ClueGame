/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	private char lastRoom;
	private BoardCell currentCell;
	private Solution suggestion;


	public ComputerPlayer(String name, Color color, String colorString, String type, BoardCell startCell) {
		super(name, color, colorString, type, startCell);
		currentCell = startCell;
		// TODO Auto-generated constructor stub
	}
	public ComputerPlayer() {
		super();
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
		currentCell = Board.getInstance().getCellAt(getRow(), getCol());
		Card room = Board.getInstance().getRoomWithInitial(currentCell.getInitial());

		Card weapon = null;
		Set<Card> allWeapons = Board.getInstance().getWeaponCards();
		Set<Card> unseenWeapons = new HashSet<Card>();


		for ( Card w : allWeapons ) {
			if ( !getSeen().contains(w) ) {
				unseenWeapons.add(w);
			}
		}

		int sizeW = unseenWeapons.size();
		int numW = new Random().nextInt(sizeW);
		int j = 0;
		for ( Card w : unseenWeapons ) {
			if ( j == numW ) {
				weapon = w;
				break;
			}
			else {
				j++;
			}
		}




		Card player = null;
		Set<Card> allPlayers = Board.getInstance().getPlayerCards();
		Set<Card> unseenPlayers = new HashSet<Card>();

		for ( Card p : allPlayers ) {
			if ( !getSeen().contains(p) ) {
				unseenPlayers.add(p);
			}
		}

		int size = unseenPlayers.size();
		int num = new Random().nextInt(size);
		int i = 0;
		for ( Card p : unseenPlayers ) {
			if ( i == num ) {
				player = p;
				break;
			}
			else {
				i++;
			}
		}



		suggestion = new Solution(room, player, weapon);

	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {

		Card room = suggestion.room;
		Card person = suggestion.person;
		Card weapon = suggestion.weapon;

		boolean containsRoom = hand.contains(room);
		boolean containsPerson = hand.contains(person);
		boolean containsWeapon = hand.contains(weapon);

		Set<Card> newHand = new HashSet<Card>();

		if (containsRoom) newHand.add(room);
		if (containsPerson) newHand.add(person);
		if (containsWeapon) newHand.add(weapon);

		if (!containsRoom && !containsPerson && !containsWeapon) {
			return null;
		} else if (hand.contains(room) || hand.contains(person) || hand.contains(weapon)) {
			int size = newHand.size();
			int num = new Random().nextInt(size);
			int i = 0;
			for (Card c : newHand) {
				if (i == num) {
					return c;
				}
				i++;
			}

		}
		return null; 
	}

		public void clearLastRoom() {
			lastRoom = '_';

		}

		public void setLastRoom(char c) {
			lastRoom = c;

		}

		public Solution getSuggestion() {
			// TODO Auto-generated method stub
			return suggestion;
		}





	}
