package findMinimumRotatedSortedArray;

/*
 * 
 Suppose a sorted array is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

Find the minimum element.

You may assume no duplicate exists in the array.

Observation:
bisect list to ensure log(N)

for case where the middle is higher or lower than edges, move the end post. Draw it out to determine logic.

 */
public class Solution {

    public int findMin(int[] num) {
    	if (num == null || num.length == 0)
    		return 0;
    	
        int left = 0;
        int right = num.length - 1;
        
        while (true) {
	        int mid = (left+right)/2;
	        int leftValue = num[left];
	        int rightValue = num[right];
	        int midValue = num[mid];
	        
	        if (midValue > leftValue && midValue > rightValue) {
	        	left = mid;
	        }
	        else if (midValue < leftValue && midValue < leftValue) {
	        	right = mid;
	        }
	        else if (leftValue < midValue) {
	        	right = mid;
	        }
	        else if (rightValue < midValue) {
	        	left = mid;
	        }
	        if (right-left == 1)
	        	return Math.min(num[left], num[right]);
	        else if (right == left)
	        	return num[left];
        }
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		int[] num;

		num = new int[] {5,1,2,3,4};
		System.out.println(sol.findMin(num) == 1);

		num = new int[] {4,5,6,7,0,1,2};
		System.out.println(sol.findMin(num) == 0);
		
		num = new int[] {};
		System.out.println(sol.findMin(num) == 0);
		
	}

}
