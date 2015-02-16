package gasStation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * 
There are N gas stations along a circular route, where the amount of gas at station i is gas[i].

You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.

Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.

Idea:
loop through each station to get net, then order station ranked by net
remove stations to check if net is < 0;
have extra check if aggregate net is < 0. If so, then return -1
trim number of stations by combining all consecutive negative ones to 1 station

AnotherIdea:
Use observation that the best starting station is when the last station has the worst net balance

 */
public class Solution {

//	AnotherIdea:
//	Use observation that the best starting station is when the last station has the worst net balance

	public int canCompleteCircuit(int[] gas, int[] cost) {
    	if (gas == null || cost == null || gas.length != cost.length || gas.length == 0)
    		return -1;
        int sum = 0;
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i=0; i<gas.length; i++){
            sum += (gas[i] - cost[i]);
            if (sum < min){
                min = sum;
                minIndex = i;
            }
        }

        if (sum < 0)
            return -1;

        if (minIndex == gas.length - 1)
            return 0;
        else return minIndex + 1;
    }
    
    public int canCompleteCircuitOriginal(int[] gas, int[] cost) {
    	if (gas == null || cost == null || gas.length != cost.length || gas.length == 0)
    		return -1;
    	
        int numStations = gas.length;
        Map<Integer, Integer> positiveStationMap = new TreeMap<Integer, Integer>();
        List<Integer> stationList = new ArrayList<Integer>();
        int subAggregate = 0;		// aggregate of previous stations whose each net was negative
        
        int aggregate = 0;
        for (int i=numStations-1; i>=0; i--) {
        	int diff = gas[i] - cost[i];
        	aggregate += diff;
        	if (diff + subAggregate >= 0) {
        		positiveStationMap.put(i, diff + subAggregate);
        		stationList.add(i);
        		subAggregate = 0;
        	}
        	else {
        		subAggregate += diff;
        	}
        }
        
        if (aggregate < 0)
        	return -1;
        
        if (subAggregate < 0) {
        	positiveStationMap.put(stationList.get(0), positiveStationMap.get(stationList.get(0)) + subAggregate);
        }
        
        for (int start=stationList.size()-1; start>=0; start--) {
 	        int balance = 0;
 	        int i = start;
        	while (true) {
        		balance += positiveStationMap.get(stationList.get(i));
         		if (balance < 0)
        			break;
         		int nextStationIndex = i-1;
         		if (nextStationIndex < 0) {
         			nextStationIndex = stationList.size() - 1;
         		}
            	if (nextStationIndex == start)
            		return stationList.get(start);
            	i = nextStationIndex;
        	}
        }
        return -1;
    }
    
	public static void main(String[] args) {
		Solution sol = new Solution();
		int[] gas;
		int[] cost;
		
		gas = new int[]{9,5,5,5,5};
		cost = new int[]{1,10,1,8,9};
		System.out.println(sol.canCompleteCircuit(gas, cost) == 0);
		
		gas = new int[]{67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66};
		cost = new int[]{27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
		System.out.println(sol.canCompleteCircuit(gas, cost) == 74);
		
		gas = new int[]{1,2,3,4,5};
		cost = new int[]{3,4,5,1,2};
		System.out.println(sol.canCompleteCircuit(gas, cost) == 3);
		
		gas = new int[]{2};
		cost = new int[]{2};
		System.out.println(sol.canCompleteCircuit(gas, cost) == 0);
		
		gas = new int[]{0, 4, 20};
		cost = new int[]{0, 10, 5};
		System.out.println(sol.canCompleteCircuit(gas, cost) == 2);
		
		gas = new int[]{2, 0, 0};
		cost = new int[]{1, 1, 1};
		System.out.println(sol.canCompleteCircuit(gas, cost) == -1);
		
		gas = new int[]{1};
		cost = new int[]{2};
		System.out.println(sol.canCompleteCircuit(gas, cost) == -1);
		
		gas = new int[]{1};
		cost = new int[]{0};
		System.out.println(sol.canCompleteCircuit(gas, cost) == 0);

		gas = new int[]{};
		cost = new int[]{};
		System.out.println(sol.canCompleteCircuit(gas, cost) == -1);
		
	}

}
