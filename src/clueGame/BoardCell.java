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

import javax.swing.JPanel;

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
	
	public JPanel draw(Graphics g) {
		JPanel cellPanel = new JPanel();
		cellPanel.setLayout(new GridLayout(1,1));
		cellPanel.setOpaque(true);
		if (this.isRoom()) {
			cellPanel.setBackground(Color.GRAY);
		}
		else if (this.isWalkway()) {
			cellPanel.setBackground(Color.YELLOW);
		}
		else {
			cellPanel.setBackground(Color.RED);
		}
		return cellPanel;
		
	}


}
