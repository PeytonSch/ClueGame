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
	private int iterator = 0;
	private Player player;
	private static String pleaseCompleteTurn = "Please finish your turn before pressing Next Player.";
	private static String incorrectLocationMessage = "Please select a valid space.";
	private static String errorTitle = "Error";
	
	private ArrayList<Player> players;
	private Board board;
	private ControlGui gui;
	private Player user = new Player();

	public ClueGame() throws BadConfigFormatException {
		board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();

		//gui is control panel at bottom
		gui = new ControlGui();
		gui.next.addActionListener(new nextPlayerListener());
		addMouseListener(new MoveHumanPlayer());
		FileDropdown menu = new FileDropdown();
		//set close op
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window size
		setSize(850,850);
		//title
		setTitle("Clue Game");

		//create Player Cards JPanel and add the JFrame
		players = board.getPlayers();
		JPanel playerCards = createPlayerCardsPanel(players);
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
	public class nextPlayerListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// run all code for game
			// goes to next player in list

			// If human is not done display error
			if (!humanTurnComplete && player instanceof HumanPlayer) {
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), pleaseCompleteTurn, "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Reset boolean
			humanTurnComplete = false;


			// Wrap around array if at max
			if (iterator >= players.size()) iterator = 0;
			player = players.get(iterator);
			// Roll dice
			int dieRoll = (int)Math.floor(Math.random() * Math.floor(6)) + 1;

			// Update GUI with current info
			gui.updateGUI(player, dieRoll);


			// Call draws targets for human
			board.nextPlayer(player, dieRoll, players);
			board.repaint();



			// Increase offset
			iterator++;					
		}
	}

	// Human move listener, handles suggestions
	public class MoveHumanPlayer implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (player instanceof ComputerPlayer) return;

			boolean validMove = false;

			// IS VALID
			System.out.println("Click X and Y");
			System.out.println(e.getX() + "  " + e.getY());
			System.out.println("OK BOUNDS");
			for (BoardCell c : board.getTargets()) {
				int scale = 25;
				System.out.println(c.getCol()*scale + "-" + c.getCol()*scale + scale);
				if (e.getX() >= c.getCol() * scale && e.getX() <= (c.getCol() * scale) + scale) {
					if (e.getY() >= c.getRow() * scale + 50 && e.getY() <= (c.getRow() * scale) + 75) {
						user.makeMove(board.getCellAt(c.getRow(), c.getCol()));
						validMove = true;
						
						humanTurnComplete = true;
						board.repaint();
					}
				}
			}

			if (!validMove) {
				//Choose a valid target
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), incorrectLocationMessage, errorTitle, JOptionPane.INFORMATION_MESSAGE);
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
