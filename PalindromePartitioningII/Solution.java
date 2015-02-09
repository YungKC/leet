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
	private String inputString = null;
	private int cutCount = 0;

    public int minCut(String s) {
    	inputString = s;
        if (isPal(0, s.length()))
        	return cutCount;

	    int start = 0;
	    do {
	    	int nextIncrement = 1;
	    	System.out.println("start:length is " + start + ":" + s.length());
	        for (int l=s.length()-start; l>1; l--) {
    			System.out.println("checking " + inputString.substring(start, start+l));
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

    private void setString(String s) {
    	inputString = s;
    }

	private void extractLargestPalindrome(int start, int length) {
		System.out.println("extracting from " + inputString.substring(start, start+length));
		int maxLength = 0;
		int startIndex = start; 
		for (int i = 0; i<length; i++) {
			for (int subStringLength = length-i; subStringLength>0; subStringLength--) {
				int ii = start+i;
				if (isPal(ii, subStringLength)) {
					if (subStringLength > maxLength) {
						maxLength = subStringLength;
						startIndex = ii;
					}
				}
			}
		}
		if (startIndex != start) {
			extractLargestPalindrome(start, startIndex-start);
			cutCount++;
			System.out.println(cutCount);
		}
		if (startIndex+maxLength < length) {
			extractLargestPalindrome(startIndex+maxLength, length-startIndex-maxLength);
			cutCount++;
			System.out.println(cutCount);
		}
	}

    // if length == 1 then return true
    // so length input should always be > 1
    private boolean isPal(int start, int length ) {
		System.out.println("isPal " + inputString.substring(start, start+length));
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
//    	argv[0] = "aaabaa";
//    	argv[0] = "aaaba";
//    	System.out.println(new Solution().minCut(argv[0]));
    	Solution sol = new Solution();
    	sol.setString(argv[0]);
    	sol.extractLargestPalindrome(0, argv[0].length());
    }
}
