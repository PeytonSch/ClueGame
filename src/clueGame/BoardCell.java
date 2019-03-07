/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */

package clueGame;

public class BoardCell {
	
	private int row;
	private int col;
	private DoorDirection dir;
	private char roomLetter;
	
	public DoorDirection getDir() {
		return dir;
	}

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

	public DoorDirection getDoorDirection() {
		return dir;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return roomLetter;
	}

}
