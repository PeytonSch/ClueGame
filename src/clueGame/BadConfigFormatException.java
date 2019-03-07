/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

public class BadConfigFormatException extends Exception{


	public BadConfigFormatException() {


	}
	
	public BadConfigFormatException(String errorMsg) {
		super(errorMsg);
		
	}
}
