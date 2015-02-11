import java.util.ArrayList;

/*
 * 
 * Given an index k, return the kth row of the Pascal's triangle.

For example, given k = 3,
Return [1,3,3,1].

Note:
Could you optimize your algorithm to use only O(k) extra space?

 */
public class PascalTriangle2 {

	public static ArrayList<Integer> getRow(int requestedRow) {
		ArrayList<Integer> result = new ArrayList<Integer>(requestedRow+1);
		for (int i=0; i<=requestedRow; i++)
			result.add(1);
        if (requestedRow <= 1)
        	return result;
        
        for (int curRow = 2; curRow <= requestedRow; curRow++) {
 //   		System.out.println("for row " + curRow);
        	
        	for (int index = 1; index <= curRow/2; index++) {
 //       		System.out.println("inserting " + (result.get(index-1) + result.get(index)) + " into " + (curRow-index));
        		result.set(curRow-index, result.get(index-1) + result.get(index));
        	}      
        	for (int index = 1; index < (curRow+1)/2; index++) {
        		int value = result.get(curRow-index);
//        		System.out.println("inserting " + value + " into " + index);
        		result.set(index, value);
        	}
//        	System.out.println(result.toString());
        }
        return result;
	}


	public static void main(String[] args) {
		System.out.println(getRow(20).toString());

	}

}
