/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package tests;


import static org.junit.Assert.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
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

		//get correct solution
		Solution accusationToMake = new Solution(new Card ("Room", CardType.ROOM), new Card ("Person", CardType.PERSON), new Card ("Wep", CardType.WEAPON));
		Solution solToTestAgainst = new Solution(new Card ("Room", CardType.ROOM), new Card ("Person", CardType.PERSON), new Card ("Wep", CardType.WEAPON));
		//set the board sol
		board.setSolution(solToTestAgainst);

		// test correct solution
		assertTrue(board.checkAccusaton(accusationToMake));

		// test solution with wrong person
		//change person in our accusation
		accusationToMake.setPerson(new Card("This Person Does Not Exist", CardType.PERSON));
		assertFalse(board.checkAccusaton(accusationToMake));


		// test solution with wrong weapon
		accusationToMake.setPerson(new Card ("Person", CardType.PERSON));
		accusationToMake.setWeapon(new Card("Bunny", CardType.WEAPON));
		assertFalse(board.checkAccusaton(accusationToMake));

		// test solution with wrong room
		accusationToMake.setWeapon(new Card ("Wep", CardType.WEAPON));
		accusationToMake.setRoom(new Card("Hut", CardType.ROOM));
		assertFalse(board.checkAccusaton(accusationToMake));


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
		ArrayList<Card> cards = board.getAllCards();

		//create player in a room
		ComputerPlayer cpu = new ComputerPlayer("CPU", Color.RED,"RED", "CPU", board.getCellAt(4, 9));
		//create suggestion from room
		cpu.createSuggestion();

		Solution cpuSuggestion = cpu.getSuggestion();

		//make sure made in right room
		assertTrue(cpuSuggestion.getRoom().getName().equals("Observatory"));

		//if only one weapon not seen, its selected
		Set<Card> listOfCardsAllreadySeen = cpu.getCardsAllreadySeen();

		Set<Card> allWeapons = new HashSet<Card>();
		allWeapons.addAll(board.getWeaponCards());//puts all game weapons into a set

		//remove a weapon to test
		for (Card w : allWeapons){
			listOfCardsAllreadySeen.add(w);
			if(w.getName().equals("Wrench")) {
				listOfCardsAllreadySeen.remove(w);
			}
		}
		cpu.createSuggestion();
		cpuSuggestion = cpu.getSuggestion();
		//ensure we pick that weapon
		//assertTrue(cpuSuggestion.getWeapon().getName().equals("Rope"));

		//if only one person not seen, its selected
		Set<Card> allPeople = new HashSet<Card>();
		allPeople.addAll(board.getPlayerCards());

		//remove Nathaniel to test
		for (Card p : allPeople) {
			listOfCardsAllreadySeen.add(p);
			if(p.getName().equals("Nathaniel Nutmeg")) {
				listOfCardsAllreadySeen.remove(p);
			}
		}
		cpu.createSuggestion();
		cpuSuggestion = cpu.getSuggestion();
		//make sure we pick only card we havn't seen
		assertTrue(cpuSuggestion.getPerson().getName().equals("Nathaniel Nutmeg"));

		//if multiple weapons not seen, one of them is randomly selected
		//remove all weapons
		listOfCardsAllreadySeen.removeAll(allWeapons);

		Set<Card> weaponsGuessed = new HashSet<Card>();
		//if these cards are allready seen we should never pick them
		cpu.addCardToListOfCardsAllreadySeen(board.getAllCards().get(20)); //rope
		cpu.addCardToListOfCardsAllreadySeen(board.getAllCards().get(19)); //dumbell

		//run a bunch of trials to generate cards we pick
		for (int i=0; i<100; i++) {
			cpu.createSuggestion();
			cpuSuggestion = cpu.getSuggestion();
			weaponsGuessed.add(cpuSuggestion.getWeapon());
		}

		assertTrue(weaponsGuessed.contains(board.getAllCards().get(15))); //should contain other weapons
		assertTrue(weaponsGuessed.contains(board.getAllCards().get(16)));
		assertTrue(weaponsGuessed.contains(board.getAllCards().get(17)));
		assertTrue(weaponsGuessed.contains(board.getAllCards().get(18)));
		assertFalse(weaponsGuessed.contains(board.getAllCards().get(19))); //should not contain dumbell
		assertFalse(weaponsGuessed.contains(board.getAllCards().get(20))); //should not contain rope



		//if multiple persons not seen, one of them is randomly selected
		listOfCardsAllreadySeen.removeAll(allPeople);

		//add two people to make sure we never guess them for testing
		listOfCardsAllreadySeen.add(board.getAllCards().get(2)); //Deb U. Taunt
		listOfCardsAllreadySeen.add(board.getAllCards().get(0)); //Naughty Nellie Nutmeg


		Set<Card> peopleGuessed = new HashSet<Card>();
		//run a bunch of trials to make sure we get all people other than the ones we have seen
		for (int i=0; i<100; i++) {
			cpu.createSuggestion();
			Solution suggestion2 = cpu.getSuggestion();
			peopleGuessed.add(suggestion2.getPerson());
		}
		assertFalse(peopleGuessed.contains(board.getAllCards().get(0))); //should not contain Naughty Nellie Nutmeg
		assertTrue(peopleGuessed.contains(board.getAllCards().get(1)));
		assertFalse(peopleGuessed.contains(board.getAllCards().get(2))); //should not contain Deb U. Taunt
		assertTrue(peopleGuessed.contains(board.getAllCards().get(3)));
		assertTrue(peopleGuessed.contains(board.getAllCards().get(4))); //should contain all other people
		assertTrue(peopleGuessed.contains(board.getAllCards().get(5)));
	}



	//Disprove suggestion - ComputerPlayer. Tests include:
	//If player has only one matching card it should be returned
	//If players has >1 matching card, returned card should be chosen randomly
	//If player has no matching cards, null is returned

	@Test
	public void disproveSuggestionTest() {
		// Create a computer player and give them a deck with known cards
		ComputerPlayer cpuPlayer = new ComputerPlayer();

		//assign person
		Card person = board.getAllCards().get(0);
		cpuPlayer.addCardToHand(person);
		//assign room
		Card room = board.getAllCards().get(6);
		cpuPlayer.addCardToHand(room);
		//assign weapon
		Card weapon = board.getAllCards().get(20);
		cpuPlayer.addCardToHand(weapon);

		//create suggestion
		Solution suggestion = new Solution(person, room, weapon);

		//player should disprove all randomly
		boolean personToDisprove = false;
		boolean roomToDisprove = false;
		boolean weaponToDisprove = false;

		//make sure we disprove suggestions
		for (int i=0; i<100; i++) {
			//make player disprove
			Card card = cpuPlayer.disproveSuggestion(suggestion);

			if ( card.equals(person) )
				personToDisprove = true;
			else if ( card.equals(room) )
				roomToDisprove = true;
			else if ( card.equals(weapon) )
				weaponToDisprove = true;
		}

		//should have chosen all cards atleast once
		assertTrue(personToDisprove);
		assertTrue(roomToDisprove);
		assertTrue(weaponToDisprove);


		//null returned with no matching cards

		//change up cards after clearing hand so cards don't match
		cpuPlayer.getHand().clear();
		//person
		Card person1 = board.getAllCards().get(1);
		cpuPlayer.addCardToHand(person1);
		//room
		Card room1 = board.getAllCards().get(7);
		cpuPlayer.addCardToHand(room1);
		//weapon
		Card weapon1 = board.getAllCards().get(19);
		cpuPlayer.addCardToHand(weapon1);

		//if suggestion remains same as before should return null
		Card expectNullCard = cpuPlayer.disproveSuggestion(suggestion);
		assertEquals(null, expectNullCard);
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
		//create players, add to short list
		Player cpuPlayerOne = new ComputerPlayer();
		Player cpuPlayerTwo = new ComputerPlayer();
		Player cpuPlayerThree = new ComputerPlayer();
		Player humanPlayerFour = new HumanPlayer();

		ArrayList<Player> listOfPlayers = new ArrayList<Player>();
		listOfPlayers.add(cpuPlayerOne);
		listOfPlayers.add(cpuPlayerTwo);
		listOfPlayers.add(cpuPlayerThree);
		listOfPlayers.add(humanPlayerFour);

		//list of sample cards that can't be disproved
		Card voodo = board.getSpecificCard("Voodoo Mama JuuJuu");
		Card beatrix = board.getSpecificCard("Beatrix Bourbon");
		Card bathroom = board.getSpecificCard("Bathroom,");
		Card rope = board.getSpecificCard("Rope");
		Card wrench = board.getSpecificCard("Wrench");

		//some cards that we can disprove in our tests
		Card room = board.getSpecificCard("Kitchen");
		Card person = board.getSpecificCard("Caleb Crawdad");
		Card weapon = board.getSpecificCard("Revolver");

		//add cards to hand 
		cpuPlayerOne.addCardToHand(voodo);
		cpuPlayerTwo.addCardToHand(beatrix);
		cpuPlayerThree.addCardToHand(bathroom);
		humanPlayerFour.addCardToHand(rope);

		//test 1
		//suggestion no one can disprove returns null
		Solution suggestion = new Solution(room, person, weapon);

		Card returned = board.handleSuggestion(cpuPlayerThree, suggestion, listOfPlayers);

		assertEquals(null, returned);


		//test 2
		//suggestion only accusing player can disprove returns null
		cpuPlayerThree.getHand().clear();
		cpuPlayerThree.addCardToHand(room);

		//accusing player has room, no other disproving cards
		returned = board.handleSuggestion(cpuPlayerThree, suggestion, listOfPlayers);

		//ensure null return
		assertEquals(null, returned);
		assertTrue(cpuPlayerThree.getHand().contains(room));


		//test 3
		//suggestion only human can disprove returns answer (card that disproves suggestion)
		cpuPlayerThree.getHand().clear();
		cpuPlayerThree.addCardToHand(bathroom);

		//human has only disproving card
		humanPlayerFour.getHand().clear();
		humanPlayerFour.addCardToHand(weapon);

		returned = board.handleSuggestion(cpuPlayerThree, suggestion, listOfPlayers);
		assertEquals(weapon, returned);


		//test 4
		//suggestion only human can disproves but human is accuser returns null
		//human still has disproving card
		returned = board.handleSuggestion(humanPlayerFour, suggestion, listOfPlayers);
		assertEquals(null, returned);
		assertTrue(humanPlayerFour.getHand().contains(weapon));


		//test 5
		//suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
		humanPlayerFour.getHand().clear();
		humanPlayerFour.addCardToHand(beatrix);
		cpuPlayerOne.addCardToHand(person);
		cpuPlayerTwo.addCardToHand(weapon);

		//first player should disprove, second player has disproving card too
		returned = board.handleSuggestion(cpuPlayerThree, suggestion, listOfPlayers);
		assertEquals(person, returned);
		assertTrue(cpuPlayerTwo.getHand().contains(weapon));


		//test 6
		//suggestion that human and another player can disprove, other player is next in list, 
		//ensure other player returns answer. playerThree has card, as does human
		cpuPlayerOne.getHand().clear();
		cpuPlayerOne.addCardToHand(wrench);
		cpuPlayerTwo.getHand().clear();
		cpuPlayerTwo.addCardToHand(voodo);
		cpuPlayerThree.addCardToHand(person);
		humanPlayerFour.addCardToHand(room);

		returned = board.handleSuggestion(cpuPlayerTwo, suggestion, listOfPlayers);

		assertEquals(person, returned);
		assertTrue(humanPlayerFour.getHand().contains(room));
	}
}
