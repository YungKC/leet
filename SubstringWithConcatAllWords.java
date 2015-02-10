package leet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;



/*
 * 
You are given a string, S, and a list of words, L, that are all of the same length. Find all starting indices of substring(s) in S that is a concatenation of each word in L exactly once and without any intervening characters.

For example, given:
S: "barfoothefoobarman"
L: ["foo", "bar"]

You should return the indices: [0,9].
(order does not matter).

 */
public class SubstringWithConcatAllWords {
	
	private static int numSubStrings;
	private static int stringSize;
	private static int[] stringCountList;

	public static ArrayList<Integer> findSubstring(String S, String[] L) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		if (S == null || S.length() == 0 || L.length == 0)
			return result;
		
		
		int wordSize = L[0].length();
		
		// construct helper data structures for efficient search and lookup
		
		List<String> uniqueStrings = new ArrayList<String>();	// unique strings
		Map<String, Integer> stringIndexMap = new HashMap<String, Integer>(); // to get the uniqueStringID from the string
		stringCountList = new int[L.length];				// give #occurrence of each stringID 
		int lastIndex = 0;
		for (int i=0; i<L.length; i++) {
			String substring = L[i];
			Integer index = stringIndexMap.get(substring);
			if (index == null) {
				stringIndexMap.put(substring, lastIndex);
				uniqueStrings.add(substring);
				stringCountList[lastIndex++] = 1;
			}
			else {
				stringIndexMap.put(substring, index);
				stringCountList[index]++;
			}
		}		
		stringIndexMap = null;
		
		Map<Integer, Set<Integer>> stringMap = new HashMap<Integer, Set<Integer>>();
		Map<Integer, Integer> indexMap = new TreeMap<Integer, Integer>();				// questionStringIndex -> stringID
		
		for (int i=0; i<uniqueStrings.size(); i++) {
//			String newString = S;
			int index = 0;
			do {
				index = S.indexOf(uniqueStrings.get(i), index);
				if (index >= 0) {
					indexMap.put(index, i);
					Set<Integer> values = stringMap.get(i);
					if (values == null) {
						values = new TreeSet<Integer>();
						stringMap.put(i,  values);
					}
					values.add(index);
					index += wordSize;
				}
			}
			while (index != -1);				
		}		
		stringMap = null;
		
		// now scan the indexMap to look for first occurrence
		// each index should be separated by L[0].length, and there should be L.length entries
		int nextResult = -1;
		int startIndex = 0;
		numSubStrings = L.length;
		stringSize = wordSize;

		do {
			nextResult = getNextIndex(indexMap, startIndex);
			if (nextResult >= 0) {
				result.add(nextResult);
				startIndex = nextResult + L.length;
			}
		} while (nextResult >= 0);
		return result;
    }
	

	// now scan the indexMap to look for first occurrence
	// each index should be separated by L[0].length, and there should be L.length entries
	// indexMap is questionStringIndex -> stringID
	// startIndex is first index in indexMap to look for complete set
	private static int getNextIndex(Map<Integer, Integer> indexMap, int startIndex) {
		List<Integer> indexList = new ArrayList<Integer>(indexMap.keySet());
		
		if (indexList.size() - startIndex < numSubStrings)
			return -1;
		if (indexList.size() - startIndex == 1) {
			if (numSubStrings == 1)
				return indexList.get(startIndex);
			else
				return -1;
		}
		int startPos = indexList.get(startIndex);
		int[] localStringCountList = stringCountList.clone(); 
		int nextMaxCount = startIndex + numSubStrings;
		for (int i=startIndex; i<indexList.size() && i<nextMaxCount; i++) {
			if (!(indexList.get(i)-startPos == (i-startIndex)*stringSize)) {
				return getNextIndex(indexMap, ++i);
			}
			int stringIndex = indexMap.get(indexList.get(i));
			localStringCountList[stringIndex]--;			
		}
		for (int index : localStringCountList) {
			if (index != 0)
				return getNextIndex(indexMap, nextMaxCount);
		}
		return startIndex;
	}



	/*
	 * 		{"foo", "foo"}
	 * 
	 */

	public static void main(String[] args) {
		System.out.println(findSubstring("barfoothefoobarman", new String[] {"foo", "bar"}));
		System.out.println(findSubstring("foofoothefoobarman", new String[] {"foo", "foo"}));
		System.out.println(findSubstring("ababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab", new String[] {"ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba"}));
	}

}
