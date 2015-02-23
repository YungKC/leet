package maximalRectangle;
/*
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing all ones and return its area.
 * 
 */
public class Solution {
    public int maximalRectangle(char[][] matrix) {
    	int maxSize = 0;
    	if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
    		return 0;
    	
    	// last index [0] == startRow# 1based, [1] == endRow# 1based
    	int state[][][] = new int[matrix.length][matrix[0].length][2];		
    	
    	if (matrix[0][0] == '1') {
    		state[0][0][0] = 1;
    		state[0][0][1] = 1;
    		maxSize = 1;
    	}
    	for (int r=0; r<matrix.length; r++) {
    		for (int c=1; c<matrix[0].length; c++) {
    			if (matrix[r][c] == '1') {
    				state[r][c][0] == 
    			}
    		}
    	}
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maximalRectangle(new char[][]{{'1','0'}})==1);

	}

}
