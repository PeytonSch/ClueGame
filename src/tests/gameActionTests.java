package tests;

<<<<<<< HEAD
import static org.junit.Assert.*;
=======
import java.awt.Color;
import java.util.Set;
>>>>>>> 3e5019bdf42060dbc8adc5fc74db9ed1a5b69491

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
<<<<<<< HEAD
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;
=======
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
>>>>>>> 3e5019bdf42060dbc8adc5fc74db9ed1a5b69491

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
		
		board.calcTargets(cpu.getStartLocation(), 4);
		
		Set<BoardCell> targets = board.getTargets();
		
		board.printTargetCells();
		
		cpu.pickLocation(targets);
		
		
		//if no rooms in list, select randomly
		//if room in list that was not just visited, must select it
		//if room just visited is in list, each target (including room) selected randomly
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
