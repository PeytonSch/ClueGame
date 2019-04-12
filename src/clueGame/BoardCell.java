/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */

package clueGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class BoardCell {

	private int row;
	private int col;
	private DoorDirection dir;
	private char roomLetter;
	private int rowSize;
	private int colSize;


	public void setDir(DoorDirection dir) {
		this.dir = dir;
	}

	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}



	public BoardCell(int row, int col, DoorDirection dir, char roomLetter) {
		super();
		this.row = row;
		this.col = col;
		this.dir = dir;
		this.roomLetter = roomLetter;
	}

	public boolean isDoorway() {
		return !(dir.equals(DoorDirection.NONE));
	}

	public boolean isWalkway() {
		return (roomLetter == 'W');
	}

	public boolean isRoom() {
		return (roomLetter != 'W' && dir ==  DoorDirection.NONE);
	}

	public DoorDirection getDoorDirection() {
		return dir;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return roomLetter;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public JPanel draw() {
		JPanel cellPanel = new JPanel();
		cellPanel.setLayout(new GridLayout(1,1));
		cellPanel.setOpaque(true);
		if (this.isRoom()) {
			cellPanel.setBackground(Color.GRAY);
			if (this.row == 1 && this.col == 1) {
				JLabel roomLabel = new JLabel("LIBRARY");
				cellPanel.add(roomLabel);
			}
			if (this.row == 1 && this.col == 9) {
				JLabel roomLabel = new JLabel("OBSERVATORY");
				cellPanel.add(roomLabel);
			}
			if (this.row == 2 && this.col == 17) {
				JLabel roomLabel = new JLabel("HALL");
				cellPanel.add(roomLabel);
			}
			if (this.row == 2 && this.col == 21) {
				JLabel roomLabel = new JLabel("BATHROOM");
				cellPanel.add(roomLabel);
			}
			if (this.row == 8 && this.col == 2) {
				JLabel roomLabel = new JLabel("WINE CELLAR");
				cellPanel.add(roomLabel);
			}
			if (this.row == 18 && this.col == 2) {
				JLabel roomLabel = new JLabel("ART ROOM");
				cellPanel.add(roomLabel);
			}
			if (this.row == 18 && this.col == 8) {
				JLabel roomLabel = new JLabel("KITCHEN");
				cellPanel.add(roomLabel);
			}
			if (this.row == 18 && this.col == 15) {
				JLabel roomLabel = new JLabel("GARAGE");
				cellPanel.add(roomLabel);
			}
			if (this.row == 15 && this.col == 21) {
				JLabel roomLabel = new JLabel("SAUNA");
				cellPanel.add(roomLabel);
			}
		}
		else if (this.isWalkway()) {
			cellPanel.setBackground(Color.YELLOW);
			cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		else if (this.isDoorway()) {
			cellPanel.setBackground(Color.GRAY);
			switch (this.getDoorDirection()) {
			case DOWN:
				cellPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLUE));
				break;
			case UP:
				cellPanel.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.BLUE));
				break;
			case LEFT:
				cellPanel.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.BLUE));
				break;
			case RIGHT:
				cellPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Color.BLUE));
				break;
			}
			
			
		}
		else {
			cellPanel.setBackground(Color.RED);
		}
		return cellPanel;
		
	}


}
