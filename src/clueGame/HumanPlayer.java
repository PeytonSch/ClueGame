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

public class HumanPlayer extends Player {
	
	private boolean waitingToGiveSuggestion;
	
	private Solution playerSuggestion;
	
	private boolean hasSuggestion;

	public HumanPlayer(String name, Color color, String colorString, String type, BoardCell startCell) {
		super(name, color, colorString, type, startCell);
		// TODO Auto-generated constructor stub
		waitingToGiveSuggestion = false;		
	}

	public HumanPlayer() {
		super();
	}
	
	public boolean getSuggestionFlag() {
		return hasSuggestion;
	}
	public void setSuggestionFlag(boolean flag) {
		hasSuggestion = flag;
	}
	
	public Solution getHumanSuggestion() {
		return playerSuggestion;
	}
	
	public void createHumanSuggestion(Card room, Card person, Card weapon) {
		playerSuggestion = new Solution(room, person, weapon);
		hasSuggestion = true;
	}

	//human player logic for disproving a suggestion. Used for testing

	public Card disproveSuggestion(Solution suggestion) {

		Card roomCard = suggestion.room;
		Card personCard = suggestion.person;
		Card weaponCard = suggestion.weapon;

		boolean doesContainsRoom = hand.contains(roomCard);
		boolean doesContainsPerson = hand.contains(personCard);
		boolean coesContainsWeapon = hand.contains(weaponCard);

		Set<Card> newHandSet = new HashSet<Card>();

		if (doesContainsRoom) newHandSet.add(roomCard);
		if (doesContainsPerson) newHandSet.add(personCard);
		if (coesContainsWeapon) newHandSet.add(weaponCard);

		if (!doesContainsRoom && !doesContainsPerson && !coesContainsWeapon) {
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
	
	

}
