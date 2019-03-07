/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class GameBoardConfigTests {

	//constants to test correct file loading
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
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
	public void fourDoorDirections() {
		//DOWN doorway
		BoardCell room = board.getCellAt(4, 0);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());

		//UP doorway
		room = board.getCellAt(6, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());

		//RIGHT doorway
		room = board.getCellAt(3, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());

		//LEFT doorway
		room = board.getCellAt(22, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());

		// Test that room pieces that aren't doors know it
		room = board.getCellAt(20, 8);
		assertFalse(room.isDoorway());	
		
		// Test that walkways are not doors
		room = board.getCellAt(13, 5);
		assertFalse(room.isDoorway());	

	}
	
	// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(26, numDoors);
		}
		
		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('N', board.getCellAt(10, 0).getInitial());
			assertEquals('W', board.getCellAt(4, 13).getInitial());
			assertEquals('O', board.getCellAt(10, 3).getInitial());
			
			// Test last cell in room
			assertEquals('L', board.getCellAt(0, 4).getInitial());
			assertEquals('A', board.getCellAt(22, 0).getInitial());
			assertEquals('S', board.getCellAt(22, 22).getInitial());
			// Test a walkway
			assertEquals('W', board.getCellAt(7, 7).getInitial());
			// Test the kitchen
			assertEquals('K', board.getCellAt(20,7).getInitial());
			
		}





}
