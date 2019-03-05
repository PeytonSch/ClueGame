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

	public boolean isDoorway() {
		return !(dir.equals(DoorDirection.NONE));
	}

	public DoorDirection getDoorDirection() {
		return dir;
	}

	public String getInitial() {
		// TODO Auto-generated method stub
		return null;
	}

}
