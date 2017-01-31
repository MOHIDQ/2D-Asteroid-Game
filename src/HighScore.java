import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
//high score class, author mohid qureshi
//high score class reading and writing to high score text file, render, and sort score list

public class HighScore {
	private FileWriter filewrite;
	private PrintWriter printwriter;
	private FileReader filereader;
	private BufferedReader bufferedreader;
	private ArrayList<Integer> scoreList;
	private String n;
	private int totalScore;
	private double averageScore;

	public HighScore() throws IOException {
		filewrite = new FileWriter("highscore.txt", true);
		printwriter = new PrintWriter(filewrite);

		filereader = new FileReader("highscore.txt");
		bufferedreader = new BufferedReader(filereader);

		scoreList = new ArrayList<Integer>();
		totalScore = 0;
	}

	// gets user points earned, writing and saving to file
	public void toWrite(int points) {
		// if points is greater than 0, then save to text file
		if (points > 0) {
			printwriter.println(points);
		}
		printwriter.flush();

	}

	// method to sort list of points in text file and sum the pointsa
	public ArrayList<Integer> sortList() throws IOException {
		// loops through text file and adding to array list
		while ((n = bufferedreader.readLine()) != null) {
			scoreList.add(Integer.parseInt(n));
			// totals score from text file
			totalScore = Integer.parseInt(n) + totalScore;
		}
		// averages score
		averageScore = totalScore / scoreList.size();
		// sorts array list of points from text file from highest to lowest
		Collections.sort(scoreList);
		Collections.reverse(scoreList);
		return scoreList;
	}

	// method to display high score
	public void render(Graphics g, int x, int y) throws IOException {
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(sortList().get(0)), x, y);
		g.drawString(Double.toString(averageScore), x, y + 50);
	}

}
