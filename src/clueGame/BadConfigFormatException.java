/*
 * Authors:
 * Peyton Scherschel
 * James Hawn
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception{


	public BadConfigFormatException() {


	}

	public BadConfigFormatException(String error) {
		super(error + " is not a valid input.");
		//Used when room config file has an invalid input
		PrintWriter out;
		try {
			out = new PrintWriter("logfile.txt");
			out.println(error + " is not a valid input.");
			out.close();
		} catch (FileNotFoundException e) {
			e.getMessage();
		}

	}

	public String getMessage() {
		String message = "There is an error with a configuration file.";
		return message;
	}
}
