package cloneGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 
Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.


OJ's undirected graph serialization:
Nodes are labeled uniquely.

We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
As an example, consider the serialized graph {0,1,2#1,2#2,2}.

The graph has a total of three nodes, and therefore contains three parts as separated by #.

First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
Second node is labeled as 1. Connect node 1 to node 2.
Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
Visually, the graph looks like the following:

       1
      / \
     /   \
    0 --- 2
         / \
         \_/


Observation: 

a node can be recursively part of a graph. Need to identify and resolve this during cloning

 */
public class Solution {

	private Map<Integer, UndirectedGraphNode> clonedMap;
	
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
    	if (node == null)
    		return null;
    	
    	clonedMap = new HashMap<Integer, UndirectedGraphNode>();
    	
    	UndirectedGraphNode result = new UndirectedGraphNode(node.label);
    	clonedMap.put(node.label, result);
    	
    	cloneGraph(node, result);
    	
    	return result;
    }
    
    private void cloneGraph(UndirectedGraphNode originalRoot, UndirectedGraphNode clonedRoot) {
    	if (originalRoot.neighbors != null) {
        	List<UndirectedGraphNode> clonedNeighbors = new ArrayList<UndirectedGraphNode>(originalRoot.neighbors.size());
        	clonedRoot.neighbors = clonedNeighbors;
    		for (UndirectedGraphNode node:originalRoot.neighbors) {
    			UndirectedGraphNode neighbor = clonedMap.get(node.label);
    			if (neighbor == null) {
    				neighbor = new UndirectedGraphNode(node.label);
    				clonedMap.put(node.label, neighbor);
    				cloneGraph(node, neighbor);
    			}
				clonedNeighbors.add(neighbor);
    		}
    	}
    }
	public static void main(String[] args) {
		Solution sol = new Solution();
		UndirectedGraphNode root = new UndirectedGraphNode(0);
		List<UndirectedGraphNode> list = new ArrayList<UndirectedGraphNode>();
		list.add(root);
		root.neighbors = list;
		
		UndirectedGraphNode cloned = sol.cloneGraph(root);
		System.out.println(cloned);

	}

}
