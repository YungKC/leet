package trappingRainWater;

import java.util.ArrayList;
import java.util.List;

/*
 * 
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.

For example, 
Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.


The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!

idea:

find first left index where height starts dropping
find last right index where height stops rising

use a stack to push last peak index. Height can be retrieved from A[index]
 */
public class Solution {
    public int trap(int[] A) {
    	
    	
    	if (A == null || A.length == 2)
    		return 0;
    	
    	
        int curLeft = 0;
        while (curLeft < A.length+1 && A[curLeft+1] >= A[curLeft])
        	curLeft++;
        int curRight = A.length-1;
        while (curRight > 0 && A[curRight] <= A[curRight-1])
        	curRight--;
        
        if (curLeft == curRight)
        	return 0;
        
        int totalRain = 0;
        
        // populate the stack with location of each peaks
    	List<Integer> heightStack = new ArrayList<Integer>();    
    	
    	heightStack.add(curLeft);
    	   		 
    	do {
    		while (++curLeft < curRight && A[curLeft] < A[curLeft-1]);
    		while (++curLeft < curRight && A[curLeft] > A[curLeft-1]);
    		if (curLeft < curRight) {
    	    	heightStack.add(curLeft);
    		}
    	} while (curLeft < curRight);
    	
    	heightStack.add(curRight);
        
    	// repeatedly get the next peak in list
    	int lastPeakIndex = heightStack.get(0);
    	int edgeHeight = A[lastPeakIndex];
    	int curListIndex = 1;
    	while (curListIndex < heightStack.size()) {
    		int nextIndex = heightStack.get(curListIndex);
    		int indexHeight = A[nextIndex];
    		if (indexHeight >= edgeHeight) {
    			// sum up all rain heights from curListIndex toward left until higher peak or reached left end
    			for (int i = lastPeakIndex+1; i<= curListIndex; i++) {
    				totalRain += edgeHeight - A[i];
    				A[i] = edgeHeight;
    			}
    			edgeHeight = indexHeight;
    		}
    		curListIndex++;
    	}

    	return totalRain;
    }
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.trap(new int[]{1,0,1}) == 1);
		System.out.println(sol.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}) == 6);

	}

}
