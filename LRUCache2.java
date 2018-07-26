import java.util.HashMap;
import java.util.Map;

public class LRUCache2 {
    private Map<Integer, Node> cacheMap = new HashMap<Integer, Node>();
    private Node start = null;
    private Node end = null;
    private int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
        Node node = cacheMap.get(key);
        
        if (node != null) {
            moveToFront(node);
            return node.getValue();
        } else {
            return -1;
        }
    }
    
    public void set(int key, int value) {
        if (cacheMap.containsKey(key)) {
             Node node = cacheMap.get(key);
             moveToFront(node);
             node.setValue(value);
             return;
        }
                
        if (cacheMap.size() == capacity) {
        	Node endNode = end;
            cacheMap.remove(endNode.key);
            remove(endNode);
        }
        
        Node node = new Node(key, value);
        add(node);
        cacheMap.put(key, node);
    }
    
    private void moveToFront(Node node) {
        remove(node);
        add(node);
    }
    
    // add a new node to front of list
    private void add(Node node) {
        if (start == null) {
            start = node;
            end = node;
        } else {
            node.next = start;
            start.prev = node;
            start = node;
        }
    }
    
    // remove given node
    private void remove(Node node) {
        // empty list
        if (start == null || node == null) {
            return;
        } else if (start == node) {
            // node is at start
            
            if (start == end) {
                // only 1 node
                start = end = null;
            } else {
                start = start.next;
                start.prev = null;
            }
        } else if (end == node) {
            // node is at end
            end = end.prev;
            end.next = null;
        } else {
            // remove in the middle;
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        node = null;
    }
}

class Node {
    int key;
    int value;
    Node next;
    Node prev;
    
    public Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.next = null;
        this.prev = null;
    }
    
    public int getKey() {
        return key;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}
