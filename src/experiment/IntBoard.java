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

	private void calcAdjacencies() {
		// TODO Auto-generated method stub
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		
		
		return null;
	}
	
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets() {
		
		return targets;
	}
	
	public BoardCell getCell(int x, int y) {
		
		return null;
	}
	

}
