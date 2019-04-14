/*
 * Authors:
 * James Hawn
 * Peyton Scherschel
 */
package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

		//add board to center and control gui to bottom
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		//add menu bar
		setJMenuBar(menu);
	}

	//main calls constructor and sets visible
	public static void main(String[] args) throws BadConfigFormatException {
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(game, "You are Caleb Crawdad, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);

	}

}
