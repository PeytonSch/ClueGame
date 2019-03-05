package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

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


	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());

		//test rooms loaded correctly
		assertEquals("Art Room", legend.get('A'));
		assertEquals("Bathroom", legend.get('B'));
		assertEquals("Garage", legend.get('G'));
		assertEquals("Hall", legend.get('H'));
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Wine Cellar", legend.get('N'));
		assertEquals("Walkway", legend.get('W'));
		assertEquals("Closet", legend.get('X'));

	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(4, 0);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());

	}





}
