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

now, traverse from leftmost peak, toward right

starts from peak at x1
at peak x2: 

if h(x2) <= h(x1), then add up rain from x1 to x2
else 
	identify the next peak toward left until at x0 so that h(x0) > h(x2)
	increase total rain by (x2-x0+1)*h(x2)-h(x1)
		(special case: if x0 is beyond the xLeft limit, then no need to increment this)
 */
public class Solution {
    public int trap(int[] A) {
    	
    	
    	if (A == null || A.length <= 2)
    		return 0;
    	
    	
        int curLeft = 1;
        while (curLeft < A.length && A[curLeft] >= A[curLeft-1])
        	curLeft++;
        int curRight = A.length-1;
        while (curRight > 0 && A[curRight] <= A[curRight-1])
        	curRight--;
        
        if (curLeft >= curRight)
        	return 0;
        
        curLeft--;
        
        int totalRain = 0;
        
        // populate the stack with location of each peaks
    	List<Integer> heightStack = new ArrayList<Integer>();    
    	
    	heightStack.add(curLeft);
    	   		 
    	do {
    		while (++curLeft < curRight && A[curLeft] < A[curLeft-1]);
    		while (++curLeft < curRight && A[curLeft] > A[curLeft-1]);
    		if (curLeft < curRight) {
    	    	heightStack.add(curLeft-1);
    		}
    	} while (curLeft < curRight);
    	
    	heightStack.add(curRight);
        
//    	starts from peak at x1
//    	at peak x2: 
//
//    	add up rain from x1 to x2
//    	if h(x2) > h(x1) 
//    		identify the next peak toward left until at x0 so that h(x0) > h(x2)
//    		increase total rain by (x2-x0+1)*h(x2)-h(x1)
//    			(special case: if x0 is beyond the xLeft limit, then no need to increment this)
//				(special case: if peak spans more than 1 width)    			
    	int lastPeakXPos = heightStack.get(0);
    	int edgeHeight = A[lastPeakXPos];
    	int lastHigherPeakListIndex = 0;
    	int curListIndex = 1;
    	while (curListIndex < heightStack.size()) {
    		int curXPos = heightStack.get(curListIndex);
    		int curHeight = A[curXPos];

    		if (curHeight < edgeHeight) {
    			for (int x = lastPeakXPos+1; x<curXPos; x++) {
    				if (curHeight - A[x] > 0)
    					totalRain += curHeight - A[x];
    			}
    		}
    		else {
    			for (int x = lastPeakXPos+1; x<curXPos; x++) {
    				if (edgeHeight - A[x] > 0)
    					totalRain += edgeHeight - A[x];
    			}
    			// find peak toward left that's >= curHeight
    			do {
    				--lastHigherPeakListIndex;
    			} while (lastHigherPeakListIndex >= 0 && A[heightStack.get(lastHigherPeakListIndex)] < curHeight);
    			if (lastHigherPeakListIndex >= 0) {
    				totalRain += (curXPos - heightStack.get(lastHigherPeakListIndex) - 1) * (curHeight - edgeHeight);
    			}
    				
    			lastHigherPeakListIndex = curListIndex;
    			lastPeakXPos = heightStack.get(lastHigherPeakListIndex);
        		edgeHeight = curHeight;
    		}
    		curListIndex++;
    	}

    	return totalRain;
    }
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.trap(new int[]{1,7,8}) == 0);
		System.out.println(sol.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}) == 6);
		System.out.println(sol.trap(new int[]{1,0,1}) == 1);

	}

}
