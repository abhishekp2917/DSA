class Solution {

    // Root node of the Trie.
    // The root itself does not represent any character.
    PrefixTree prefixTree;

    public Solution() {
        prefixTree = new PrefixTree();
    }

    public void insert(String word) {

        // Traverse the Trie while inserting the word character by character.
        PrefixTree root = prefixTree;

        for(char ch : word.toCharArray()) {
            int idx = ch-'a';

            // Create the path for this character if it does not already exist.
            if(root.map[idx]==null) root.map[idx] = new PrefixTree();

            // Move to the node representing the current character.
            root = root.map[idx];

            // Every inserted word passing through this node contributes
            // to the number of words having this prefix.
            root.prefixCount++;
        }

        // Count how many times this exact word has been inserted.
        // This allows duplicate words to be stored and erased independently.
        root.endCount++;
    }

    public int countWordsEqualTo(String word) {

        // Traverse the complete word.
        PrefixTree root = prefixTree;

        for(char ch : word.toCharArray()) {
            int idx = ch-'a';

            // If any character path is missing, the word was never inserted.
            if(root.map[idx] == null) return 0;

            root = root.map[idx];
        }

        // endCount represents the number of times this exact word was inserted.
        return root.endCount;
    }

    public int countWordsStartingWith(String word) {

        // Traverse the given prefix.
        PrefixTree root = prefixTree;

        for(char ch : word.toCharArray()) {
            int idx = ch-'a';

            // If the prefix path does not exist, no word can start with it.
            if(root.map[idx] == null) return 0;

            root = root.map[idx];
        }

        // Every word having this prefix passes through this node.
        return root.prefixCount;
    }

    public void erase(String word) {

        int n = word.length();

        // Store every node belonging to the word's path so that after
        // verifying the word exists, we can decrease prefixCount on all
        // nodes traversed by this word.
        PrefixTree[] path = new PrefixTree[n];

        PrefixTree root = prefixTree;

        for(int i=0; i<n; i++) {
            int idx = word.charAt(i)-'a';

            // The word does not exist in the Trie, so there is nothing to erase.
            if(root.map[idx] == null) return;

            root = root.map[idx];
            path[i] = root;
        }

        // The path may exist because another longer word has the current
        // word as a prefix, so checking endCount is necessary.
        if(root.endCount==0) return;

        // Remove one occurrence of the exact word.
        root.endCount--;

        // Since one occurrence of this word no longer passes through
        // these nodes, decrease their prefix counts by one.
        for(PrefixTree node : path) node.prefixCount--;
    }
}

class PrefixTree {

    // Child nodes for the 26 lowercase English characters.
    PrefixTree[] map;

    // Number of times a complete word ends at this node.
    int endCount;

    // Number of inserted words whose prefix includes this node.
    int prefixCount;

    PrefixTree() {
        map = new PrefixTree[26];
    }
}