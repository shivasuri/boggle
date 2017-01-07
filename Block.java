import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class Block implements Comparable<Block> {
	// private instance variables
	private final char c;
	private static final int width = 100;
	private static final int height = 100;
	public static final int SIZE = 100;
	private int xPos;
	private int yPos;
	Color col;
	// constructor
	public Block(char c, int xPos, int yPos, int courtWidth, int courtHeight, Color col) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.c = c;
		this.col = col;
	}
	
	// methods
	public char getCharacter() {
		return c;
	}
	
	public Color getColor() {
		return col;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public void draw(Graphics g) {
		g.setColor(col);
		
		//g.fillRect(xPos, yPos, width, height);
		g.fill3DRect(xPos, yPos, width, height, Board.sunk);
		
		g.setColor(Color.BLACK);
		//g.drawRect(xPos, yPos, width, height);
		g.drawString("" + c, xPos + width / 2, yPos + width / 2);
	}
	
	public void changeColor() {
		col = Color.yellow;
	}
	
	public void changeGreen() {
		col = Color.green;
	}
	
	public void changeGray() {
		col = Color.LIGHT_GRAY;
	}
	
	public void changeColorBack() {
		col = Color.cyan;
	}
	
	
	@Override
	public int compareTo(Block block) {
		int x = 1;
		if ((this.c == block.c) && 
				(this.xPos == block.xPos) && (this.yPos == block.yPos)) {
			x = 0;
		}
		else if (this.c < block.c) {
			x = -1;
		}
		return x;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Block block = (Block) obj;
		boolean isEqual = (xPos == block.xPos) && 
				(yPos == block.yPos) && (c == block.c);
		return isEqual;
	}
}
