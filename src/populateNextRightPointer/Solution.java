package populateNextRightPointer;

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
 
Observation:

breath first search
constant space requirement means cannot use recursion.

 */
public class Solution {

    TreeLinkNode lastRightChild = null;

	public void connect(TreeLinkNode root) {
        if (root == null)
        	return;
        
        traverseTree(root);
    }
    
	private void traverseTree(TreeLinkNode root) {
		if (root.right != null) {
			root.right.next = lastRightChild;
			lastRightChild = root.right;
		}
		if (root.left != null) {
			root.left.next = lastRightChild;
			lastRightChild = root.left;
		}
		else {
			return;
		}
		if (root.right != null)
			traverseTree(root.right);
		traverseTree(root.left);
	}
	
    public static void main(String[] args) {
		Solution sol = new Solution();
		TreeLinkNode node = new TreeLinkNode(0);
		node.left = new TreeLinkNode(1);
		node.right = new TreeLinkNode(2);
		node.left.left = new TreeLinkNode(3);
		node.left.right = new TreeLinkNode(4);
		node.right.left = new TreeLinkNode(5);
		node.right.right = new TreeLinkNode(6);
		sol.connect(node);
		System.out.println(node);
		

	}

}
