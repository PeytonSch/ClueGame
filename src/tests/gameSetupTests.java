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
	
	
	
	

}
