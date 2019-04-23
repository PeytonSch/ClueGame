/*
 * Authors:
 * James Hawn
 * Peyton Scherschel
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame {

	private static String userName;
	private boolean humanTurnComplete = false;
	private int counter = 1; //human starts first (index 1 in config file)
	private Player player;
	private static String completeTurnMessage = "Turn not finished";
	private static String wrongLocationMessage = "Invalid location selected";
	private static String errorMessage = "Error";
	private static String wrongAnswerTitle = "Incorrect Accusation : Player Lost";
	private static String wrongAnswerMsg = " submitted an incorrect accusation. The player has been removed from the game.";
	private static String unableToMakeAccusation = "You can only make an accusation when inside of a room.";
	private static String winTitle = "Winner!";
	private static String exitTitle = "Game Over";
	private static String exitMsg = "Thank you for playing, please exit the game at this time. Restart if you wish to play again.";
	private static String poorlyTimedAccusation = "You can only make an accusation at the beginning of your turn.";
	private GuessDialog guessDialog;
	private String response;
	private String cpuWins;

	private ArrayList<Player> playerList;
	private Board board;
	private ControlGui gui;
	private Player user;
	private boolean firstIteration = true;
	private int dieNum;
	private boolean gameWon = false; //
	private boolean humanGuessSubmitted = false; //
	private String humanWins; //

	public ClueGame() throws BadConfigFormatException {
		board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();

		//gui is control panel at bottom
		gui = new ControlGui();
		gui.next.addActionListener(new NextPlayerButtonListener());
		gui.accuse.addActionListener(new accusationListener());
		addMouseListener(new MouseClickTarget());
		FileDropdown menu = new FileDropdown();
		//set close op
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window size
		setSize(1000,1000);
		//title
		setTitle("Clue Game");

		//create Player Cards JPanel and add the JFrame
		playerList = board.getPlayers();
		JPanel playerCards = createPlayerCardsPanel(playerList);
		add(playerCards, BorderLayout.EAST);

		//add board to center and control gui to bottom
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		//add menu bar
		setJMenuBar(menu);
		player = playerList.get(1);
		if (player instanceof HumanPlayer) {
			System.out.println("Human");
		}
		else if (player instanceof ComputerPlayer) {
			System.out.println("CPU");
		}
		else {
			System.out.println("ERROR");
		}
	}

	private JPanel createPlayerCardsPanel(ArrayList<Player> players) {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(3,1)); 

		//double randomPlayer = Math.random() * players.size();
		//user = players.get((int)randomPlayer);
		user = players.get(1);
		//user = players.get(1);
		HashSet<Card> playerCardSet = (HashSet<Card>) user.getHand();


		JLabel personName = null;
		JLabel roomName = null;
		JLabel weaponName = null;


		personName = new JLabel(user.getName());
		userName = user.getName();

		for (Card card : playerCardSet) {
			if (card.getCardType() == CardType.ROOM) {
				roomName = new JLabel(card.getName());
				JPanel room = new JPanel();
				room.setLayout(new GridLayout(2,1));      
				room.setBorder(new TitledBorder ("Room"));
				room.add(roomName);
				panel.add(room);
			}
			else if (card.getCardType() == CardType.WEAPON) {
				weaponName = new JLabel(card.getName());
				JPanel weapon = new JPanel();
				weapon.setLayout(new GridLayout(2,1));      
				weapon.setBorder(new TitledBorder ("Weapon"));
				weapon.add(weaponName);
				panel.add(weapon);
			}
			else {
				JPanel person = new JPanel();
				person.setLayout(new GridLayout(2,1));      
				person.setBorder(new TitledBorder ("Person"));
				person.add(personName);
				panel.add(person);
			}
		}

		panel.setBorder(new TitledBorder ("My Cards"));
		return panel;
	}

	// Next Player listener
	public class NextPlayerButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(!firstIteration) {
				if (!humanTurnComplete && player instanceof HumanPlayer) {
					JOptionPane errorPane = new JOptionPane();
					errorPane.showMessageDialog(new JFrame(), completeTurnMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			firstIteration = false;



			//reset turn complete
			humanTurnComplete = false;


			//go through playerList
			if (counter >= playerList.size()) counter = 0;
			player = playerList.get(counter);


			//roll dice
			double dieRoll = Math.floor(Math.random() * Math.floor(6)) + 1;
			dieNum = (int) dieRoll;

			//refresh gui
			gui.refreshGui(player, dieNum);
			
			//Handle computer accusation before movement
			if (counter != 1 && player.getCurrentlyInRoom()) {
				if (((ComputerPlayer) player).getAccuseFlag()) {
					if (board.checkAccusaton(((ComputerPlayer) player).getSuggestion()) ) {
						//Comp player wins
						response = ((ComputerPlayer) player).getSuggestion().toString();
						cpuWins = player.getName() + " wins!" + "Answer: " + response;

						JOptionPane winnerPane = new JOptionPane();
						winnerPane.showMessageDialog(new JFrame(), cpuWins, winTitle, JOptionPane.INFORMATION_MESSAGE);

						// Exit
						JOptionPane exitPane = new JOptionPane();
						exitPane.showMessageDialog(new JFrame(), exitMsg, exitTitle, JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					else if (!board.isCorrectGuess()){
						//player loses
						//Player is kicked message
						player.setIsAlive(false);
						board.showCardsOnDeath(player);
						playerList.remove(player);

						//Message
						JOptionPane kickedPane = new JOptionPane();
						kickedPane.showMessageDialog(new JFrame(), player.getName() + wrongAnswerMsg, wrongAnswerTitle, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

			//draw player targets
			board.nextPlayer(player, dieNum, playerList);


			board.repaint();



			//increase counter
			counter++;					
		}
	}

	//move human by clicking targets
	public class MouseClickTarget implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(player instanceof ComputerPlayer) return;


			boolean acceptableTarget = false;


			board.calcTargets(user.getRow(),user.getCol(), dieNum);
			HashSet<BoardCell> targets = (HashSet<BoardCell>) board.getTargets();
			
			int scale = 23;
			int selectedRow = ((e.getY()-23)/scale)-1;
			int selectedCol = (e.getX())/scale;
			//System.out.println("Click: " + e.getX() + " " + e.getY());
			//System.out.println("Row: " + selectedRow);
			//System.out.println("Col: " + selectedCol);
			for (BoardCell cell : board.getTargets()) {
				//System.out.println("OK: " + cell.getRow() + " " + cell.getCol());
				if (selectedRow == cell.getRow() && selectedCol == cell.getCol()) {
					user.makeMove(board.getCellAt(cell.getRow(), cell.getCol()));
					acceptableTarget = true;
					
					

					// Draw suggestion box
					if (((HumanPlayer) player).getCurrentlyInRoom()) {
						guessDialog = new GuessDialog(player);
						guessDialog.setModalityType(ModalityType.APPLICATION_MODAL);
						guessDialog.setVisible(true);

						// If guess submitted, handle suggestion, update GUI
						if (player.getSuggestionFlag()) {
							Card proof = board.handleSuggestion(player, ((HumanPlayer)player).getHumanSuggestion(), playerList);
							gui.updateGuessGUI(((HumanPlayer)player).getHumanSuggestion(), proof);
						}
					}
					player.setSuggestionFlag(false);

					humanTurnComplete = true;
					board.repaint();
				}
			}

			if (!acceptableTarget) {
				//Choose a valid target
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), wrongLocationMessage, errorMessage, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}

	}
	//accusation listener
	public class accusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (humanTurnComplete || player instanceof ComputerPlayer) {
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), poorlyTimedAccusation, errorMessage, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if (!player.getCurrentlyInRoom()) {
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), unableToMakeAccusation, errorMessage, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else {
				JDialog accuseDialog = new GuessDialog(player);
				accuseDialog.setModalityType(ModalityType.APPLICATION_MODAL);
				accuseDialog.setVisible(true);

				if (((HumanPlayer) player).getSuggestionFlag()) {
					Card proof = board.handleSuggestion(player, ((HumanPlayer) player).getHumanSuggestion(), playerList);
					gui.updateGuessGUI(((HumanPlayer) player).getHumanSuggestion(), proof);

					if (gameWon) {
						//game over, human wins
						humanWins = player.getName() + " wins! Congratulations!";
						JOptionPane winnerPane = new JOptionPane();
						winnerPane.showMessageDialog(new JFrame(), humanWins, winTitle, JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						//game over, human loses, comps continue
						player.setIsAlive(false);
						playerList.remove(player);
						humanTurnComplete = true;
						board.repaint();

						//Message
						JOptionPane kickedPane = new JOptionPane();
						kickedPane.showMessageDialog(new JFrame(), "You" + wrongAnswerMsg, wrongAnswerTitle, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}

	//main calls constructor and sets visible
	public static void main(String[] args) throws BadConfigFormatException {
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(game, "You are " + userName + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);
	}

}
