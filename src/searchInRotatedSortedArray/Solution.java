package searchInRotatedSortedArray;

/*
 * 
Suppose a sorted array is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.

Observation:

This is very similar to findPeakElement puzzle: https://oj.leetcode.com/problems/find-peak-element/
find middle index for log(N) search

 */
public class Solution {

    public int search(int[] A, int target) {
    	if (A.length == 0)
    		return -1;
    	
        int left = 0;
        int right = A.length-1;
        
        for(;;) {
        	int middle = (left+right)/2;
        	int middleVal = A[middle];
        	int leftVal = A[left];
        	int rightVal = A[right];
        	if (right - left <= 1) {
        		if (target == leftVal)
        			return left;
        		else if (target == rightVal)
        			return right;
        		else
        			return -1;
        	}
        	if (rightVal > leftVal) {
        		if (target > middleVal)
        			left=middle;
        		else
        			right=middle;
        	}
        	else if (middleVal > leftVal){
        		if (leftVal<= target && target <= middleVal)
        			right=middle;
        		else
        			left=middle;        		
        	}
        	else {
        		if (middleVal <= target && target <= rightVal)
        			left=middle;
        		else
        			right=middle;        		
        	}
        }
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.search(new int[]{5,1,3}, 3) == 2);
		System.out.println(sol.search(new int[]{3,5,1}, 3) == 0);
		System.out.println(sol.search(new int[]{2,3,4,5,6,7,1}, 1) == 6);
		System.out.println(sol.search(new int[]{7}, 7) == 0);
		System.out.println(sol.search(new int[]{1}, 7) == -1);
		System.out.println(sol.search(new int[]{4,5,6,7,0,1,2}, 7) == 3);
		System.out.println(sol.search(new int[]{}, 7) == -1);

	}

}
