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
    	
    	int[] left = new int[maxCol];
    	int[] right = new int[maxCol];
    	int[] height = new int[maxCol];
    	
    	for (int r=0; r<maxRow; r++) {
    		for (int c=0; c<maxCol; c++) {
    			if (matrix[r][c] == one) {

    			}
    		}
    	}	
    	
    	return maxSize;
    	
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maximalRectangle(new char[][]{s("0010"),s("1111"),s("1111"),s("1110"),s("1100"),s("1111"),s("1110")})==12);
		System.out.println(sol.maximalRectangle(new char[][]{s("11111111"),s("11111110"),s("11111110"),s("11111000"),s("01111000")})==21);

		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,zero}})==2);
		System.out.println(sol.maximalRectangle(new char[][]{{zero,one,one,zero,one},{one,one,zero,one,zero},{zero,one,one,one,zero},{one,one,one,one,zero},{one,one,one,one,one},{zero,zero,zero,zero,zero}})==9);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,one},{zero,one,one,zero}})==4);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one}})==2);
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,one}})==4);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero,one,one},{one,zero,one,one},{zero,one,one,one}})==6);		
		System.out.println(sol.maximalRectangle(new char[][]{{one,zero}})==1);

	}

	private static char[] s(String string) {
		return string.toCharArray();
	}

}
