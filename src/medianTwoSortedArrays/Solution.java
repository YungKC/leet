package medianTwoSortedArrays;



/*
 * 
Median of Two Sorted Arrays
There are two sorted arrays A and B of size m and n respectively. 
Find the median of the two sorted arrays. 
The overall run time complexity should be O(log (m+n)).

idea:

find median of each long array, identify how many elements from each array that falls on left and right side
if balance is -1, 0 or 1 then we found a contribution to the median.

otherwise, modify the search range in the longer array and repeat

We will need to reverse the 2 arrays since the median can be coming from the other array.

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
    	
    	int longer[], shorter[];
    	if (A.length > B.length) {
    		longer = A;
    		shorter = B;
    	}
    	else {
    		longer = B;
    		shorter = A;
    	}
    	
    	if (shorter.length == 1) {
    		return findMedian(shorter[0], longer, longer.length/2);
    	}

    	Integer[] result = new Integer[2];
	    inner(shorter, longer, result);
	    
	    if (result[0] == null || result[1] ==  null) {
	    	inner(longer, shorter, result);
	    }
	    
	    return (result[0] + result[1])/2.0;
	    
    }
    
    // util function
    // given a value, and an array with a midpt, find the median
    private double findMedian(int value, int[] longer, int midPt) {
    	if (longer.length == 1) {
    		return (value + longer[0])/2.0;
    	}
    	if (longer.length % 2 == 1) {
    		int centerValue = longer[midPt];
    		if (value <= centerValue) {
    			int nextValue = Math.max(value, longer[midPt-1]);
    			return (nextValue+centerValue)/2.0;
    		}
    		else {
    			int nextValue = Math.min(value, longer[midPt+1]);
    			return (nextValue+centerValue)/2.0;
    		}
    	}
    	else {
    		int leftValue = longer[midPt-1];
    		int rightValue = longer[midPt];
    		if (value < leftValue)
    			return leftValue;
    		else if (value > rightValue)
    			return rightValue;
    		return value;
    	}
    }

    private void inner(int[] shorter, int[] longer, Integer[] result) {
     	int longerLeft=0, shorterLeft=0, longerRight=longer.length-1, shorterRight=shorter.length-1;
   		int totalLength = longer.length + shorter.length;
   		boolean isTotalLengthOdd = totalLength%2 == 1;
   		
     	// [1 2], [4 5]
    	if (shorter[shorterRight] <= longer[longerLeft]) {
    		if (longerRight == shorterRight) {
    			result[0] = shorter[shorterRight];
    			result[1] = longer[longerLeft];
    			return;
    		}
    		else {
    			int centerIndex = (longer.length-shorter.length)/2;
    			if (!isTotalLengthOdd) {
        			result[0] = longer[centerIndex-1];
        			result[1] = longer[centerIndex];
    				return;
    			}
    			else {
        			result[0] = longer[centerIndex];
        			result[1] = longer[centerIndex];
    			}
    		}
    	}
    	// [4 5], [1 2]
    	else if (shorter[shorterLeft] >= longer[longerRight]) {
    		if (longerRight == shorterRight) {
    			result[0] = shorter[shorterLeft];
    			result[1] = longer[longerRight];
    			return;
    		}
    		else {
    			int centerIndex = shorter.length + (longer.length-shorter.length)/2;
    			if (!isTotalLengthOdd) {
        			result[0] = longer[centerIndex-1];
        			result[1] = longer[centerIndex];
    				return;
    			}
    			else {
        			result[0] = longer[centerIndex];
        			result[1] = longer[centerIndex];
    				return;
    			}
    		}
    	}

//    	find median of each long array, identify how many elements from each array that falls on left and right side
//    	now check if we find the next number in sequence to see if the balance is switched.
//    	If so, then we have identified the median candidate!
//    	otherwise, modify the search range in the longer array and repeat
    	
    	int medianLongerIndex = 0;
    	int medianValue = 0;
    	int numElementsMoreOnRight = 0;
    	
    	boolean hasTriedRightMost = false;
     	for (;;) {
   			medianLongerIndex = (longerLeft + longerRight)/2; // for even array, median is leaning on the left
	    	medianValue = longer[medianLongerIndex];
	    	// find the best candidate so that the left size from shorter array compensates for the input length, true if want value <= 0
	    	// matchResult == number elements tilted toward the left of the median value

	    	numElementsMoreOnRight = longer.length-1-2*medianLongerIndex;
	    	int numMoreOnRight = findBestElementWithRightSize(shorter, medianValue, numElementsMoreOnRight);
	    	if (numMoreOnRight == 0) {
	    		// done!
    			result[0] = medianValue;
    			result[1] = medianValue;
	    		return ;
	    	}
	    	else if (numMoreOnRight == 1) {
	    		result[0] = medianValue;
	    		if (result[1] != null)
	    			return;
	    	}
	    	else if (numMoreOnRight == -1) {
	    		result[1] = medianValue;
	    		if (result[0] != null)
	    			return;
	    	}
	    	if (numMoreOnRight > 0) {
	    		if (longerLeft != medianLongerIndex)
	    			longerLeft = medianLongerIndex;
	    		else if (!hasTriedRightMost) {
	    			longerLeft = longerRight;
	    			hasTriedRightMost = true;
	    		}
	    		else
	    			break;
	    	}
	    	else {
	    		if (longerRight != medianLongerIndex)
	    			longerRight = medianLongerIndex;
	    		else
	    			break;
	    	}
		}     	
     	return;
    }

    
	// find the best candidate so that the left size from shorter array compensates for the input length, true if want value <= 0
    // in order to get as close as possible, take special care when an element matches the median Value. 
    // If it can be accomplished to +/- 1, then it's OK to return the next element.
	// matchResult == number elements tilted toward the left of the median value
    private int findBestElementWithRightSize(int[] array, int medianValue, int idealNumElementsMoreOnLeft) {
    	
    	int right = array.length-1;
    	if (medianValue > array[right]) {
    		return idealNumElementsMoreOnLeft - array.length;
    	}
    	else if (medianValue < array[0]) {
    		return idealNumElementsMoreOnLeft + array.length;
    	}
    	int left = 0;

    	while (right - left > 1) {
        	int median = (left+right)/2;
    		if (array[median] > medianValue)
    			right = median;
    		else if (array[median] <= medianValue) 
    			left = median;
    	}
    	
    	if (array[left] == medianValue) {
    		if (2*left + 1 - array.length < idealNumElementsMoreOnLeft) {
    			while (left < array.length - 1 && 2*left + 1 - array.length < idealNumElementsMoreOnLeft && array[left+1] == medianValue) {
    				left++;
    			}
    		}
    		else if (2*left + 1 - array.length> idealNumElementsMoreOnLeft) {
    			while (left > 0 && 2*left + 1 - array.length > idealNumElementsMoreOnLeft && array[left-1] == medianValue) {
    				left--;
    			}
    		}
    		int length = idealNumElementsMoreOnLeft - (2*left + 1 - array.length);
    		if (length == -2)
    			return -1;
    		else if (length == 2)
    			return 1;
    			
    		return length;
    	}    	
   		return idealNumElementsMoreOnLeft - (2*right - array.length);
    }
    	    
    private double median(int[] array) {
    	if (array.length == 1)
    		return array[0];
    	
    	int midPt = array.length/2;
    	if (array.length % 2 == 1)
    		return array[midPt];
    	else
    		return (array[midPt-1]+array[midPt])/2.0;
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();

		System.out.println(sol.findBestElementWithRightSize(new int[]{8,9,10}, 8, -4) == -1);

		System.out.println(sol.findMedianSortedArrays(new int[]{1}, new int[]{1}) == 1);
		
		System.out.println(sol.findMedianSortedArrays(new int[]{8,9,10}, new int[]{1,4,5,6,7,8,9}) == 7.5);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,4}, new int[]{3,5,6,7,8}) == 4.5);
		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,4}, new int[]{3,5,6}) == 3.5);
		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{4,5,6,7,8}) == 4.5);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{4,5,6}) == 3.5);
		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{1,4,5,6,7,8}) == 4);
		
		System.out.println(sol.findMedianSortedArrays(new int[]{2,10}, new int[]{1,2,3,4,5,6,7,8,9,10}) == 5.5d);
		System.out.println(sol.findMedianSortedArrays(new int[]{8,9,10}, new int[]{1,4,5,6,7,8,9}) == 7.5);


		System.out.println(sol.findMedianSortedArrays(new int[]{1,1,3,3}, new int[]{1,1,3,3}) == 2);

		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,4,5,6}, new int[]{2,3,7,8}) == 4.5);		


		
		System.out.println(sol.findMedianSortedArrays(new int[]{2}, new int[]{1,3}) == 2);
		

		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{1,2,2}) == 2);

		System.out.println(sol.findMedianSortedArrays(new int[]{1,2}, new int[]{1,2,3}) == 2);



		System.out.println(sol.findMedianSortedArrays(new int[]{1,4,5,6}, new int[]{2,3,7,8}) == 4.5);		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{1,4,5,6,7,8,9}) == 4.5);


		System.out.println(sol.findMedianSortedArrays(new int[]{8,9,10}, new int[]{1,4,5,6,7,8}) == 7);





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
