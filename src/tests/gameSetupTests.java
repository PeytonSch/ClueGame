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
import clueGame.Player;

public class gameSetupTests {
private static Board board;
	
	//do once before running any tests
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {
		board = Board.getInstance();
		
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
		ArrayList<Player> players = board.getPlayers();
		
		Player humanPlayer = players.get(1);
		//test that the correct number of players was loaded
		assertTrue(players.size() == 6);
		/*
		 * The following test will test different players for correct 
		 * Name, Color, Human/Computer, Start Location
		 */
		
		
		//test the human player
		assertTrue(humanPlayer.getColor() == Color.GREEN);
		assertTrue(humanPlayer.getName() == "Beatrix Bourbon");
		assertTrue(humanPlayer.getType() == "Human");
		assertTrue(humanPlayer.getStartLocation() == board.getCellAt(5, 5));
		
		//test the first player in file 
		assertTrue(players.get(0).getColor() == Color.RED);
		assertTrue(players.get(0).getName() == "Voodoo Mama JuuJuu");
		assertTrue(players.get(0).getType() == "CPU");
		assertTrue(players.get(0).getStartLocation() == board.getCellAt(1, 1));
		//test the third player in file
		assertTrue(players.get(2).getColor() == Color.PINK);
		assertTrue(players.get(2).getName() == "Naughty Nellie Nutmeg");
		assertTrue(players.get(2).getType() == "CPU");
		assertTrue(players.get(2).getStartLocation() == board.getCellAt(6, 7));
		//test the last player in file
		assertTrue(players.get(players.size()).getColor() == Color.GRAY);
		assertTrue(players.get(players.size()).getName() == "Deb U. Taunt");
		assertTrue(players.get(players.size()).getType() == "CPU");
		assertTrue(players.get(players.size()).getStartLocation() == board.getCellAt(13, 7));
	}
	
	
	
	

}
