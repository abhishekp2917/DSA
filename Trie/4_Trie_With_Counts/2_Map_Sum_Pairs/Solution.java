import java.util.HashMap;
import java.util.Map;

class MapSum {

    // Stores the current value of every complete key.
    // This is required because inserting an existing key replaces its old value,
    // so we need the previous value to calculate the change in each prefix sum.
    Map<String, Integer> map;

    // Trie stores the cumulative sum of all keys passing through each prefix.
    Trie trie; 

    public MapSum() {
        map = new HashMap<>();
        trie = new Trie();
    }

    public void insert(String key, int val) {

        Trie root = trie;

        // If the key already exists, its old value must be removed from
        // every prefix sum before the new value is added.
        int prevVal = map.getOrDefault(key, 0);

        // Store the new value so that it can be used during a future update.
        map.put(key, val);

        for(char ch : key.toCharArray()) {
            int idx = ch-'a';

            // Create the Trie path if this character does not exist yet.
            if(root.map[idx]==null) root.map[idx] = new Trie();

            // Move to the node representing the current prefix.
            root = root.map[idx];

            // Replace the contribution of the old key value with the new value.
            // Using -= prevVal and += val also handles updating an existing key.
            root.sum -= prevVal;
            root.sum += val;
        }
    }

    public int sum(String prefix) {

        Trie root = trie;

        // Traverse the Trie to reach the node representing the requested prefix.
        for(char ch : prefix.toCharArray()) {
            int idx = ch-'a';

            // If the prefix does not exist, no key can start with it.
            if(root.map[idx]==null) return 0;

            root = root.map[idx];
        }

        // Every key having this prefix contributed its value to this node's sum.
        return root.sum;
    }
}


class Trie {

    // Each index represents one lowercase English character.
    Trie[] map;

    // Sum of values of all keys whose prefix reaches this node.
    int sum;

    Trie() {
        map = new Trie[26];
    }
}