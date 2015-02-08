/* if an element is zero, then set all elements in that column and row to zeros
	perform this in place.

	Difficulty is in desire that we need to identify all zero elements first before modifying the rows and columns

	strategy:
	- identify the first row that we will zero out. This row wiill be used to indentify which columns will be zeroed out
 */

public class Solution {
    public void setZeroes(int[][] matrix) {
        int numRows = matrix.length;
        int numColumns = 0;
        if (numRows > 0)
        	numColumns = matrix[0].length;

        System.out.println(numRows + ":" + numColumns);

        int referenceRowNum = -1;
       	for (int r=0; r<numRows && referenceRowNum < 0; r++) {
    		for (int c=0; c<numColumns && referenceRowNum < 0; c++) {
    			if (matrix[r][c] == 0) {
    				referenceRowNum = r;
    			}
    		}
    	}

        System.out.println("referenceRowNum: " + referenceRowNum);

    	if (referenceRowNum < 0)
    		return;



       	for (int r=referenceRowNum+1; r<numRows; r++) {
    		for (int c=0; c<numColumns; c++) {
    			if (matrix[r][c] == 0) {
    				matrix[referenceRowNum][c] = 0;
    				zeroRow(matrix, r, numColumns);
    			}
    		}
    	}

		for (int c=0; c<numColumns; c++) {
			if (matrix[referenceRowNum][c] == 0) {
				for (int r=0; r<numRows; r++) {
					matrix[r][c] = 0;
				}
			}
		}

        zeroRow(matrix, referenceRowNum, numColumns);
       
    }

    private void zeroRow(int[][] matrix, int rowNum, int numColumns) {
    	for (int c=0; c<numColumns; c++)
    		matrix[rowNum][c] = 0;
    }

    public static void main(String[] argv) {
        int numColumns = 1;
        int numRows = 2;
        int[][] matrix = new int[numRows][numColumns];
        matrix[1][0] = 1;

        System.out.println("Before");
        for (int r=0; r<numRows; r++) {
            for (int c=0; c<numColumns; c++) {
                System.out.print(matrix[r][c]+" ");
            }
            System.out.println();
        }

        new Solution().setZeroes(matrix);

        System.out.println("After");
        for (int r=0; r<numRows; r++) {
            for (int c=0; c<numColumns; c++) {
                System.out.print(matrix[r][c]+" ");
            }
            System.out.println();
        }
    }
}

