package leet.PalindromePartioningII;

import java.util.HashMap;
import java.util.Map;

/*
 Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

For example, given s = "aab",
Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut. 
strategy:

loop through possible solution of 0, 1, ...

0 if s is itself a palindrome...

*/

public class Solution {


/*
Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

For example, given s = "aab",
Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut. 
strategy:

loop through possible solution of 0, 1, ...

0 if s is itself a palindrome...

*/

    public int minCut(String s) {
        // use dynamic programming to solve this
        int length = s.length();
        
        if (length == 0)
            return 0;
        
        int lastIndex = length-1;
        
        boolean[][] isPal = new boolean[length][length];
        int[] minNumCut = new int[length];
        
        for (int i=lastIndex; i>=0; i--) {
            minNumCut[i] = lastIndex - i;           // for string of length i, there is at least i cuts to make every substring palindrome
            for (int j=i; j<length; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j-i<2 || (isPal[i+1][j-1])) {
                        isPal[i][j] = true;
                        if (j == lastIndex)
                            minNumCut[i] = 0;
                        else
                            minNumCut[i] = Math.min(minNumCut[i], minNumCut[j+1]+1);
                    }
                }
            }
        }
        return minNumCut[0];
    }

   public static void main(String[] argv) {
       Map<String,Integer> questions = new HashMap<String, Integer>();
       questions.put("", 0);
       questions.put("a", 0);
       questions.put("aa", 0);
       questions.put("ab", 1);
       questions.put("aba", 0);
       questions.put("aaa", 0);
       questions.put("aaabaa", 1);
       questions.put("aaaba", 1);
       questions.put("abcddefgh", 7);
       questions.put("aaaaaaaaaaaaaaaa", 0);
       questions.put("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 1);

       PalindromePartioningII worker = new PalindromePartioningII();
       for (String key : questions.keySet()) {
           int result = worker.minCut(key);
           if (result != questions.get(key)) {
               System.out.println("expected " + questions.get(key) + ". Got " + result + " for " + key);
           }
       }

   }
}
