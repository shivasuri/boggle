import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import javax.swing.*;
import javax.swing.Timer;
import java.util.Iterator;

import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Board extends JPanel {

	// Game constants

	private JPanel solPanel;
	private int time = 60;
	private boolean inst = false;
	public static final int COURT_WIDTH = 400;
	public static final int COURT_HEIGHT = 400;
	public static boolean sunk = false;
	private int points;
	private Block[][] blocks = new Block[4][4];
	static Set<String> dictionary = new TreeSet<String>();
	private String word = "";
	public boolean playing = false; // whether the game is running
	private JLabel status; // Current word status text (i.e. letters picked...)
	private JLabel pointsStatus; // Current point status text
	private JLabel cd; // Current countdown status
	public static final int boardWidth = 400;
	public static final int boardHeight = 400;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	Block previous;
	Set<Block> usedBlocks = new TreeSet<Block>();
	Set<String> usedWords = new TreeSet<String>();
	private final int second = 1000;
	// constructor
	public Board(JLabel status, JLabel pointsStatus, JLabel cd) {
		
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		
		timer.start(); // MAKE SURE TO START THE TIMER!
		Timer countdown = new Timer(second, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tock();
			}
		});
		countdown.start();
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && !inst) {
					for (int x = 0; x <= 400; x++) {
						if (e.getX() == x) {
							for (int y = 0; y <= 400; y++) {
								if (e.getY() == y) {
									int h = x / 100;
									int v = y / 100;
									if (word.equals("")) {
										word += blocks[h][v].getCharacter();
										previous = blocks[h][v];
										if (Game.divisible == 1) {
											previous.changeColor();
										}
										else if (Game.divisible == 2) {
											previous.changeGreen();
										}
										else if (Game.divisible == 3) {
											previous.changeGray();
										}
										usedBlocks.add(previous);
									}
									
									else if (!usedBlocks.contains(blocks[h][v])) {
										if (((h == previous.getXPos() / 100 + 1) && (v == previous.getYPos() / 100 || v == previous.getYPos() / 100 - 1 || v == previous.getYPos() / 100 + 1)) ||
											((h == previous.getXPos() / 100 - 1) && (v == previous.getYPos() / 100 || v == previous.getYPos() / 100 - 1 || v == previous.getYPos() / 100 + 1)) ||
											((v == previous.getYPos() / 100 + 1) && (h == previous.getXPos() / 100 || h == previous.getXPos() / 100 - 1 || h == previous.getXPos() / 100 + 1)) ||
											((v == previous.getYPos() / 100 - 1) && (h == previous.getXPos() / 100 || h == previous.getXPos() / 100 - 1 || h == previous.getXPos() / 100 + 1))) {
										word += blocks[h][v].getCharacter();
										previous = blocks[h][v];
										if (Game.divisible == 1) {
											previous.changeColor();
										}
										else if (Game.divisible == 2) {
											previous.changeGreen();
										}
										else if (Game.divisible == 3) {
											previous.changeGray();
										}
										usedBlocks.add(previous);
										}
									}
								}
							}
						}
					}
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && !inst) {
					if (dictionary.contains(word.toLowerCase()) && !usedWords.contains(word)) {
						usedWords.add(word);
						// implement Boggle Point System
						if (word.length() == 3 || word.length() == 4) {
							points += 1;
						}
						else if (word.length() == 5) {
							points += 2;
						}
						else if (word.length() == 6) {
							points += 3;
						}
						else if (word.length() == 7) {
							points += 5;
						}
						else if (word.length() >= 8) {
							points += 11;
						}
					}
					word = "";
					for (Block block : usedBlocks) {
						block.changeColorBack();
					}
					usedBlocks.clear();
				}
			}
		});
		this.status = status;
		this.pointsStatus = pointsStatus;
		this.cd = cd;
	}
	
	public int getPoints() {
		return points;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getTime() {
		return time;
	}
	
	// for the purpose of JUnit test
	public void setTime() {
		time = 1;
	}
	
	void tick() {
		if (playing) {
			status.setText(word);
			pointsStatus.setText("Points: " + points);
			repaint();
		}
	}
	
	void tock() {
		if (playing && !inst && time > 0 && time <= 60) {
			time -= 1;
		}
		cd.setText("Time Remaining: " + time);
		if (time == 0) {
			JOptionPane.showMessageDialog(null, solPanel, "All the Possible Solutions:", JOptionPane.PLAIN_MESSAGE);
			reset();
			inst = true;
		}
	}
	
	public void reset() {
 		if (inst) {
			inst = false;
		}
		blocks = new Block[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				// make probability of vowels > than that of consonants
				double rand = Math.random();
				char randChar;
				if (rand < 0.05) {
					randChar = 'U';
				}
				else if (rand < 0.10) {
					randChar = 'O';
				}
				else if (rand < 0.15) {
					randChar = 'I';
				}
				else if (rand < 0.20) {
					randChar = 'E';
				}
				else if (rand < 0.25) {
					randChar = 'A';
				}
				else {
					// random letter
					randChar = (char) (65 + (int) (Math.random() * (26)));
				}
				Block random = new Block
						(randChar, 
								100 * i, 100 * j, COURT_WIDTH, COURT_HEIGHT, Color.cyan);
				blocks[i][j] = random;
			}
			word = "";
			time = 60;
		}
		Solver s = new Solver(this);
		Set <String> solutions = s.getSolutionSet();
		String[] arr = solutions.toArray(new String[solutions.size()]);
		JScrollPane solutionPane = new JScrollPane(); 
		solutionPane.setPreferredSize(new Dimension(50, COURT_HEIGHT - 100));
		JList solList = new JList(arr);
		solutionPane = new JScrollPane(solList);
		solPanel = new JPanel();
		solPanel.add(solutionPane);
		usedBlocks.clear();
		playing = true;
		points = 0;
		status.setText(word);
		sunk = !sunk;

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	public void instructions() {
		inst = !inst;
	}
	
    public void make() throws FileNotFoundException {
	Scanner s = new Scanner(new File("dictionary.txt"));
		while (s.hasNext()) {
			String string = s.next();
			dictionary.add(string);
		}
    }
	
    public Block[][] getBlocks() {
    	return blocks;
    }
    
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!inst) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					blocks[i][j].draw(g);
				}
			}
		}
		else {
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, boardWidth, boardHeight);
			g.setColor(Color.DARK_GRAY);
			g.drawString("BOGGLE RULES:", 150, 15);
			g.setColor(Color.RED);
			g.drawString("Create as many words as you can within a minute.", 10, 30);
			g.drawString("Longer words get you more points!", 10, 45);
			g.drawString("You cannot select the same letter more than once,", 10, 60);
			g.drawString("and once a letter is selected, the next letter selected", 10, 75);
			g.drawString("must be touching that letter tile.", 10, 90);
			g.drawString("Left-click within a letter's square to select that letter.", 10, 105);
			g.drawString("Type enter to submit/clear a word.", 10, 120);
			g.setColor(Color.DARK_GRAY);
			g.drawString("POINT SYSTEM:", 150, 135);
			g.drawString("Word length:", 10, 145);
			g.setColor(Color.BLUE);
			g.drawString("1 = 0 pts, 2 = 0 pts, 3 = 1 pt, 4 = 1 pt", 10, 160);
			g.drawString("5 = 2 pts, 6 = 3 pts, 7 = 5 pts, 8+ = 11 pts", 10, 175);
			g.drawString("Reclick Instructions to resume game or New Game to restart", 10, 190);
		}
	}
	
	
	public boolean getInst() {
		return inst;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}



