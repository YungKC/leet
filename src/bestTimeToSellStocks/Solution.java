package bestTimeToSellStocks;

import java.util.ArrayList;
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

 */
public class Solution {

    public int maxProfit(int k, int[] prices) {

    	int priceLength = prices.length;
    	if (prices == null || priceLength < 2)
    		return 0;

    	List<Integer> priceDrops = new ArrayList<Integer>();
    	int lastHigh = prices[0];
    	int lastLow = prices[0];
    	int netProfit = 0;
    	int maxProfit = 0;
    	int index = 1;    	
    	while (index < priceLength) {
    		while (index < priceLength && prices[index-1] <= prices[index]) {
    			index++;
    		}
    		lastHigh = prices[index-1];
    		netProfit += lastHigh - lastLow;
    		if (netProfit  > maxProfit)
    			maxProfit = netProfit;
    		
			while (index < priceLength && prices[index-1] >= prices[index]) {
				index++;
			}
			int priceDrop = lastHigh-prices[index-1];
			priceDrops.add(priceDrop);
			lastLow = prices[index-1];
			netProfit -= priceDrop;
			
			// reset if we are now in the red.
			if (netProfit <= 0) {
				priceDrops = new ArrayList<Integer>();
				netProfit = 0;
			}
    	}
    	
    	return maxProfit;
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maxProfit(10, new int[]{10,30,0,40}) == 40);
		System.out.println(sol.maxProfit(10, new int[]{10,30,20,40}) == 30);
		System.out.println(sol.maxProfit(10, new int[]{10,30}) == 20);

	}

}
