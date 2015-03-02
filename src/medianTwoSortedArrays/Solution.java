package medianTwoSortedArrays;

import java.util.ArrayList;
import java.util.List;


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
   		int idealNumLeftElements = totalLength/2;
   		if (!isTotalLengthOdd)
   			idealNumLeftElements--;
   		
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
    	int smallerShortIndex = 0;
    	int biggerShortIndex = 0;
    	int numLeftElements = 0;
    	boolean foundWindow = false;
     	do {
   			medianLongerIndex = (longerLeft + longerRight)/2;
	    	medianValue = longer[medianLongerIndex];
	    	smallerShortIndex = findSmallestLeftElementSize(shorter, medianValue);
	    	numLeftElements = smallerShortIndex + medianLongerIndex;

	    	if (numLeftElements > idealNumLeftElements) {
	    		longerRight = medianLongerIndex;
	    	}
	    	else if (numLeftElements + 2 >= idealNumLeftElements) {
		    	// see if we move the longer index to 1 step right, that the sign changes. then that means we found the window
	    		int nextMedianValue = longer[medianLongerIndex+1];
	    		biggerShortIndex = findBiggestRightElementSize(shorter, nextMedianValue);
	    		int biggerNumLeftElements = biggerShortIndex + medianLongerIndex + 1;
	    		if (biggerNumLeftElements >= idealNumLeftElements) {
	    			foundWindow = true;
	    		}
	    		else {
	    			longerLeft = medianLongerIndex;
	    		}
	    	}
	    	else {
	    		longerLeft = medianLongerIndex;
	    	}
		} while (!foundWindow);
//    	System.out.println("foundWindow at " + medianLongerIndex + " with value " + medianValue);

//    	medianLongerIndex
//    	medianValue
//    	smallerShortIndex
//     	numLeftElements
// 		isTotalLengthOdd
   		
// mean is calculated from combination of 
//     	longer[medianLongerIndex], longer[medianLongerIndex+1], 
//     	and any values between shorter[smallerShortIndex] and shorter[biggerShortIndex]
     	
     	if (isTotalLengthOdd) {
     		if (numLeftElements == idealNumLeftElements)
     			return longer[medianLongerIndex];
     		else
     			return shorter[smallerShortIndex];
     	}
     	else {
     		List<Integer> zeroSolution = new ArrayList<Integer>();
     		List<Integer> negOneSolution = new ArrayList<Integer>();
     		List<Integer> oneSolution = new ArrayList<Integer>();
     		
     		// now need to cycle through possible shortArray values to see which is a better median
// TODO: create method to return numbers of left and right elements from an array for a given value     		
     		int result = idealNumLeftElements - (medianLongerIndex + biggerShortIndex);
     		System.out.println(longer[medianLongerIndex] + " : " + result);
     		if (result == 0) {
     			zeroSolution.add(longer[medianLongerIndex]);
     		}
     		else if (result == -1) {
     			negOneSolution.add(longer[medianLongerIndex]);
     		}
     		else if (result == 1) {
     			oneSolution.add(longer[medianLongerIndex]);
     		}
     		
     		int tmp = findSmallestLeftElementSize(shorter, longer[medianLongerIndex+1]);
     		result = idealNumLeftElements - (medianLongerIndex + 1 + tmp);
     		System.out.println(longer[medianLongerIndex+1] + " : " + result);
     		if (result == 0) {
     			zeroSolution.add(longer[medianLongerIndex+1]);
     		}
     		else if (result == -1) {
     			negOneSolution.add(longer[medianLongerIndex+1]);
     		}
     		else if (result == 1) {
     			oneSolution.add(longer[medianLongerIndex+1]);
     		}
     		
     		for (int i=smallerShortIndex; i<=biggerShortIndex && i<shorter.length; i++) {
     			tmp = findSmallestLeftElementSize(longer, shorter[i]);
     			result = idealNumLeftElements - (tmp + i);
     			System.out.println(shorter[i] + " : " + result);
         		if (result == 0) {
         			zeroSolution.add(shorter[i]);
         		}
         		else if (result == -1) {
         			negOneSolution.add(shorter[i]);
         		}
         		else if (result == 1) {
         			oneSolution.add(shorter[i]);
         		}
     		}
         	
     		int count=0;
     		int total = 0;
     		for (int value : zeroSolution) {
     			if (count < 2) {
	     			total += value;
	     			count++;
     			}
     		}
     		for (int value : negOneSolution) {
     			if (count < 2) {
	     			total += value;
	     			count++;
     			}
     		}
     		for (int value : oneSolution) {
     			if (count < 2) {
	     			total += value;
	     			count++;
     			}
     		}
     		return total/2.0;

     	}
    }

    
    // return the smallest numbers of elements possible left of medianValue
    private int findSmallestLeftElementSize(int[] array, int medianValue) {
    	int right = array.length-1;
    	if (medianValue > array[right])
    		return array.length;
    	int left = 0;
    	if (medianValue >= array[right]) {
    		int result = right;
    		while (medianValue == array[result-1]) {
    			result--;
    		}
    		return result;
    	}
    	else if (medianValue <= array[0]) {
    		return 0;
    	}
    	while (right - left > 1) {
        	int median = (left+right)/2;
    		if (array[median] > medianValue)
    			right = median;
    		else if (array[median] <= medianValue) 
    			left = median;
    	}
		while (left > 0 && medianValue == array[left-1]) {
			left--;
		}
		while (left < array.length-1 && array[left] < medianValue) {
			left++;
		}
		return left;
    }
    	
    // return the largest numbers of elements possible left of medianValue
    private int findBiggestRightElementSize(int[] array, int medianValue) {
    	int right = array.length-1;
    	int left = 0;
    	if (medianValue >= array[right]) {
    		return array.length;
    	}
    	else if (medianValue <= array[0]) {
    		int result = 0;
    		while (medianValue == array[result+1]) {
    			result++;
    		}
    		return result;
    	}
    	while (right - left > 1) {
        	int median = (left+right)/2;
    		if (array[median] >= medianValue)
    			right = median;
    		else if (array[median] < medianValue) 
    			left = median;
    	}
		while (right < array.length-1 && medianValue == array[right+1]) {
			right++;
		}
		if (medianValue == array[right])
			return 	right+1;
		else
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

		
//		System.out.println(sol.findSmallestLeftElementSize(new int[]{1,4,5,6}, 4) == 1);
//		System.out.println(sol.findSmallestLeftElementSize(new int[]{1,4,5,6}, 3) == 1);
//		System.out.println(sol.findBiggestRightElementSize(new int[]{1,4,5,6}, 4) == 2);
//		System.out.println(sol.findBiggestRightElementSize(new int[]{1,4,5,6}, 3) == 1);

		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,4}, new int[]{3,5,6,7,8}) == 4.5);
		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{4,5,6,7,8}) == 4.5);
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,3}, new int[]{4,5,6}) == 3.5);
		
		System.out.println(sol.findMedianSortedArrays(new int[]{1,2,4}, new int[]{3,5,6}) == 3.5);
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
