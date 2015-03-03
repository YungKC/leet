package medianTwoSortedArrays;



/*
 * 
Median of Two Sorted Arrays
There are two sorted arrays A and B of size m and n respectively. 
Find the median of the two sorted arrays. 
The overall run time complexity should be O(log (m+n)).

idea:

find median of each long array, identify how many elements from each array that falls on left and right side
now check if we we shift by 1 to see if the balance is reversed. 
If so, then we can now narrow down
otherwise, modify the search range in the longer array and repeat


Issues: lots of edge cases, so be sure to add many test cases.

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

	    return inner(shorter, longer);
    }
    
    // util function
    // given a value, and an array with a midpt, find the median
    private double findMedian(int value, int[] longer, int midPt) {
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

    private double inner(int[] shorter, int[] longer) {
     	int longerLeft=0, shorterLeft=0, longerRight=longer.length-1, shorterRight=shorter.length-1;
   		int totalLength = longer.length + shorter.length;
   		boolean isTotalLengthOdd = totalLength%2 == 1;
   		
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

//    	find median of each long array, identify how many elements from each array that falls on left and right side
//    	now check if we find the next number in sequence to see if the balance is switched.
//    	If so, then we have identified the median candidate!
//    	otherwise, modify the search range in the longer array and repeat
    	
    	int medianLongerIndex = 0;
    	int medianValue = 0;
    	int numElementsMoreOnRight = 0;

     	for (;;) {
   			medianLongerIndex = (longerLeft + longerRight)/2; // for even array, median is leaning on the left
	    	medianValue = longer[medianLongerIndex];
	    	// find the best candidate so that the left size from shorter array compensates for the input length, true if want value <= 0
	    	// matchResult[0] == num elements on the left of the returned value
	    	// matchResult[1] == index value of the returned value
	    	// matchResult[2] == value at that position
	    	numElementsMoreOnRight = longer.length-1-2*medianLongerIndex;
	    	int[] matchResult = findBestElementWithRightSize(shorter, medianValue, numElementsMoreOnRight, true);
	    	int numMoreOnRight = numElementsMoreOnRight - matchResult[0];
	    	if (numMoreOnRight == 0) {
	    		// done!
	    		return medianValue;
	    	}
	    	else if (numMoreOnRight > 0 && numMoreOnRight < 2*(shorter.length - matchResult[0])) {
	    		int nextMedianLongerIndex = medianLongerIndex+1;
	    		int nextNumRightElements = longer.length-1-2*nextMedianLongerIndex;
		    	int[] nextMatchResult = findBestElementWithRightSize(shorter, longer[nextMedianLongerIndex], nextNumRightElements, false);
		    	int nextNumMoreOnRight = nextNumRightElements - nextMatchResult[0];
		    	if (nextNumMoreOnRight == 0) {
		    		return longer[nextMedianLongerIndex]; 
		    	}
		    	else if (nextNumMoreOnRight < 0) {
		    		if (isTotalLengthOdd) {
		    			// either shorter[matchResult[2]] or shorter[nextMatchResult[2]];
		    			nextNumRightElements = shorter.length-1-2*nextMatchResult[1];
		    			nextMatchResult = findBestElementWithRightSize(longer, shorter[nextMatchResult[1]], nextNumRightElements, true);
		    			if (nextMatchResult[0] == 0)
		    				return nextMatchResult[2];
		    			else
		    				return matchResult[2];
		    		}
		    		else if (medianLongerIndex < longer.length-1){
		    			// average of the inner 2 values of 
		    			// 	shorter[matchResult[2]], shorter[nextMatchResult[2]]
		    			//	longer[medianLongerIndex], longer[medianLongerIndex+1];
		    			int value1 = Math.max(matchResult[2], longer[medianLongerIndex]);
		    			int value2 = Math.min(nextMatchResult[2], longer[medianLongerIndex+1]);
		    			return (value1+value2)/2.0;
		    		}
		    	}
	    	}
	    	if (numMoreOnRight > 0) {
	    		longerLeft = medianLongerIndex;
	    	}
	    	else {
	    		longerRight = medianLongerIndex;
	    	}
		}

    }

    
	// find the best candidate so that the left size from shorter array compensates for the input length, true if want value <= 0
	// matchResult[0] == num elements on the left of the returned value
	// matchResult[1] == index value of the returned value
	// matchResult[2] == value at that position
    private int[] findBestElementWithRightSize(int[] array, int medianValue, int idealNumElementsMoreOnLeft, boolean isLeanOnLeft) {
    	
    	int right = array.length-1;
    	if (medianValue > array[right]) {
    		return new int[]{array.length, array.length-1, array[array.length-1]};
    	}
    	else if (medianValue < array[0]) {
    		return new int[]{0, 0, array[0]};
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
    		return new int[]{2*left + 1 - array.length, left, medianValue};
    	}
    	
    	if (isLeanOnLeft) {
    		return new int[]{2*right - array.length, left, array[left]};
    	}
    	else {
    		return new int[]{2*right - array.length, right, array[right]};
    	}
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

//		System.out.println(sol.findBestElementWithRightSize(new int[]{1,2,3,5,6}, 4, 1, true)[0] == 1);
//		System.out.println(sol.findBestElementWithRightSize(new int[]{1,2,3,5,6}, 4, 0, false)[0] == 1);
//		System.out.println(sol.findBestElementWithRightSize(new int[]{1,2,3,3,4}, 3, 0, true)[0] == 0);
//		System.out.println(sol.findBestElementWithRightSize(new int[]{1,2,3,3,4}, 3, 2, true)[0] == 2);

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
