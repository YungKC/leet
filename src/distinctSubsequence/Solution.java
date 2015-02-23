package distinctSubsequence;

/*
 * 
 Given a string S and a string T, count the number of distinct subsequences of T in S.

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

Here is an example:
S = "rabbbit", T = "rabbit"

Return 3.

Ideas:

can use reg expression
use dynamic programming, build up state data:
https://oj.leetcode.com/discuss/19735/a-dp-solution-with-clarification-and-explanation

note: 
state[s,0] = 1
state[s,t] = 0 if s<t;

if (S[s] != T[t]) state[s,t]=state[s-1,t]
if (S[s] == T[t]) state[s,t]=state[s-1,t] + state[s-1,t-1]
 */
public class Solution {

    public int numDistinct(String S, String T) {
    	int[][] state = new int[S.length()+1][T.length()+1];
    	
    	for (int s=0; s<=S.length(); s++)
    		state[s][0] = 1;
    	
    	for (int t=1; t<=T.length(); t++) {
    		for (int s=1; s<=S.length(); s++) {
    			if (S.charAt(s-1) != T.charAt(t-1))
    				state[s][t] = state[s-1][t];
    			else {
    				state[s][t] = state[s-1][t] + state[s-1][t-1];
    			}
    		}
    	}
    	return state[S.length()][T.length()];
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.numDistinct("abab", "ab")==3);
		System.out.println(sol.numDistinct("aab", "ab")==2);
		System.out.println(sol.numDistinct("aaaaa", "a")==5);
		System.out.println(sol.numDistinct("a", "a")==1);
		System.out.println(sol.numDistinct("ab", "a")==1);
		System.out.println(sol.numDistinct("", "")==1);

	}

}
