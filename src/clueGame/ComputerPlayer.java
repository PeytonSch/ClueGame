/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	private char lastRoom;
	private BoardCell cellAt;
	private Solution suggestion;
	
	private boolean shouldAccuse = false;

	public ComputerPlayer(String name, Color color, String colorString, String type, BoardCell startCell) {
		super(name, color, colorString, type, startCell);
		cellAt = startCell;
		// TODO Auto-generated constructor stub
	}
	//simple constructor for testing
	public ComputerPlayer() {
		super();
	}
	public boolean getAccuseFlag() {
		return shouldAccuse;
	}
	public void setAccuseFlag() {
		shouldAccuse = true;
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		//Should only choose a valid target (calculating targets already tested �yay)
		//If room in list and has not been visited last:
		for ( BoardCell cell : targets ) {
			if ( cell.isDoorway() && cell.getInitial() != lastRoom ) {
				//choose room
				lastRoom = cell.getInitial();
				cellAt = Board.getInstance().getCellAt(getRow(), getCol());
				return cell;
			}
		}

		// Else, choose random cell and return
		int size = targets.size();
		int num = new Random().nextInt(size);
		int i = 0;
		for ( BoardCell cell : targets ) {
			if ( i == num ) {
				cellAt = Board.getInstance().getCellAt(getRow(), getCol());
				return cell;
			}
			i++;
		}

		//this shouldn't run
		return null;
	}

	//	public void makeAccusation() {
	//		// picks 3 random, unseen cards (room, person, weapon). calls board.testAccusation(Card, Card, Card)
	//	}

	public void createSuggestion() {
		cellAt = Board.getInstance().getCellAt(getRow(), getCol());
		Card roomCard = Board.getInstance().getRoomWithInitial(cellAt.getInitial());

		Card weaponCard = null;
		ArrayList<Card> allWeapons = Board.getInstance().getAllWeaponCards();
		Set<Card> setOfUnseenWeapons = new HashSet<Card>();

		//add weapons to unseen list if we havn't seen them
		for ( Card w : allWeapons ) {
			if ( !getCardsAllreadySeen().contains(w) ) {
				setOfUnseenWeapons.add(w);
			}
		}
		//System.out.println("cardsAllreadySeen: " + getCardsAllreadySeen().size());
		int weapSize = setOfUnseenWeapons.size();
		//System.out.println("Wep size (unsean weapons):  " + weapSize);
		int numWeapons = new Random().nextInt(weapSize);
		//System.out.println("numWeapons:   " + numWeapons);
		int j = 0;
		for ( Card w : setOfUnseenWeapons ) {
			if ( j == numWeapons ) {
				weaponCard = w;
				break;
			}
			else {
				j++;
			}
		}




		Card playerCard = null;
		Set<Card> setOfAllPlayers = Board.getInstance().getPlayerCards();
		Set<Card> setOfUnseenPlayers = new HashSet<Card>();

		//add players to unseen if we havn't seen them
		for ( Card p : setOfAllPlayers ) {
			if ( !getCardsAllreadySeen().contains(p) ) {
				setOfUnseenPlayers.add(p);
			}
		}

		int numUnseenPlayers = setOfUnseenPlayers.size();
		int randInUnseenPlayers = new Random().nextInt(numUnseenPlayers);
		int i = 0;
		for ( Card p : setOfUnseenPlayers ) {
			if ( i == randInUnseenPlayers ) {
				playerCard = p;
				break;
			}
			else {
				i++;
			}
		}


		//create suggestion
		suggestion = new Solution(roomCard, playerCard, weaponCard);
		
		//handles if human dies
		Board board = Board.getInstance();
		if (board.checkAccusaton(suggestion)) {
			//System.out.println("correct");
		}
		
		//end testing

	}

	//disprove suggestion if we have a card to disprove it
	public Card disproveSuggestion(Solution suggestion) {

		Card roomCard = suggestion.room;
		Card personCard = suggestion.person;
		Card weaponCard = suggestion.weapon;

		boolean doesContainsRoom = hand.contains(roomCard);
		boolean doesContainsPerson = hand.contains(personCard);
		boolean doesContainsWeapon = hand.contains(weaponCard);

		Set<Card> newHandSet = new HashSet<Card>();

		if (doesContainsRoom) newHandSet.add(roomCard);
		if (doesContainsPerson) newHandSet.add(personCard);
		if (doesContainsWeapon) newHandSet.add(weaponCard);

		if (!doesContainsRoom && !doesContainsPerson && !doesContainsWeapon) {
			return null;
		} else if (hand.contains(roomCard) || hand.contains(personCard) || hand.contains(weaponCard)) {
			int size = newHandSet.size();
			int randNumInSize = new Random().nextInt(size);
			int i = 0;
			for (Card c : newHandSet) {
				if (i == randNumInSize) {
					return c;
				}
				i++;
			}

		}
		return null; 
	}

	//clear out last room to random char
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
	
	public Card getCurrentRoomCard() {
		Card roomCard = Board.getInstance().getRoomWithInitial(cellAt.getInitial());
		return roomCard;
	}





}
