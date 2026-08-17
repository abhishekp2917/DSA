class WordDictionary {

    // Root of the Trie.
    // All added words are stored as character-by-character paths from this node.
    PrefixTree prefixTree;

    public WordDictionary() {
        prefixTree = new PrefixTree();
    }

    public void addWord(String word) {

        // Start from the Trie root and create/follow one node for every character.
        PrefixTree root = prefixTree;

        for(char ch : word.toCharArray()) {
            int idx = ch-'a';

            // Create the character's node only if its path does not already exist.
            // Existing nodes are reused because multiple words can share prefixes.
            if(root.map[idx]==null) root.map[idx] = new PrefixTree();

            // Move to the node representing the current character.
            root = root.map[idx];
        }

        // Mark the final node so that we can distinguish a complete
        // inserted word from a path that is only a prefix of another word.
        root.isLast = true;
    }

    public boolean search(String word) {

        // Searching may require branching whenever '.' appears,
        // so use recursive search instead of following only one Trie path.
        return searchSufix(word, 0, prefixTree);
    }

    private boolean searchSufix(String word, int start, PrefixTree root) {

        // This Trie path does not exist, so this branch cannot match the word.
        if(root==null) return false;

        // All characters have been matched.
        // Return true only if the current node represents the end of
        // an inserted word, otherwise we have matched only a prefix.
        if(start==word.length()) return root.isLast;

        char ch = word.charAt(start);

        if(ch=='.') {

            // '.' can represent any lowercase character.
            // Therefore, try every existing child as a possible match
            // for the current position.
            for(PrefixTree node : root.map) {
                if(node==null) continue;

                // If even one child can match the remaining suffix,
                // the complete search pattern is valid.
                if(searchSufix(word, start+1, node)) return true;
            }
        }

        // For a normal character there is only one possible Trie path,
        // so directly continue through that character's child.
        else return searchSufix(word, start+1, root.map[ch-'a']);

        // Reaching here means '.' tried every possible child
        // but none could match the remaining suffix.
        return false;
    }
}

class PrefixTree {

    // map[i] represents the child corresponding to ('a' + i).
    PrefixTree[] map;

    // Marks whether a complete added word ends at this node.
    boolean isLast;

    PrefixTree() {
        this.map = new PrefixTree[26];
    }
}