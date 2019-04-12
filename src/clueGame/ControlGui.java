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
	private JTextField name;

	public ControlGui(){
		// Create a layout with 2 rows

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
		JLabel rollLabel = new JLabel("Response");
		JLabel rollNum = new JLabel("                  Test                   ");
		rollNum.setBorder(new EtchedBorder());
		panel.add(rollLabel);
		panel.add(rollNum);
		panel.setBorder(new TitledBorder ("Guess Result"));
		return panel;
	}

	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(2,1));      
		JLabel rollLabel = new JLabel("Guess");
		JLabel rollNum = new JLabel("              Test                       ");
		rollNum.setBorder(new EtchedBorder());
		panel.add(rollLabel);
		panel.add(rollNum);
		panel.setBorder(new TitledBorder ("Guess"));
		return panel;
	}

	private JPanel createDicePanel() {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(2,2));      
		JLabel rollLabel = new JLabel("Roll  ");
		JLabel rollNum = new JLabel("4");
		rollNum.setBorder(new EtchedBorder());
		panel.add(rollLabel);
		panel.add(rollNum);
		panel.setBorder(new TitledBorder ("Die"));
		return panel;
	}

	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();

		// Use a grid layout, 1 row, 2 elements (label, text)

		panel.setLayout(new GridLayout(4,2));     
		JLabel nameLabel = new JLabel("Test");
		nameLabel.setBorder(new EtchedBorder());
		JLabel turnLabel = new JLabel("Whose Turn?");
		panel.add(turnLabel);
		panel.add(nameLabel);
		return panel;
	}

	private JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JPanel panel = new JPanel();
		panel.setSize(500, 250);
		panel.setLayout(new GridLayout(1,2));      
		JButton next = new JButton("Next Player");
		JButton accuse = new JButton("Make an Accusation");
		panel.add(next);
		panel.add(accuse);
		return panel;
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
