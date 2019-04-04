package tests;


import static org.junit.Assert.*;
import java.awt.Color;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;
import clueGame.BoardCell;

public class gameActionTests {
	private static Board board;
	//do once before running any tests

	@BeforeClass
	public static void setUp() throws BadConfigFormatException {

		//set up the board and initialize everything with config files

		board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();

	}


	//CPU selecting a target
	@Test
	public void targetTests() {
		ComputerPlayer cpu = new ComputerPlayer("CPU", Color.RED,"RED", "CPU", board.getCellAt(5, 7));



		//if no rooms in list, select randomly
		board.calcTargets(5, 7, 2);

		boolean loc_05_05 = false;
		boolean loc_05_09 = false;
		boolean loc_04_06 = false;
		boolean loc_06_06 = false;
		boolean loc_06_08 = false;

		//test a whole bunch of times to make sure it selects everything at some point
		for (int i=0; i<100; i++) {
			BoardCell selected = cpu.pickLocation(board.getTargets());
			if (selected == board.getCellAt(5, 5))
				loc_05_05 = true;
			else if (selected == board.getCellAt(5, 9))
				loc_05_09 = true;
			else if (selected == board.getCellAt(4, 6))
				loc_04_06 = true;
			else if (selected == board.getCellAt(6, 6))
				loc_06_06 = true;
			else if (selected == board.getCellAt(6, 8))
				loc_06_08 = true;
			else
				fail("Invalid target selected");
		}
		// Ensure targets were picked
		assertTrue(loc_05_05);
		assertTrue(loc_05_09);
		assertTrue(loc_04_06);
		assertTrue(loc_06_06);
		assertTrue(loc_06_08);

		//if room in list that was not just visited, must select it
		board.calcTargets(5,8,2);
		

		boolean loc_06_07 = false;
		boolean loc_04_07 = false;
		boolean loc_05_06 = false;
		boolean loc_05_10 = false;
		boolean loc_06_09 = false;
		boolean door_1 = false; //cell 4,9
		

		for (int i=0; i<100; i++) {
			BoardCell selected = cpu.pickLocation(board.getTargets());
			if (selected == board.getCellAt(6, 7))
				loc_06_07 = true;
			if (selected == board.getCellAt(4, 7))
				loc_04_07 = true;
			if (selected == board.getCellAt(5, 6))
				loc_05_06 = true;
			if (selected == board.getCellAt(5, 10))
				loc_05_10 = true;
			if (selected == board.getCellAt(6, 9))
				loc_06_09 = true;
			cpu.clearLastRoom();
			if (selected == board.getCellAt(4, 9))
				door_1 = true;
			cpu.clearLastRoom();
		}

		// Ensure doors were picked
		assertTrue(door_1);
		assertFalse(loc_06_07);
		assertFalse(loc_04_07);
		assertFalse(loc_05_06);
		assertFalse(loc_05_10);
		assertFalse(loc_06_09);

		//if room just visited is in list, each target (including room) selected randomly
		board.calcTargets(5, 9, 1);
		
		cpu.setLastRoom('O');
		
		boolean door = false; //cell 4,9
		loc_05_10 = false;
		loc_06_09 = false;
		boolean loc_05_08 = false;
		
		//test a whole bunch of times to make sure it selects everything at some point
		for (int i=0; i<100; i++) {
			BoardCell selected = cpu.pickLocation(board.getTargets());
			if (selected == board.getCellAt(4, 9))
				door = true;
			if (selected == board.getCellAt(5, 10))
				loc_05_10 = true;
			if (selected == board.getCellAt(6, 9))
				loc_06_09 = true;
			if (selected == board.getCellAt(5, 8))
				loc_05_08 = true;
		}
		
		// Ensure all targets chosen
		assertTrue(door);
		assertTrue(loc_05_10);
		assertTrue(loc_06_09);
		assertTrue(loc_05_08);
	}

	//Board
	//Make an accusation. Tests include:
	//solution that is correct
	//solution with wrong person
	//solution with wrong weapon
	//solution with wrong room
	@Test
	public void accusationTest() {
		// create solution string, will be populated by dealCards()
		String roomCard = "Observatory";
		String personCard = "Caleb Crawdad";
		String weaponCard = "Revolver";

		// Turn solution strings to Card type
		Card roomSolution = new Card(roomCard, CardType.ROOM);
		Card personSolution = new Card(personCard, CardType.PERSON);
		Card weaponSolution = new Card(weaponCard, CardType.WEAPON);

		// set the solution to ours for testing purposes
		board.setSolution(new Solution(roomSolution, personSolution, weaponSolution));

		// test correct solution
		assertTrue(board.getSolution().room.equals(roomCard));
		assertTrue(board.getSolution().person.equals(personCard));
		assertTrue(board.getSolution().weapon.equals(weaponCard));

		// test solution with wrong person
		assertTrue(board.getSolution().room.equals(roomCard));
		assertFalse(board.getSolution().person.equals("Naughty Nellie Nutmeg"));
		assertTrue(board.getSolution().weapon.equals(weaponCard));

		// test solution with wrong weapon
		assertTrue(board.getSolution().room.equals(roomCard));
		assertTrue(board.getSolution().person.equals(personCard));
		assertFalse(board.getSolution().weapon.equals("Dumbell"));

		// test solution with wrong room
		assertFalse(board.getSolution().room.equals("Sauna"));
		assertTrue(board.getSolution().person.equals(personCard));
		assertTrue(board.getSolution().weapon.equals(weaponCard));

	}

	//CPU player
	//Create suggestion. Tests include:
	//Room matches current location
	//If only one weapon not seen, it's selected
	//If only one person not seen, it's selected (can be same test as weapon)
	//If multiple weapons not seen, one of them is randomly selected
	//If multiple persons not seen, one of them is randomly selected
	@Test
	public void createSuggestionTest() {

	}


	//Disprove suggestion - ComputerPlayer. Tests include:
	//If player has only one matching card it should be returned
	//If players has >1 matching card, returned card should be chosen randomly
	//If player has no matching cards, null is returned

	@Test
	public void disproveSuggestionTest() {

	}

	//Handle suggestion - Board. Tests include:
	//Suggestion no one can disprove returns null
	//Suggestion only accusing player can disprove returns null
	//Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
	//Suggestion only human can disprove, but human is accuser, returns null
	//Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
	//Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer

	@Test
	public void handleSuggestionTest() {

	}
}
