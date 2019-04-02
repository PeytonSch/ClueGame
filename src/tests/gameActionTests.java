package tests;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

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

	//	if no rooms in list, select randomly
	//	if room in list that was not just visited, must select it
	//	if room just visited is in list, each target (including room) selected randomly

	@Test
	public void targetTests() {
		
	}

	//	Make an accusation. Tests include:
	//		solution that is correct
	//		solution with wrong person
	//		solution with wrong weapon
	//		solution with wrong room
	@Test
	public void accusationTest() {

	}

	//	Create suggestion. Tests include:
	//			Room matches current location
	//			If only one weapon not seen, it's selected
	//			If only one person not seen, it's selected (can be same test as weapon)
	//			If multiple weapons not seen, one of them is randomly selected
	//			If multiple persons not seen, one of them is randomly selected
	@Test
	public void createSuggestionTest() {

	}

	//	(15pts) Disprove suggestion - ComputerPlayer. Tests include:
	//				If player has only one matching card it should be returned
	//				If players has >1 matching card, returned card should be chosen randomly
	//				If player has no matching cards, null is returned

	@Test
	public void disproveSuggestionTest() {

	}

	//	(15pts) Handle suggestion - Board. Tests include:
	//					Suggestion no one can disprove returns null
	//					Suggestion only accusing player can disprove returns null
	//					Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
	//					Suggestion only human can disprove, but human is accuser, returns null
	//					Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
	//					Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer

	@Test
	public void handleSuggestionTest() {

	}
}
