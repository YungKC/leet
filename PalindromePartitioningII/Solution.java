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
	public String inputString = null;
    public int minCut(String s) {
    	inputString = s;
    	int cutCount = 0;
        if (isPal(0, s.length()))
        	return cutCount;

	    int start = 0;
	    do {
	    	int nextIncrement = 1;
//	    	System.out.println("start:length is " + start + ":" + s.length());
	        for (int l=s.length()-start; l>1; l--) {
//    			System.out.println("checking " + inputString.substring(start, start+l));
	        	if (isPal(start, l)) {
	        		nextIncrement = l;
	        		break;
	        	}
	        }
	        cutCount++;
	        start += nextIncrement;
	    } while (start < s.length());
        return cutCount-1;
    }

    // if length == 1 then return true
    // so length input should always be > 1
    private boolean isPal(int start, int length ) {
    	if (length == 1)
    		return true;
    	int checkLength = length/2;
    	int lastCharIndex = start+length-1;
    	for (int i=0; i<checkLength; i++) { 
    		if (inputString.charAt(start+i) != inputString.charAt(lastCharIndex-i))
    			return false;
    	}
    	return true;
    } 

    public static void main(String[] argv) {
    	System.out.println(new Solution().minCut(argv[0]));

    }
}
