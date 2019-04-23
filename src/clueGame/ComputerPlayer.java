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
	private BoardCell currentCell;
	private Solution suggestion;
	
	private boolean shouldAccuse = false;

	public ComputerPlayer(String name, Color color, String colorString, String type, BoardCell startCell) {
		super(name, color, colorString, type, startCell);
		currentCell = startCell;
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

		//this shouldn't run
		return null;
	}

	//	public void makeAccusation() {
	//		// picks 3 random, unseen cards (room, person, weapon). calls board.testAccusation(Card, Card, Card)
	//	}

	public void createSuggestion() {
		currentCell = Board.getInstance().getCellAt(getRow(), getCol());
		Card roomCard = Board.getInstance().getRoomWithInitial(currentCell.getInitial());

		Card weaponCard = null;
		ArrayList<Card> allWeapons = Board.getInstance().getAllWeaponCards();
		Set<Card> unseenWeapons = new HashSet<Card>();

		//add weapons to unseen list if we havn't seen them
		for ( Card w : allWeapons ) {
			if ( !getCardsAllreadySeen().contains(w) ) {
				unseenWeapons.add(w);
			}
		}
		System.out.println("cardsAllreadySeen: " + getCardsAllreadySeen().size());
		int weapSize = unseenWeapons.size();
		System.out.println("Wep size (unsean weapons):  " + weapSize);
		int numWeapons = new Random().nextInt(weapSize);
		System.out.println("numWeapons:   " + numWeapons);
		int j = 0;
		for ( Card w : unseenWeapons ) {
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

	}

	//disprove suggestion if we have a card to disprove it
	@Override
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





}
