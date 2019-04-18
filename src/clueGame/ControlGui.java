/*
 * Authors:
 * James Hawn
 * Peyton Scherschel
 */

package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGui extends JPanel {
	
	private JLabel guessLabel;
	private JLabel guessText;
	private JLabel nameLabel;
	private JLabel turnLabel;
	private JLabel responseText;
	private JLabel responseLabel;
	private JLabel rollLabel;
	private JLabel rollText;
	
	JButton next, accuse;

	public ControlGui(){
		//Create a layout with 2 rows

		setLayout(new GridLayout(2,1));
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1,2));  
		JPanel turnPanel = createTurnPanel();
		upperPanel.add(turnPanel);
		JPanel buttonPanel = createButtonPanel();
		upperPanel.add(buttonPanel);
		add(upperPanel);

		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1,3));  
		JPanel dicePanel = createDicePanel();
		JPanel guessPanel = createGuessPanel();
		JPanel guessResult = createGuessResult();
		lowerPanel.add(dicePanel);
		lowerPanel.add(guessPanel);
		lowerPanel.add(guessResult);
		add(lowerPanel);
	}

	private JPanel createGuessResult() {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(2,2));      
		responseLabel = new JLabel("Response");
		responseText = new JLabel("");
		responseText.setBorder(new EtchedBorder());
		panel.add(responseLabel);
		panel.add(responseText);
		panel.setBorder(new TitledBorder ("Guess Result"));
		return panel;
	}

	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(2,1));      
		guessLabel = new JLabel("Guess");
		guessText = new JLabel("");
		guessText.setBorder(new EtchedBorder());
		panel.add(guessLabel);
		panel.add(guessText);
		panel.setBorder(new TitledBorder ("Guess"));
		return panel;
	}

	private JPanel createDicePanel() {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(2,2));      
		rollLabel = new JLabel("Roll");
		rollText = new JLabel("");
		rollText.setBorder(new EtchedBorder());
		panel.add(rollLabel);
		panel.add(rollText);
		panel.setBorder(new TitledBorder ("Die"));
		return panel;
	}

	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(4,2));     
		nameLabel = new JLabel("");
		nameLabel.setBorder(new EtchedBorder());
		turnLabel = new JLabel("Whose Turn?");
		panel.add(turnLabel);
		panel.add(nameLabel);
		return panel;
	}

	private JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JPanel panel = new JPanel();
		panel.setSize(500, 250);
		panel.setLayout(new GridLayout(1,2));      
		next = new JButton("Next Player");
		accuse = new JButton("Make an Accusation");
		panel.add(next);
		panel.add(accuse);
		return panel;
	}
	
	public void refreshGui(Player player, int dieRoll) {
		//set dice roll and player name
		rollText.setText(Integer.toString(dieRoll));
		nameLabel.setText(player.getName());
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Control GUI");frame.setSize(1000, 200);
		// Create the JPanel and add it to the JFrame
		ControlGui gui = new ControlGui();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}
}
