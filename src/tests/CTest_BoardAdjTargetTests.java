package tests;

import java.util.HashSet;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CTest_BoardAdjTargetTests {

	//constants to test correct file loading
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 23;

	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameLayout.csv", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(board.getCellAt(0, 0));
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.getCellAt(2, 4));
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(board.getCellAt(16, 8));
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.getCellAt(2, 10));
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(board.getCellAt(3, 10));
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.getCellAt(10, 20));
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(board.getCellAt(9, 4));
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 5)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(board.getCellAt(22, 6));
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(22, 5)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.getCellAt(4, 10));
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 10)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.getCellAt(16, 9));
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 9)));
		//TEST DOORWAY LEFT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(board.getCellAt(4, 17));
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 16)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(board.getCellAt(9, 5));
		assertTrue(testList.contains(board.getCellAt(9, 4)));
		assertTrue(testList.contains(board.getCellAt(9, 6)));
		assertTrue(testList.contains(board.getCellAt(8, 5)));
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.getCellAt(5, 10));
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		assertTrue(testList.contains(board.getCellAt(5, 11)));
		assertTrue(testList.contains(board.getCellAt(6, 10)));
		assertTrue(testList.contains(board.getCellAt(4, 10)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.getCellAt(18, 20));
		assertTrue(testList.contains(board.getCellAt(18, 19)));
		assertTrue(testList.contains(board.getCellAt(18, 21)));
		assertTrue(testList.contains(board.getCellAt(19, 20)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(board.getCellAt(15, 9));
		assertTrue(testList.contains(board.getCellAt(15, 10)));
		assertTrue(testList.contains(board.getCellAt(15, 8)));
		assertTrue(testList.contains(board.getCellAt(14, 9)));
		assertTrue(testList.contains(board.getCellAt(16, 9)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(board.getCellAt(0, 5));
		assertTrue(testList.contains(board.getCellAt(1, 5)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(board.getCellAt(13, 0));
		assertTrue(testList.contains(board.getCellAt(13, 1)));
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		assertTrue(testList.contains(board.getCellAt(14, 0)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(board.getCellAt(22, 19));
		assertTrue(testList.contains(board.getCellAt(22, 20)));
		assertTrue(testList.contains(board.getCellAt(21, 19)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.getCellAt(3, 5));
		assertTrue(testList.contains(board.getCellAt(3, 4)));
		assertTrue(testList.contains(board.getCellAt(3, 6)));
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		assertTrue(testList.contains(board.getCellAt(4, 5)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(board.getCellAt(22, 12));
		assertTrue(testList.contains(board.getCellAt(21, 12)));
		assertEquals(1, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(board.getCellAt(8, 22));
		assertTrue(testList.contains(board.getCellAt(8, 21)));
		assertTrue(testList.contains(board.getCellAt(9, 22)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(board.getCellAt(5, 17));
		assertTrue(testList.contains(board.getCellAt(5, 16)));
		assertTrue(testList.contains(board.getCellAt(5, 18)));
		assertTrue(testList.contains(board.getCellAt(6, 17)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(board.getCellAt(22, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 19)));
		assertTrue(targets.contains(board.getCellAt(21, 20)));	
		
		board.calcTargets(board.getCellAt(22, 5), 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 5)));
		assertTrue(targets.contains(board.getCellAt(22, 4)));	
		assertTrue(targets.contains(board.getCellAt(22, 6)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(board.getCellAt(22, 12), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 12)));
		assertTrue(targets.contains(board.getCellAt(21, 11)));
		
		board.calcTargets(board.getCellAt(22, 5), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 5)));
		assertTrue(targets.contains(board.getCellAt(22, 3)));	
		assertTrue(targets.contains(board.getCellAt(22, 6)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(board.getCellAt(22, 5), 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 5)));
		assertTrue(targets.contains(board.getCellAt(22, 3)));	
		assertTrue(targets.contains(board.getCellAt(22, 6)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(board.getCellAt(0, 5), 4);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 4)));
		assertTrue(targets.contains(board.getCellAt(3, 6)));	
		assertTrue(targets.contains(board.getCellAt(4, 5)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(board.getCellAt(9, 22), 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 19)));
		assertTrue(targets.contains(board.getCellAt(7, 21)));	
		assertTrue(targets.contains(board.getCellAt(6, 19)));	
		assertTrue(targets.contains(board.getCellAt(8, 19)));	
		assertTrue(targets.contains(board.getCellAt(9, 20)));	
		assertTrue(targets.contains(board.getCellAt(10, 21)));	
		assertTrue(targets.contains(board.getCellAt(8, 21)));
		assertTrue(targets.contains(board.getCellAt(10, 19)));
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(board.getCellAt(9, 22), 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// directly left (can't go right 2 steps)
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(18, 17)));
		assertTrue(targets.contains(board.getCellAt(18, 15)));
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(16, 15)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(board.getCellAt(21, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(15, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		// directly right (can't go left)
		assertTrue(targets.contains(board.getCellAt(12, 10)));
		// right then down
		assertTrue(targets.contains(board.getCellAt(13, 9)));
		assertTrue(targets.contains(board.getCellAt(13, 7)));
		// down then left/right
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 8)));
		// right then up
		assertTrue(targets.contains(board.getCellAt(10, 8)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 6)));		
		// 
		assertTrue(targets.contains(board.getCellAt(11, 7)));		
		assertTrue(targets.contains(board.getCellAt(12, 8)));		
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(board.getCellAt(3, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 4)));
		// Take two steps
		board.calcTargets(board.getCellAt(3, 3), 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 5)));
		assertTrue(targets.contains(board.getCellAt(4, 4)));
	}

}