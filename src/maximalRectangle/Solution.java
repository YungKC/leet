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
    	
    	int[] start = new int[maxCol];	// value starts at 1 instead of 0 to differentiate it from the default value of 0 when the array is initialized
    	int[] end = new int[maxCol];
    	int[] height = new int[maxCol];
    	
    	for (int r=0; r<maxRow; r++) {
    		for (int c=0; c<maxCol; c++) {
    			if (matrix[r][c] == one) {
    				height[c]++;
    				if (c==0 || matrix[r][c-1] == zero) {
    					start[c] = c+1;		// make it starts at index 1 to differentiate it from the default value of 0
    				}
    				else
    					start[c] = start[c-1];
    			}
    			else {
    				height[c] = 0;
    				for (int lastC=c-1; lastC >=0 && start[lastC] != 0; lastC--)
    					end[lastC] = c;		// make it starts at index 1 to differentiate it from the default value of 0
    			}
    		}

    		// find length across where height >= height[c]
    		for (int c=0; c<maxCol; c++) {
    			int curHeight = height[c];
    			if (curHeight == 0)
    				continue;
    			int length = 1;
    			for(int i=1; c+1-i >= start[c] && height[c-i] >= curHeight ; i++) {
    				length++;
    			}
    			for(int i=1; c+1+i <= end[c] && height[c+i] >= curHeight; i++) {
    				length++;
    			}
    			if (length * curHeight > maxSize) {
    				maxSize = length * curHeight;
    			}
    		}

    	}	
    	return maxSize;
    	
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maximalRectangle(new char[][]{s("0110111111111111110"),s("1011111111111111111"),s("1101111111110111111"),  s("1111111111111011111"),s("1111111111111101111"),s("1110111011111111101"),  s("1011111111111101111"),s("1111111111111110110"),s("0011111111111110111"),  s("1101111111011111111"),s("1111111110111111111"),s("0110111011111111111"),  s("1111011111111101111"),s("1111111111111111111"),s("1111111111111111111"),  s("1111111111111111101"),s("1111111101101101111"),s("1111110111111110111")})==51);		
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
/*
    1234567890123456789

1	0110111111111111110
2	1011111111111111111
3	1101111111110111111
4	1111111111111011111
5	1111111111111101111
6	1110111011111111101
7	1011111111111101111
8	1111111111111110110
9	0011111111111110111
0	1101111111011111111
1	1111111110111111111
2	0110111011111111111
3	1111011111111101111
4	1111111111111111111
5	1111111111111111111
6	1111111111111111101
7	1111111101101101111
8	1111110111111110111



*/
