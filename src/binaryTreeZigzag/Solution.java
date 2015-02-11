package binaryTreeZigzag;

import java.util.ArrayList;
import java.util.List;


/**
 * 
Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

For example:
Given binary tree {3,9,20,#,#,15,7},
    3
   / \
  9  20
    /  \
   15   7
return its zigzag level order traversal as:
[
  [3],
  [20,9],
  [15,7]
]
 * 
* Definition for binary tree
* public class TreeNode {
*     int val;
*     TreeNode left;
*     TreeNode right;
*     TreeNode(int x) { val = x; }
* }
*/
public class Solution {
	
	private List<List<Integer>> solution = new ArrayList<List<Integer>>();
	
	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		if (root == null)
			return solution;
		
		inner(root, 0);
		return solution;
	}
	
	private void inner(TreeNode parent, int level) {
		List<Integer> curLevelList;
		if (solution.size() == level) {
			curLevelList = new ArrayList<Integer>();
			solution.add(curLevelList);
		}
		else
			curLevelList = solution.get(level);
		
//		System.out.println("adding " + parent.val + " at level " + level);
		
		// Key idea here: the display order is determined how we insert data into array
		if (level%2 == 0)
			curLevelList.add(parent.val);
		else
			curLevelList.add(0, parent.val);
		
		int nextLevel = level+1;
		
		if (parent.left != null)
			inner(parent.left, nextLevel);
		if (parent.right != null)
			inner(parent.right, nextLevel);
	}
	
	public static void main(String[] argv) {
    	TreeNode parent = new TreeNode(1);
    	parent.left = new TreeNode(2);
    	parent.right = new TreeNode(3);
    	parent.left.left = new TreeNode(4);
    	parent.right.right = new TreeNode(5);
    	
    	System.out.println(new Solution().zigzagLevelOrder(parent));
	}
}