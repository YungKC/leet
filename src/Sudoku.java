import java.util.HashSet;
import java.util.Set;

/*
Determine if a Sudoku is valid, according to: Sudoku Puzzles - The Rules.

The Sudoku board could be partially filled, where empty cells are filled with the character '.'.

Note:
A valid Sudoku board (partially filled) is not necessarily solvable. Only the filled cells need to be validated.


TODO: may want to switch on how sparse the matrix is. Can trim/sort as preprocess step

 */
public class Sudoku {

	private static boolean isValidChar(char inChar) {
		return inChar >= '1' && inChar <= '9';
	}
	
    public static boolean isValidSudoku(char[][] board) {
    	if (board.length != 9 || board[0].length != 9)
    		return false;
    	for (int i=0; i<9; i++) {
    		Set<Character> observedChars = new HashSet<Character>();
    		for (int j=0; j<9; j++) {
    			char curChar = board[i][j];
    			if (curChar != '.') {
    				if (!isValidChar(curChar) || observedChars.contains(curChar))
    					return false;
    				else
    					observedChars.add(curChar);
    			}
    		}
    	}
    	for (int i=0; i<9; i++) {
    		Set<Character> observedChars = new HashSet<Character>();
    		for (int j=0; j<9; j++) {
    			char curChar = board[j][i];
    			if (curChar != '.') {
    				if (!isValidChar(curChar) || observedChars.contains(curChar))
    					return false;
    				else
    					observedChars.add(curChar);
    			}
    		}
    	}

    	for (int i=0; i<3; i++) {
    		for (int j=0; j<3; j++) {
        		Set<Character> observedChars = new HashSet<Character>();
        		for (int ii=0; ii<3; ii++) {
        			for (int jj=0; jj<3; jj++) {
		    			char curChar = board[i*3+ii][j*3+jj];
		    			if (curChar != '.') {
		    				if (!isValidChar(curChar) || observedChars.contains(curChar))
		    					return false;
		    				else
		    					observedChars.add(curChar);
		    			}
        			}
        		}
    		}
    	}
        return true;
    }

	public static void main(String[] args) {
		int xDim = 9;
		int yDim = 9;
		
		char[][]  myBoard = new char[xDim][yDim];
		
    	for (int i=0; i<xDim; i++) {
    		for (int j=0; j<yDim; j++) {
    			myBoard[i][j] = '.';
    		}
    	}
    	myBoard[4][3] = '3';
    	myBoard[4][5] = '4';
    	myBoard[3][4] = '3';
    	
		System.out.println(isValidSudoku(myBoard));
	}
}
