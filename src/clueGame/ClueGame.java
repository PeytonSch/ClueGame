/*
 * Authors:
 * James Hawn
 * Peyton Scherschel
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame {

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
		setSize(850,850);
		//title
		setTitle("Clue Game");
		
		//create Player Cards JPanel and add the JFrame
		JPanel playerCards = createPlayerCardsPanel();
		add(playerCards, BorderLayout.EAST);

		//add board to center and control gui to bottom
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		//add menu bar
		setJMenuBar(menu);
	}
	
	private JPanel createPlayerCardsPanel() {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(3,1));      
		
		JPanel person = new JPanel();
		person.setLayout(new GridLayout(2,1));      
		person.setBorder(new TitledBorder ("Person"));
		JLabel personName = new JLabel("Caleb Crawdad");
		person.add(personName);
		
		JPanel room = new JPanel();
		room.setLayout(new GridLayout(2,1));      
		room.setBorder(new TitledBorder ("Room"));
		JLabel roomName = new JLabel("Observatory");
		room.add(roomName);
		
		JPanel weapon = new JPanel();
		weapon.setLayout(new GridLayout(2,1));      
		weapon.setBorder(new TitledBorder ("Weapon"));
		JLabel weaponName = new JLabel("Dumbell");
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
		JOptionPane.showMessageDialog(game, "You are Caleb Crawdad, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);
	}

}
