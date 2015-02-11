package pathSum;

/**
 *
 * 

Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.

For example:
Given the below binary tree and sum = 22,
              5
             / \
            4   8
           /   / \
          11  13  4
         /  \      \
        7    2      1
return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.


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
    public boolean hasPathSum(TreeNode root, int sum) {
    	if (root == null)
    		return false;
    	return inner(root, sum);
    }
    
    private boolean inner(TreeNode root, int sum) {
    	if (root.left == null && root.right == null)
    		return (sum == root.val);

    	int diff = sum - root.val;
    	if (root.left != null && inner(root.left, diff))
    		return true;
    	return (root.right != null && inner(root.right, diff));
    }
    
    public static void main(String[] argv) {
    	TreeNode parent, left, right, next;
    	parent = new TreeNode(11);
    	left = new TreeNode(7);
    	right = new TreeNode(2);
    	parent.left = left;
    	parent.right = right;
    	next = new TreeNode(4);
    	next.left = parent;
    	parent = next;
    	next = new TreeNode(5);
    	next.left = parent;
    	parent = next;
    	System.out.println(new Solution().hasPathSum(parent, 22));
    	
    	parent = new TreeNode(1);
    	left = new TreeNode(2);
    	parent.left = left;
    	System.out.println(new Solution().hasPathSum(parent, 1));
    }
}