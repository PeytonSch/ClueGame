/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */

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
	
	
	/*
	 * The tests below test the creation of adjacency lists
	 */
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
		BoardCell testCell = board.getCell(1, 1);
		Set<BoardCell> testList = board.getAdjList(testCell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(testList.size(), 4);
	}
	
	@Test
	public void secondFromLastColMiddle() {
		BoardCell testCell = board.getCell(1, 2);
		Set<BoardCell> testList = board.getAdjList(testCell);
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertEquals(testList.size(), 4);
	}

	
	
	
	/*
	 * The Tests below test target creation
	 */
	
	@Test
	public void targetTest0_0_3() {
		BoardCell testCell = board.getCell(0, 0);
		board.calcTargets(testCell, 3);
		Set<BoardCell> testList = board.getTargets();
		
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(testList.size(), 6);
	}
	
	@Test
	public void targetTest3_3_2() {
		BoardCell testCell = board.getCell(3, 3);
		board.calcTargets(testCell, 2);
		Set<BoardCell> testList = board.getTargets();
		
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertEquals(testList.size(), 3);
	}
	
	@Test
	public void targetTest2_2_1() {
		BoardCell testCell = board.getCell(2, 2);
		board.calcTargets(testCell, 1);
		Set<BoardCell> testList = board.getTargets();
		
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(testList.size(), 4);
	}
	
	@Test
	public void targetTest0_2_2() {
		BoardCell testCell = board.getCell(0, 2);
		board.calcTargets(testCell, 2);
		Set<BoardCell> testList = board.getTargets();
		
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertEquals(testList.size(), 4);
	}
	
	@Test
	public void targetTest1_2_3() {
		BoardCell testCell = board.getCell(1, 2);
		board.calcTargets(testCell, 3);
		Set<BoardCell> testList = board.getTargets();
		
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertEquals(testList.size(), 8);
	}
	
	@Test
	public void targetTest3_1_4() {
		BoardCell testCell = board.getCell(3, 1);
		board.calcTargets(testCell, 4);
		Set<BoardCell> testList = board.getTargets();
		
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertEquals(testList.size(), 7);
	}
	
	
	
	

}
