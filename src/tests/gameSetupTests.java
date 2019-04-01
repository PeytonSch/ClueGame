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



		board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();
	}
	//do before each test
	@Before
	public void reset() {

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
		assertTrue(humanPlayer.getStartLocation() == board.getCellAt(5, 5));

		//test the first player in file 
		assertTrue(players.get(0).getColorString().equals("RED"));
		assertTrue(players.get(0).getName().equals("Voodoo Mama JuuJuu"));
		assertTrue(players.get(0).getType().equals("CPU"));
		assertTrue(players.get(0).getStartLocation() == board.getCellAt(1, 1));
		//test the third player in file
		assertTrue(players.get(2).getColorString().equals("PINK"));
		assertTrue(players.get(2).getName().equals("Naughty Nellie Nutmeg"));
		assertTrue(players.get(2).getType().equals("CPU"));
		assertTrue(players.get(2).getStartLocation() == board.getCellAt(6, 7));
		//test the last player in file
		assertTrue(players.get(players.size()-1).getColorString().equals("GRAY"));
		assertTrue(players.get(players.size()-1).getName().equals("Deb U. Taunt"));
		assertTrue(players.get(players.size()-1).getType().equals("CPU"));
		assertTrue(players.get(players.size()-1).getStartLocation() == board.getCellAt(13, 7));

		//test number of people loaded
		assertEquals(players.size(), NUM_PEOPLE);
	}

	//test the creation of card deck 
	@Test
	public void testDeckLoaded() {
		//Take all the cards and place into one hashset

		boolean containsBathroom = false;
		boolean containsNellie = false;
		boolean containsWrench = false;

		int numRooms = 0;
		int numPeople = 0;
		int numWeapons = 0;

		for ( Card card : board.getCards() ) {
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
		assertEquals(TOTAL_CARDS, board.getCards().size());

		// Test for containment
		assertTrue(containsBathroom);
		assertTrue(containsNellie);
		assertTrue(containsWrench);
	}
}
