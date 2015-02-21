package searchInsertPosition;

/*
 * 
Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You may assume no duplicates in the array.

Here are few examples.
[1,3,5,6], 5 → 2
[1,3,5,6], 2 → 1
[1,3,5,6], 7 → 4
[1,3,5,6], 0 → 0

Observation:

find middle index for log(N) search

 */
public class Solution {

    public int searchInsert(int[] A, int target) {
    	if (A.length == 0)
    		return 0;

    	if (target <= A[0])
    		return 0;
        int left = 0;
        int right = A.length-1;
    	if (target > A[right])
    		return right+1;
       

        for(;;) {
        	int middle = (left+right)/2;
        	int middleVal = A[middle];
        	int leftVal = A[left];
        	if (right - left <= 1) {
        		if (target > leftVal)
        			return right;
        		else
        			return left;
        	}
    		if (target >= middleVal)
    			left=middle;
    		else
    			right=middle;
        }
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.searchInsert(new int[]{1,3,5}, 4) == 2);
		System.out.println(sol.searchInsert(new int[]{1,3,5}, 3) == 1);
		System.out.println(sol.searchInsert(new int[]{}, 7) == 0);
		System.out.println(sol.searchInsert(new int[]{2,3,5}, 1) == 0);
		System.out.println(sol.searchInsert(new int[]{2,3,5}, 6) == 3);
	}

}
