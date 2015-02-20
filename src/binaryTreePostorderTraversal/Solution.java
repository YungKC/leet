package binaryTreePostorderTraversal;

/*
 * 
Given a binary tree, return the postorder traversal of its nodes' values.

For example:
Given binary tree {1,#,2,3},
   1
    \
     2
    /
   3
return [3,2,1].

Note: Recursive solution is trivial, could you do it iteratively?

 */
import java.util.ArrayList;
import java.util.List;

public class Solution {

    List<Integer> result;
    
    public List<Integer> postorderTraversal(TreeNode root) {
    	result = new ArrayList<Integer>();
    	List<TreeNode> nodeList = new ArrayList<TreeNode>();
    	boolean isPopped = false;
    	boolean hasWalkRightSide = false;
    	TreeNode curNode = root;
    	while (curNode != null) {
    		if (curNode.left == null && curNode.right == null) {
    			isPopped = false;
    			result.add(curNode.val);
    			if (nodeList.size() > 0) {
    				TreeNode lastCurNode = curNode;
    				curNode = nodeList.get(nodeList.size()-1);
    				if (curNode.right != null) {
    					if (curNode.right != lastCurNode) {
    						curNode = curNode.right;
    					}
    					else {
    						result.add(curNode.val);
    						curNode = nodeList.remove(nodeList.size()-1);
    						isPopped = true;
    						curNode = nodeList.get(nodeList.size()-1);
    						if (curNode == root) {
    							if (!hasWalkRightSide)
    								hasWalkRightSide = true;
    							else {
    								result.add(curNode.val);
    								break;
    							}
    						}
    					}
    				}
    				else {
						result.add(curNode.val);
						curNode = nodeList.remove(nodeList.size()-1);
						curNode = nodeList.get(nodeList.size()-1);
						isPopped = true;    					
    				}
    			}
    			else curNode = null;
    		}
    		else if (curNode.left != null && !isPopped) {
    			nodeList.add(curNode);
    			curNode = curNode.left;
    			isPopped = false;
    		}
    		else {
    			if (!isPopped)
    				nodeList.add(curNode);
    			curNode = curNode.right;
    			isPopped = false;
    		}

    	} 
        return result;
    }
    
    public List<Integer> postorderTraversalRecurse(TreeNode root) {
    	result = new ArrayList<Integer>();
        recurse(root);
        return result;
    }
    
    private void recurse(TreeNode node) {
    	if (node == null)
    		return;
    	recurse(node.left);
    	recurse(node.right);
    	result.add(node.val);
    }

	public static void main(String[] args) {
		Solution sol = new Solution();
		TreeNode node;
		
		node = new TreeNode(1);
		node.left = new TreeNode(2);
		node.right = new TreeNode(3);
		node.left.left = new TreeNode(4);
		node.left.right = new TreeNode(5);
		node.right.left = new TreeNode(6);
		node.right.right = new TreeNode(7);		
		printResult(sol.postorderTraversalRecurse(node));
		printResult(sol.postorderTraversal(node));
		
		node = new TreeNode(1);
		printResult(sol.postorderTraversalRecurse(node));
		printResult(sol.postorderTraversal(node));

		node = new TreeNode(1);
		node.right = new TreeNode(2);
		node.right.left = new TreeNode(3);
		printResult(sol.postorderTraversalRecurse(node));
		printResult(sol.postorderTraversal(node));

	}

	private static void printResult(List<Integer> list) {
		for (int value:list) {
			System.out.print(value + " ");
		}
		System.out.println();
	}

}
