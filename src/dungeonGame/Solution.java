package dungeonGame;

import java.util.ArrayList;
import java.util.List;

/*
 * 
The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon. 
The dungeon consists of M x N rooms laid out in a 2D grid. 
Our valiant knight (K) was initially positioned in the top-left room and must fight his way 
through the dungeon to rescue the princess.

The knight has an initial health point represented by a positive integer. 
If at any point his health point drops to 0 or below, he dies immediately.

Some of the rooms are guarded by demons, so the knight loses health (negative integers) 
upon entering these rooms; other rooms are either empty (0's) 
or contain magic orbs that increase the knight's health (positive integers).

In order to reach the princess as quickly as possible, 
the knight decides to move only rightward or downward in each step.


Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.

For example, given the dungeon below, 
the initial health of the knight must be at least 7 if he follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.

-2 (K)	-3	3
-5	-10	1
10	30	-5 (P)

Notes:

The knight's health has no upper bound.
Any room can contain threats or power-ups, 
even the first room the knight enters and the bottom-right room where the princess is imprisoned.

Observation: 

do depth first search. If found a path where delta >=0, then finished,
since health at start >0, so ideal min health = 1.

choose to move toward higher dungeon value first

ensure at each spot, health > 0


key: 
	start from the endspot, determine the minimum health needed to enter there.
	at each spot, there is only 2 possible moves. find minimum health needed there to move to the next best spot.
	recursion takes too long and too resource intensive. Use inner loop instead.

previous attempt:

startHealth can only get higher as one progress. 
If we found a solution with startHealth ss, then any attempt with current startHealth sc where sc >= ss can be terminated.
 */

public class Solution {

	int[][] curMaze;
	int maxRow, maxCol;
	int minStartHealth;
	int curBalance;
	boolean foundPath;


    public int calculateMinimumHP(int[][] dungeon) {
    	if (dungeon == null)
    		return 1;
    	int[][] curMaze = dungeon;
        int maxRow = curMaze.length - 1;
        if (maxRow == -1)
        	return 1;
        int maxCol = curMaze[0].length - 1;
        if (maxCol == -1)
        	return 1;

        
    	curMaze[maxRow][maxCol] = curMaze[maxRow][maxCol] >= 0 ? 0 : -curMaze[maxRow][maxCol];
    	// bottom row
    	for (int col=maxCol-1; col>=0; col--) {
    		curMaze[maxRow][col] = curMaze[maxRow][col] >= curMaze[maxRow][col+1] ? 0:curMaze[maxRow][col+1]-curMaze[maxRow][col];    		
    	}
    	// right column
    	for (int row=maxRow-1; row>=0; row--) {
    		curMaze[row][maxCol] = curMaze[row][maxCol] >= curMaze[row+1][maxCol] ? 0:curMaze[row+1][maxCol]-curMaze[row][maxCol];    		
    	}
    	// others
    	for (int row = maxRow-1; row>=0; row--) {
    		for (int col=maxCol-1; col>=0; col--) {
    			int minHealth = curMaze[row][col+1] < curMaze[row+1][col] ? curMaze[row][col+1] : curMaze[row+1][col];
        		curMaze[row][col] = curMaze[row][col] >= minHealth ? 0 : minHealth-curMaze[row][col];    		
    		}
    	}
    	return curMaze[0][0]+1;
    }  	
		    
    public int calculateMinimumHP_Old(int[][] dungeon) {
    	if (dungeon == null)
    		return 1;
        curMaze = dungeon;
        maxRow = curMaze.length - 1;
        if (maxRow == -1)
        	return 1;
        maxCol = curMaze[0].length - 1;
        if (maxCol == -1)
        	return 1;

        minStartHealth = Integer.MAX_VALUE;
        foundPath = false;
        
        traverseTo(0,0,1,1);
        return minStartHealth;
    }
    
    // returns the deficit needed to add to initial balance
    private void traverseTo(int r, int c, int startHealth, int balance) {
    	if (startHealth >= minStartHealth)
    		return;
    	
    	int newBalance = balance + curMaze[r][c];
    	if (newBalance < 1) {
    		int healthNeeded = 1 - curMaze[r][c];
    		newBalance = 1;
    		startHealth += healthNeeded - balance;
    	}
    	if (startHealth > minStartHealth)
    		return;
    	
    	if (r==maxRow && c==maxCol) {
			minStartHealth = startHealth;
			return;
    	}

    	if (c == maxCol)
    		traverseTo(r+1, c, startHealth, newBalance);
    	else if (r == maxRow)
    		traverseTo(r, c+1, startHealth, newBalance);    		
    	else if (curMaze[r+1][c] > curMaze[r][c+1]) {
    		traverseTo(r+1, c, startHealth, newBalance);
    		traverseTo(r, c+1, startHealth, newBalance);
    	}
    	else {
    		traverseTo(r, c+1, startHealth, newBalance);
    		traverseTo(r+1, c, startHealth, newBalance);
    	}
    	return ;
    }
    
    public static void main(String[] args) {
		Solution sol = new Solution();
		int[][] maze;
				
		maze = new int[][]{{0,-51,-51,51},{-50,-1000,-1000,-51},{-150,-1000,-1000,51},{1000,-100,-100,0}};		
		System.out.println(sol.calculateMinimumHP(maze) == 103);

		maze = new int[][]{{3, 0, -3},{-3, -2, -2},{3, 1, -3}};		
		System.out.println(sol.calculateMinimumHP(maze) == 1);

		maze = new int[][]{{1, -3, 3},{0, -2, 0},{-3, -3, -3}};		
		System.out.println(sol.calculateMinimumHP(maze) == 3);

		maze = new int[][]{{-2, -3, 3},{-5, -10, 1},{10, 30, -5}};		
		System.out.println(sol.calculateMinimumHP(maze) == 7);

		maze = new int[][]{{-3,5}};		
		System.out.println(sol.calculateMinimumHP(maze) == 4);

		maze = new int[][]{{2, 3, 3},{5, 10, 1},{10, 30, 5}};		
		System.out.println(sol.calculateMinimumHP(maze) == 1);
		
		maze = new int[][]{{-2, -3, -3},{-5, -10, -1}};		
		System.out.println(sol.calculateMinimumHP(maze) == 10);
		
		maze = new int[][]{{0}};		
		System.out.println(sol.calculateMinimumHP(maze) == 1);
		
		maze = new int[][]{{}};		
		System.out.println(sol.calculateMinimumHP(maze) == 1);
		
		maze = new int[][]{{0, 0, 0},{0, 0, 0}};		
		System.out.println(sol.calculateMinimumHP(maze) == 1);
	}

}
