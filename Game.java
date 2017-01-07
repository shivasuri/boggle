/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.*;
import java.awt.List;
/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public static int divisible = 1;
	public void run() {
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables.
		
		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("BOGGLE");
		frame.setLocation(300, 300);
		
		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Words: ");
		status_panel.add(status);
		final JLabel pointsStatus = new JLabel("Points: ");
		status_panel.add(pointsStatus);
		final JLabel cd = new JLabel("Time Remaining: ");
		status_panel.add(cd);
		
		// Main playing area
		final Board board = new Board(status, pointsStatus, cd);
		frame.add(board, BorderLayout.CENTER);
		
		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("New Game");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.reset();
				divisible = (divisible + 1) % 4;
				}
		});
		control_panel.add(reset);
		
		final JButton instructions = new JButton("Instructions");
		instructions.setFocusable(false);
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.instructions();
			}
		});
		control_panel.add(instructions);
		
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		board.reset();
		try {
		board.make();
		} catch (FileNotFoundException e) {
		}
		
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Game());
	}
}
