package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	
	private BoardCell [] [] grid;
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	
	

	public IntBoard() {
		calcAdjacencies();
	}

	private void calcAdjacencies() {
		// TODO Auto-generated method stub
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		
		
		return null;
	}
	
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets() {
		
		return null;
	}
	
	public BoardCell getCell(int x, int y) {
		
		return null;
	}
	

}
