import java.util.HashMap;  

class LRUCache
{
    static int capacity;
    static HashMap<Integer, Node> map;
    static Node head,tail;
    
    //Constructor for initializing the cache capacity with the given value.
    LRUCache(int cap)
    {
        capacity = cap;
        // maintaining map to retrieve key value in o(1) time for get operation
        map = new HashMap<Integer, Node>();
        // for keep track of LRU, creating a linkedlist whose head and tail will be kept as a reference
        // all the elements will be added between this head and tail node
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    //Function to return value corresponding to the key.
    public static int get(int key)
    {
        // if map contains the key, then we have to move that key node to the front of linkedlist
        if(map.containsKey(key)) {
            // getting the key node
            Node node = map.get(key);
            // removing the node from the linkedlist first and then inserting it to the front of linkedlist
            delete(node);
            insert(node);
            return node.data;
        }
        // if map doesn't contain the key, that means that key has been evicted so return -1 
        else return -1;
    }

    //Function for storing key-value pair.
    public static void set(int key, int value)
    {
        // if map already contains that node, then we have to move that key node to the front of linkedlist
        if(map.containsKey(key)) {
             // getting the key node
            Node node = map.get(key);
            // removing the node from the linkedlist first and then inserting it to the front of linkedlist
            delete(node);
            insert(new Node(key, value));
        }
        // if map doesn't contain the key
        else{
            // if cache capacity threshold reaches, then delete the last node of the linkedlist
            if(capacity==map.size()) delete(tail.prev);
            // at last insert the new node infront of the linkedlist
            insert(new Node(key, value));
        }
    }
    
    // helper function which will remove the node passed as argument from the linkedlist and will remove 
    // it from the map
    public static void delete(Node node) {
        Node prev = node.prev, next = node.next;
        prev.next = next;
        next.prev = prev;
        map.remove(node.key);
    }
    
    // helper function to add node in front of the linkedlist and will add it into the map
    public static void insert(Node node) {
        Node next = head.next;
        head.next = node;
        node.prev = head;
        node.next = next;
        next.prev = node;
        map.put(node.key, node);
    }
}

class Node {
    int data;
    int key;
    Node next;
    Node prev;
    Node(int key, int data) {
        this.key = key;
        this.data = data;
    }
}