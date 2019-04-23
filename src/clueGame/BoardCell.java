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
		//System.out.println("Room check " + (roomLetter != 'W' && dir ==  DoorDirection.NONE));
		//System.out.println(roomLetter + " " + dir);
		return (roomLetter != 'W');
		//return (roomLetter != 'W' && dir ==  DoorDirection.NONE);
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

	//draw boardcells
	public void drawCell(Graphics g) {
		int scale = 23;
		switch(this.roomLetter) {

		case 'W':
			//walkways
			g.setColor(Color.YELLOW);
			g.drawRect(this.col * scale, this.row * scale, scale, scale);
			g.fillRect(this.col * scale, this.row * scale, scale, scale);
			g.setColor(Color.BLACK);
			g.drawRect(this.col * scale, this.row * scale, scale, scale);
			break;
		default:
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(this.col * scale, this.row * scale, scale, scale);
			break;

		}

		//show door dir
		if(this.dir != DoorDirection.NONE) {
			if(this.dir == DoorDirection.UP) {
				g.setColor(Color.BLUE);
				g.drawRect(this.col * scale, this.row * scale, scale, 2);
				g.fillRect(this.col * scale, this.row * scale, scale, 2);
			}
			if(this.dir == DoorDirection.DOWN) {
				g.setColor(Color.BLUE);
				g.drawRect(this.col * scale, this.row * scale + 21, scale, 2);
				g.fillRect(this.col * scale, this.row * scale + 21, scale, 2);
			}
			if(this.dir == DoorDirection.LEFT) {
				g.setColor(Color.BLUE);
				g.drawRect(this.col * scale, this.row * scale, 2, scale);
				g.fillRect(this.col * scale, this.row * scale, 2, scale);
			}
			if(this.dir == DoorDirection.RIGHT) {
				g.setColor(Color.BLUE);
				g.drawRect(this.col * scale + 21, this.row * scale, 2, scale);
				g.fillRect(this.col * scale + 21, this.row * scale, 2, scale);
			}

		}

	}
	
	public void showTargets(Graphics g) {
		int scale = 23;
		g.setColor(Color.CYAN);
		g.drawRect(this.col * scale, this.row * scale, scale, scale);
		g.fillRect(this.col * scale, this.row * scale, scale, scale);
		g.setColor(Color.BLACK);
		g.drawRect(this.col * scale, this.row * scale, scale, scale);
	}


}
