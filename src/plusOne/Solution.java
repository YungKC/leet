package plusOne;

import java.util.ArrayList;
import java.util.List;

/*
 * 
Given a non-negative number represented as an array of digits, plus one to the number.

The digits are stored such that the most significant digit is at the head of the list.

 */
public class Solution {

    public int[] plusOne(int[] digits) {
        int count = digits.length;
    	int[] result = new int[count];
    	boolean isCarried = false;
    	int lastDigit = count-1;
    	if (digits[lastDigit] == 9) {
    		result[lastDigit] = 0;
    		isCarried = true;
    	}
    	else
    		result[lastDigit] = digits[lastDigit]+1;
    	
        for (int i = lastDigit-1; i>=0; i--) {
        	if (isCarried) {
            	if (digits[i] == 9) {
            		result[i] = 0;
            		isCarried = true;
            	}
            	else {
            		result[i] = digits[i] + 1;
            		isCarried = false;
            	}
        	}
        	else {
        		result[i] = digits[i];
        		isCarried = false;        		
        	}
        }
        if (isCarried) {
        	int[] newResult = new int[count+1];
        	for (int i=1; i<count; i++)
        		newResult[i] = 0;
        	newResult[0] = 1;
        	newResult[count] = 0;
        	return newResult;
        }
        else
        	return result;
    }
    
    public static void main(String[] args) {
		Solution sol = new Solution();
		printArray(sol.plusOne(getArray(8999)));
		printArray(sol.plusOne(getArray(10)));
		printArray(sol.plusOne(getArray(12345)));
		printArray(sol.plusOne(getArray(99999)));
		printArray(sol.plusOne(getArray(0)));

	}

    private static void printArray(int[] value) {
    	for (int i=0; i<value.length; i++)
    		System.out.print(value[i]);
    	System.out.println();
    }
    
    private static int[] getArray(int value) {
    	List<Integer> result = new ArrayList<Integer>();
    	int digit = -1;
    	int curValue = value;
    	do {
	    	digit = curValue % 10;
	    	result.add(0, digit);
	    	curValue = curValue/10;
    	} while (curValue != 0);
    	
    	int[] finalResult = new int[result.size()];
    	for (int i=0; i<result.size(); i++)
    		finalResult[i] = result.get(i);
    	return finalResult;
    }
}
