import java.util.HashMap;

class LFUCache {

    // map to store the key mapped to its freq in LFU cache
    HashMap<Integer, Integer> keyMap;
    // map to store the freq mapped to the LRU cache having that freq
    HashMap<Integer, LRUCache> freqMap;
    // minFreq to track the LRU cache having least freq
    int capacity, minFreq;
    // size object to track the size of the LFU cache
    int[] size;

    public LFUCache(int capacity) {
        this.keyMap = new HashMap<Integer, Integer>();
        this.freqMap = new HashMap<Integer, LRUCache>();
        this.capacity = capacity;
        this.size = new int[1];
        this.minFreq = Integer.MAX_VALUE;
    }
    
    public int get(int key) {
        if(keyMap.containsKey(key)) {
            int value = freqMap.get(keyMap.get(key)).map.get(key).val;
            int freq = removeNode(key);
            addNode(key, value, freq+1);
            if(minFreq==freq && !freqMap.containsKey(freq)) minFreq = freq+1;
            return value;
        }
        else return -1;
    }
    
    public void put(int key, int value) {
        if(keyMap.containsKey(key)) {
            int freq = removeNode(key);
            addNode(key, value, freq+1);
            if(minFreq==freq && !freqMap.containsKey(freq)) minFreq = freq+1;
        }
        else {
            if(size[0]==capacity) {
                System.out.println(minFreq + " " + key + " " + value);
                removeNode(freqMap.get(minFreq).rear.key);
                addNode(key, value, 1);
                minFreq = 1;
            }
            else {
                addNode(key, value, 1);
            }
        }
    }


    private int removeNode(int key) {
        int freq = keyMap.get(key);
        keyMap.remove(key);
        LRUCache cache = freqMap.get(freq);
        cache.removeNode(key);
        if(cache.map.size()==0) {
            freqMap.remove(freq);
        }
        return freq;
    }

    private void addNode(int key, int value, int freq) {
        keyMap.put(key, freq);
        LRUCache cache = null;
        if(freqMap.containsKey(freq)) cache = freqMap.get(freq);
        else {
            cache = new LRUCache(size);
            freqMap.put(freq, cache);
        }
        cache.put(key, value);
        if(minFreq>freq) minFreq = freq;
    }
}

// LRU cache to store the key-value
class LRUCache {

    Node head, rear;
    HashMap<Integer, Node> map;
    int[] size;
    public LRUCache(int[] size) {
        this.map = new HashMap<Integer, Node>();
        this.size = size;
    }
    
    public int get(int key) {
        if(map.containsKey(key)) {
            Node temp = map.get(key);
            removeNode(key);
            addNodeAtHead(key, temp);
            return temp.val;
        }
        else return -1;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node temp = map.get(key);
            removeNode(key);
            temp = new Node(key, value);
            addNodeAtHead(key, temp);
        }
        else {
            Node temp = new Node(key, value);
            addNodeAtHead(key, temp);
        }
    }

    public void removeNode(int key) {
        Node node = map.get(key);
        Node prev = node.prev;
        Node next = node.next;
        if(head==rear) {
            head = null;
            rear = null;
        }
        else if(node==rear) {
            rear = rear.prev;
        }
        else if(node==head) {
            head = head.next;
        }
        if(prev!=null) prev.next = next;
        if(next!=null) next.prev = prev;
        node.prev = null;
        node.next = null;
        map.remove(key);
        size[0]--;
    }

    private void addNodeAtHead(int key, Node node) {
        if(map.size()==0) {
            head = node;
            rear = node;
        }
        else {
            node.next = head;
            if(head!=null) head.prev = node;
            head = node;
        }
        map.put(key, node);
        size[0]++;
    }
}

class Node {
    int key, val;
    Node next, prev;
    Node(int key, int val) {
        this.key = key;
        this.val = val;
    } 
}
