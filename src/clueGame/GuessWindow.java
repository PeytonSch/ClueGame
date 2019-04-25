/*
 * Authors:
 * James Hawn
 * Peyton Scherschel
 */
package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GuessWindow extends JDialog  {

	private JTextField roomIn;
	private JTextField personField;
	private JTextField weaponField;
	private String person;
	private String weapon;
	public Card roomGuess;
	public Card personGuess;
	public Card wepGuess;
	public Solution suggestion;

	public Board board;
	
	private Player currentPlayer;
	
	JComboBox<String> playerMenu;
	JComboBox<String> wepMenu;
	JComboBox<String> roomMenu;
	JButton submitButton;
	JButton cancelButton;
	
	public GuessWindow(Player p) {
		//Board/player
		Board board = Board.getInstance();
		this.currentPlayer = p;
		
		//Settings
		setSize(300,300);
		setLayout(new GridLayout(4,2));
		
		//rooms
		JLabel roomLabel = new JLabel("Room");
		roomIn = new JTextField(20);
		roomIn.setText(board.getRoom(currentPlayer).getName());
		roomGuess = board.getRoom(currentPlayer);
		roomIn.setEditable(false);
		
		//menus
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		
		playerMenu = new JComboBox<String>();
		playerMenu = createPlayersDropdown(board.getPeople());
		
		wepMenu = new JComboBox<String>();
		wepMenu = createWeaponsDropdown(board.getAllWeaponCards());
		
		//Buttons
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitGuessListener());
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener());
		
		//add to panel
		add(roomLabel);
		add(roomIn);
		add(personLabel);
		add(playerMenu);
		add(weaponLabel);
		add(wepMenu);
		add(submit);
		add(cancel);
	}


	private JComboBox<String> createWeaponsDropdown(ArrayList<Card> weapons) {
		JComboBox<String> weaponList = new JComboBox<String>();
		for (Card w : weapons) {
			weaponList.addItem(w.getName());
		}
		return weaponList;
	}


	private JComboBox<String> createPlayersDropdown(Set<Card> players) {
		JComboBox<String> people = new JComboBox<String>();
		for (Card p : players) {
			people.addItem(p.getName());
		}
		return people;
	}
	
	//listeners
	public class SubmitGuessListener implements ActionListener {

		Board board = Board.getInstance();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			person = (String) playerMenu.getSelectedItem();
			weapon = (String) wepMenu.getSelectedItem();
			
			
			for (Card c : board.getAllCards()) {
				if ( c.getName().equals(person) )
					personGuess = c;
				else if (c.getName().equals(weapon))
					wepGuess = c;
			}
			
			//set flag
			((HumanPlayer)currentPlayer).createHumanSuggestion(board.getRoom(currentPlayer), personGuess, wepGuess);
			
			//close
			dispose();
		}

	}

	//cancel button
	public class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	



	
}
