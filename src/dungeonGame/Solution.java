package dungeonGame;

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

key: mark each spot with minHealthBalance. If entry with this spot is higher than this health, then stop since a lower value was already found

 */
public class Solution {

	int[][] curMaze;
	int maxRow, maxCol;
	int minHealth;
	int curBalance;
	boolean foundPath;
	int[][] maxHealthBalance;

    public int calculateMinimumHP(int[][] dungeon) {
    	if (dungeon == null)
    		return 1;
        curMaze = dungeon;
        maxRow = curMaze.length - 1;
        if (maxRow == -1)
        	return 1;
        maxCol = curMaze[0].length - 1;
        if (maxCol == -1)
        	return 1;
        maxHealthBalance = new int[maxRow+1][maxCol+1];
        for (int r=0; r<=maxRow; r++)
        	for (int c=0; c<=maxCol; c++)
        		maxHealthBalance[r][c] = Integer.MIN_VALUE;

        minHealth = Integer.MAX_VALUE;
        foundPath = false;
        traverseTo(0,0,1,1);
        return minHealth;
    }
    
    // returns the deficit needed to add to initial balance
    private boolean traverseTo(int r, int c, int startHealth, int balance) {
    	if (foundPath)
    		return false;
    	if (balance < maxHealthBalance[r][c])
    		return true;
    	
    	int newBalance = balance + curMaze[r][c];
    	if (newBalance < 1) {
    		int healthNeeded = 1 - curMaze[r][c];
    		newBalance = 1;
    		startHealth += healthNeeded - balance;
    	}
    	if (startHealth > minHealth)
    		return true;
    	
    	if (r==maxRow && c==maxCol) {
			minHealth = startHealth;
			if (minHealth == 1) {
				foundPath = true;
				return false;
    		}
			return true;
    	}

    	// Important: only set this if we are not at the final spot! Tricky!!!
    	maxHealthBalance[r][c] = balance;

    	boolean shouldContinue;
    	if (c == maxCol)
    		shouldContinue = traverseTo(r+1, c, startHealth, newBalance);
    	else if (r == maxRow)
    		shouldContinue = traverseTo(r, c+1, startHealth, newBalance);    		
    	else if (curMaze[r+1][c] > curMaze[r][c+1]) {
    		shouldContinue = traverseTo(r+1, c, startHealth, newBalance);
    		if (shouldContinue)
    			shouldContinue = traverseTo(r, c+1, startHealth, newBalance);
    	}
    	else {
    		shouldContinue = traverseTo(r, c+1, startHealth, newBalance);
    		if (shouldContinue)
    			shouldContinue = traverseTo(r+1, c, startHealth, newBalance);
    	}
    	return shouldContinue;
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
