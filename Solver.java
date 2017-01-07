import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solver {
	// NavigableSet so I can implement dictionary.subSet
	static Set<String> dictionary = new TreeSet<String>();
	Set<String> solutionSet = new TreeSet<String>();
	Board b;
	
	public Solver(Board b) {
		this.b = b;
		try{
		make();
		addWords();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
    public void make() throws FileNotFoundException {
	Scanner s = new Scanner(new File("dictionary.txt"));
		while (s.hasNext()) {
			String string = s.next();
			dictionary.add(string);
		}
    }
    
    // recursive dfs solver
    public boolean solve(Board board, int x, int y, int n, String s) {
    	Block[][] blocks = board.getBlocks();
    	// base
    	if (x >= 4 || y >= 4 || x < 0 || y < 0) {
    		return false;
    	}
    	if (Character.toLowerCase(s.charAt(n)) == Character.toLowerCase(blocks[x][y].getCharacter())) {
	    	if (n == s.length() - 1) {
	    		return true;
	    	}
	    	else {
	    		return (solve(board, x + 1, y, n + 1, s) || 
						solve(board, x, y + 1, n + 1, s) ||
						solve(board, x - 1, y, n + 1, s) ||
						solve(board, x, y - 1, n + 1, s) ||
						solve(board, x + 1, y + 1, n + 1, s) ||
						solve(board, x + 1, y - 1, n + 1, s) ||
						solve(board, x - 1, y + 1, n + 1, s) ||
						solve(board, x - 1, y - 1, n + 1, s));
	    	}
    	}
    	return false;
    }
    
    // helper function to dfs solver
    public boolean isInDictionary(Board board, String s) {
    	for (int x = 0; x < 4 ; x++) {
    		for (int y = 0; y < 4; y++) {
    			if (solve(board, x, y, 0, s)) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    	
    public void addWords() {
    	for (String s : dictionary) {
    		if (isInDictionary(b, s) && s.length() > 2) {
    			solutionSet.add(s.toLowerCase());
    		}
    	}
    }
    
    public Set<String> getSolutionSet() {
    	return solutionSet;
    }

}
