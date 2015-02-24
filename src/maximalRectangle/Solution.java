package maximalRectangle;
/*
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing all ones and return its area.
 * 
 */
public class Solution {
    public int maximalRectangle(char[][] matrix) {
    	int maxSize = 0;
    	int maxRow = matrix.length;
    	if (matrix == null || maxRow == 0)
    		return 0;
    	int maxCol = matrix[0].length; 
    	if (matrix[0].length == 0)
    		return 0;
    	
    	// last index [0] == startRow# 1based, [1] == endRow# 1based
    	int state[][][] = new int[maxRow][maxCol][2];		
    	
    	if (matrix[0][0] == '1') {
    		state[0][0][0] = 1;
    		state[0][0][1] = 1;
    		maxSize = 1;
    	}
    	for (int r=1; r<matrix.length; r++) {
    		if (matrix[r][0] == '1') {
    			state[r][0][0] = state[r-1][0][0];
    			state[r][0][1] = state[r-1][0][1];
    			
    		}
    	}

    	for (int c=1; c<maxCol; c++) {
    		if (matrix[0][c] == '1') {
    			state[0][c][0] = state[0][c-1][0];
    			state[0][c][1] = state[0][c-1][1];
    		}
    	}

    	
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maximalRectangle(new char[][]{{'1','0'}})==1);

	}

}
