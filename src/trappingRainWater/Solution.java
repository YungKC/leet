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

The the first left peak
for each subsequent peak, 
	if current peak is higher than last peak, 
		then add up the collected rain from this peak leftward, and reset tallest peaks

At the end, the water level is same as the right peak, so add up rain water from last tallest peak to this right peak.

 */
public class Solution {
    public int trap(int[] A) {
    	
    	if (A == null || A.length <= 2)
    		return 0;
    	
    	
        int headPeakXPos = 1;
        while (headPeakXPos < A.length && A[headPeakXPos] >= A[headPeakXPos-1])
        	headPeakXPos++;
        int tailPeakXPos = A.length-1;
        while (tailPeakXPos > 0 && A[tailPeakXPos] <= A[tailPeakXPos-1])
        	tailPeakXPos--;
        
        if (headPeakXPos >= tailPeakXPos)
        	return 0;
        
        headPeakXPos--;
        
        int totalRain = 0;
        
    	int tallestPeakXPos = headPeakXPos;
    	int tallestPeakHeight = A[headPeakXPos];
    	int curPeakXPos = headPeakXPos+1;
    	do {
    		// find next peak at curPeakXPos
    		while (curPeakXPos < tailPeakXPos && A[curPeakXPos] <= A[curPeakXPos-1]) {
    			curPeakXPos++;
    		}
    		while (curPeakXPos <= tailPeakXPos && A[curPeakXPos] > A[curPeakXPos-1]) {
    			curPeakXPos++;
    		}
    		curPeakXPos--;
    		// if current peak is >= first peak, then we can sum up the rain and drop all previous peaks
    		if (A[curPeakXPos] >= tallestPeakHeight) {
    			for (int x=tallestPeakXPos+1; x<curPeakXPos; x++) {
    				if (tallestPeakHeight > A[x])
    					totalRain += tallestPeakHeight - A[x];
    			}
    			tallestPeakXPos = curPeakXPos;
    			tallestPeakHeight = A[curPeakXPos];
    		}
			curPeakXPos++;
    	} while (curPeakXPos < tailPeakXPos);
    		
    	// update rain volume from peaks of decreasing heights
    	int waterHeight = A[tailPeakXPos];
    	for (int x=tailPeakXPos-1; x>tallestPeakXPos; x--) {
			if (waterHeight > A[x])
				totalRain += waterHeight - A[x];
			else
				waterHeight = A[x];
		}

    	return totalRain;
    }
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.trap(new int[]{9,6,8,8,5,6,3}) == 3);
		System.out.println(sol.trap(new int[]{4,3,3,9,3,0,9,2,8,3}) == 23);
		System.out.println(sol.trap(new int[]{5,4,1,2}) == 1);
		System.out.println(sol.trap(new int[]{6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3}) == 83);
		System.out.println(sol.trap(new int[]{5,5,1,7,1,1,5,2,7,6}) == 23);
		System.out.println(sol.trap(new int[]{4,2,0,3,2,5}) == 9);
		System.out.println(sol.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}) == 6);
		System.out.println(sol.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}) == 6);
		System.out.println(sol.trap(new int[]{1,0,1}) == 1);
		System.out.println(sol.trap(new int[]{1,7,8}) == 0);

	}

}
