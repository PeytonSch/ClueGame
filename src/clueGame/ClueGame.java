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

	private ArrayList<Player> playerList;
	private Board board;
	private ControlGui gui;
	private Player user;
	private boolean firstIteration = true;

	public ClueGame() throws BadConfigFormatException {
		board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();

		//gui is control panel at bottom
		gui = new ControlGui();
		gui.next.addActionListener(new NextPlayerButtonListener());
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
	}

	private JPanel createPlayerCardsPanel(ArrayList<Player> players) {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(3,1)); 

		// CHoose a random Player
		double randomPlayer = Math.random() * players.size();
		user = players.get((int)randomPlayer);
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
			}
			else if (card.getCardType() == CardType.WEAPON) {
				weaponName = new JLabel(card.getName());
			}
			else {
				break;
			}
		}

		JPanel person = new JPanel();
		person.setLayout(new GridLayout(2,1));      
		person.setBorder(new TitledBorder ("Person"));

		person.add(personName);

		JPanel room = new JPanel();
		room.setLayout(new GridLayout(2,1));      
		room.setBorder(new TitledBorder ("Room"));
		room.add(roomName);

		JPanel weapon = new JPanel();
		weapon.setLayout(new GridLayout(2,1));      
		weapon.setBorder(new TitledBorder ("Weapon"));
		weapon.add(weaponName);

		panel.add(person);
		panel.add(room);
		panel.add(weapon);
		panel.setBorder(new TitledBorder ("My Cards"));
		return panel;
	}

	// Next Player listener
	public class NextPlayerButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(!firstIteration) {
				if (!humanTurnComplete && player.getType().equals("Human")) {
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
			
			//int dieNum = (int) dieRoll;
			int dieNum = 1;

			//refresh gui
			gui.refreshGui(player, dieNum);


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
			
			
			boolean acceptableTarget = false;

			board.calcTargets(user.getRow(),user.getCol(), 1);
			HashSet<BoardCell> targets = (HashSet<BoardCell>) board.getTargets();
			int scale = 23;
			int selectedRow = ((e.getY()-56)/scale)-1;
			int selectedCol = (e.getX()-11)/scale;
			//System.out.println("Click: " + e.getX() + " " + e.getY());
			//System.out.println("Row: " + selectedRow);
			//System.out.println("Col: " + selectedCol);
			for (BoardCell cell : board.getTargets()) {
				scale = 23;
				if (selectedRow == cell.getRow() && selectedCol == cell.getCol()) {
					user.makeMove(board.getCellAt(cell.getRow(), cell.getCol()));
					acceptableTarget = true;

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
	
	//main calls constructor and sets visible
	public static void main(String[] args) throws BadConfigFormatException {
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(game, "You are " + userName + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);
	}

}
