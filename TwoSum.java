package leet;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TwoSum {

    public static int[] twoSum1(int[] numbers, int target) {
    	Map<Integer, Integer> index = new TreeMap<Integer, Integer>();
    	for (int i=0; i<numbers.length; i++) {
    		if (index.get(numbers[i]) == null)
    			index.put(numbers[i], i);
    	}
    		
//    	List<Integer> list = asList(numbers);
    	List<Integer> list = new ArrayList<Integer>(index.keySet());
//    	Collections.sort(list);
/*
     	for (int i=0; i<list.size(); i++) {
    		System.out.println(list.get(i));
    	}
*/
        int[] result = new int[2];
        int length = numbers.length;
        for (int i=0; i<length-1; i++) {
    		int left = i+1;
    		int right = length;
        	for (;;) {
        		int current = left + (right - left)/2;
        		int diff = target - list.get(i) - list.get(current);
        		if (diff == 0) {
        			int firstIndex = index.get(list.get(i)) + 1;
        			int secondIndex = index.get(list.get(current)) + 1;
        			if( firstIndex > secondIndex) {
        				result[0] = secondIndex;
        				result[1] = firstIndex;
        			}
        			else {
        				result[0] = firstIndex;
        				result[1] = secondIndex;
        			}
        			return result;
        		}
        		else if (diff < 0) {
        			right = current;
        		}
        		else {
        			left = current+1;
        		}
    			if (left >= right)
    				break;
    			else
    				continue;        			
        	}
        }
        return result;
    }
    
	public static int[] twoSum2(int[] numbers, int target) {
    	Map<Integer, Integer[]> index = new HashMap<Integer, Integer[]>();
    	for (int i=0; i<numbers.length; i++) {
    		Integer[] holder = index.get(numbers[i]);
    		if (holder == null) {
    			holder = new Integer[2];
    			holder[0] = i;
    			index.put(numbers[i], holder);
    		}
    		else {
    			if (holder[1] == null) {
    				holder[1] = i;
    			}
    		}
    	}
    		
    	List<Integer> list = asList(numbers);
    	Collections.sort(list);
/*
     	for (int i=0; i<list.size(); i++) {
    		System.out.println(list.get(i));
    	}
*/
        int[] result = new int[2];
        int length = numbers.length;
        for (int i=0; i<length-1; i++) {
    		int left = i+1;
    		int right = length;
        	for (;;) {
        		int current = left + (right - left)/2;
        		int diff = target - list.get(i) - list.get(current);
        		if (diff == 0) {
        			if (list.get(i) == list.get(current)) {
        				Integer[] holder = index.get(list.get(i));
        				result[0] = holder[0] + 1;
        				result[1] = holder[1] + 1;
        				return result;
        			}
        			
        			int firstIndex = index.get(list.get(i))[0] + 1;
        			int secondIndex = index.get(list.get(current))[0] + 1;
        			if( firstIndex > secondIndex) {
        				result[0] = secondIndex;
        				result[1] = firstIndex;
        			}
        			else {
        				result[0] = firstIndex;
        				result[1] = secondIndex;
        			}
        			return result;
        		}
        		else if (diff < 0) {
        			right = current;
        		}
        		else {
        			left = current+1;
        		}
    			if (left >= right)
    				break;
    			else
    				continue;        			
        	}
        }
        return result;
    }
    
    public static List<Integer> asList(final int[] is)
    {
            return new AbstractList<Integer>() {
                    public Integer get(int i) { return is[i]; }
                    public int size() { return is.length; }
                    public Integer set(int i, Integer value) { is[i] = value; return value;}
           };
    }
    
	public static void main(String[] args) {
		int[] input = {0,4,3,0};
		int[] result = twoSum2(input, 0);
		System.out.println(result[0] + ":" + result[1]);
		
		
		int[] input2 = {0,4,3,8};
		result = twoSum2(input2, 7);
		System.out.println(result[0] + ":" + result[1]);

	}

}
