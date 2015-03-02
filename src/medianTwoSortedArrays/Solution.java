package medianTwoSortedArrays;


/*
 * 
Median of Two Sorted Arrays
There are two sorted arrays A and B of size m and n respectively. 
Find the median of the two sorted arrays. 
The overall run time complexity should be O(log (m+n)).

idea:

find median of each array, then shift boarder by lesser length until the shorter array is narrowed to 2 values
then find center 4 values of the longer array.

Now it's just a problem of finding median of at most 6 values.
 */
public class Solution {

    public double findMedianSortedArrays(int A[], int B[]) {
    	if (A.length == 0 && B.length == 0)
    		return 0;
    	else if (A.length == 0) {
    		return median(B);
    	}
    	else if (B.length == 0) {
    		return median(A);
    	}
    	
    	int longer[];
    	int shorter[];
    	if (A.length > B.length) {
    		longer = A;
    		shorter = B;
    	}
    	else {
    		longer = B;
    		shorter = A;
    	}
    	

	    return inner(shorter, longer);
    }

    private double inner(int[] shorter, int[] longer) {
     	int longerLeft=0, shorterLeft=0, longerRight=longer.length-1, shorterRight=shorter.length-1;
   		int totalLength = longer.length + shorter.length;
   		boolean isTotalLengthOdd = totalLength%2 == 1;
   		int idealNumLeftElements = totalLength/2;

     	// [1 2], [4 5]
    	if (shorter[shorterRight] <= longer[longerLeft]) {
    		if (longerRight == shorterRight) {
    			return (shorter[shorterRight] + longer[longerLeft])/2.0;
    		}
    		else {
    			int centerIndex = (longer.length-shorter.length)/2;
    			if (!isTotalLengthOdd) {
    				return (longer[centerIndex-1] + longer[centerIndex])/2.0;
    			}
    			else {
    				return longer[centerIndex];
    			}
    		}
    	}
    	// [4 5], [1 2]
    	else if (shorter[shorterLeft] >= longer[longerRight]) {
    		if (longerRight == shorterRight) {
    			return (shorter[shorterLeft] + longer[longerRight])/2.0;
    		}
    		else {
    			int centerIndex = shorter.length + (longer.length-shorter.length)/2;
    			if (!isTotalLengthOdd) {
    				return (longer[centerIndex-1] + longer[centerIndex])/2.0;
    			}
    			else {
    				return longer[centerIndex];
    			}
    		}
    	}

    	// find median of longer array, then find position where that value will be in shorter array
    	// compare the num elements on either side of this value
    	// adjust left or right boundary of longer array, and repeat

// 		int shortStartIndex = 0;
    	int medianShorterIndex  = 0;
// 		int shortEndIndex = shorter.length-1;
    	boolean isFailed = false;
     	do {
   			int medianLongerIndex = (longerLeft + longerRight)/2;
	    	int medianValue = longer[medianLongerIndex];
	    	medianShorterIndex= findMedianIndex(shorter, 0, shorter.length-1, medianValue);

	    	int numLeftElements = medianShorterIndex + medianLongerIndex;
	    	if (shorter[medianShorterIndex] <= medianValue)
	    		numLeftElements++;
	    	
	    	int numElementsMoreOnLeft = numLeftElements - idealNumLeftElements;

	    	if (numElementsMoreOnLeft == 0 || (!isTotalLengthOdd && numElementsMoreOnLeft == -1)) {
		    	// now we are narrowed to picking the median
		    	if (medianLongerIndex == 0) {
		    		if (isTotalLengthOdd)
		    			return longer[0];
		    		else
		    			return (longer[0] + shorter[shorter.length-1])/2.0;
		    	}
		    	else if (medianLongerIndex == longer.length - 1) {
		    		if (isTotalLengthOdd) {
		    			return longer[longer.length-1];
		    		}
		    		else
		    			return (longer[longer.length-1] + shorter[0])/2.0;
		    	}
		    	else if (longer[medianLongerIndex-1] > shorter[shorter.length-1] || longer[medianLongerIndex+1] < shorter[0]) {
		    		if (isTotalLengthOdd)
		    			return longer[medianLongerIndex];
		    		else
		    			return (longer[medianLongerIndex] + longer[medianLongerIndex+1])/2.0;
		    	}
		    	else {
		    		if (isTotalLengthOdd)
		    			return longer[medianLongerIndex];
		    		else {
		    			if (numElementsMoreOnLeft >= 0) {
		    				int nextValue = longer[medianLongerIndex-1];
		    				if (longer[medianLongerIndex] > shorter[medianShorterIndex] && shorter[medianShorterIndex] > nextValue)
		    					nextValue = shorter[medianShorterIndex];
			    			return (longer[medianLongerIndex] + nextValue)/2.0;
		    			}
		    			else {
		    				int nextValue = longer[medianLongerIndex+1];
		    				if (longer[medianLongerIndex] < shorter[medianShorterIndex] && shorter[medianShorterIndex] < nextValue)
		    					nextValue = shorter[medianShorterIndex];
			    			return (longer[medianLongerIndex] + nextValue)/2.0;
		    			}
		    		}
		    	}
	    	}
	    	else if (numElementsMoreOnLeft > 0) {
	    		if (longerRight == medianLongerIndex)
	    			isFailed = true;
	    		else
	    			longerRight = medianLongerIndex;
	    	}
	    	else {
	    		if (longerLeft == medianLongerIndex)
	    			isFailed = true;
	    		else
	    			longerLeft = medianLongerIndex;
	    	}
		} while (!isFailed);
    	
     	return inner(longer, shorter);
    }
    
    
    
    private int findMedianIndex(int[] array, int left, int right, int medianValue) {
    	if (medianValue >= array[right])
    		return right;
    	else if (medianValue <= array[left])
    		return left;
    	while (right - left > 1) {
        	int median = (left+right)/2;
    		if (array[median] > medianValue)
    			right = median;
    		else if (array[median] < medianValue) 
    			left = median;
    	}
    	return right;
    }
    	
    
    private double median(int[] array) {
    	int midPt = array.length/2;
    	if (array.length % 2 == 1)
    		return array[midPt];
    	else
    		return (array[midPt-1]+array[midPt])/2.0;
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		

		System.out.println(sol.findMedianSortedArrays(new int[]{1,4,5,6}, new int[]{2,3,7,8}) == 4.5);		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{1,4,5,6,7,8,9}) == 4.5);

		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,4}, new int[]{3,5,6,7,8}) == 4.5);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,4}, new int[]{3,5,6}) == 3.5);

		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{1,4,5,6,7,8}) == 4);
		System.out.println(sol.findMedianSortedArrays(new int[]{8,9,10}, new int[]{1,4,5,6,7,8}) == 7);
		System.out.println(sol.findMedianSortedArrays(new int[]{8,9,10}, new int[]{1,4,5,6,7,8,9}) == 7.5);

		System.out.println(sol.findMedianSortedArrays(new int[]{2,10}, new int[]{1,2,3,4,5,6,7,8,9,10}) == 5.5d);



		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{4,5,6,7,8}) == 4.5);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{4,5,6}) == 3.5);
		System.out.println(sol.findMedianSortedArrays(new int[]{2,10}, new int[]{1}) == 2);
		System.out.println(sol.findMedianSortedArrays(new int[]{1, 2 , 3}, new int[]{}) == 2.0d);
		System.out.println(sol.findMedianSortedArrays(new int[]{1, 2 , 3}, new int[]{3, 4}) == 3.0d);
		System.out.println(sol.findMedianSortedArrays(new int[]{1, 1}, new int[]{3, 4}) == 2.0d);
		System.out.println(sol.findMedianSortedArrays(new int[]{10}, new int[]{1,2,3,4,5,6,7,8,9,10}) == 6d);
		System.out.println(sol.findMedianSortedArrays(new int[]{1, 1}, new int[]{3, 4}) == 2.0d);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3,4,5,6,7,8,9,10}, new int[]{10}) == 6d);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1}, new int[]{10}) == 1d);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1}, new int[]{0}) == 1d);
		System.out.println(sol.findMedianSortedArrays(new int[]{}, new int[]{1,2,3,4}) == 2.5d);

		System.out.println(sol.findMedianSortedArrays(new int[]{1,1,3,3}, new int[]{1,1,3,3}) == 2);


	}

}
