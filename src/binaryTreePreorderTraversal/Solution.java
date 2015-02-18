package binaryTreePreorderTraversal;

import java.util.List;

/*
 * 
Given a binary tree, return the preorder traversal of its nodes' values.

For example:
Given binary tree {1,#,2,3},
   1
    \
     2
    /
   3
return [1,2,3].

Note:

preorder is a depth first search where node, left, right
postOrder is left, right, node
symetric is left, node, right
 */

public class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        
    }
    
    public static void main(String[] argv) {
    	Solution sol = new Solution();
    	
    	TreeNode node = new TreeNode(1);
    	TreeNode root = node;
    	node.right = new TreeNode(2);
    	node = node.right;
    	node.left = new TreeNode(3);
    	
    	sol.preorderTraversal(root);
    }
}