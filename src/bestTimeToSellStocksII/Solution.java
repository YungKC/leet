package bestTimeToSellStocksII;

/*
 * 
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. 
You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times). 
However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

idea:

this is simply all possible price rises
 */
public class Solution {

    public int maxProfit(int[] prices) {
        int lengthMinus1 = prices.length-1;
        if (prices == null || lengthMinus1 < 1)
        	return 0;
     
        int minValue = prices[0];
        int maxProfit = 0;
        
        int i=0;
        while (i<lengthMinus1) {
	        // find min
	        for (; i<lengthMinus1 && prices[i+1] <= prices[i]; i++);
	        minValue = prices[i];

	        // find max
	        for (; i<lengthMinus1 && prices[i+1] >= prices[i]; i++);
    		maxProfit += prices[i] - minValue;
        }
    	return maxProfit;       	
    }
    
    public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.maxProfit(new int[] {10,1,2,3,6,4,2,7}) == 10);
		System.out.println(sol.maxProfit(new int[] {10,8,9,6,7,3,5,1}) == 4);
		System.out.println(sol.maxProfit(new int[] {10, 9, 8, 7}) == 0);
		System.out.println(sol.maxProfit(new int[] {1}) == 0);
		System.out.println(sol.maxProfit(new int[] {1,2}) == 1);
		System.out.println(sol.maxProfit(new int[] {1,1,1,1,1}) == 0);
	}

}
