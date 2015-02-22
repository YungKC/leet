package lruCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 
Design and implement a data structure for Least Recently Used (LRU) cache. 
It should support the following operations: get and set.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, 
otherwise return -1.

set(key, value) - Set or insert the value if the key is not already present. 
When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

Idea:

Use double link list 

 */
public class LRUCache {
	
	class DoubleLinkListNode {
		int key;
		DoubleLinkListNode next;
		DoubleLinkListNode previous;
		public DoubleLinkListNode(int key) {
			this.key = key;
		}
	}
	
	class DoubleLinkList {
		DoubleLinkListNode head;
		DoubleLinkListNode tail;
		void addToTail(DoubleLinkListNode node) {
			if (tail == null) {
				head = node;
				tail = node;
				node.previous = null;
				node.next = null;
			}
			else {		// both cases of tail == head and tail != head
				tail.next = node;
				node.previous = tail;
				node.next = null;
				tail = node;
			}
		}
		void removeHead() {
			if (head != tail) {
				head = head.next;
				head.previous = null;
			}
			else {
				head = null;
				tail = null;
			}
		}
		void moveToTail(DoubleLinkListNode node) {
			if (node == tail) {
				return;
			}
			else if (node == head) {
				removeHead();
				addToTail(node);
			}
			else {
				node.previous.next = node.next;
				node.next.previous = node.previous;
				addToTail(node);
			}
		}
	}
	
	class ValueHolder {
		int value;
		DoubleLinkListNode node;
		public ValueHolder(int value, DoubleLinkListNode node) {
			this.value = value;
			this.node = node;
		}
	}
	
	int cap;
	Map<Integer, ValueHolder> map;
	DoubleLinkList cacheList;
	
    public LRUCache(int capacity) {
        cap = capacity;
        map = new HashMap<Integer, ValueHolder>(cap);
        cacheList = new DoubleLinkList();
    }
    
    public int get(int key) {
    	ValueHolder valueHolder = map.get(key);
        if (valueHolder == null) {
        	return -1;
        }
        cacheList.moveToTail(valueHolder.node);
        return valueHolder.value;
    }
    
    public void set(int key, int value) {
    	if (cap == 0)
    		return;
    	
    	if (map.keySet().contains(key)) {
    		ValueHolder valueHolder = map.get(key);
    		valueHolder.value = value;
    		return;
    	}
    	
    	DoubleLinkListNode node = new DoubleLinkListNode(key);
    	ValueHolder valueHolder = new ValueHolder(value, node);
    	if (map.size() == cap) {
    		map.remove(cacheList.head.key);
    		cacheList.removeHead();
    	}
    	map.put(key, valueHolder);
    	cacheList.addToTail(node);
    }
    
    public static void main(String[] args) {
		LRUCache cache;
		List<Integer> result;
				
		result = new ArrayList<Integer>();
		cache= new LRUCache(2);		
		cache.set(2, 1);
		cache.set(1, 1);
		cache.set(2, 3);
		cache.set(4, 1);
		result.add(cache.get(1));
		result.add(cache.get(2));
		printList(result);


		result = new ArrayList<Integer>();
		cache= new LRUCache(2);		
		cache.set(2, 1);
		cache.set(1, 1);
		result.add(cache.get(2));
		cache.set(4, 1);
		result.add(cache.get(1));
		result.add(cache.get(2));
		printList(result);

		cache= new LRUCache(1);		
		cache.set(1, 1);
		cache.set(2, 2);
		System.out.println(cache.get(1) == -1 && cache.get(2) == 2);

		cache= new LRUCache(0);		
		cache.set(1, 1);
		System.out.println(cache.get(1) == -1);

		cache= new LRUCache(4);		
		cache.set(1, 1);
		System.out.println(cache.get(1) == 1);
	}

	private static void printList(List<Integer> result) {
		for (int value : result)
			System.out.print(value + " ");
		System.out.println();
	}

}
