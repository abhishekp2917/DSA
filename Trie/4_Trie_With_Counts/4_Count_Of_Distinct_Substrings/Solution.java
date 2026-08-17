class Solution {
    public static int countSubs(String s) {
        int n = s.length();
        int count = 0;
        PrefixTree prefixTree = new PrefixTree();

        // Start a Trie traversal from every position because every suffix
        // can generate substrings starting at that position.
        for(int start=0; start<n; start++) {
            PrefixTree root = prefixTree;

            // Extend the substring one character at a time.
            // Every Trie path from this starting position represents a substring.
            for(int i=start; i<n; i++) {
                int idx = s.charAt(i)-'a';

                // If this character path does not already exist, we have found
                // a new distinct substring, so create the node and increment count.
                if(root.map[idx]==null) {
                    root.map[idx] = new PrefixTree();
                    count++;
                }

                // Move to the node representing the current substring.
                // The path from the root to this node represents s[start...i].
                root = root.map[idx];
            }
        }

        // Every newly created Trie node corresponds to exactly one
        // distinct non-empty substring.
        return count;
    }
}


class PrefixTree {

    // Each child represents one possible next character.
    PrefixTree[] map;

    PrefixTree() {
        map = new PrefixTree[26];
    }
}