/*
 * Authors:
 * James Hawn
 * Peyton Scherschel
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class FileDropdown extends JMenuBar {
	private JDialog notesWin;
	private JMenu fileMenuOption;
	private JMenuItem notes;
	private JMenuItem exit;

	public FileDropdown() {
		// Create
		createNotesDialog();
		createMenuBar();
	}


	private void createMenuBar() {
		// Initialize
		fileMenuOption = new JMenu("File");
		notes = new JMenuItem("Show Notes");
		exit = new JMenuItem("Exit");

		// Setup- exit
		exit.addActionListener(new exitListener());

		// Setup- notes
		notes.addActionListener(new notesDialogListener());

		// Add all to JMenuBar
		fileMenuOption.add(notes);
		fileMenuOption.add(exit);
		this.add(fileMenuOption);
	}

	// Exit listener
	public class exitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Exit
			System.exit(0);
		}
	}

	// Window listener
	public class notesDialogListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Show dialog
			notesWin.setVisible(true);
		}
	}

	private void createNotesDialog() {
		// Initialize
		notesWin = new JDialog();
		JDialog n = notesWin;

		// Set attributes
		n.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		n.setTitle("Detective Notes");
		n.setSize(425, 600);

		// CHECKBOXES PANEL ************************************
		JPanel checkboxes = new JPanel();
		checkboxes.setLayout(new GridLayout(3, 1));

		// People boxes
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(3, 2));

		JCheckBox box1 = new JCheckBox("Voodoo Mama JuuJuu");
		people.add(box1);
		JCheckBox box2 = new JCheckBox("Beatrix Bourbon");
		people.add(box2);
		JCheckBox box3 = new JCheckBox("Naughty Nellie Nutmeg");
		people.add(box3);
		JCheckBox box4 = new JCheckBox("Caleb Crawdad");
		people.add(box4);
		JCheckBox box5 = new JCheckBox("Deb U. Taunt");
		people.add(box5);
		JCheckBox box6 = new JCheckBox("Nathaniel Nutmeg");
		people.add(box6);
		// Title
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		checkboxes.add(people);

		// Room boxes
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(5, 2));

		JCheckBox kitchen = new JCheckBox("Kitchen");
		rooms.add(kitchen);
		JCheckBox artRoom= new JCheckBox("Art Room");
		rooms.add(artRoom);
		JCheckBox bathroom = new JCheckBox("Bathroom");
		rooms.add(bathroom);
		JCheckBox garage = new JCheckBox("Garage");
		rooms.add(garage);
		JCheckBox hall = new JCheckBox("Hall");
		rooms.add(hall);
		JCheckBox lib = new JCheckBox("Library");
		rooms.add(lib);
		JCheckBox wineCellar = new JCheckBox("Wine Cellar");
		rooms.add(wineCellar);
		JCheckBox observatory = new JCheckBox("Observatory");
		rooms.add(observatory);
		JCheckBox sauna = new JCheckBox("Sauna");
		rooms.add(sauna);
		//Title
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		checkboxes.add(rooms);

		// Weapon boxes
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(3, 2));

		JCheckBox candlestick = new JCheckBox("Candlestick");
		weapons.add(candlestick);
		JCheckBox dumbell = new JCheckBox("Dumbell");
		weapons.add(dumbell);
		JCheckBox pipe = new JCheckBox("Lead Pipe");
		weapons.add(pipe);
		JCheckBox revolver = new JCheckBox("Revolver");
		weapons.add(revolver);
		JCheckBox rope = new JCheckBox("Rope");
		weapons.add(rope);
		JCheckBox wrench = new JCheckBox("Wrench");
		weapons.add(wrench);
		// Title
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		checkboxes.add(weapons);

		// Add checkboxes to main panel
		n.add(checkboxes, BorderLayout.WEST);

		// DROPDOWN PANEL ******************************************
		JPanel dropdowns = new JPanel();
		dropdowns.setLayout(new GridLayout(3, 1));

		// People dropdown
		String[] playerGuess = { "Voodoo Mama JuuJuu", "Beatrix Bourbon", "Naughty Nellie Nutmeg", "Caleb Crawdad", "Deb U. Taunt", "Nathaniel Nutmeg"};
		JComboBox playerBox = new JComboBox(playerGuess);
		playerBox.setBorder(new TitledBorder(new EtchedBorder(), "Player Guess"));
		dropdowns.add(playerBox);

		// Room dropdown
		String[] roomGuess = { "Kitchen", "Art Room", "Bathroom", "Garage", "Hall", 
				"Library", "Wine Cellar", "Observatory", "Sauna"};
		JComboBox roomBox = new JComboBox(roomGuess);
		roomBox.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		dropdowns.add(roomBox);

		// Weapon dropdown
		String[] weaponGuess = { "Candlestick", "Dumbell", "Lead Pipe", "Revolver", "Rope", "Wrench"};
		JComboBox weaponBox = new JComboBox(weaponGuess);
		weaponBox.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		dropdowns.add(weaponBox);

		// Add dropdowns to main panel
		n.add(dropdowns, BorderLayout.CENTER);

	}
}
