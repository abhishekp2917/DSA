import java.util.HashMap;

class LFUCache {

    // Maps each key to its current frequency.
    // This lets us determine which frequency bucket contains the key.
    HashMap<Integer, Integer> keyMap;

    // Maps each frequency to an LRU cache containing all keys
    // that currently have that frequency.
    // LRU ordering is needed because when multiple keys have the
    // same minimum frequency, the least recently used one is evicted.
    HashMap<Integer, LRUCache> freqMap;

    // Tracks the minimum frequency currently present in the cache.
    // This lets us find the LFU bucket in O(1).
    int capacity, minFreq;

    // Shared size across all frequency-specific LRU caches.
    // An array is used so that all LRUCache objects can update
    // the same size value.
    int[] size;

    public LFUCache(int capacity) {
        this.keyMap = new HashMap<Integer, Integer>();
        this.freqMap = new HashMap<Integer, LRUCache>();
        this.capacity = capacity;
        this.size = new int[1];
        this.minFreq = Integer.MAX_VALUE;
    }

    public int get(int key) {

        // A missing key is not present in keyMap.
        if(keyMap.containsKey(key)) {

            // First retrieve the value from the LRU cache corresponding
            // to the key's current frequency.
            int value = freqMap.get(keyMap.get(key)).map.get(key).val;

            // Accessing a key increases its frequency, so remove it
            // from its current frequency bucket first.
            int freq = removeNode(key);

            // Reinsert the key into the next frequency bucket.
            // addNode() also places it at the head of that bucket's
            // LRU list, making it the most recently used key at that frequency.
            addNode(key, value, freq+1);

            // If the old minimum-frequency bucket became empty,
            // the minimum frequency must move to the new frequency.
            if(minFreq==freq && !freqMap.containsKey(freq))
                minFreq = freq+1;

            return value;
        }
        else return -1;
    }

    public void put(int key, int value) {

        // Updating an existing key also counts as using it,
        // so its frequency must increase by one.
        if(keyMap.containsKey(key)) {
            int freq = removeNode(key);
            addNode(key, value, freq+1);

            // If the old minimum-frequency bucket became empty,
            // update minFreq to the new frequency.
            if(minFreq==freq && !freqMap.containsKey(freq))
                minFreq = freq+1;
        }
        else {

            // If the cache is full, remove one key before inserting
            // the new key.
            if(size[0]==capacity) {

                // Among all keys, minFreq identifies the LFU bucket.
                // Within that bucket, rear is the least recently used key,
                // so it is the correct key to evict.
                removeNode(freqMap.get(minFreq).rear.key);

                // New keys always start with frequency 1.
                addNode(key, value, 1);

                // Since the newly inserted key has frequency 1,
                // the minimum frequency becomes 1.
                minFreq = 1;
            }
            else {
                // There is still capacity, so simply insert the new key
                // with its initial frequency of 1.
                addNode(key, value, 1);
            }
        }
    }

    private int removeNode(int key) {

        // Find the frequency bucket containing the key.
        int freq = keyMap.get(key);

        // Remove the key from the frequency map first.
        keyMap.remove(key);

        LRUCache cache = freqMap.get(freq);

        // Remove the key from the LRU list belonging to this frequency.
        cache.removeNode(key);

        // If this frequency bucket becomes empty, remove the bucket itself.
        // This is important because minFreq relies on the existence of
        // frequency buckets.
        if(cache.map.size()==0) {
            freqMap.remove(freq);
        }

        return freq;
    }

    private void addNode(int key, int value, int freq) {

        // Record the key's new frequency.
        keyMap.put(key, freq);

        LRUCache cache = null;

        // Reuse the existing LRU cache if this frequency already exists.
        if(freqMap.containsKey(freq)) cache = freqMap.get(freq);
        else {
            // Otherwise create a new LRU cache representing this frequency.
            cache = new LRUCache(size);
            freqMap.put(freq, cache);
        }

        // Insert at the head because this key is now the most recently
        // used key within this frequency bucket.
        cache.put(key, value);

        // A newly inserted/lifted key can become the new minimum frequency.
        if(minFreq>freq) minFreq = freq;
    }
}


// LRU cache used for each individual frequency.
// It maintains the keys having the same frequency in recency order.
class LRUCache {

    // head = most recently used, rear = least recently used.
    Node head, rear;

    // Provides O(1) access to a node so that it can be removed
    // from the doubly linked list without searching.
    HashMap<Integer, Node> map;

    // Shared with LFUCache so that all frequency buckets contribute
    // to one global cache size.
    int[] size;

    public LRUCache(int[] size) {
        this.map = new HashMap<Integer, Node>();
        this.size = size;
    }

    public int get(int key) {

        // If the key exists, accessing it makes it the most recently
        // used key within this frequency bucket.
        if(map.containsKey(key)) {
            Node temp = map.get(key);

            // Remove it from its current position.
            removeNode(key);

            // Reinsert it at the head to mark it as most recently used.
            addNodeAtHead(key, temp);

            return temp.val;
        }
        else return -1;
    }

    public void put(int key, int value) {

        // Updating an existing key should make it the most recently
        // used key within this frequency bucket.
        if(map.containsKey(key)) {
            Node temp = map.get(key);

            // Remove the old node before inserting the updated value.
            removeNode(key);

            temp = new Node(key, value);
            addNodeAtHead(key, temp);
        }
        else {
            // A new key is inserted at the head because it is
            // the most recently used key.
            Node temp = new Node(key, value);
            addNodeAtHead(key, temp);
        }
    }

    public void removeNode(int key) {

        // HashMap gives direct access to the node that needs to be removed.
        Node node = map.get(key);
        Node prev = node.prev;
        Node next = node.next;

        // Removing the only node makes both head and rear null.
        if(head==rear) {
            head = null;
            rear = null;
        }

        // Removing the rear requires moving rear one position backward.
        else if(node==rear) {
            rear = rear.prev;
        }

        // Removing the head requires moving head one position forward.
        else if(node==head) {
            head = head.next;
        }

        // Connect the surrounding nodes so that node is removed
        // from the doubly linked list.
        if(prev!=null) prev.next = next;
        if(next!=null) next.prev = prev;

        // Clear the removed node's links to fully detach it.
        node.prev = null;
        node.next = null;

        // Remove the node from O(1) lookup map.
        map.remove(key);

        // Decrease the global cache size because one key was removed.
        size[0]--;
    }

    private void addNodeAtHead(int key, Node node) {

        // If this is the first node, it is both the most and
        // least recently used node.
        if(map.size()==0) {
            head = node;
            rear = node;
        }
        else {

            // Insert the new node before the current head.
            node.next = head;

            // The old head now has the new node as its previous node.
            if(head!=null) head.prev = node;

            // The new node becomes the most recently used node.
            head = node;
        }

        // Add the node to the O(1) lookup map.
        map.put(key, node);

        // Increase the global cache size.
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