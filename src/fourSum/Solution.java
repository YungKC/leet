package fourSum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 
 Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? 
 Find all unique quadruplets in the array which gives the sum of target.

Note:
Elements in a quadruplet (a,b,c,d) must be in non-descending order. (ie, a ≤ b ≤ c ≤ d)
The solution set must not contain duplicate quadruplets.
    For example, given array S = {1 0 -1 0 -2 2}, and target = 0.

    A solution set is:
    (-1,  0, 0, 1)
    (-2, -1, 1, 2)
    (-2,  0, 0, 2)
    
More or less brute force method, using biset solution to search for last element

Another approach is to use 2 2sum solution, treat it as a 2sum problem.

 */
public class Solution {
    public List<List<Integer>> fourSum(int[] num, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int listLength = num.length;
        if (listLength < 4)
        	return result;
        
        Set<String> prefix = new HashSet<String>();
        
        List<Integer> inputList = new ArrayList<Integer>(listLength);
        for (int entry : num)
        	inputList.add(entry);
        Collections.sort(inputList);
        int maxValue = inputList.get(listLength-1);
        
        // smallest 4:
        int initSum = 0;
        for (int i=0; i<4; i++){
        	initSum += inputList.get(i);
        }
        if (initSum > target)
        	return result;
        
        initSum = 0;
        for (int i=listLength-4; i<listLength; i++)
        	initSum += inputList.get(i);
        if (initSum < target)
        	return result;
        
        for (int i=0; i<listLength-3; i++) {
        	int targetValue0 = target - inputList.get(i);        	
            for (int j=i+1; j<listLength-2; j++) {
            	int targetValue1 = targetValue0 - inputList.get(j);
                for (int k=j+1; k<listLength-1; k++) {               	
                	int targetValue = targetValue1 - inputList.get(k);
                	if (targetValue < inputList.get(k+1))
                		break;
                	if (targetValue <= maxValue) {
	                	// use bisect to determine if there is a match
	                	int left = k+1;
	                	int right = listLength-1;
	                	boolean found = false;
	                	do {
		                	int center = (right + left)/2;
		                	int value =  inputList.get(center);
		                	if (value > targetValue)
		                		right = center-1;
		                	else if (value < targetValue)
		                		left = center+1;
		                	else {
		                		found = true;
		                		break;
		                	}
	                	} while (left <= right);
	                	
	                	if (found) {
	                    	String key = new StringBuffer().append(inputList.get(i)).append("x").append(inputList.get(j)).append("x").append(inputList.get(k)).toString();
	                    	if (!prefix.contains(key)){
	                    		prefix.add(key);
		                		ArrayList<Integer> innerList = new ArrayList<Integer>(4);
		                		innerList.add(inputList.get(i));
		                		innerList.add(inputList.get(j));
		                		innerList.add(inputList.get(k));
		                		innerList.add(targetValue);
		                		result.add(innerList);
	                    	}
	                	}
                	}
                }
            }
        }
        
        return result;
    }
    
    public static final void main(String[] argv) {
    	Solution sol = new Solution();
    	int[] input;

    	input = new int[]{-3,-2,-1,0,0,1,2,3};
    	System.out.println(sol.fourSum(input, 0));

    	input = new int[]{-1,0,1,2,-1,-4};
    	System.out.println(sol.fourSum(input, -1));
    	
    	input = new int[]{91277418,66271374,38763793,4092006,11415077,60468277,1122637,72398035,-62267800,22082642,60359529,-16540633,92671879,-64462734,-55855043,-40899846,88007957,-57387813,-49552230,-96789394,18318594,-3246760,-44346548,-21370279,42493875,25185969,83216261,-70078020,-53687927,-76072023,-65863359,-61708176,-29175835,85675811,-80575807,-92211746,44755622,-23368379,23619674,-749263,-40707953,-68966953,72694581,-52328726,-78618474,40958224,-2921736,-55902268,-74278762,63342010,29076029,58781716,56045007,-67966567,-79405127,-45778231,-47167435,1586413,-58822903,-51277270,87348634,-86955956,-47418266,74884315,-36952674,-29067969,-98812826,-44893101,-22516153,-34522513,34091871,-79583480,47562301,6154068,87601405,-48859327,-2183204,17736781,31189878,-23814871,-35880166,39204002,93248899,-42067196,-49473145,-75235452,-61923200,64824322,-88505198,20903451,-80926102,56089387,-58094433,37743524,-71480010,-14975982,19473982,47085913,-90793462,-33520678,70775566,-76347995,-16091435,94700640,17183454,85735982,90399615,-86251609,-68167910,-95327478,90586275,-99524469,16999817,27815883,-88279865,53092631,75125438,44270568,-23129316,-846252,-59608044,90938699,80923976,3534451,6218186,41256179,-9165388,-11897463,92423776,-38991231,-6082654,92275443,74040861,77457712,-80549965,-42515693,69918944,-95198414,15677446,-52451179,-50111167,-23732840,39520751,-90474508,-27860023,65164540,26582346,-20183515,99018741,-2826130,-28461563,-24759460,-83828963,-1739800,71207113,26434787,52931083,-33111208,38314304,-29429107,-5567826,-5149750,9582750,85289753,75490866,-93202942,-85974081,7365682,-42953023,21825824,68329208,-87994788,3460985,18744871,-49724457,-12982362,-47800372,39958829,-95981751,-71017359,-18397211,27941418,-34699076,74174334,96928957,44328607,49293516,-39034828,5945763,-47046163,10986423,63478877,30677010,-21202664,-86235407,3164123,8956697,-9003909,-18929014,-73824245};
    	System.out.println(sol.fourSum(input, -236727523));
    	
    	input = new int[]{-479,-472,-469,-461,-456,-420,-412,-403,-391,-377,-362,-361,-340,-336,-336,-323,-315,-301,-288,-272,-271,-246,-244,-227,-226,-225,-210,-194,-190,-187,-183,-176,-167,-143,-140,-123,-120,-74,-60,-40,-39,-37,-34,-33,-29,-26,-23,-18,-17,-11,-9,6,8,20,29,35,45,48,58,65,122,124,127,129,145,164,182,198,199,206,207,217,218,226,267,274,278,278,309,322,323,327,350,361,372,376,387,391,434,449,457,465,488};
    	System.out.println(sol.fourSum(input, 1979));
    	
    	input = new int[]{0, 0, 0, 0};
    	System.out.println(sol.fourSum(input, 0));
    	
    	input = new int[]{1, 0, -1, 0, -2, 2};
    	System.out.println(sol.fourSum(input, 0));
    	
    	
//    	[[-3, -2, 2, 3], [-3, -1, 1, 3], [-3, 0, 0, 3], [-3, 0, 1, 2], [-2, -1, 0, 3], [-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]
//    	[[-4, 0, 1, 2], [-1, -1, 0, 1]]
//    	[[-79583480, -70078020, -65863359, -21202664], [-76072023, -59608044, -58094433, -42953023]]
//    	[]
//    	[[0, 0, 0, 0]]
//    	[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]
    					
    }
}
