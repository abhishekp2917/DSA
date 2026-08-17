import java.util.Deque;
import java.util.ArrayDeque;

class StreamChecker {

    // Store every word in reverse order because queries arrive
    // from left to right, while we need to check whether the recent
    // stream suffix matches the beginning of any word.
    Trie trie;

    // Stores the stream characters with the newest character at the front.
    // This lets us traverse the most recent characters in the same order
    // as the reversed words stored in the Trie.
    Deque<Character> queue;

    public StreamChecker(String[] words) {
        trie = new Trie();
        queue = new ArrayDeque<>();

        for(String word : words) {
            int n = word.length();
            Trie root = trie;

            // Insert each word backwards because a query checks whether
            // the suffix of the stream forms a complete dictionary word.
            for(int i=n-1; i>=0; i--) {
                int idx = word.charAt(i)-'a';

                // Create the Trie path for this reversed word if necessary.
                if(root.map[idx]==null) root.map[idx] = new Trie();

                // Move to the node representing the current character.
                root = root.map[idx];
            }

            // Mark the final node because the complete reversed word
            // corresponds to a valid suffix of the stream.
            root.isEnd = true;
        }
    }

    public boolean query(char letter) {

        // Add the newest character to the front so iteration starts
        // from the most recent stream character.
        queue.addFirst(letter);

        Trie root = trie;

        // Traverse the stream backwards, which matches the direction
        // of the reversed words stored in the Trie.
        for(char ch : queue) {
            int idx = ch-'a';

            // No stored word has the current suffix as a prefix,
            // so no longer suffix can match either.
            if(root.map[idx]==null) return false;

            // Continue along the Trie path for this suffix.
            root = root.map[idx];

            // A complete dictionary word has been matched,
            // so the current stream ends with a valid word.
            if(root.isEnd) return true;
        }

        // We exhausted the available stream suffix without finding
        // a complete dictionary word.
        return false;
    }
}

class Trie {

    // Each index represents one lowercase English character.
    Trie[] map;

    // Marks that a complete reversed dictionary word ends here.
    boolean isEnd;

    Trie() {
        map = new Trie[26];
    }
}