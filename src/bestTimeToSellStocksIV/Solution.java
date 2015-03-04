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

find each stock rise segment
a drop will negate previous positive segments, when trying to find best entry/exit point for 1 transaction

I assume that we do not support trading via buying shorts. That is, one has to buy first before selling.
if k is odd, then the last sold price can be considered as the very last price.

if k=2 then it's the max difference between any rise (with potential drops in between)
if the net drops below 0, then one can ignore previous history and starts anew.

then for k' = k_previous + 2, the increase in net profit is the negative of the next biggest drop.

since k can be big, we need to store all known bull runs and each price drop. Sort this list by potential gains.

cannot count a price drop as a potential if it occurs between bull runs

for any bull runs that ran up to a max, then series ends or dropped to negative gain, we need to include the potential profit from end of that peak.
 */
public class Solution {

    public int maxProfit(int k, int[] prices) {

    	int priceLength = prices.length;

    	if (prices == null || priceLength < 2)
    		return 0;

    	// find the first entry point where the price is at a low
    	int index = 0;
    	while (index < priceLength-1 && prices[index] >= prices[index+1]) {
    		index++;
    	}
    	
    	if (index == 0)
    		index++;

    	List<Integer> acceptedProfitPotential = new ArrayList<Integer>();

    	int lastHigh = prices[index-1];
    	int lastLow = prices[index-1];
    	
    	int netProfit = 0;
    	int maxProfit = 0;
    	List<Integer[]> curRunProfitPotential = new ArrayList<Integer[]>();	// price drop object, 0=value, 1=index
    	int indexMaxProfitCurrentBullRun = Integer.MIN_VALUE;
    	
    	while (index < priceLength) {
    		while (index < priceLength && prices[index-1] <= prices[index]) {
    			index++;
    		}
    		lastHigh = prices[index-1];
    		netProfit += lastHigh - lastLow;
    		if (netProfit  > maxProfit) {
    			maxProfit = netProfit;
    			indexMaxProfitCurrentBullRun = index-1;
    		}
			while (index < priceLength && prices[index-1] >= prices[index]) {
				index++;
			}
			int priceDrop = lastHigh-prices[index-1];
			curRunProfitPotential.add(new Integer[]{priceDrop, index-1});
			lastLow = prices[index-1];
			netProfit -= priceDrop;
			
			// reset if we are now in the red.
			// be careful here.
			// If the maxProfit occurs in this stretch, store the list of drops in a holding list,
			// if not in this stretch, save all the other possible run ups to maximize profits
			if (netProfit <= 0) {
		    	tally(maxProfit, indexMaxProfitCurrentBullRun, acceptedProfitPotential, curRunProfitPotential);
				maxProfit = 0;
				netProfit = 0;
				curRunProfitPotential = new ArrayList<Integer[]>();
				indexMaxProfitCurrentBullRun = Integer.MIN_VALUE;
			}
    	}
    	tally(maxProfit, indexMaxProfitCurrentBullRun, acceptedProfitPotential, curRunProfitPotential);

		// need to add up all possible gains after the last bull run
    	maxProfit = 0;
    	Collections.sort(acceptedProfitPotential);
    	
    	int maxPotentialIndex = acceptedProfitPotential.size() - 1;
    	for (int i=0; i<k && i<=maxPotentialIndex; i++) {
    		maxProfit += acceptedProfitPotential.get(maxPotentialIndex - i);
    	}
    	return maxProfit;
    }
    
    private static void tally(int maxProfit, int indexMaxProfitCurrentBullRun, List<Integer> acceptedProfitPotential, List<Integer[]> curRunProfitPotential) {
		if (maxProfit > 0)
			acceptedProfitPotential.add(maxProfit);
		for (Integer[] potential : curRunProfitPotential) {
			if (potential[1] < indexMaxProfitCurrentBullRun && potential[0] > 0) {
				acceptedProfitPotential.add(potential[0]);
			}
		}
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maxProfit(5, new int[]{1,7,2,4}) == 8);
		System.out.println(sol.maxProfit(5, new int[]{1,7,2,4,3,5}) == 10);
		System.out.println(sol.maxProfit(5, new int[]{10,30,0,40,10,30,20,40,30,70}) == 140);
		System.out.println(sol.maxProfit(5, new int[]{30,10,30,0,40,10,30,20,40,30,70}) == 140);
		System.out.println(sol.maxProfit(1, new int[]{10,30,0,40,10,30,20,40,30,70}) == 70);
		System.out.println(sol.maxProfit(5, new int[]{10,30,20,40}) == 40);
		System.out.println(sol.maxProfit(1, new int[]{10,30,20,40}) == 30);
		System.out.println(sol.maxProfit(5, new int[]{10,30}) == 20);
		System.out.println(sol.maxProfit(2, new int[]{2,1,2,0,1}) == 2);
		System.out.println(sol.maxProfit(5, new int[]{10,30,0,40}) == 60);

	}

}
