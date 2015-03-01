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
    	
     	int longerLeft=0, shorterLeft=0, longerRight=longer.length-1, shorterRight=shorter.length-1;
   		int totalLength = longer.length + shorter.length;
   		int idealMedianIndex = totalLength/2;

     	// [1 2], [4 5]
    	if (shorter[shorterRight] <= longer[longerLeft]) {
    		if (longerRight == shorterRight) {
    			return (shorter[shorterRight] + longer[longerLeft])/2.0;
    		}
    		else {
    			int centerIndex = (longer.length-shorter.length)/2;
    			if (totalLength%2 == 0) {
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
    			if (totalLength%2 == 0) {
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

     	do {
     		int shortStartIndex = 0;
     		int shortEndIndex = shorter.length-1;
   			int medianLongerIndex = (longerLeft + longerRight)/2;
	    	int medianValue = longer[medianLongerIndex];
	    	int medianShorterIndex = findMedianIndex(shorter, shortStartIndex, shortEndIndex, medianValue);
	    	if (medianShorterIndex >= shortStartIndex) {
	    		shortStartIndex = medianShorterIndex;
	    	}
	    	else {
	    		shortEndIndex = medianShorterIndex;
	    	}
	    	int leftElements = medianShorterIndex + medianLongerIndex;
	    	int diff = leftElements - idealMedianIndex;
	    	if (diff > 1) {
	    		longerRight = medianLongerIndex;
	    	}
	    	else if (diff < 1) {
	    		longerLeft = medianLongerIndex;
	    	}
	    	// now we are narrowed to picking the median
	    	if (medianLongerIndex == 0) {
	    		if (totalLength%2 == 1)
	    			return longer[0];
	    		else
	    			return (longer[0] + shorter[shorter.length-1])/2.0;
	    	}
	    	else if (medianLongerIndex == longer.length - 1) {
	    		if (totalLength%2 == 1) {
	    			return longer[longer.length-1];
	    		}
	    		else
	    			return (longer[longer.length-1] + shorter[0])/2.0;
	    	}
	    	else if (medianLongerIndex == 0 || medianLongerIndex >= longer.length -1 || longer[medianLongerIndex-1] > shorter[shorter.length] || longer[medianLongerIndex+1] < shorter[0]) {
	    		if (longer.length % 2 == 1)
	    			return longer[medianLongerIndex];
	    		else
	    			return (longer[medianLongerIndex] + longer[medianLongerIndex+1])/2.0;
	    	}
		} while (longerRight - longerLeft > 1);

	    	






/*
    	// special case where short array has only 1 element
    	if (shorter.length == 1) {
        	int midPt = longer.length/2;
        	if (longer.length % 2 == 1) {
        		if (shorter[0] >= longer[midPt-1] && shorter[0] <= longer[midPt+1])
        			return (shorter[0] + longer[midPt])/2.0;
        		else if (shorter[0] < longer[midPt-1])
        			return (longer[midPt-1] + longer[midPt])/2.0;
        		else
        			return (longer[midPt+1] + longer[midPt])/2.0;
        	}
        	else {
        		if (shorter[0] >= longer[midPt-1] && shorter[0] <= longer[midPt])
        			return shorter[0];
        		else if (shorter[0] < longer[midPt-1])
        			return longer[midPt-1];
        		else
        			return longer[midPt];
        	}
    	}
    	
    	
    	int longerLeft=0, shorterLeft=0, longerRight=longer.length-1, shorterRight=shorter.length-1;

		int longerMidX2 = longerRight+longerLeft;
		int shorterMidX2 = shorterRight+shorterLeft;
		
    	while (shorterRight-shorterLeft >= 2) {
    		longerMidX2 = longerRight+longerLeft;
    		shorterMidX2 = shorterRight+shorterLeft;
    		int longerMidValue = longer[longerMidX2/2];
    		int shorterMidValue = shorter[shorterMidX2/2];
    		
    		if (longerMidValue > shorterMidValue) {
    			int tmp = shorterLeft;
				shorterLeft = shorterMidX2/2;
				longerRight = longerRight - shorterLeft + tmp;
    		}
    		else {
    			int tmp = shorterRight;
				shorterRight = shorterMidX2/2;
				longerLeft = longerLeft - shorterRight + tmp;
    		}
    	}
    	// we are now left with shorter array is bound by 2 values
    	// 5 possible cases:
    	int centerLeft = (longerLeft + longerRight)/2;
    	int centerRight = (longerLeft + longerRight) % 2 == 0 ? centerLeft : centerLeft+1;
    	
    	// [1 2], [4 5]
    	if (shorter[shorterRight] <= longer[centerLeft]) {
    		if (centerLeft == 0) {
    			return (shorter[shorterRight] + longer[0])/2.0;
    		}
    		else {
    			return (longer[centerLeft-1] + longer[centerRight-1])/2.0;
    		}
    	}
    	// [4 5], [1 2]
    	else if (shorter[shorterLeft] >= longer[centerRight]) {
    		if (centerRight == longer.length-1) {
    			return (shorter[shorterLeft] + longer[centerRight])/2.0;
    		}
    		else {
    			return (longer[centerLeft+1] + longer[centerRight+1])/2.0;
    		}
    	}
    	// [4 5], [2 7]
    	else if (shorter[shorterLeft] >= longer[centerLeft] && shorter[shorterRight] <= longer[centerRight]) {
    		return (shorter[shorterLeft] + shorter[shorterRight])/2.0;
    	}
    	// [1 3], [2 4]
    	else if (shorter[shorterLeft] <= longer[centerLeft] && shorter[shorterRight] <= longer[centerRight]) {
    		return (shorter[shorterRight] + longer[centerLeft])/2.0;
    	}
    	// [2 4], [1 3]
    	else {
    		return (longer[centerRight] + shorter[shorterLeft])/2.0;
    	}

*/

    	return -1;
    }

    private int findMedianIndex(int[] array, int left, int right, int medianValue) {
    	int median = (left+right)/2;
    	while (right-left > 1) {
    		if (array[median] > medianValue)
    			left = median;
    		else if (array[median] < medianValue) 
    			right = median;
    	}
    	return left;
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
		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,4}, new int[]{3,5,6}) == 3.5);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,4}, new int[]{3,5,6,7,8}) == 4);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{1,4,5,6,7,8}) == 4);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{1,4,5,6,7,8,9}) == 4.5);
		System.out.println(sol.findMedianSortedArrays(new int[]{8,9,10}, new int[]{1,4,5,6,7,8}) == 7);
		System.out.println(sol.findMedianSortedArrays(new int[]{8,9,10}, new int[]{1,4,5,6,7,8,9}) == 7.5);



		System.out.println(sol.findMedianSortedArrays(new int[]{1,4,5,6}, new int[]{2,3,7,8}) == 4.5);		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,1,3,3}, new int[]{1,1,3,3}) == 2);
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



	}

}
