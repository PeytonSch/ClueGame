/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

public class Card {
	private String name;
	private CardType type;


	public CardType getCardType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Card(String name, CardType type) {
		this.name = name;
		this.type = type;
	}

	public boolean cardNameEquals(String name) {
		return (this.name == name);
	}

}
