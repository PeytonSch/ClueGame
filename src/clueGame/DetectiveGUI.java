package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class DetectiveGUI {
	private JTextField name;
	
	public DetectiveGUI(){
		
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Control GUI");frame.setSize(1000, 200);
		// Create the JPanel and add it to the JFrame
		GUI gui = new GUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}
}
