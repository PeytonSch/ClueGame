/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
	private String playerConfigFile;
	private Set<BoardCell> visited;
	private Set<Player> players;

	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		//initialize data structures 
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		players = new HashSet<Player>();
	}

	/**
	 * Sets up configurations and catches errors doing so
	 * calculates room adj
	 * @throws BadConfigFormatException 
	 */
	public void initialize() throws BadConfigFormatException {
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

	/**
	 * loads room config by reading legend.txt, throws BadConfigFormatException if there are
	 * errors within the file
	 * @throws BadConfigFormatException
	 */
	public void loadRoomConfig() throws BadConfigFormatException{
		//take legend.txt and put into legend
		Scanner in = null;
		try {
			FileReader legendFile = new FileReader(roomConfigFile);
			in = new Scanner(legendFile);

			//read in file
			while(in.hasNext()) {
				String line = in.nextLine();

				List<String> legendFromFile = Arrays.asList(line.split(", "));

				//test key, name, card type
				char key = legendFromFile.get(0).charAt(0);
				String name = legendFromFile.get(1);
				String type = legendFromFile.get(2);

				//throw exception if key name or card type isn't what we expect
				if(!type.equals("Other") && !type.equals("Card")) {
					throw new BadConfigFormatException();
				}
				legend.put(key, name);

			}

		}
		catch (FileNotFoundException e) {
			e.getMessage();
		}
		finally {
			in.close();
		}

	}

	/**
	 * Loads board config by reading csv file, throws BadConfigFormatException
	 * if there is an error in file format
	 * @throws BadConfigFormatException
	 */
	public void loadBoardConfig() throws BadConfigFormatException {
		Scanner in = null;
		//Read in csv file one letter at a time,
		try {
			FileReader boardConfigInputFile = new FileReader(boardConfigFile);

			in = new Scanner(boardConfigInputFile);
			int rowCount = 0;
			int rowSize = 0;

			//read in file
			while(in.hasNext()) {
				String nextLine = in.nextLine();
				List<String> row = Arrays.asList(nextLine.split(","));
				//set numCols = num of entries in each row
				numColumns = row.size();

				//throw an error if a row has a different size than a prev row
				if ( row.size() < rowSize ) {
					throw new BadConfigFormatException();
				} else {
					rowSize = row.size();
				}

				DoorDirection doorDirection;
				char roomLetter;
				for ( int col = 0; col < row.size(); col++ ) {

					char roomChar;
					roomLetter = row.get(col).charAt(0);

					//throw an error if we have repeat room letters in legend file
					if (!legend.containsKey(roomLetter)) {
						throw new BadConfigFormatException();
					}

					//handle doors ignoring N case
					if ( (row.get(col).length() == 2) && row.get(col).charAt(1) != 'N' ) {
						roomChar = row.get(col).charAt(1);
					}
					else {
						// assign a roomChar for switch statement to
						roomChar = '@';
					}

					switch (roomChar) {
					case 'R':
						doorDirection = DoorDirection.RIGHT;
						break;
					case 'L':
						doorDirection = DoorDirection.LEFT;
						break;
					case 'D':
						doorDirection = DoorDirection.DOWN;
						break;
					case 'U':
						doorDirection = DoorDirection.UP;
						break;
					default:
						doorDirection = DoorDirection.NONE;
						break;
					}
					//create the board from our data by adding Cells
					board[rowCount][col] = new BoardCell(rowCount, col, doorDirection, roomLetter);
				}
				rowCount++;
			}
			//set numRows
			numRows = rowCount;
		} catch (FileNotFoundException e) {
			System.out.println("Load Board Config File Not Found");
			e.getMessage();
		}
		finally {
			in.close();
		}
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException {
		//take legend.txt and put into legend
		Scanner in = null;
		try {
			FileReader playerFile = new FileReader(playerConfigFile);
			in = new Scanner(playerFile);

			//read in file
			while(in.hasNext()) {
				String line = in.nextLine();

				List<String> playerFromFile = Arrays.asList(line.split(", "));

				//test key, name, card type
				String name = playerFromFile.get(0);
				Color color = convertColor(playerFromFile.get(1));
				String type = playerFromFile.get(2);
				int startX = Integer.parseInt(playerFromFile.get(3));
				int startY = Integer.parseInt(playerFromFile.get(4));

				//throw exception if key name or card type isn't what we expect
				if(!type.equals("Other") && !type.equals("Card")) {
					throw new BadConfigFormatException();
				}
				Player p = new Player(name, color, type, startX, startY);
				players.add(p);

			}

		}
		catch (FileNotFoundException e) {
			e.getMessage();
		}
		finally {
			in.close();
		}
	}

	private Color convertColor(String string) {
		Color color; 
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(string.trim()); 
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}

	/**
	 * Calculates the list of cells that are adjacent to each cell.
	 * @throws BadConfigFormatException 
	 */
	private void calcAdjacencies() throws BadConfigFormatException {
		// add cells to map as keys and add adjacent values if within bounds
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {

				// if cell is in a room and not a door, add empty adjacent matrix, showing no moves
				if (board[i][j].isRoom()) {

					// create empty set
					HashSet<BoardCell> adj = new HashSet<BoardCell>();
					//add to adjMtx map <BoardCell, Set<BoardCell>>
					adjMtx.put(board[i][j], adj);
				}

				// if doorway, add cell in direction and break
				else if (board[i][j].isDoorway()) {

					//add to adjMtx map <BoardCell, Set<BoardCell>>
					adjMtx.put(board[i][j], doorAdjacents(i,j));
				}

				// if walkway, test surrounding cells thru walkwayAdjacents
				else if (board[i][j].isWalkway()) {

					//add to adjMtx map <BoardCell, Set<BoardCell>>
					adjMtx.put(board[i][j], walkwayAdjacents(i,j));
				}

				// if not a walkway, door, or room, the cell is not correctly formatted
				else {
					String msg = "Config Error: Cell (" + i + "," + j + ") not a Room, Door, or Walkway.";
					throw new BadConfigFormatException(msg);

				}


			}
		}

	}

	public Set<BoardCell> doorAdjacents(int i, int j){

		// create surrounding indices for easy switches
		// also helps read code and get meaning
		int above = i-1;
		int below = i+1;
		int left = j-1;
		int right = j+1;

		// initialize HashSet for adjacent BoardCells
		HashSet<BoardCell> adj = new HashSet<BoardCell>();

		// use a switch with DoorDirection enum to know which way we can exit
		switch (board[i][j].getDoorDirection()) {
		case RIGHT:
			adj.add(board[i][right]);
			break;
		case LEFT:
			adj.add(board[i][left]);
			break;
		case DOWN:
			adj.add(board[below][j]);
			break;
		case UP:
			adj.add(board[above][j]);
			break;
		default:
			break;
		}		
		// return cell as a set for adjMap purposes
		return adj;
	}

	public Set<BoardCell> walkwayAdjacents(int i, int j){

		// create surrounding indices for easy switches
		// also helps read code and get meaning
		int above = i-1;
		int below = i+1;
		int left = j-1;
		int right = j+1;

		// initialize HashSet for adjacent BoardCells
		HashSet<BoardCell> adj = new HashSet<BoardCell>();

		// confirm cell is on the board (greater or equal to index 0)
		if (above >= 0) {

			// check if it's a door or a room, and, if door, faces the right direction
			if (board[above][j].isDoorway() || board[above][j].isRoom()) {
				if (board[above][j].getDoorDirection() == DoorDirection.DOWN) {
					adj.add(board[above][j]);
				}
			}
			// else, is a walkway and a valid adjacent cell
			else {
				adj.add(board[above][j]);
			}
		}
		// confirm cell is on the board (less than length index board.length)
		if (below < numRows) {

			// check if it's a door or a room, and, if door, faces the right direction
			if (board[below][j].isDoorway() || board[below][j].isRoom()) {
				if (board[below][j].getDoorDirection() == DoorDirection.UP) {
					adj.add(board[below][j]);
				}
			}
			// else, is a walkway and a valid adjacent cell
			else {
				adj.add(board[below][j]);
			}
		}
		// confirm cell is on the board (greater or equal to index 0)
		if (left >= 0) {

			// check if it's a door or a room, and, if door, faces the right direction
			if (board[i][left].isDoorway() || board[i][left].isRoom()) {
				if (board[i][left].getDoorDirection() == DoorDirection.RIGHT) {
					adj.add(board[i][left]);
				}
			}
			// else, is a walkway and a valid adjacent cell
			else {
				adj.add(board[i][left]);
			}
		}
		// confirm cell is on the board (less than length index board.length)
		if (right < numColumns) {

			// check if it's a door or a room, and, if door, faces the right direction
			if (board[i][right].isDoorway() || board[i][right].isRoom()) {
				if (board[i][right].getDoorDirection() == DoorDirection.LEFT) {
					adj.add(board[i][right]);
				}
			}
			// else, is a walkway and a valid adjacent cell
			else {
				adj.add(board[i][right]);
			}
		}

		return adj;

	}

	/**
	 * calculates all available target cells to move to given a number of moves
	 * 
	 * @param startCell cell that we start counting from (cell that piece is on)
	 * @param pathLength die roll, how many places we can move
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		//clear visited and targets to make sure they apply to this start cell and path length
		visited.clear();
		targets.clear();

		//add start location to visited so we don't cycle through this cell
		visited.add(startCell);

		//call recursive function to find all targets
		findTargets(startCell, pathLength);
	}

	public void calcTargets(int i, int j, int pathLength) {
		BoardCell cell = board[i][j];
		calcTargets(cell, pathLength);
	}

	public void findTargets(BoardCell startCell, int movesLeft) {
		//for each adjCell in adjacentCells

		for(BoardCell cell : getAdjList(startCell)) {

			//if already in visited list, skip rest of this
			if(!visited.contains(cell)) {

				//add adjCell to visited list
				visited.add(cell);

				//if doorway with moves remaining
				if (cell.isDoorway()) {
					targets.add(cell);
				}

				//if numSteps == 1, add adjCell to Targets
				if(movesLeft == 1) {
					targets.add(cell);
				}
				//else call findAllTargets with adjCell, numSteps-1
				else {
					findTargets(cell, movesLeft-1);
				}

				//remove adjCell from visited list
				visited.remove(cell);
			}
		}
	}

	public static Board getInstance() {
		return instance;
	}

	/**
	 * returns the list of adjacent cells to a cell
	 * 
	 * @param cell is the cell passed in that we will return the list of adjacent cells too
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	public Set<BoardCell> getAdjList(int i, int j) {
		BoardCell cell = board[i][j];
		return adjMtx.get(cell);
	}

	/**
	 * returns the set of target cells formed from calcTargets that we can move to
	 */
	public Set<BoardCell> getTargets() {

		return targets;
	}

	public int getNumRows() {
		return numRows;
	}


	public int getNumColumns() {
		return numColumns;
	}


	public BoardCell[][] getBoard() {
		return board;
	}



	public Map<Character, String> getLegend() {
		return legend;
	}


	public Map<BoardCell, Set<BoardCell>> getAdjMtx() {
		return adjMtx;
	}



	public void setConfigFiles(String string, String string2) {
		setBoardConfigFile(string);
		setRoomConfigFile(string2);
	}
	public void setBoardConfigFile(String boardConfigFile) {
		this.boardConfigFile = boardConfigFile;
	}


	public void setRoomConfigFile(String roomConfigFile) {
		this.roomConfigFile = roomConfigFile;
	}
	
	public void setPlayerFile(String playerConfigFile) {
		this.playerConfigFile = playerConfigFile;
	}

	public static int getMaxBoardSize() {
		return MAX_BOARD_SIZE;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
	public Set<Player> getPlayers() {
		return players;
	}
	
}
