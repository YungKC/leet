import java.util.HashSet;
import java.util.Set;

/*
 * 
Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

Follow up:
Can you solve it without using extra space?

 */

public class LinkedListCycle2 {

    public static ListNode firstInCycle(ListNode head) {
    	Set<ListNode> visitedNodes = new HashSet<ListNode>();
    	ListNode currentNode = head;
    	for (;;) {
	    	if (currentNode == null)
	    		return null;
	    	if (visitedNodes.contains(currentNode))
	    		return currentNode;
	    	else {
	    		visitedNodes.add(currentNode);
	    		currentNode = currentNode.next;
	    	}
    	}
    }

	public static void main(String[] args) {
		ListNode head = new ListNode(5);
		System.out.println(firstInCycle(head));
	}

}
