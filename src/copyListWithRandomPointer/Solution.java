package copyListWithRandomPointer;

import java.util.HashMap;
import java.util.Map;

/*
 * 
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.

observations:
- linked list can be circular
- create a map to retain an exact copy of the list

 */
public class Solution {

    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null)
        	return null;
        
        Map<RandomListNode, RandomListNode> sourceParent = new HashMap<RandomListNode, RandomListNode>();
        Map<RandomListNode, RandomListNode> sourceToClone = new HashMap<RandomListNode, RandomListNode>();

        RandomListNode source = head;
        RandomListNode result = new RandomListNode(source.label);
        sourceToClone.put(source, result);
        if (source.random != null) {
        	if (sourceToClone.get(source.random) == null) {
        		RandomListNode curRandom = new RandomListNode(source.random.label);
        		sourceToClone.put(source.random, curRandom);
        		
        	}
        }
        RandomListNode current = result;
        if (source.next != null) {
        	current.next = new RandomListNode(source.next.label);
        }
    }
    
	public static void main(String[] args) {

	}

}
