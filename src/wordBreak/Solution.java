package wordBreak;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*
 * 
Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated sequence of one or more dictionary words.

For example, given
s = "leetcode",
dict = ["leet", "code"].

Return true because "leetcode" can be segmented as "leet code".

idea:

get maxLength of entries in dictionary
generate map of startIndex to [list of substring length whose substring is in the dictionary] ordered by length descending

 * 
 */
public class Solution {
	private Map<Integer, Set<Integer>> prefixMap;
	private int strLen = 0;
	
	public boolean wordBreak(String s, Set<String> dict) {
	    if (s == null)
	    	return false;
	    strLen = s.length();
	    if (strLen == 0)
	    	return true;	    
		String lowercase = s.toLowerCase();
		
	    int maxDictEntryStrLen = 0;
	    for (String entry : dict) {
	    	if (entry.length() > maxDictEntryStrLen)
	    		maxDictEntryStrLen = entry.length();
	    }
	    prefixMap = new HashMap<Integer, Set<Integer>>();
	    
	    boolean canReachEnd = false;
	    
	    for (int startIndex = 0; startIndex<strLen; startIndex++) {
		    for (int i=1; i<=maxDictEntryStrLen && i+startIndex <= strLen; i++) {
		    	if (dict.contains(lowercase.substring(startIndex, i+startIndex))) {
		    		Set<Integer> list = prefixMap.get(startIndex);
		    		if (list == null) {
		    			list = new TreeSet<Integer>(new Comparator<Integer>(){
		    				public int compare(Integer o1, Integer o2) 
                            {
                                return o2-o1;
                            }
		    			});
		    			prefixMap.put(startIndex, list);
		    		}
	    			list.add(startIndex+i);
	    			if (startIndex+i == strLen)
	    				canReachEnd = true;
		    	}
		    }
	    }
	    
	    if (!canReachEnd)
	    	return false;
	    
	    return findPrefix(0);
	}
	
	private boolean findPrefix(int startIndex) {    
    	Set<Integer> prefixLengthList = prefixMap.get(startIndex);
    	if (prefixLengthList == null) {
    		return false;
    	}
    	for (int nextIndex:prefixLengthList) {
 //   		System.out.println("level: " + level + ", index: " + startIndex + " to " + nextIndex);
    		if (nextIndex == strLen || findPrefix(nextIndex))
    			return true;
    	}
    	prefixMap.remove(startIndex);		// need to remove the map entry if we verified that we cannot reach end from this index
    	return false;
	}

	public static void main(String argv[]) {
		Set<String> dict = new HashSet<String>();
		Solution sol;
		boolean result;
		
//		dict.add("ag");
//		dict.add("a");
//		dict.add("gun");
//		
//		sol = new Solution();
//		result = sol.wordBreak("aGuna", dict);
//		System.out.println(result);
		
//		dict = new HashSet<String>();
//		dict.add("aaaa");
//		dict.add("aaa");
//		
//		sol = new Solution();
//		result = sol.wordBreak("aaaaa", dict);
//		System.out.println(result);
		
		dict = new HashSet<String>();
		dict.add("a");
		dict.add("abc");
		dict.add("b");
		dict.add("cd");

		sol = new Solution();
		result = sol.wordBreak("abcd", dict);
		System.out.println(result == true);
		
		dict = new HashSet<String>();
		dict.add("a");
		dict.add("aa");
		dict.add("aaa");
		dict.add("aaaa");
		dict.add("aaaaa");
		dict.add("aaaaaa");
		dict.add("aaaaaaa");
		dict.add("aaaaaaaa");
		dict.add("aaaaaaaaa");
		dict.add("aaaaaaaaaa");
		sol = new Solution();
		result = sol.wordBreak("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab", dict);
		System.out.println(result == false);
	}
}
