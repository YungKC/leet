import java.util.List;
import java.util.ArrayList;

/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

public class BSTIterator {

	TreeNode minParent = null;
	TreeNode rootNode = null;
	List<TreeNode> leftMostList = null;

    public BSTIterator(TreeNode root) {
    	rootNode = root;
    	leftMostList = new ArrayList<TreeNode>();
    	TreeNode curNode = root;
    	if (root != null)
            leftMostList.add(root);
    	while (curNode != null && curNode.left != null) {
    		curNode = curNode.left;
    		leftMostList.add(curNode);
    	}
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return leftMostList.size() != 0;
    }

    /** @return the next smallest number */
    public int next() {
        int size = leftMostList.size();
        TreeNode returnNode = leftMostList.remove(size-1);
        if (returnNode.right != null) {
	    	TreeNode curNode = returnNode.right;
	    	leftMostList.add(curNode);
	    	while (curNode != null && curNode.left != null) {
	    		curNode = curNode.left;
	    		leftMostList.add(curNode);
	    	}
        }
        return returnNode.val;
    }

    public static void main(String[] argv) {
    	TreeNode node = new TreeNode(10);
    	TreeNode leftNode = new TreeNode(4);
    	TreeNode lleftNode = new TreeNode(1);
    	TreeNode lRightNode = new TreeNode(6);
    	leftNode.left = lleftNode;
    	leftNode.right = lRightNode;
    	TreeNode rightNode = new TreeNode(14);
    	node.left = leftNode;
    	node.right = rightNode;

    	BSTIterator it = new BSTIterator(node);
    	while(it.hasNext()) {
    		System.out.println(it.next());
    	}
    }
 }

/**
 * Your BSTIterator will be called like this:
 * BSTIterator i = new BSTIterator(root);
 * while (i.hasNext()) v[f()] = i.next();
 */