class Solution {
    public int[] sumPrefixScores(String[] words) {

        int n = words.length;
        int[] sum = new int[n];
        Trie trie = new Trie();

        // First pass: build the Trie and count how many words
        // pass through every prefix node.
        for(String word : words) {
            Trie root = trie;

            for(char ch : word.toCharArray()) {
                int idx = ch-'a';

                // Create the Trie path if this character does not exist yet.
                if(root.map[idx]==null) root.map[idx] = new Trie();

                // Move to the node representing the current prefix.
                root = root.map[idx];

                // Every word passing through this node has this prefix,
                // so increment the number of words sharing this prefix.
                root.count++;
            }
        }

        // Second pass: for each word, traverse all of its prefixes
        // and add the number of words sharing each prefix.
        for(int i=0; i<n; i++) {
            String word = words[i];
            Trie root = trie;

            for(char ch : word.toCharArray()) {
                int idx = ch-'a';

                // The path must exist because this word was inserted earlier.
                if(root.map[idx]==null) break;

                root = root.map[idx];

                // root.count is exactly the number of words having
                // the current prefix, which is the score of this prefix.
                sum[i] += root.count;
            }
        }

        return sum;
    }
}


class Trie {

    // Each index represents one lowercase English character.
    Trie[] map;

    // Number of words having the prefix represented by this node.
    int count;

    Trie() {
        map = new Trie[26];
    }
}