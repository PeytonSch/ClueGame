/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class Board {
	
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private static Board instance = new Board();
	
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		
	}
	
	public static Board getInstance() {
		return instance;
	}
	
	public void initialize() {
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}
				
		//Calculates room adj
		calcAdjacencies();
	}
	
	public void loadRoomConfig() throws BadConfigFormatException{
		
	}
	
	public void loadBoardConfig() throws BadConfigFormatException{
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	public BoardCell[][] getBoard() {
		return board;
	}

	public void setBoard(BoardCell[][] board) {
		this.board = board;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}

	public Map<BoardCell, Set<BoardCell>> getAdjMtx() {
		return adjMtx;
	}

	public void setAdjMtx(Map<BoardCell, Set<BoardCell>> adjMtx) {
		this.adjMtx = adjMtx;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void setTargets(Set<BoardCell> targets) {
		this.targets = targets;
	}

	public String getBoardConfigFile() {
		return boardConfigFile;
	}
	
	public void setConfigFiles(String string, String string2) {
		setBoardConfigFile(string);
		setRoomConfigFile(string2);
	}
	public void setBoardConfigFile(String boardConfigFile) {
		this.boardConfigFile = boardConfigFile;
	}

	public String getRoomConfigFile() {
		return roomConfigFile;
	}

	public void setRoomConfigFile(String roomConfigFile) {
		this.roomConfigFile = roomConfigFile;
	}

	public static int getMaxBoardSize() {
		return MAX_BOARD_SIZE;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	
	

}
