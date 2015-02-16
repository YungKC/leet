package longestSubstringWithoutRepeatChars;

import java.util.HashMap;
import java.util.Map;

/*
 * 
Given a string, find the length of the longest substring without repeating characters. 
For example, the longest substring without repeating letters for "abcabcbb" is "abc", 
which the length is 3. For "bbbbb" the longest substring is "b", with the length of 1.

idea: store a map of each unique char and where it was encountered in the string.
make sure to test dup characters inside the string.
 */
public class Solution {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0)
        	return 0;
        
        int maxLength = 0;
        int strLen = s.length();
        int startIndex = 0;
        Map<Character, Integer>charIndex = new HashMap<Character, Integer>();
        for (int i=0; i<strLen; i++) {
        	Integer lastCharIndex = charIndex.get(s.charAt(i));
        	if (lastCharIndex == null) {
        		int curLength = i-startIndex+1;
        		if (curLength > maxLength) {
        			maxLength = curLength;
        		}
        	}
        	else {
        		// be sure to remove entries from the map of previous characters
        		for (int j=startIndex; j<lastCharIndex; j++) {
        			charIndex.remove(s.charAt(j));
        		}
        		startIndex = charIndex.get(s.charAt(i))+1;
        	}
    		charIndex.put(s.charAt(i), i);
        }
        return maxLength;
    }
    
    public static void main(String[] args) {
    	Solution sol = new Solution();
    	
    	System.out.println(sol.lengthOfLongestSubstring("tmmzuxt") == 5);
    	System.out.println(sol.lengthOfLongestSubstring("abcabc") == 3);
    	System.out.println(sol.lengthOfLongestSubstring("abacbc") == 3);
    	System.out.println(sol.lengthOfLongestSubstring("bbbbbb") == 1);
    	System.out.println(sol.lengthOfLongestSubstring("") == 0);
    	
	}

}
