package populateNextRightPointers;

/*
 * 
 Given a binary tree

    struct TreeLinkNode {
      TreeLinkNode *left;
      TreeLinkNode *right;
      TreeLinkNode *next;
    }
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

Note:

You may only use constant extra space.
You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
For example,
Given the following perfect binary tree,
         1
       /  \
      2    3
     / \  / \
    4  5  6  7
After calling your function, the tree should look like:
         1 -> NULL
       /  \
      2 -> 3 -> NULL
     / \  / \
    4->5->6->7 -> NULL
    
Observations:

need constant space, so no recursions
use the node.next pointer to walk from far left to far right node in a level

 */
public class Solution {

    public void connect(TreeLinkNode root) {
        if (root == null)
        	return;
        
        TreeLinkNode parent = root;
        while (parent.left != null) {
            TreeLinkNode lastRightNode = null;
            TreeLinkNode leftMostNode = null;
	        do {
	        	if (lastRightNode != null)
	        		lastRightNode.next = parent.left;
	        	else
	        		leftMostNode = parent.left;
	        	if (parent.left != null) {
		        	parent.left.next = parent.right;
		        	lastRightNode = parent.right;
	        	}
	        	parent = parent.next;
	        } while (parent != null);
	        parent = leftMostNode;
        }
    }
    
    public static void main(String[] args) {
		Solution sol = new Solution();
		TreeLinkNode root;
		root = new TreeLinkNode(0);
		sol.connect(root);
		System.out.println(root);

		
		root = new TreeLinkNode(0);
		root.left = new TreeLinkNode(1);
		root.right = new TreeLinkNode(2);
		root.left.left = new TreeLinkNode(3);
		root.left.right = new TreeLinkNode(4);
		root.right.left = new TreeLinkNode(5);
		sol.connect(root);
		System.out.println(root);

	}

}
