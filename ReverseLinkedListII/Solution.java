/*
Reverse a linked list from position m to n. Do it in-place and in one-pass.

For example:
Given 1->2->3->4->5->NULL, m = 2 and n = 4,

return 1->4->3->2->5->NULL.

Note:
Given m, n satisfy the following condition:
1 ≤ m ≤ n ≤ length of list.
*/


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode reverseBetween(ListNode head, final int m, final int n) {
    	if (head == null || m >= n || m < 1) {
    		return head;
		}
    	ListNode topLast = null;
    	if (m > 1) {
    		topLast = head;
	        for (int i=2; i<m; i++) {
	        	topLast = head.next;
	        	if (topLast == null)
					return head;
	        }
	    }

 
 		ListNode reversedHead = topLast.next;
		ListNode reversedTail = reversedHead;
		ListNode nextNode = reversedHead==null?null:reversedHead.next;

        for (int i=m+1; i<=n && nextNode != null; i++) {
        	reversedTail.next = nextNode.next;
	       	nextNode.next = reversedHead;
        	reversedHead = nextNode;
        	nextNode = reversedTail.next;
//        	System.out.print("processing " + i + ": ");
//        	printNode(reversedHead);
        }

        topLast.next=reversedHead;

        return head;
    }

    private static ListNode constructNodes(final int[] data) {
    	ListNode result = new ListNode(data[0]);
    	ListNode lastFilledNode = result;
    	for (int i=1; i<data.length; i++) {
    		ListNode aNode = new ListNode(data[i]);
    		lastFilledNode.next = aNode;
    		lastFilledNode = aNode;
    	}
    	return result;
    }

    private static void printNode(final ListNode inNode) {
    	ListNode result = inNode;
	   	while (result != null) {
    		System.out.print(result.val + ", ");
    		result = result.next;
    	}
    	System.out.println();
    }

    public static void main(String argv[]) {
    	int[] intArray = {1,2,3,4,5,6,7,8,9,10};

    	ListNode testNode = constructNodes(intArray);
    	printNode(testNode);

    	ListNode result = new Solution().reverseBetween(testNode, 2, 5);

    	printNode(result);
    }
}

