package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class GameBoardConfigTests {

	//constants to test correct file loading
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;

	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();

		//using our teams files
		board.setConfigFiles("ClueGameLayout.csv","ClueRooms.txt");
		//load both config files
		board.initialize();
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}




}
