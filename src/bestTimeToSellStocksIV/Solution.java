package bestTimeToSellStocksIV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most k transactions.

Note:
You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).


Idea:

find boundary of time from lowestPrice to highestPrice
for this group, 
	p_high - p_low is a profit potential
	each price drop segment is a profit potential
for all price point before this group, repeat step above
for all price pints after this group, repeat step above


 */
public class Solution {

	List<Integer> acceptedProfitPotential;
	
	public int maxProfit(int k, int[] prices) {
		
    	if (prices == null || prices.length < 2 || k == 0)
    		return 0;
    	
    	
    	// optimization
    	// squeeze all increasing or decreasing series into 1 entry
    	int[] squeezedPrices = new int[prices.length];
    	int squeezedIndex = 0;
    	int index = 0;
    	while (prices[index] >= prices[index+1]) {
    		index++;
    	}
    	squeezedPrices[squeezedIndex++] = prices[index++];
    	int sign = 1;
    	while (index < prices.length) {
	    	while (index < prices.length && prices[index] <= sign * prices[index-1]) {
	    		index++;
	    	}
	    	if (index < prices.length) {
			    squeezedPrices[squeezedIndex++] = prices[index++];
			    sign *= -1;
	    	}
    	}
    	
    	int startIndex = 0;
    	int endIndex = squeezedIndex-1;
    	if (endIndex == 0)
    		return 0;
    	
    	// make sure starting and ending is increasing
    	if (squeezedPrices[0] >= squeezedPrices[1]) {
    		startIndex = 1;
    	}
    	if (squeezedPrices[endIndex] <= squeezedPrices[endIndex-1]) {
    		endIndex--;
    	}

    	acceptedProfitPotential = new ArrayList<Integer>();

    	searchBullCycleRecursive(squeezedPrices, startIndex, endIndex);
		int result = 0;
    	int sizeOfPotentialMinus1 = acceptedProfitPotential.size() - 1;
    	int numTransactions = Math.min(k, sizeOfPotentialMinus1+1);

    	Collections.sort(acceptedProfitPotential);
    	for (int i=0; i<numTransactions; i++) {
    		result += acceptedProfitPotential.get(sizeOfPotentialMinus1-i);
    	}
    	return result;
		
	}
	
	private void searchBullCycleRecursive(int[] prices, int startIndex, int endIndex) {
		// find the lowest and highest price index
		int lowest = prices[startIndex];
		int lowestIndex = startIndex;
		
		// note the equal signs to ensure we have the tightest bull cycle, and lowest index <= highest index
		for (int i = startIndex+1; i <= endIndex; i++) {
			if (prices[i] <= lowest) {
				lowestIndex = i;
				lowest = prices[i];
			}
		}
		int highest = prices[lowestIndex];
		int highestIndex = lowestIndex;
		for (int i = lowestIndex+1; i<= endIndex; i++) {
			if (prices[i] > highest) {
				highestIndex = i;
				highest = prices[i];
			}
		}
		if (highest != lowest)
			acceptedProfitPotential.add(highest-lowest);
		
		// every other is a drop. e.g., 3, 5, 4 , ....
		for (int i = lowestIndex+1; i<highestIndex; i+=2) {
			acceptedProfitPotential.add(prices[i]-prices[i+1]);
		}
		if (startIndex < lowestIndex-1)
			searchBullCycleRecursive(prices, startIndex, lowestIndex-1);
		if (endIndex > highestIndex+1)
			searchBullCycleRecursive(prices, highestIndex+1, endIndex);		
	}

    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maxProfit(5, new int[]{30,10,30,0,40,10,30,20,40,30,70,60,50,40,30}) == 140);
		System.out.println(sol.maxProfit(5, new int[]{30,10,30,0,40,10,30,20,40,30,70,80,85,86}) == 156);
		System.out.println(sol.maxProfit(5, new int[]{30,10,30,0,40,10,30,20,40,30,70}) == 140);
		System.out.println(sol.maxProfit(5, new int[]{10,30}) == 20);
		System.out.println(sol.maxProfit(5, new int[]{1,7,2,4,3,5}) == 10);
		System.out.println(sol.maxProfit(5, new int[]{10,30,0,40,10,30,20,40,30,70}) == 140);
		System.out.println(sol.maxProfit(1, new int[]{10,30,0,40,10,30,20,40,30,70}) == 70);
		System.out.println(sol.maxProfit(5, new int[]{10,30,20,40}) == 40);
		System.out.println(sol.maxProfit(1, new int[]{10,30,20,40}) == 30);
		System.out.println(sol.maxProfit(2, new int[]{2,1,2,0,1}) == 2);
		System.out.println(sol.maxProfit(5, new int[]{1,7,2,4}) == 8);
		System.out.println(sol.maxProfit(5, new int[]{10,30,0,40}) == 60);

	}

}
