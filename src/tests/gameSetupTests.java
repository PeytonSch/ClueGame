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

	//do once before running any tests
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {
		board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt");
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
	}

	//test the creation of card deck 
	@Test
	public void testDeckLoaded() {
		//Take all the cards and place into one hashset

		boolean containsRoom = false;
		boolean containsPerson = false;
		boolean containsWeapon = false;

		int numRooms = 0;
		int numPeople = 0;
		int numWeapons = 0;

		for ( Card card : board.getCards() ) {
			if ( card.getCardType() == CardType.ROOM ) {
				numRooms++;
				if ( card.getName().equals("Ballroom") ) {
					containsRoom = true;
				}
			}
			else if ( card.getCardType() == CardType.PERSON ) {
				numPeople++;
				if ( card.getName().equals("Naughty Nellie Nutmeg") ) {
					containsPerson = true;
				}
			}
			else if ( card.getCardType() == CardType.WEAPON ) {
				numWeapons++;
				if (card.getName().equals("Wrench") ) {
					containsWeapon = true;
				}
			}
		}





	}
}
