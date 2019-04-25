/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

public class Solution {
	public Card person;
	public Card room ;
	public Card weapon;

	public Solution(Card room, Card person, Card weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
		//System.out.println(person.getName()+room.getName()+weapon.getName());
	}

	public Card getPerson() {
		return person;
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public Card getRoom() {
		return room;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	public Card getWeapon() {
		return weapon;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}

	//function to help determine if solutions are equal. Lets us compare them
	public boolean isEqualTo(Solution s) {
		if (this.person.cardNameEquals(s.person.getName()) && this.room.cardNameEquals(s.room.getName()) && this.weapon.cardNameEquals(s.weapon.getName()) ) {
			return true;
		}
		else {
			return false;
		}
	}



}
