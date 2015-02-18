package binaryTreePreorderTraversal;

import java.util.ArrayList;
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
	private List<Integer> result;
    public List<Integer> preorderTraversal(TreeNode root) {
        result = new ArrayList<Integer>();
    	if (root == null)
    		return result;
        getPreorderRecursive(root);
        return result;
    }
    
    private void getPreorderRecursive(TreeNode root) {
    	result.add(root.val);
    	if (root.left != null)
    		getPreorderRecursive(root.left);
    	if (root.right != null)
    		getPreorderRecursive(root.right);
    }
    
    public static void main(String[] argv) {
    	Solution sol = new Solution();
    	
    	TreeNode node = new TreeNode(1);
    	TreeNode root = node;
    	node.right = new TreeNode(2);
    	node = node.right;
    	node.left = new TreeNode(3);
    	node.right = new TreeNode(4);
    	
    	List<Integer> answer = sol.preorderTraversal(root);
    	for (int val : answer)
    		System.out.print(val + " ");
    	System.out.println();
    }
}