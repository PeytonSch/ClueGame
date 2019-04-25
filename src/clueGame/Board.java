/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.BoardCell;

public class Board extends JPanel {

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
	private String weaponConfigFile;
	private Set<BoardCell> visited;
	private ArrayList<Player> players;
	private ArrayList<Card> cardDeck;
	private Set<Card> playerCards;
	private boolean isHumanPlayer = false;
	private boolean correctGuess;
	private Card disproven;

	public Set<Card> getPlayerCards() {
		return playerCards;
	}
	public Card getRoom(Player player) {
		return getRoomWithInitial(getCellAt(player.getRow(), player.getCol()).getInitial());
	}
	public Set<Card> getPeople(){
		return playerCards;
	}

	private ArrayList<Card> allWeaponCards;
	public ArrayList<Card> getAllWeaponCards() {
		return allWeaponCards;
	}
	private ArrayList<Card> weaponCards;
	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}
	public Set<Card>getWeapons() {
		Set <Card >weapons = new HashSet<Card>();
		weapons.addAll(weaponCards);
		return weapons;
	}

	private ArrayList<Card> roomCards;
	private Solution solution;
	private ArrayList<Card> allCards;


	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public Solution getSolution() {
		return solution;
	}

	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		//initialize data structures 
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		players = new ArrayList<Player>();
		playerCards = new HashSet<Card>();
		weaponCards = new ArrayList<Card>();
		allWeaponCards = new ArrayList<Card>(); //this list does not empty when dealing
		roomCards = new ArrayList<Card>();
		cardDeck = new ArrayList<Card>();
		allCards = new ArrayList<Card>();


		//setting this file to a default for our board because some of the given tests do not specify a file
		//and therefore do not run. We are not allowed to change the test they give us and if the graders use a 
		//script with unchanged tests we still need this to run for the old test. Therefore I am just setting
		//a default file so that the initialize() method will run correctly on older test written by the instructor.
		//these will always be overwritten outside of instructor provided tests
		playerConfigFile = "PlayerConfig.txt";
		weaponConfigFile = "WeaponsConfig.txt";
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
		try {
			loadPlayerConfig();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}
		try {
			loadWeaponConfig();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}


		//Calculates room adj
		calcAdjacencies();
		insertCardsIntoCardDeck();
		dealCards();
		ControlGui g = new ControlGui();

	}

	//gives cards to all players and assigns solution
	public void dealCards() {

		String roomCard = null;
		String personCard = null;
		String weaponCard = null;


		boolean hasRoom = false;
		boolean hasPerson = false;
		boolean hasWeapon = false;

		// 3 cards randomly to each player

		//while solution is not complete
		//pick a random card from the deck

		while ( !hasRoom || !hasPerson || !hasWeapon ) {
			Card next = cardDeck.get((int)(Math.random() * cardDeck.size()));

			//assign solution based on card type for all
			if (!hasRoom && next.getCardType() == CardType.ROOM) {
				roomCard = next.getName();
				cardDeck.remove(next);
				hasRoom = true;
			}
			else if ( !hasPerson  && next.getCardType() == CardType.PERSON) {
				personCard = next.getName();
				cardDeck.remove(next);
				hasPerson = true;
			}
			else if ( !hasWeapon && next.getCardType() == CardType.WEAPON) {
				weaponCard = next.getName();
				cardDeck.remove(next);
				hasWeapon = true;
			}
		}

		//these variables hold the solution we picked
		Card roomSolution = new Card(roomCard, CardType.ROOM);
		Card personSolution = new Card(personCard, CardType.PERSON);
		Card weaponSolution = new Card(weaponCard, CardType.WEAPON);

		//System.out.println("SOLUTION: " + roomCard + personCard + weaponCard);

		setSolution(new Solution(roomSolution, personSolution, weaponSolution));
		//move on to dealing deck if person, weapon and room are filled


		// While deck is not empty, assign card at random to a player,
		// move on to next one. Removes card after assignment


		for(Player p : players) {
			for(int i =0; i < 3; i++) {
				Card randomCard = cardDeck.get((int)(Math.random() * cardDeck.size()));
				p.giveCard(randomCard);
				p.addCardToListOfCardsAllreadySeen(randomCard);
				//System.out.println(p.getName() + " given " + randomCard.getName() + " card");
				cardDeck.remove(randomCard);
			}
		}
	}

	//add all cards into one deck
	private void insertCardsIntoCardDeck() {
		cardDeck.addAll(playerCards);
		cardDeck.addAll(roomCards);
		cardDeck.addAll(weaponCards);
		allCards.addAll(playerCards);
		allCards.addAll(roomCards);
		allCards.addAll(weaponCards);
		allWeaponCards.addAll(weaponCards);

	}

	private void loadWeaponConfig() throws BadConfigFormatException{
		Scanner in = null;
		try {
			FileReader weaponFile = new FileReader(weaponConfigFile);

			in = new Scanner(weaponFile);

			//Split based on commas with limit equal to 2 commas
			while(in.hasNextLine()){
				String weapon = in.nextLine();
				weaponCards.add(new Card(weapon, CardType.WEAPON));
			}

		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		finally {
			in.close();
		}

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
				if(type.equals("Card")) {
					roomCards.add(new Card(name, CardType.ROOM));
				}

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
			//System.out.println(numRows);
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
				String colorString = playerFromFile.get(1);
				Color color = convertColor(playerFromFile.get(1));
				String type = playerFromFile.get(2);
				int startX = Integer.parseInt(playerFromFile.get(3));
				int startY = Integer.parseInt(playerFromFile.get(4));
				BoardCell cell = board[startX][startY];

				//throw exception if key name or card type isn't what we expect
				if(!type.equals("Human") && !type.equals("CPU")) {
					throw new BadConfigFormatException();
				}
				else if (type.equals("Human")) {
					HumanPlayer p = new HumanPlayer(name, color, colorString, type, cell);
					players.add(p);
					playerCards.add(new Card(name, CardType.PERSON));
				}
				else if (type.equals("CPU")) {
					ComputerPlayer p = new ComputerPlayer(name, color, colorString, type, cell);
					players.add(p);
					playerCards.add(new Card(name, CardType.PERSON));
				}
				else {
					System.out.println("ERROR IN DETERMINING PLAYER TYPE");
					throw new BadConfigFormatException();
				}
				//				Player p = new Player(name, color, colorString, type, cell);
				//				players.add(p);
				//				playerCards.add(new Card(name, CardType.PERSON));

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
				if (board[i][j].isRoom() && !board[i][j].isDoorway()) {

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

	public boolean checkAccusaton(Solution accusation) {
		if (accusation.isEqualTo(solution)) {
			return true;
		}
		else {
			return false;
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



	public void setAllConfigFiles(String boardConfigString, String roomConfigString, String playerConfig, String weaponConfig) {
		setBoardConfigFile(boardConfigString);
		setRoomConfigFile(roomConfigString);
		setPlayerFile(playerConfig);
		setWeaponConfigFile(weaponConfig);
	}
	public void setConfigFiles(String boardConfigString, String roomConfigString) {
		setBoardConfigFile(boardConfigString);
		setRoomConfigFile(roomConfigString);

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
	public void setWeaponConfigFile(String configFile) {
		this.weaponConfigFile = configFile;
	}

	public static int getMaxBoardSize() {
		return MAX_BOARD_SIZE;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getCards() {

		return cardDeck;
	}

	public ArrayList<Card> getAllCards() {

		return allCards;
	}

	public Card getRoomWithInitial(char initial) {
		String roomName = legend.get(initial);
		for (Card r : allCards) {
			if (r.getName().equals(roomName)) {
				return r;
			}
		}
		return null;
	}


	//for testing
	public void printTargetCells() {
		System.out.println("Row - Col - Room");
		for (BoardCell cell : targets) {
			System.out.println(cell.getRow() + " - " + cell.getCol()+ " - " + cell.getInitial());
		}
	}
	public Card getSpecificCard(String name) {
		for ( Card c : allCards ) {
			if ( c.getName().equals(name) ) {
				return c;
			}
		}
		// Only returns if name does not match
		return null;
	}

	public Card handleSuggestion(Player currentPlayer, Solution guess, ArrayList<Player> playerList) {

		//locations to loop through players
		int end = playerList.indexOf(currentPlayer);
		int start = end + 1;

		// wrapping loop for players
		while ( start != end ) {
			if (start >= playerList.size() ) {
				start = 0;
			}

			// if player can disprove, return card
			Player player = playerList.get(start);
			Card returnedCard;
			if(player instanceof ComputerPlayer) {
				returnedCard = ((ComputerPlayer)player).disproveSuggestion(guess);
			}
			else if(player instanceof HumanPlayer) {
				returnedCard = ((HumanPlayer)player).disproveSuggestion(guess);
			}
			else {
				System.out.println("ERROR PLAYER NOT HUMAN OR COMPUTER WHEN CREATING SUGGESTION");
				returnedCard = null;
			}

			if ( returnedCard != null ) {
				//add to list of cards allready seen
				for (Player p : playerList) {
					p.addCardToListOfCardsAllreadySeen(returnedCard);
				}

				return returnedCard;
			}
			// else go to next player
			start++;
		}		
		// if reach the suggestor, return null
		return null;
	}

	//draw board componenents
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int scale = 23;
		//make frame
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, scale * numColumns, scale * numRows);

		//render board cells
		for (int i = 0; i < getNumRows(); i++) { 
			for(int j = 0; j < getNumColumns(); j++) { 
				getCellAt(i, j).drawCell(g);
			}
		}

		//show targets for human players
		if (isHumanPlayer) {
			for (BoardCell cell : targets) {
				cell.showTargets(g);
			}
			isHumanPlayer = false;
		}



		//room label
		g.setColor(Color.BLUE);
		g.drawString("Library", 15, 70);
		g.drawString("Observatory", 200, 40);
		g.drawString("Hall", 390, 60);
		g.drawString("Bathroom", 465, 70);
		g.drawString("Wine Cellar", 30, 225);
		g.drawString("Art Room", 25, 475);
		g.drawString("Kitchen", 185, 475);
		g.drawString("Garage", 370, 480);
		g.drawString("Sauna", 480, 375);


		//render people
		for (Player person : players) {
			person.drawPlayer(g);
		}
	}


	public void nextPlayer(Player currentPlayer, int roll, ArrayList<Player> playerLust, ControlGui gui) {


		//calc targets for player
		calcTargets(currentPlayer.getRow(), currentPlayer.getCol(), roll);

		//draw the targets and let human decide if its a human
		if (currentPlayer instanceof HumanPlayer) {
			isHumanPlayer = true;
			//for debugging
			//			BoardCell temp = player.pickLocation(targets);
			//			if(temp == null) {
			//				System.out.println("ERROR PART 1");
			//			}
			//for testing
			//player.makeMove(temp);
		}		

		//if its a CPU player then picks location randomly 
		else if (currentPlayer instanceof ComputerPlayer) {
			if (((ComputerPlayer) currentPlayer).getAccuseFlag()) {
				setCorrectGuess(checkAccusaton(((ComputerPlayer) currentPlayer).getSuggestion()));
				return;
			}
			//error checking and testing
			if(targets.size() == 0) {
				Board board = Board.getInstance();
				System.out.println("TARGETS SIZE IS ZERO FOR COMPUTER PLAYER");
				System.out.println("Player: " + currentPlayer.getName());
				System.out.println("Type: " + currentPlayer.getType());
				System.out.println("Color: " + currentPlayer.getColorString());
				System.out.println("Die Roll: " + roll);
				System.out.println("Row/Col: " + currentPlayer.getRow() + " / " + currentPlayer.getCol());
				System.out.println("Current Cell: " + board.getCellAt(currentPlayer.getRow(), currentPlayer.getCol()).isRoom() + " " + board.getCellAt(currentPlayer.getRow(), currentPlayer.getCol()).isDoorway());
				calcTargets(currentPlayer.getRow(), currentPlayer.getCol(), roll);
			}
			currentPlayer.makeMove(currentPlayer.pickLocation(targets));
			BoardCell playerLoc = getCellAt(currentPlayer.getRow(), currentPlayer.getCol());

			if (playerLoc.isRoom()) {
				((ComputerPlayer) currentPlayer).createSuggestion();
				disproven = handleSuggestion(currentPlayer, ((ComputerPlayer) currentPlayer).getSuggestion(), playerLust);

				gui.updateGuessPannel(((ComputerPlayer)currentPlayer).getSuggestion(), disproven);

				//String response = ((ComputerPlayer) player).getSuggestion().getPerson().getName() + " in the " + ((ComputerPlayer) player).getSuggestion().getRoom().getName() + " with the " + ((ComputerPlayer) player).getSuggestion().getWeapon().getName();
				//JOptionPane accusationPane = new JOptionPane();
				//accusationPane.showMessageDialog(new JFrame(), "Suggestion: " + response, player.getName() + " is making an suggestion ", JOptionPane.INFORMATION_MESSAGE);
				if ( disproven == null && !currentPlayer.getCardsAllreadySeen().contains(((ComputerPlayer) currentPlayer).getCurrentRoomCard()) ) {
					((ComputerPlayer)currentPlayer).setAccuseFlag();
					//JOptionPane responsePane = new JOptionPane();
					//responsePane.showMessageDialog(new JFrame(), "No new clue was given","Response", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					//JOptionPane responsePane = new JOptionPane();
					//responsePane.showMessageDialog(new JFrame(), "Clue: " + disproven.getName() ,"Response", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		//for debugging and error catching
		else {
			System.out.println("ERROR not computer or human player");
			System.out.println(currentPlayer.getType());
		}
	}

	public boolean isCorrectGuess() {
		return correctGuess;
	}

	public void setCorrectGuess(boolean correctGuess) {
		this.correctGuess = correctGuess;
	}

	public void revealDeadPlayerCards(Player player) {
		String message = player.getName() + " had the following cards: ";
		for (Card card : player.getHand()) {
			message += (" (" + card.getName() + ")");
			for(Player person : players)
				person.addCardToListOfCardsAllreadySeen(card);
		}
		JOptionPane showCards = new JOptionPane();
		showCards.showMessageDialog(new JFrame(), message, "Revealed Cards", JOptionPane.INFORMATION_MESSAGE);
	}
	



}
