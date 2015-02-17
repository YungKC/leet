package maxProductSubarray;

import java.util.ArrayList;
import java.util.List;

/*
 * 
Find the contiguous subarray within an array (containing at least one number) which has the largest product.

For example, given the array [2,3,-2,4],
the contiguous subarray [2,3] has the largest product = 6.

Observations:
since each element is an int, thus want to group as many as possible
- boils down to ensure that the sign is positive (e.g., numbers of negative values is even)
- watch for 0's
-- verify [-1 0 -1] or [0 -1] or [-1 0]
 */
public class Solution {
    public int maxProduct(int[] A) {
        int length = A.length;
        boolean hasZero = false;
        
        int startIndex = 0;
        for (; startIndex<length; startIndex++)
        	if (A[startIndex] != 0)
        		break;
        if (startIndex != 0)
        	hasZero = true;
        
        int endIndex = length-1;
        for (; endIndex>startIndex; endIndex--)
        	if (A[endIndex] != 0)
        		break;
        if (endIndex != length-1)
        	hasZero = true;
        
 
        List<Segment> segmentList = new ArrayList<Segment>();
        int lastNonZeroIndex = startIndex;
        int numNegatives = 0;
        endIndex++;	// mark last good index + 1;
        
        for (int i=startIndex; i<=endIndex; i++) {
        	if (i==endIndex || A[i] == 0) {
        		if (i != startIndex) {
        			segmentList.add(new Segment(startIndex, lastNonZeroIndex, numNegatives));
        		}
        		if (i==endIndex)
        			break;
        		else
        			hasZero = true;
        		startIndex = i+1;
        		numNegatives = 0;
        	}
        	else {
        		lastNonZeroIndex = i;
        		if (A[i] < 0)
        			numNegatives++;
        	}
        }
        
        int maxProduct = Integer.MIN_VALUE;
        if (hasZero)
        	maxProduct = 0;
        
        for (Segment segment : segmentList) {
        	boolean isNegative = segment.numNegatives%2 == 1;

        	if (isNegative) {
        		if (segment.end-segment.start >= 1) {
        			// remove either the first or last negative entry
        			int leftInnerProduct = 1;
        			int leftIndex=0;
        			for (int i=segment.start;;i++) {
        				leftInnerProduct *= A[i];
        				if (A[i] < 0) {
        					leftIndex = i;
        					break;
        				}
        			}
        			int rightInnerProduct = 1;
        			int rightIndex=0;
        			for (int i=segment.end;;i--) {
        				rightInnerProduct *= A[i];
        				if (A[i] < 0) {
        					rightIndex = i;
        					break;
        				}
        			}
        			if (leftInnerProduct < rightInnerProduct)
        				segment.end = rightIndex-1;
        			else
        				segment.start = leftIndex+1;	
        		}
        		else {
        			if (maxProduct < A[segment.start])
        				maxProduct = A[segment.start];
        			continue;
        		}
        	}
        	int product = 1;
    		for (int i=segment.start; i<= segment.end; i++) {
    			product *= A[i];
    		}
    		if (product > maxProduct) {
    			maxProduct = product;
    		}
        }
        return maxProduct;
    }
        
    class Segment {
    	int start;
    	int end;
    	int numNegatives;
    	
    	private Segment(int startIndex, int endIndex, int numNegatives) {
    		start = startIndex;
    		end = endIndex;
    		this.numNegatives = numNegatives;
    	}
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		long time = System.currentTimeMillis();
		System.out.println(sol.maxProduct(new int[]{-1,0,-2,2}) == 2);
		System.out.println(sol.maxProduct(new int[]{-5,0,2}) == 2);
		System.out.println(sol.maxProduct(new int[]{-4,-3,-2}) == 12);
		System.out.println(sol.maxProduct(new int[]{0}) == 0);
		System.out.println(sol.maxProduct(new int[]{0,-1}) == 0);
		System.out.println(sol.maxProduct(new int[]{0,1}) == 1);
		System.out.println(sol.maxProduct(new int[]{1,2,3,4,5,6,7}) == 1*2*3*4*5*6*7);
		System.out.println(sol.maxProduct(new int[]{-1,0,-1}) == 0);
		System.out.println(sol.maxProduct(new int[]{-1,0}) == 0);
		System.out.println(sol.maxProduct(new int[]{0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0}) == 1*2*3*4*5);
		System.out.println(sol.maxProduct(new int[]{0,0,0,0,1,-2,-3,-4,-5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0}) == 1*2*3*4*5);
		System.out.println(sol.maxProduct(new int[]{0,0,0,0,1,-2,-3,-4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0}) == -3*-4*5);
		System.out.println(sol.maxProduct(new int[]{0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,6,7,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0,1,2,3,4,5,0,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,6,7,0,0,0,0,1,2,3,4,5,0}) == 1*2*3*4*5);
		System.out.println(System.currentTimeMillis()-time);
	}

}
