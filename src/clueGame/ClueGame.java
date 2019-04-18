/*
 * Authors:
 * James Hawn
 * Peyton Scherschel
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

	public ClueGame() throws BadConfigFormatException {
		Board board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();

		//gui is control panel at bottom
		ControlGui gui = new ControlGui();
		FileDropdown menu = new FileDropdown();
		//set close op
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window size
		setSize(1000,1000);
		//title
		setTitle("Clue Game");

		//create Player Cards JPanel and add the JFrame
		ArrayList<Player> players = board.getPlayers();
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
		Player user = players.get((int)randomPlayer);
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

	//main calls constructor and sets visible
	public static void main(String[] args) throws BadConfigFormatException {
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(game, "You are " + userName + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);
	}

}
