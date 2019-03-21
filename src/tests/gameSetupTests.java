package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class gameSetupTests {
private static Board board;
	
	//do once before running any tests
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		private Set<Player> players = board.getPlayers();
		board.setPlayerFile("PlayersConfig.txt");
		board.initialize();
	}
	//do before each test
	@Before
	public void reset() {
		
	}
	
	
	//test loading the people 
	@Test
	public void testPlayersLoaded() {
		Player humanPlayer = players.get(1);
		//test that the correct number of players was loaded
		assertTrue(players.length == 6);
		/*
		 * The following test will test different players for correct 
		 * Name, Color, Human/Computer, Start Location
		 */
		
		
		//test the human player
		assertTrue(humanPlayer.getColor == color);
		assertTrue(humanPlayer.getName == name)
		assertTrue(humanPlayer.getTypeOfPlayer == human)
		assertTrue(humanPlayer.getStartLocation == aLocation)
		
		//test the first player in file 
		assertTrue(players.get(0).getColor == color);
		assertTrue(players.get(0).getName == name)
		assertTrue(players.get(0).getTypeOfPlayer == human)
		assertTrue(players.get(0).getStartLocation == aLocation)
		//test the third player in file
		assertTrue(players.get(2).getColor == color);
		assertTrue(players.get(2).getName == name)
		assertTrue(players.get(2).getTypeOfPlayer == human)
		assertTrue(players.get(2).getStartLocation == aLocation)
		//test the last player in file
		assertTrue(players.get(players.length).getColor == color);
		assertTrue(players.get(players.length).getName == name)
		assertTrue(players.get(players.length).getTypeOfPlayer == human)
		assertTrue(players.get(players.length).getStartLocation == aLocation)
	}
	
	
	
	

}
