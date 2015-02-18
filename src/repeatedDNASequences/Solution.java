package repeatedDNASequences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 
 All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG". 
 When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.

Write a function to find all the 10-letter-long sequences (substrings) that occur more than once 
in a DNA molecule.

For example,

Given s = "AAAAACCCCC AAAAACCCCC CAAAAAGGGT TT",

Return:
["AAAAACCCCC", "CCCCCAAAAA"].

Observation: brute force string comparison got memory limit exceeded error. 
Let's encode the sequence in more compact form, since there are only 4 possible values of bases in DNA.

A: 00
C: 01
G: 10
T: 11

so
1 byte encodes 2 DNA bases
5 bytes encode 10 DNA bases

AA: 0000 = 0
AC: 0001 = 1
AG: 0010 = 2
AT: 0000 = 3
...

 *
 */
public class Solution {

    public List<String> findRepeatedDnaSequences(String s) {
        List<String> finalResult = new ArrayList<String>();
        
        List<Integer> result = new ArrayList<Integer>();
        Set<Integer> sequences = new HashSet<Integer>();
        
        if (s == null || s.length() <= 10)
        	return finalResult;
        
        char[] baseSequence = s.toCharArray();        
		int val = encodeDNA(baseSequence, 0, 10);
		sequences.add(val);
        int length = baseSequence.length;
        
        for (int i=10; i<length; i++) {
//        	val = encodeDNA(val, baseSequence[i]);
        	val = (val<<2 & 0xFFFFF) + Base2Val.get(baseSequence[i]);

        	if (sequences.contains(val)) {
        		if (!result.contains(val)) {
        			result.add(val);
        		}
        	}
        	else
        		sequences.add(val);
        }
        for (int entry : result) {
        	finalResult.add(String.valueOf(decodeDNA(entry)));
        }
        return finalResult;
    }
    
    private static Map<Character, Integer> Base2Val = new HashMap<Character, Integer>(4);
    private static Map<Integer, Character> Val2Base = new HashMap<Integer, Character>(4);
    static {
    	Base2Val.put('A', (int) 0);
    	Base2Val.put('C', (int) 1);
    	Base2Val.put('G', (int) 2);
    	Base2Val.put('T', (int) 3);    
    	Val2Base.put((int) 0, 'A');
    	Val2Base.put((int) 1, 'C');
    	Val2Base.put((int) 2, 'G');
    	Val2Base.put((int) 3, 'T');    	
    }
    
    private static int encodeDNA(final int baseDNA, final char nextBase) {
    	return (baseDNA<<2 & 0xFFFFF) + Base2Val.get(nextBase);
//    	System.out.println("from: " +  String.valueOf(Integer.toBinaryString(baseDNA) + " : " + nextBase + " to : " + Integer.toBinaryString(tmp)));
//    	return tmp;
    }
    
    private static int encodeDNA(final char[] inCharArray, final int start, final int end) {
    	int val = Base2Val.get(inCharArray[start]);
    	
    	for (int i=start+1; i<end; i++) {
    		val = (int)(val<<2);
    		val += Base2Val.get(inCharArray[i]);
    	}
    	return val;
    }
    
    private static char[] decodeDNA(final int dnaVal) {
    	int val = dnaVal;
    	char[] result = new char[10];
    	for (int i=9; i>=0; i--) {
    		result[i] = Val2Base.get((int)(val & 0x3));
    		val = val >> 2;
    	}
    	return result;
    }
    
    public static void main(String[] args) {
		Solution sol = new Solution();
		List<String> result;
		String inString = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
		String subString = inString.substring(0, Math.min(10, inString.length()));
		System.out.println(subString);
		int val = encodeDNA(subString.toCharArray(), 0, 10);
		System.out.println(val);
		String decodedString = String.valueOf(decodeDNA(val));
		System.out.println(decodedString);
		if (inString.length() > 10) {
			val = encodeDNA(val, inString.charAt(10));
			System.out.println(val);
		}
		decodedString = String.valueOf(decodeDNA(val));
		System.out.println(decodedString);
		System.out.println();
		
		System.out.println(inString);
		result = sol.findRepeatedDnaSequences(inString);
		
		printResult(result);
	}
    
    private static void printResult(List<String> result) {
    	for (String val : result)
    		System.out.print(val + " ");
    	System.out.println();
    }
}

