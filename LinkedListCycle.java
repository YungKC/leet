package leet;

/*
 * 
Given a linked list, determine if it has a cycle in it.

Follow up:
Can you solve it without using extra space?

 */

public class LinkedListCycle {

    public static boolean hasCycle(ListNode head) {
    	if (head == null) {
    		return false;
    	}
        ListNode rabbit = head.next;
        ListNode snail = head;
        
        for (;;) {
        	if (rabbit == snail)
        		return true;
	        if (snail == null || snail.next == null || rabbit == null || rabbit.next == null || rabbit.next.next == null)
	        	return false;

	        snail = snail.next;
	        rabbit = rabbit.next.next;
        }
    }

	public static void main(String[] args) {
		ListNode head = new ListNode(5);
		System.out.println(hasCycle(head));
	}

}
