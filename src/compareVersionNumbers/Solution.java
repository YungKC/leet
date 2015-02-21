package compareVersionNumbers;

/*
 * 
Compare two version numbers version1 and version2.
If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.

You may assume that the version strings are non-empty and contain only digits and the . character.
The . character does not represent a decimal point and is used to separate number sequences.
For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.

Here is an example of version numbers ordering:

0.1 < 1.1 < 1.2 < 13.37

Observation:

need to account for 1.0.0.0.0 vs 1


 */
public class Solution {

    public int compareVersion(String version1, String version2) {
        String[] v1Parts = version1.split("\\.");
        String[] v2Parts = version2.split("\\.");
        int partSize = Math.min(v1Parts.length, v2Parts.length);
       for (int part = 0; part<partSize; part++) {
    	   int v1Part = v1Parts[part].length()==0?0:Integer.parseInt(v1Parts[part]);
    	   int v2Part = v2Parts[part].length()==0?0:Integer.parseInt(v2Parts[part]);
    	   if (v1Part > v2Part)
    		   return 1;
    	   else if (v1Part < v2Part)
    		   return -1;
       }
       if (v1Parts.length == v2Parts.length)
    	   return 0;
       // now check for the version string with the most parts
       
       int result = -1;
       String[] partsInQuestion= v2Parts;
       if (v1Parts.length > v2Parts.length) {
    	   result = 1;
    	   partsInQuestion = v1Parts;
       }
       for (int part = partSize; part<partsInQuestion.length; part++) {
    	   if (partsInQuestion[part].length()!=0 && Integer.parseInt(partsInQuestion[part]) != 0)
    		   return result;
       }
       return 0;
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.compareVersion("1","1.0") == 0);
		System.out.println(sol.compareVersion("..1","0.0.1") == 0);
		System.out.println(sol.compareVersion("1.1","1.2") == -1);
		System.out.println(sol.compareVersion("1.11","1.2") == 1);
		System.out.println(sol.compareVersion("1.1","1.1") == 0);
		System.out.println(sol.compareVersion("1.1","1.01") == 0);
		System.out.println(sol.compareVersion("1.1.1","1.2") == -1);
		System.out.println(sol.compareVersion("1.1.1","1.1") == 1);
		

	}

}
