package findPeakElement;

/*
 * 
A peak element is an element that is greater than its neighbors.

Given an input array where num[i] ≠ num[i+1], find a peak element and return its index.

The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.

You may imagine that num[-1] = num[n] = -∞.

For example, in array [1, 2, 3, 1], 3 is a peak element and your function should return the index number 2.
 
 *
 */
public class Solution {

    public int findPeakElement(int[] num) {
    	if (num.length <= 1) 
    		return 0;
    	
        // idea: divide and conquer: look at region of array where delta between first and last is minimal
    	int l = 0;
    	int r = num.length-1;
    	int center = 0;
    	for(;;) {
    		center = (l+r)/2;
    		if (num[center] > num[center+1])
    			r = center;
    		else
    			l = center;
    		
    		if (r-l <= 1)
    			return num[r]>num[l]?r:l;
    	}
    }
    
	public static void main(String[] args) {
		System.out.println(new Solution().findPeakElement(new int[]{0,1,2,3,4,5,6,7,8,9,1}));

	}

}
