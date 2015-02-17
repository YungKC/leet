package surroundedRegions;

/*
 * 
Given a 2D board containing 'X' and 'O', capture all regions surrounded by 'X'.

A region is captured by flipping all 'O's into 'X's in that surrounded region.

For example,
X X X X
X O O X
X X O X
X O X X
After running your function, the board should be:

X X X X
X X X X
X X X X
X O X X

idea:

start from top left corner, find any 'O' at the edge. Mark it as safe. Then mark each adjacent 'O' as safe.

 */
public class Solution {
	private char[][] board;
	private int numRows;
	private int numCols;
	
    public void solve(char[][] board) {
    	this.board = board;
        numRows = board.length;
        if (numRows == 0)
        	return;
        numCols = board[0].length;
        if (numCols == 0)
        	return;

        
        for (int r=0; r<numRows; r++) {
        	if (board[r][0]=='O') {
        		board[r][0] = '.';
        		if (numCols > 1)
        			spreadIfPossible(r, 1);
        	}
        }
        for (int r=0; r<numRows; r++) {
        	if (board[r][numCols-1]=='O') {
        		board[r][numCols-1] = '.';
        		if (numCols > 1)
        			spreadIfPossible(r, numCols-2);
        	}
        }
        for (int c=1; c<numCols-1; c++) {
        	if (board[0][c]=='O') {
        		board[0][c] = '.';
        		if (numRows > 1)
        			spreadIfPossible(1, c);
        	}
        }
        for (int c=1; c<numCols-1; c++) {
        	if (board[numRows-1][c]=='O') {
        		board[numRows-1][c] = '.';
        		if (numRows > 1)
        			spreadIfPossible(numRows-2, c);
        	}
        }
        
        for (int r=0; r<numRows; r++) {
            for (int c=0; c<numCols; c++) {
	        	if (board[r][c] == 'O')
	        		board[r][c] = 'X';
	        	else if (board[r][c] == '.')
	        		board[r][c] = 'O';
            }
        }        
    }
    
    private void spreadIfPossible(int row, int col) {
		if (board[row][col] == 'O') {
			board[row][col] = '.';
			if (row+1 < numRows)
				spreadIfPossible(row+1, col);
			if (row > 1)
				spreadIfPossible(row-1, col);
			if (col+1 < numCols)
				spreadIfPossible(row, col+1);
			if (col > 1)
				spreadIfPossible(row, col-1);
		}
    }
    
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		char[][] board;
		
		board = new char[][] {
				{'X','X','X','X','X'},
				{'X','O','O','X','X'},
				{'X','O','X','O','X'},
				{'X','X','O','O','O'},
				{'X','X','O','X','O'},
		};
		display(board);
		sol.solve(board);
		display(board);
		
		
		
		
		board = new char[][]{
				{'X','X','X','X'},
				{'X','O','O','X'},
				{'X','X','X','O'},
				{'X','O','X','X'}
		};
		display(board);
		sol.solve(board);
		display(board);
	}
	
	public static void display(char[][] board) {
		for (char[] row: board) {
			for (char col : row) {
				System.out.print(col + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
