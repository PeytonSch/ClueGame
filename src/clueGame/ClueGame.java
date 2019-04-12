package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame {

	public ClueGame() throws BadConfigFormatException {
		Board board = Board.getInstance();

		board.setAllConfigFiles("ClueGameLayout.csv", "ClueRooms.txt", "PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();

		ControlGui gui = new ControlGui();
		FileDropdown menu = new FileDropdown();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920,1080);

		
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		//add(side, BorderLayout.EAST);
		setJMenuBar(menu);
	}

	public static void main(String[] args) throws BadConfigFormatException {
		ClueGame game = new ClueGame();
		game.setVisible(true);

	}

}
