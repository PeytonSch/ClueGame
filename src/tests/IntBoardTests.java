package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	
	private IntBoard board;
	
	@Before
	public void beforeAll() {
		board = new IntBoard();
	}
	
	
	//test adj list of top left corner
	@Test
	public void topLeftCorner() {
		BoardCell testCell = board.getCell(0, 0);
		Set<BoardCell> testList = board.getAdjList(testCell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertEquals(testList.size(), 2);
	}
	
	@Test
	public void bottomRightCorner() {
		BoardCell testCell = board.getCell(3, 3);
		Set<BoardCell> testList = board.getAdjList(testCell);
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(testList.size(), 2);
	}
	
	@Test
	public void rightEdge() {
		BoardCell testCell = board.getCell(3, 1);
		Set<BoardCell> testList = board.getAdjList(testCell);
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(testList.size(), 3);
		
	}
	
	@Test
	public void leftEdge() {
		BoardCell testCell = board.getCell(0, 1);
		Set<BoardCell> testList = board.getAdjList(testCell);
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertEquals(testList.size(), 3);
	}
	
	@Test
	public void secondColMiddle() {
		
	}
	
	@Test
	public void secondFromLastColMiddle() {
		
	}
	

}
