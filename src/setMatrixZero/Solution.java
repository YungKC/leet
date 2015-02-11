package setMatrixZero;

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

        int referenceRowNum = -1;
        for (int r=0; r<numRows && referenceRowNum < 0; r++) {
            for (int c=0; c<numColumns && referenceRowNum < 0; c++) {
                if (matrix[r][c] == 0) {
                    referenceRowNum = r;
                }
            }
        }

        if (referenceRowNum < 0)
            return;

        for (int r=referenceRowNum+1; r<numRows; r++) {
            boolean foundZero = false;
            for (int c=0; c<numColumns; c++) {
                if (matrix[r][c] == 0) {
                    foundZero = true;
                    matrix[referenceRowNum][c] = 0;
                }
            }
            if (foundZero)
                for (int c=0; c<numColumns; c++)
                    matrix[r][c] = 0;

        }

        for (int c=0; c<numColumns; c++) {
            if (matrix[referenceRowNum][c] == 0) {
                for (int r=0; r<numRows; r++) {
                    matrix[r][c] = 0;
                }
            }
            
            
        }

        for (int c=0; c<numColumns; c++)
            matrix[referenceRowNum][c] = 0;

    }


    public static void main(String[] argv) {
// [[0,0,0,5],[4,3,1,4],[0,1,1,4],[1,2,1,3],[0,0,1,1]]
        int numColumns = 4;
        int numRows = 5;
        int[][] matrix = new int[numRows][numColumns];
        matrix[0][3] = 5;
        matrix[1][0] = 4;
        matrix[1][1] = 3;
        matrix[1][2] = 1;
        matrix[1][3] = 4;
        matrix[2][1] = 1;
        matrix[2][2] = 1;
        matrix[2][3] = 4;
        matrix[3][0] = 1;
        matrix[3][1] = 2;
        matrix[3][2] = 1;
        matrix[3][3] = 3;
        matrix[4][2] = 1;
        matrix[4][3] = 1;

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

