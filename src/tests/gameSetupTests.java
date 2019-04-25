/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class gameSetupTests {
	private static Board board;
	// Constants to check whether data was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 22;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PEOPLE = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int TOTAL_CARDS = 21;


	//do once before running any tests
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {

		//set up the board and initialize everything with config files

		board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();
	}



	//test loading the people 
	@Test
	public void testPlayersLoaded() {
		ArrayList<Player> players = board.getPlayers();

		Player humanPlayer = players.get(1);
		//test that the correct number of players was loaded
		assertTrue(players.size() == 6);
		/*
		 * The following test will test different players for correct 
		 * Name, Color, Human/Computer, Start Location
		 */


		//test the human player
		assertTrue(humanPlayer.getColorString().equals("GREEN"));
		assertTrue(humanPlayer.getName().equals("Beatrix Bourbon"));
		assertTrue(humanPlayer.getType().equals("Human"));
		assertTrue(humanPlayer.getStartLocation() == board.getCellAt(22, 5));

		//test the first player in file 
		assertTrue(players.get(0).getColorString().equals("RED"));
		assertTrue(players.get(0).getName().equals("Voodoo Mama JuuJuu"));
		assertTrue(players.get(0).getType().equals("CPU"));
		assertTrue(players.get(0).getStartLocation() == board.getCellAt(0, 5));
		//test the third player in file
		assertTrue(players.get(2).getColorString().equals("PINK"));
		assertTrue(players.get(2).getName().equals("Naughty Nellie Nutmeg"));
		assertTrue(players.get(2).getType().equals("CPU"));
		assertTrue(players.get(2).getStartLocation() == board.getCellAt(5, 0));
		//test the last player in file
		assertTrue(players.get(players.size()-1).getColorString().equals("GRAY"));
		assertTrue(players.get(players.size()-1).getName().equals("Deb U. Taunt"));
		assertTrue(players.get(players.size()-1).getType().equals("CPU"));
		assertTrue(players.get(players.size()-1).getStartLocation() == board.getCellAt(14, 0));

		//test number of people loaded
		assertEquals(players.size(), NUM_PEOPLE);
	}

	//test the creation of card deck 
	@Test
	public void testDeckLoaded() {
		//Take all the cards and place into one hashset
		
		/*
		 * I have picked a couple of cards at random to make sure that our deck contains
		 * the cards we want it to.
		 */
		boolean containsBathroom = false;
		boolean containsNellie = false;
		boolean containsWrench = false;
		
		/*
		 * These variables will ensure we load the correct total number of cards from files
		 */

		int numRooms = 0;
		int numPeople = 0;
		int numWeapons = 0;

		/*
		 * Iterate through all the cards we have loaded looking for the rooms we are testing
		 * and incrementing the card types to test
		 */
		for ( Card card : board.getAllCards() ) {
			if ( card.getCardType() == CardType.ROOM ) {
				numRooms++;
				if ( card.getName().equals("Bathroom") ) {
					containsBathroom = true;
				}
			}
			else if ( card.getCardType() == CardType.PERSON ) {
				numPeople++;
				if ( card.getName().equals("Naughty Nellie Nutmeg") ) {
					containsNellie = true;
				}
			}
			else if ( card.getCardType() == CardType.WEAPON ) {
				numWeapons++;
				if (card.getName().equals("Wrench") ) {
					containsWrench = true;
				}
			}
		}

		// Test deck sizes
		assertEquals(NUM_ROOMS, numRooms);
		assertEquals(NUM_PEOPLE, numPeople);
		assertEquals(NUM_WEAPONS, numWeapons);
		//make sure the total number of cards we loaded is correct
		assertEquals(TOTAL_CARDS, board.getAllCards().size());

		// Test for the cards we know should be there
		assertTrue(containsBathroom);
		assertTrue(containsNellie);
		assertTrue(containsWrench);
	}
	
	//test that we dealt all cards correctly from the deck
	@Test
	public void testDealCards() {
		//get cards returns the deck, which we should have allready delt so this should be empty
		ArrayList<Card> deck = board.getCards();

		//deck should be empty after it is dealt
		assertEquals(0, deck.size());
		
		//if any of the players do not have a hand size within 1 of what is expected then this test will not pass
		boolean handSizeNotWithinOneCardForPlayer = false;
		
		ArrayList<Player> players = board.getPlayers();
		
		
		
		// Test player hands - players should have close to same number of cards
		for (Player p : players) {
			/*
			 * This condition essentially takes the total number of cards, divides it by the number of players, and then assumes that each player
			 * should have this number of cards +- 1. This will work for all card deck sizes. We subtract 3 to account for the cards that are
			 * contained within the solution. 
			 */
			if(p.getHand().size() < ((board.getAllCards().size()-3) / NUM_PEOPLE) -1 || p.getHand().size() > ((board.getAllCards().size()-3) / NUM_PEOPLE ) + 1)  {
				handSizeNotWithinOneCardForPlayer = true;
			}
		}
		
		assertFalse(handSizeNotWithinOneCardForPlayer);
		
		

	}
}
