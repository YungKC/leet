package populateNextRightPointersII;

/*
 * 
 Follow up for problem "Populating Next Right Pointers in Each Node".

What if the given tree could be any binary tree? Would your previous solution still work?

Note:

You may only use constant extra space.
For example,
Given the following binary tree,
         1
       /  \
      2    3
     / \    \
    4   5    7
    
After calling your function, the tree should look like:
         1 -> NULL
       /  \
      2 -> 3 -> NULL
     / \    \
    4-> 5 -> 7 -> NULL
    
Observations:

need constant space, so no recursions
use the node.next pointer to walk from far left to far right node in a level

 */
public class Solution {

    public void connect(TreeLinkNode root) {
        if (root == null)
        	return;
        
        TreeLinkNode parent = root;
        while (parent.left != null || parent.right != null) {
            TreeLinkNode lastNodeToLinkFrom = null;
            TreeLinkNode firstNodeInCurrentLevel = null;
	        do {
	        	if (parent.left != null) {
		        	if (lastNodeToLinkFrom != null)
		        		lastNodeToLinkFrom.next = parent.left;
		        	else
		        		firstNodeInCurrentLevel = parent.left;
		        	if (parent.right != null) {
		        		parent.left.next = parent.right;
			        	lastNodeToLinkFrom = parent.right;
		        	} else {
			        	lastNodeToLinkFrom = parent.left;
		        	}
	        	} else if (parent.right != null) {
		        	if (lastNodeToLinkFrom != null)
		        		lastNodeToLinkFrom.next = parent.right;
		        	else
		        		firstNodeInCurrentLevel = parent.right;
		        	lastNodeToLinkFrom = parent.right;	        		
	        	}
	        	parent = parent.next;
	        } while (parent != null);
	        parent = firstNodeInCurrentLevel;
        }
    }
    
    public static void main(String[] args) {
		Solution sol = new Solution();
		TreeLinkNode root;
		
		root = new TreeLinkNode(0);
		root.left = new TreeLinkNode(11);
		root.right = new TreeLinkNode(12);
		root.right.left = new TreeLinkNode(21);
		root.right.right = new TreeLinkNode(22);
		sol.connect(root);
		System.out.println(root);		
		
		root = new TreeLinkNode(0);
		root.left = new TreeLinkNode(11);
		root.right = new TreeLinkNode(12);
		root.left.left = new TreeLinkNode(21);
		root.left.right = new TreeLinkNode(22);
		root.right.left = new TreeLinkNode(23);
		root.right.right = new TreeLinkNode(24);
		root.left.left.left = new TreeLinkNode(31);
		root.left.left.right = new TreeLinkNode(32);
		root.left.right.left = new TreeLinkNode(33);
		root.left.right.right = new TreeLinkNode(34);
		root.right.left.left = new TreeLinkNode(35);
		root.right.left.right = new TreeLinkNode(36);
		root.left.left.left.left = new TreeLinkNode(41);
		root.left.left.left.right = new TreeLinkNode(42);
		sol.connect(root);
		System.out.println(root);
		
		
		root = new TreeLinkNode(0);
		root.left = new TreeLinkNode(1);
		root.right = new TreeLinkNode(2);
		root.left.left = new TreeLinkNode(3);
		root.left.right = new TreeLinkNode(4);
		root.right.right = new TreeLinkNode(5);
		sol.connect(root);
		System.out.println(root);

		root = new TreeLinkNode(0);
		sol.connect(root);
		System.out.println(root);
	}

}
