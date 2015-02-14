package minWindowSubstring;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
 * 
Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

Note:
If there is no such window in S that covers all characters in T, return the emtpy string "".

If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.

idea:

Create a map of where key is each unique char in T, where the value is numOccurance and a fixed size queue
aAs we walk through each char in S, if S in the key set, then insert char index into respective queue. 
Hold a result variable containing the min size and window location

 *
 */
public class Solution {

    public String minWindow(String S, String T) {
    	if (S == null || S.length() == 0 || T == null || T.length() == 0)
    		return "";
    	
        int windowSize = Integer.MAX_VALUE;
        int startIndex = -1;
        int endIndex = -1;
        
        Map<Character, ValueHolder>map = new HashMap<Character, ValueHolder>();
        
        int tSize = T.length();
        for (int i=0; i<tSize; i++) {
        	ValueHolder v = map.get(T.charAt(i));
        	if (v == null) {
        		v = new ValueHolder();
        		map.put(T.charAt(i), v);
        	}
        	v.count++;
        }
        
        for (char c : map.keySet()) {
        	ValueHolder v = map.get(c);
        	v.queue = new LimitedQueue<Integer>(v.count);
        }
        
        int sSize = S.length();
        for (int i=0; i<sSize; i++) {
        	char curChar = S.charAt(i);
        	ValueHolder v = map.get(curChar);
        	if (v != null) {
        		v.queue.add(i);
        		// check if we found a good substring
        		boolean foundSubString = true;
        		int minIndex = Integer.MAX_VALUE;
        		int maxIndex = -1;
        		for (ValueHolder vh : map.values()) {
        			if (vh.queue.size() < vh.count) {
        				foundSubString = false;
        				break;
        			}
        			if (vh.count == 1) {
        				int index = vh.queue.get(0);
        				if (index > maxIndex)
        					maxIndex = index;
        				if (index < minIndex)
        					minIndex = index;
        			}
        			else {
        				if (vh.queue.get(vh.count-1) > maxIndex)
        					maxIndex = vh.queue.get(vh.count-1);
        				if (vh.queue.get(0) < minIndex)
        					minIndex = vh.queue.get(0);
        			}
        		}
        		if (foundSubString) {
        			int curLength = maxIndex - minIndex;
        			if (curLength >= 0 && curLength < windowSize) {
        				startIndex = minIndex;
        				endIndex = maxIndex;
        				windowSize = curLength;
        			}
        		}
        	}
        }
        if (startIndex != -1) {
        	return S.substring(startIndex, endIndex+1);
        }
        else
        	return "";
    }
    
    private class ValueHolder {
    	int count = 0;
    	LimitedQueue<Integer> queue;
    }
    
    private class LimitedQueue<E> extends LinkedList<E> {
		private static final long serialVersionUID = -7024309956232955611L;
		private int limit;
        public LimitedQueue(int limit) {
            this.limit = limit;
        }

        @Override
        public boolean add(E o) {
            boolean added = super.add(o);
            while (added && size() > limit) {
               super.remove();
            }
            return added;
        }
    }
    
	public static void main(String[] args) {
		Solution solution = new Solution();
		
		System.out.println(solution.minWindow("cabwefgewcwaefgcf", "cae").equals("cwae"));
		System.out.println(solution.minWindow("a", "a").equals("a"));
		System.out.println(solution.minWindow("abcad", "aa").equals("abca"));
		System.out.println(solution.minWindow("abcd", "cb").equals("bc"));
		System.out.println(solution.minWindow("abcd", "aa").equals(""));

	}

}
