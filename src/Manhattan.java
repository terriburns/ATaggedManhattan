/**
 * A Tagged Manhattan
 * 
 * This program generates a Manhattan Graffiti Finder App,
 * returning the various open locations of Graffiti art created
 * between December 2010 and June 2011. (Street address, precinct, X and Y coordinates)
 * 
 * [original data set found at]
 * https://data.cityofnewyork.us/Social-Services/Graffiti-Locations/2j99-6h29
 * 
 * TODO (bug fixes):
 * -Proper page/cursor scrolling
 * -Program breaks if mouseClicked is called more than twice before keyPressed, making the currently malfunctioning clear button useless
 * 
 * ADDITIONAL NOTES:
 * When the program runs, the mouseClicked function is called, which prompts the user to see each respective page.
 * The keyPressed function is called when the user begins to type in their street name. After the user selects 'enter' the keys they pressed
 * are stored as a string value, which is then used to compare to the data from Excel file, and ultimately returns a match.
 * 
 * @author: Terri Burns, NYU
 */


import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import processing.core.*;

public class Manhattan extends PApplet {
	boolean click = true;
	boolean arrayOnce = true;
	int count = 0;
	int keycount = 0;
	int storeDataCount = 0;
	int streetNamesCounter = 0;
	int windowWidth = 610;
	int windowHeight = 613;
	// int space = 50;
	PFont font;
	PImage bg;
	PImage bg2;
	PImage bg3;
	int y;
	int blockSpace = 80;

	// the following variables are for the cursor
	// -------------------------------------------
	int yPos1 = 7;
	int yPos2 = 27;
	int ySpeed = 2; // Speed of the shape
	int yDirection = 1; // Top to Bottom
	// -------------------------------------------

	// create an arraylist to store each char that was entered
	ArrayList<String> nameEntered = new ArrayList<String>();

	/**
	 * Called once (see Processing documentation)
	 */
	public void setup() {
		// size of the window, type, loading/applying Instagram images
		size(windowWidth, windowHeight);
		font = createFont("AmericanTypewriter", 48);

		bg = loadImage("A Tagged Manhattan.png");
		bg2 = loadImage("page 2.png");
		bg3 = loadImage("page 3.png");
		background(bg2);

		// text font, size, color, and location for title
		textFont(font, 28);
		fill(0, 0, 0);
		textAlign(LEFT, TOP);

		// A
		textSize(55);
		text("A", 150, 20); // ints are x and y coordinates for location

		// TAGGED
		font = createFont("AmericanTypewriter-Bold", 48);
		textFont(font, 28);
		textSize(60);
		text("Tagged", 84, 40);

		// MANHATTAN
		font = createFont("AmericanTypewriter", 48);
		textFont(font, 28);
		textSize(40);
		text("Manhattan", 85, 90);

		// CLICK TO BEGIN
		fill(255, 255, 255);
		textSize(15);
		textAlign(CENTER, CENTER);
		text("Click Anywhere To Begin", windowWidth / 2, 600);

	}

	/**
	 * Called continuously (See Processing documentation)
	 */
	public void draw() {
		if (count >= 3) {
			// SCROLL BAR
			yPos1 += (2);
			yPos2 += (2);

			// moves the cursor accordingly
			if (mouseY > yPos2) {
				// black background
				stroke(0);
				strokeWeight(20);
				line(600, 0, 600, 615);
				// white cursor
				stroke(255);
				strokeWeight(17);
				line(600, yPos1 - 10, 600, yPos2 + 10);

			} else {
				// black background
				stroke(0);
				strokeWeight(20);
				line(600, 0, 600, 615);
				// white cursor
				stroke(255);
				strokeWeight(17);
				line(600, mouseY - 10, 600, mouseY + 10);
			}
		}

	}

	/**
	 * Called when the mouse is clicked. Prompts user to see
	 * respective/consecutive pages. Counters ensure pages appear consecutively. 
	 */
	public void mouseClicked() {
		// use a counter and a while loop to limit calling ATaggedManhattan()
		// just once
		count++;
		while (click) {
			// second page
			if (count == 1) {
				windowWidth = 610;
				windowHeight = 609;
				size(windowWidth, windowHeight);
				background(bg);
				ATaggedManhattan();
				break;
			} // third page
			else if (count == 2) {
				streetNamesCounter++; // make sure this section only runs once
				if (streetNamesCounter == 1) {
					windowWidth = 611;
					windowHeight = 612;
					size(windowWidth, windowHeight);
					background(bg3);
					EnterStreetNames();
				}
				break;
			}
			// NOTE-- the final page (below) should only appear after keyPressed
			// is called.
			// In other words, it should only appear once the user enters a
			// street name.

			// CLEAR BUTTON
			else if (count > 2 && mouseX >= windowWidth / 2 - 18
					&& mouseX < windowWidth / 2 + 10 && mouseY >= 260
					&& mouseY <= 290) {
				// clear the screen by redrawing the background
				EnterStreetNames();
				streetNamesCounter++;
			}

			// ENTER BUTTON
			else if (count > 2 && mouseX >= windowWidth / 2 - 18
					&& mouseX < windowWidth / 2 + 10 && mouseY >= 240
					&& mouseY <= 260) {

				// convert arraylist to a string that is the streetname we will
				// compare to data
				String comparison = storeData(nameEntered);
				// which compares street name to nyc Data & prints a new screen
				compareData(comparison);

				break;
			}
		}
	}

	/**
	 * This method sets up the frontend of the app.
	 */
	private void ATaggedManhattan() {
		textAlign(CENTER, TOP);
		textFont(font, 15);
		fill(0, 0, 0);
		text("Manhattan's a cool place, occasionally doused in",
				windowWidth / 2, 100);
		text("your sporadic spray of street-tagging.", windowWidth / 2, 120);
		text("When people request that graffiti be removed,", windowWidth / 2,
				140);
		text("the information is stored.", windowWidth / 2, 160);
		text("Enter any street name in Manhattan to find out more",
				windowWidth / 2, 180);
		text("about tagged locations that may need cleaning.", windowWidth / 2,
				200);
		// imperfect handling but....
		text("(Note- if a streetname is more than one word, just include one word of the name.)",
				windowWidth / 2, 220);
		fill(255, 255, 255);
		text("Click anywhere to continue.", windowWidth / 2, 580);

	}

	/**
	 * This method creates the interface that prompts the user to enter any
	 * street name.
	 */
	private void EnterStreetNames() {
		stroke(0);
		strokeWeight(60);
		line(0, 100, windowWidth, 100);
		fill(255, 255, 255);
		textSize(30);
		text("Begin typing any street in Manhattan: ", windowWidth / 2, 85);

	}

	/**
	 * Called when a button is pressed on the keyboard. See Processing
	 * documentation.
	 */
	public void keyPressed() {
		// make sure keyPressed only works on 3rd page
		while (click) {
			if (count < 2) {
				break;
			} else {
				keycount++;
				if (keycount == 1) {
					// line background
					stroke(0);
					strokeWeight(40);
					line(0, 200, windowWidth, 200);

					// enter button
					stroke(255);
					strokeWeight(20);
					line(windowWidth / 2 - 18, 250, windowWidth / 2 + 10, 250);
					font = createFont("AmericanTypewriter", 10);
					textFont(font, 10);
					textSize(10);
					textAlign(CENTER, CENTER);
					fill(0, 0, 0);
					text("ENTER", windowWidth / 2 - 5, 250);

					// clear button
					stroke(255);
					strokeWeight(20);
					line(windowWidth / 2 - 18, 285, windowWidth / 2 + 10, 285);
					font = createFont("AmericanTypewriter", 10);
					textFont(font, 10);
					textSize(10);
					textAlign(CENTER, CENTER);
					fill(0, 0, 0);
					text("CLEAR", windowWidth / 2 - 5, 285);

					// "press enter" @ bottom of page
					font = createFont("AmericanTypewriter", 20);
					textFont(font, 15);
					textSize(15);
					textAlign(CENTER, CENTER);
					fill(0, 0, 0);
					text("Select ENTER to continue.", windowWidth / 2, 580);
				}

				// text that the user types
				font = createFont("AvenirNext-UltraLightItalic", 48);
				textFont(font, 24);
				fill(255, 255, 255);
				textSize(18);
				text(key, 10 + keycount * 20, 197);

				// add each keyPressed to arraylist
				if (count == 2) {
					if (key >= 65 && key <= 90 ^ key >= 97 && key <= 122) {
						String name = Character.toString(key); // convert to a
																// letter
						nameEntered.add(name); // add each letter to an
												// arraylist
					}
				}
				break;
			}
		}

	}

	/**
	 * This method returns a String representation of the user Input, i.e. the
	 * street name we are searching for
	 * 
	 * @param userInput
	 * @return
	 */
	private String storeData(ArrayList<String> userInput) {
		// if the user clicks the "enter" box,
		// add each char that was entered to an arraylist
		String listString = "";

		// account only for letters & numbers by looking at char value
		for (String s : userInput) {
			char charinput = s.charAt(0);
			int asciiinput = (int) (charinput);
			if (asciiinput > 48 || (asciiinput > 57 && asciiinput < 97)
					|| asciiinput < 123) {
				listString += s;

			}

			// streetNamesCounter == 2 if the Clear button is hit. Clear
			// listString if clear button is hit
			else if (streetNamesCounter == 2) {
				listString = "";
			}

			else {
				System.out.println("numbers + letters only");
			}
		}

		// STRING VALUE WE ARE COMPARING TO FILE:
		String streetname = listString.toUpperCase();
		return streetname;

	}

	/**
	 * This method creates the front end display for each returned location.
	 * 
	 * @param spacing
	 *            , spaces between words
	 * @param totalSpaces
	 *            , the Y coordinate placement of the text
	 * @param addresses
	 *            , a record of the total number of addresses
	 * @param nameOfStreet
	 *            , the name of the street,
	 * @param element
	 *            , type of data (address, precinct, or coordinates) to display
	 */
	private void printData(int spacing, int totalSpaces, int addresses,
			String nameOfStreet, String[] element) {

		if (nameOfStreet == "") {
			background(0, 0, 0);
			noneFound();
		}

		// TITLE
		if (addresses == 1) {
			textFont(font, 30);
			text("List of locations on ", windowWidth / 2, totalSpaces);
			fill(255, 0, 127);
			text(nameOfStreet + ":", windowWidth / 2, totalSpaces + 25);
			fill(255, 255, 255);
			textFont(font, 15);
			fill(255, 255, 255);
		}

		// EACH nameOfStreet
		fill(0, 204, 204);
		text("ADDRESS " + addresses + ": " + element[0], windowWidth / 2,
				spacing * totalSpaces + 15);
		fill(255, 255, 255);
		text("Nearest Precinct: " + element[1], windowWidth / 2, spacing
				* totalSpaces + 30);
		text("X Coordinate: " + element[2], windowWidth / 2, spacing
				* totalSpaces + 55);
		text("Y Coordinate: " + element[3], windowWidth / 2, spacing
				* totalSpaces + 80);

	}

	/**
	 * This method informs the user that no Graffiti was found on the street
	 * they entered.
	 */
	private void noneFound() {
		textAlign(CENTER, TOP);
		textFont(font, 15);
		fill(255, 255, 255);
		text("There is no graffiti on this street. ", windowWidth / 2, 85);
		text("P.S. Are you sure you entered a street that really exists?",
				windowWidth / 2, 100);
		text("P.P.S. Be sure to include some letters and only numbers if otherwsie necessary.",
				windowWidth / 2, 115);
	}

	/**
	 * This method compares the (user input) street name to the data that is
	 * stored, determining graffiti locations for that given street
	 * 
	 * @param streetname
	 */
	private void compareData(String streetname) {
		// page setup
		background(0, 0, 0);
		font = createFont("AmericanTypewriter", 48);
		textFont(font, 28);
		textSize(28);
		fill(255, 255, 255);

		final String PATH = "Graffiti_Locals.csv";
		File Graffiti = new File(PATH);
		// Open it with a scanner
		Scanner lineScanner = null;
		Scanner s = null;

		try {
			s = new Scanner(Graffiti);
		} catch (FileNotFoundException e) {
			System.out.println("Could not open file: " + Graffiti.getName());
			System.exit(1);
		}

		// create ArrayList
		ArrayList<String> list = new ArrayList<String>();

		// scanner info to list
		while (s.hasNext()) {
			list.add(s.nextLine());
		}

		int streetCount = 0;
		int streetCount2 = 0;
		int pageCount = 1;

		int spaceCounter = 0;

		// search for street name within file, and prints
		// respective information
		for (int i = 0; i < list.size(); i++) {
			String info = list.get(i);// info refers to each line

			if (info.contains(streetname)) {
				streetCount++;
				spaceCounter++;
				font = createFont("AmericanTypewriter-Bold", 48);
				textAlign(CENTER, TOP);
				textFont(font, 15);
				fill(255, 255, 255);

				String[] element = info.split(","); // returns a string array of
													// each respective element
													// per line, noted by comma
													// = delimiter

				// PRINT THE STREETS
				printData(blockSpace, spaceCounter, streetCount, streetname,
						element);

			} else {
				streetCount2++;
				continue;

			}

		}
		if (streetCount2 == list.size()) {
			// if name isn't found anywhere in
			// info

			noneFound();

		}

	}
}
