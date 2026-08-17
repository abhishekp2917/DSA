import java.util.Arrays;

class Solution {
    public String longestWord(String[] words) {

        // Store all words in a Trie so that while processing a word,
        // we can efficiently verify whether every prefix is already a word.
        Trie trie = new Trie();

        String longestWord = "";

        // Process shorter words first so that when we encounter a longer word,
        // its shorter prefixes have already been inserted into the Trie.
        // This allows us to check the "every prefix exists" condition while inserting.
        Arrays.sort(words, (w1, w2) -> Integer.compare(w1.length(), w2.length()));

        for(String word : words) {
            int n = word.length();
            Trie root = trie;
            boolean hasAllPrefix = true;

            for(int i=0; i<n; i++) {
                int idx = word.charAt(i)-'a';

                // Create the Trie node if this character path does not exist.
                // Even if the prefix is not a complete word, we still insert
                // the path because a later word may use it as its prefix.
                if(root.map[idx]==null) root.map[idx] = new Trie(); 

                // Move to the node representing the current character.
                root = root.map[idx];

                // Every prefix before the complete word must itself be a word.
                // The final node is checked separately after the loop.
                if(!root.isEnd && i!=n-1) hasAllPrefix = false;
            }

            // Mark the complete word so that it can serve as a valid prefix
            // for longer words processed later.
            root.isEnd = true;

            // Update the answer only if every prefix exists as a complete word.
            //
            // Prefer:
            // 1. Longer words.
            // 2. Lexicographically smaller word when lengths are equal.
            if(hasAllPrefix && 
                (word.length()>longestWord.length() || 
                (word.length()==longestWord.length() && word.compareTo(longestWord)<0))) {
                longestWord = word;
            } 
        }

        return longestWord;
    }
}


class Trie {
    // Each index represents one lowercase English character.
    Trie[] map;

    // True means a complete word ends at this Trie node.
    // This is important because a Trie path existing does not necessarily
    // mean that the corresponding prefix is itself present in words.
    boolean isEnd;

    Trie() {
        map = new Trie[26];
    }
}