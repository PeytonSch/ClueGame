/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */

package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class IntBoard {

	
	private BoardCell [] [] grid;
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	
	private Set<BoardCell> targets;
	
	

	public IntBoard() {
		// initialize board
		grid = new BoardCell[4][4];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new BoardCell(i,j);
			}
		}
		calcAdjacencies();
	}

	
	/**
	 * Calculates the list of cells that are adjacent to each cell.
	 */
	private void calcAdjacencies() {
		// add cells to map as keys and add adjacent values if within bounds
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				// fill cells with 0 value
				// create indices for adjacent cells
				int above = i-1;
				int below = i+1;
				int left = j-1;
				int right = j+1;
				
				// create set of adjancent cell and fill with valid cells
				HashSet<BoardCell> adj = new HashSet<BoardCell>();
				if (above >= 0) {
					adj.add(grid[above][j]);
				}
				if (below < grid.length) {
					adj.add(grid[below][j]);
				}
				if (left >= 0) {
					adj.add(grid[i][left]);
				}
				if (right < grid[i].length) {
					adj.add(grid[i][right]);
				}
				
				// add set to map with current cell as key
				adjMtx.put(grid[i][j], adj);
			}
		}
		
	}
	
	/**
	 * returns the list of adjacent cells to a cell
	 * 
	 * @param cell is the cell passed in that we will return the list of adjacent cells too
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
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
		
		return grid[x][y];
	}
	

}
