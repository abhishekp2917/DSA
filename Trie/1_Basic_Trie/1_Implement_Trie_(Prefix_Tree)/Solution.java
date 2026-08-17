class Trie {

    // Root node of the Trie.
    // It does not represent any character itself; it is the starting point
    // from which all inserted words can be traversed.
    PrefixTree prefixTree;

    public Trie() {
        prefixTree = new PrefixTree();
    }

    public void insert(String word) {

        // Start from the root and move through one Trie node per character.
        PrefixTree root = prefixTree;

        for(char ch : word.toCharArray()) {
            int idx = ch-'a';

            // If the current character's path does not exist,
            // create a new node so that this character becomes part of the Trie.
            if(root.map[idx]==null) root.map[ch-'a'] = new PrefixTree();

            // Move to the node representing the current character.
            root = root.map[idx];
        }

        // Mark the final node because reaching this node means that
        // the complete word has been inserted.
        // This is necessary because one word can be a prefix of another.
        root.isLast = true;
    }

    public boolean search(String word) {

        // Start from the root and try to follow the complete word character by character.
        PrefixTree root = prefixTree;

        for(char ch : word.toCharArray()) {
            int idx = ch-'a';

            // If the required character path does not exist,
            // the word cannot have been inserted.
            if(root.map[idx]==null) return false;

            // Move to the node corresponding to the current character.
            root = root.map[idx];
        }

        // The path may exist without the complete word being inserted.
        // Therefore, check whether this node was explicitly marked as a word's end.
        return root.isLast;
    }

    public boolean startsWith(String prefix) {

        // Start from the root and traverse the prefix.
        PrefixTree root = prefixTree;

        for(char ch : prefix.toCharArray()) {
            int idx = ch-'a';

            // If any character in the prefix is missing,
            // no inserted word can start with this prefix.
            if(root.map[idx]==null) return false;

            // Move to the node corresponding to the current character.
            root = root.map[idx];
        }

        // Reaching the end of the prefix means the complete prefix path exists.
        // We do not need isLast because the prefix itself does not have to be a word.
        return true;
    }
}

class PrefixTree {

    // Each index represents one lowercase English character:
    // index 0 = 'a', index 1 = 'b', ..., index 25 = 'z'.
    PrefixTree[] map;

    // Marks whether a complete inserted word ends at this node.
    // A node can simultaneously be the end of one word and a prefix of another.
    boolean isLast;

    PrefixTree() {
        map = new PrefixTree[26];
    }
}