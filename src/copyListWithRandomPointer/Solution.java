package copyListWithRandomPointer;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

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
        
        Set<RandomListNode> sourceNodes = new HashSet<RandomListNode>();
        Map<RandomListNode, RandomListNode> sourceToClone = new HashMap<RandomListNode, RandomListNode>();

        RandomListNode source = head;
        RandomListNode result = new RandomListNode(source.label);
        RandomListNode clonedNode = result;
        sourceNodes.add(source);
        sourceToClone.put(source, clonedNode);

        RandomListNode nextSource = source.next;
        RandomListNode circularNode = null;
        while (nextSource != null) {
            if (sourceNodes.contains(nextSource)) {
                clonedNode.next = sourceToClone.get(nextSource);
                circularNode = nextSource;
                break;
            }
            else {
                clonedNode.next = new RandomListNode(nextSource.label);
                clonedNode = clonedNode.next;
                sourceToClone.put(nextSource, clonedNode);
                sourceNodes.add(nextSource);
                nextSource = nextSource.next;
            }
        }

        source = head;
        clonedNode = result;
        do {
            if (source.random != null)
                clonedNode.random = sourceToClone.get(source.random);
            source = source.next;
            clonedNode = clonedNode.next;
        } while (source != circularNode);

        return result;
    }

	public static void main(String[] args) {

	}

}
