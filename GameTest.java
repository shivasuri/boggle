import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import org.junit.*;

public class GameTest {
	@Test public void getTopRow() {
		final JLabel s = new JLabel("s");
		final JLabel t = new JLabel("t");
		final JLabel u = new JLabel("u");
		final Board board = new Board(s, t, u);
		board.reset();
		Block[][] blocks = board.getBlocks();
		String str = "" + blocks[0][0].getCharacter() + blocks[1][0].getCharacter() +
				blocks[2][0].getCharacter() +blocks[3][0].getCharacter();
		String x = "four";
		assertTrue(x.length() == str.length());
	}
	
	@Test public void isBlockRandomCapitalLetter() {
		final JLabel s = new JLabel("s");
		final JLabel t = new JLabel("t");
		final JLabel u = new JLabel("u");
		final Board board = new Board(s, t, u);
		board.reset();
		Block[][] blocks = board.getBlocks();
		Block random = blocks[2][2];
		assertTrue(random.getCharacter() >= 60 && random.getCharacter() < 91);
	}
	
	@Test public void areSolverSolutionsWords() {
		Set<String> dictionary = new TreeSet<String>();
		try {
			Scanner sc = new Scanner(new File("dictionary.txt"));
			while (sc.hasNext()) {
				String string = sc.next();
				dictionary.add(string);
			}
		} catch (IOException e) {
		}
		final JLabel s = new JLabel("s");
		final JLabel t = new JLabel("t");
		final JLabel u = new JLabel("u");
		final Board board = new Board(s, t, u);
		board.reset();
		Solver sol = new Solver(board);
		assertTrue(	dictionary.containsAll(sol.getSolutionSet()));
	}
	
	@Test public void isGameInSesion() {
		// i.e. instructions are off and board.isPlaying() is true
		final JLabel s = new JLabel("s");
		final JLabel t = new JLabel("t");
		final JLabel u = new JLabel("u");
		final Board board = new Board(s, t, u);
		board.reset();
		assertTrue(!board.getInst() && board.isPlaying());
	}
	
	@Test public void areInstructionsPresentWhenTimerEnds() {
		final JLabel s = new JLabel("s");
		final JLabel t = new JLabel("t");
		final JLabel u = new JLabel("u");
		final Board board = new Board(s, t, u);
		board.reset();
		board.setTime();
		board.tock(); // hit time = 0 and simultaneously tests board.tock() method
		assertTrue(board.getInst());
	}
	
	@Test public void isLabelWord() {
		final JLabel s = new JLabel("s");
		final JLabel t = new JLabel("t");
		final JLabel u = new JLabel("u");
		final Board board = new Board(s, t, u);
		board.reset();
		board.setTime();
		board.tick(); // hit time = 0 and also tests board.tock() method
		assertTrue(s.getText().equals(board.getWord()));
	}
	
	@Test public void isBoardFourByFourBlockArray() {
		// i.e. instructions are off and board.isPlaying() is true
		final JLabel s = new JLabel("s");
		final JLabel t = new JLabel("t");
		final JLabel u = new JLabel("u");
		final Board board = new Board(s, t, u);
		board.reset();
		Block[][] blocks = board.getBlocks();
		assertTrue(blocks.length == 4 && blocks[0].length == 4);
	}
}
