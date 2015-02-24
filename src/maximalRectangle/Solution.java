package maximalRectangle;
/*
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing all ones and return its area.
 * 
 * idea:
 * 
 * use DP:
 * 
 * consider 4 cases:
 * 
 *    . 1	. 1		. 0 	. 0
 *    1 1	0 1		1 1		0 1
 */
public class Solution {
	private static final int FIRST_ROW_WITH_1 = 0;		// first cell is (1,1)
	private static final int FIRST_COL_WITH_1 = 1;
	private static final int ROW_NUM_WITH_1 = 2;
	private static final int COL_NUM_WITH_1 = 3;
	private static final char one = '1';
	private static final char zero = '0';
	
	
    public int maximalRectangle(char[][] matrix) {
    	int maxSize = 0;
    	int maxRow = matrix.length;
    	if (matrix == null || maxRow == 0)
    		return 0;
    	int maxCol = matrix[0].length; 
    	if (matrix[0].length == 0)
    		return 0;
    	
    	// last index [0] == startRow# 1based, [1] == endRow# 1based
    	int state[][][] = new int[maxRow][maxCol][4];		
    	
    	if (matrix[0][0] == one) {
    		state[0][0][FIRST_ROW_WITH_1] = 1;
    		state[0][0][FIRST_COL_WITH_1] = 1;
    		state[0][0][ROW_NUM_WITH_1] = 1;
    		state[0][0][COL_NUM_WITH_1] = 1;
    		maxSize = 1;
    	}
    	
    	for (int r=1; r<maxRow; r++) {
    		if (matrix[r][0] == one) {
    			state[r][0][FIRST_COL_WITH_1] = 1;
    			if (matrix[r-1][0] == one) {
        			state[r][0][FIRST_ROW_WITH_1] = state[r-1][0][FIRST_ROW_WITH_1];
	    			state[r][0][ROW_NUM_WITH_1] = state[r-1][0][ROW_NUM_WITH_1];
	    			state[r][0][COL_NUM_WITH_1] = 1;
    			}
    			else {
        			state[r][0][FIRST_ROW_WITH_1] = r+1;
	    			state[r][0][ROW_NUM_WITH_1] = r+1;
	    			state[r][0][COL_NUM_WITH_1] = 1;
    			}
    			int curSize = r - state[r][0][ROW_NUM_WITH_1] + 2;
    			if (curSize > maxSize)
    				maxSize = curSize;
    		}
    	}

    	for (int c=1; c<maxCol; c++) {
    		if (matrix[0][c] == one) {
    			state[0][c][FIRST_ROW_WITH_1] = 1;
    			if (matrix[0][c-1] == one) {
        			state[0][c][FIRST_COL_WITH_1] = state[0][c-1][FIRST_COL_WITH_1];
	    			state[0][c][ROW_NUM_WITH_1] = 1;
	    			state[0][c][COL_NUM_WITH_1] = state[0][c-1][COL_NUM_WITH_1];
    			}
    			else {
        			state[0][c][FIRST_COL_WITH_1] = c+1;
	    			state[0][c][ROW_NUM_WITH_1] = 1;
	    			state[0][c][COL_NUM_WITH_1] = c+1;
    			}
    			int curSize = c - state[0][c][COL_NUM_WITH_1] + 2;
    			if (curSize > maxSize)
    				maxSize = curSize;
    		}
    	}

    	for (int r=1; r<maxRow; r++) {
        	for (int c=1; c<maxCol; c++) {
	    		if (matrix[r][c] == one) {
	    			if (matrix[r-1][c] == one && matrix[r][c-1] == one) {
	    				if (matrix[r-1][c-1] == one) {
			    			state[r][c][FIRST_COL_WITH_1] = state[r-1][c-1][FIRST_COL_WITH_1];
		        			state[r][c][FIRST_ROW_WITH_1] = state[r-1][c-1][FIRST_ROW_WITH_1];
			    			state[r][c][ROW_NUM_WITH_1] = state[r-1][c][ROW_NUM_WITH_1];
			    			state[r][c][COL_NUM_WITH_1] = state[r][c-1][COL_NUM_WITH_1];
	    				}
	    				else {
	    					// determine which is longer (from top or from left)
	    					if (c - state[r][c][COL_NUM_WITH_1] > r - state[r][c][ROW_NUM_WITH_1]) {
	    	        			state[r][c][FIRST_ROW_WITH_1] = r+1;
	    		    			state[r][c][FIRST_COL_WITH_1] = state[r][c-1][FIRST_COL_WITH_1];
	    		    			state[r][c][ROW_NUM_WITH_1] = r+1;
	    		    			state[r][c][COL_NUM_WITH_1] = state[r][c-1][COL_NUM_WITH_1];
	    					}
	    					else {
	    	        			state[r][c][FIRST_ROW_WITH_1] = state[r-1][c][FIRST_ROW_WITH_1];
	    		    			state[r][c][FIRST_COL_WITH_1] = c+1;
	    		    			state[r][c][ROW_NUM_WITH_1] = state[r-1][c][ROW_NUM_WITH_1];
	    		    			state[r][c][COL_NUM_WITH_1] = c+1;
	    					}
	    				}
	    			}
	    			else if (matrix[r][c-1] == one) {
	        			state[r][c][FIRST_ROW_WITH_1] = r+1;
		    			state[r][c][FIRST_COL_WITH_1] = state[r][c-1][FIRST_COL_WITH_1];
		    			state[r][c][ROW_NUM_WITH_1] = r+1;
		    			state[r][c][COL_NUM_WITH_1] = state[r][c-1][COL_NUM_WITH_1];
	    			}
	    			else if (matrix[r-1][c] == one) {
	        			state[r][c][FIRST_ROW_WITH_1] = state[r-1][c][FIRST_ROW_WITH_1];
		    			state[r][c][FIRST_COL_WITH_1] = c+1;
		    			state[r][c][ROW_NUM_WITH_1] = state[r-1][c][ROW_NUM_WITH_1];
		    			state[r][c][COL_NUM_WITH_1] = c+1;
	    			}
	    			else {
	        			state[r][c][FIRST_ROW_WITH_1] = r+1;
		    			state[r][c][FIRST_COL_WITH_1] = c+1;
		    			state[r][c][ROW_NUM_WITH_1] = r+1;
		    			state[r][c][COL_NUM_WITH_1] = c+1;
	    			}
	    			
	    			int curSize = (r - state[r][c][FIRST_ROW_WITH_1] + 2) * (c - state[r][c][FIRST_COL_WITH_1] +2);
	    			if (curSize > maxSize)
	    				maxSize = curSize;
	    		}
	    	}
    	}
    	
    	return maxSize;
    	
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maximalRectangle(new char[][]{{zero,one,one,zero,one},{one,one,zero,one,zero},{zero,one,one,one,zero},{one,one,one,one,zero},{one,one,one,one,one},{zero,zero,zero,zero,zero}})==9);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,one},{zero,one,one,one}})==6);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,one},{zero,one,one,zero}})==4);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,one}})==4);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,zero}})==2);
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one}})==2);
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero}})==1);

	}

}
