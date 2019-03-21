package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
		
		//test that the correct number of players was loaded
		
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
		
		//test the third player in file
		
		//test the last player in file
	}
	
	
	
	

}
