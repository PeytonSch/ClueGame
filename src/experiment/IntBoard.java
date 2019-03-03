/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */

package experiment;

import java.util.Map;
import java.util.Set;


public class IntBoard {

	
	private BoardCell [] [] grid;
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	
	private Set<BoardCell> targets;
	
	

	public IntBoard() {
		calcAdjacencies();
	}

	
	/**
	 * Calculates the list of cells that are adjacent to each cell.
	 */
	private void calcAdjacencies() {
		// TODO Auto-generated method stub
		return;
	}
	
	/**
	 * returns the list of adjacent cells to a cell
	 * 
	 * @param cell is the cell passed in that we will return the list of adjacent cells too
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		
		
		return null;
	}
	
	
	/**
	 * calculates all available target cells to move to given a number of moves
	 * 
	 * @param startCell cell that we start counting from (cell that piece is on)
	 * @param pathLength die roll, how many places we can move
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		
		return;
	}
	
	/**
	 * returns the set of target cells formed from calcTargets that we can move to
	 */
	public Set<BoardCell> getTargets() {
		
		return null;
	}
	
	/**
	 * returns a cell at a given row and column
	 * 
	 * @param x = row
	 * @param y = column
	 */
	public BoardCell getCell(int x, int y) {
		
		return null;
	}
	

}
