package maximalRectangle;
/*
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing all ones and return its area.
 * 
 * idea:
 * 
 * use DP:
 * 
 * go from top row to bottom row
 * 
 * keep state left[c], right[c], height[c]
 * 
 * at end of each row, calculate max of area
 * 
 */
public class Solution {
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
    	
    	int[] len = new int[maxCol];
    	int[] height = new int[maxCol];
    	
    	for (int r=0; r<maxRow; r++) {
    		for (int c=0; c<maxCol; c++) {
    			if (matrix[r][c] == one) {
    				height[c]++;
    				if (c==0 || matrix[r][c-1] == zero)
    					len[c] = 1;
    				else
    					len[c] = len[c-1]+1;
    			}
    			else {
    				height[c] = 0;
    				len[c] = 0;
    			}
    		}
    		for (int c=0; c<maxCol; c++) {
    			// find max height from c-len[c] to c
    			if (height[c] == 0 || len[c] == 0)
    				continue;
    			int maxHeight = Integer.MAX_VALUE;
    			for (int innerC = c-len[c]+1; innerC <= c; innerC++)
    				if (maxHeight > height[innerC])
    					maxHeight = height[innerC];
    			if (height[c] != 0 && len[c]*maxHeight > maxSize)
    				maxSize = len[c]*maxHeight;
    		}
    	}	
    	return maxSize;
    	
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maximalRectangle(new char[][]{s("1011"),s("1011"),s("0110")})==4);		
		System.out.println(sol.maximalRectangle(new char[][]{s("01101"),s("11010"),s("01110"),s("11110"),s("11111"),s("00000")})==9);		
		System.out.println(sol.maximalRectangle(new char[][]{s("0010"),s("1111"),s("1111"),s("1110"),s("1100"),s("1111"),s("1110")})==12);
		System.out.println(sol.maximalRectangle(new char[][]{s("11111111"),s("11111110"),s("11111110"),s("11111000"),s("01111000")})==21);
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,zero}})==2);
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one}})==2);
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,one}})==4);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,one},{zero,one,one,one}})==6);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero}})==1);

	}

	private static char[] s(String string) {
		return string.toCharArray();
	}

}
