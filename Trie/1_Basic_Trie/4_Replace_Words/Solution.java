import java.util.List;

class Solution {
    public String replaceWords(List<String> dictionary, String sentence) {

        // Store all dictionary roots in a Trie so that we can find
        // the shortest root of each sentence word by traversing its prefix.
        StringBuilder newSentence = new StringBuilder(); 
        Trie trie = new Trie();

        for(String word : dictionary) {
            Trie root = trie;

            // Insert every dictionary word character by character.
            for(char ch : word.toCharArray()) {
                int idx = ch-'a';

                // Create the character node if this prefix does not exist yet.
                if(root.map[idx]==null) root.map[idx] = new Trie();

                // Move to the node representing the current character.
                root = root.map[idx];
            }

            // Mark the final node because this complete prefix is a valid root.
            root.isEnd =true;
        }

        // Process each sentence word independently because replacement
        // depends only on the prefixes of that particular word.
        String[] words = sentence.split(" ");

        for(String word : words) {
            Trie root = trie;
            StringBuilder rootWord = new StringBuilder();
            boolean hasRootWord = false;

            // Traverse the sentence word through the Trie.
            // The first dictionary word ending on this path is the shortest root.
            for(char ch : word.toCharArray()) {
                int idx = ch-'a';

                // No dictionary root can match this word beyond this point.
                if(root.map[idx]==null) break;

                // Build the prefix that corresponds to the current Trie path.
                rootWord.append(ch);
                root = root.map[idx];

                // The first isEnd encountered is the shortest valid root,
                // so we can stop immediately without checking longer prefixes.
                if(root.isEnd) {
                    hasRootWord = true;
                    break;
                }
            }

            // Replace the word only if a dictionary root was found;
            // otherwise keep the original word unchanged.
            if(hasRootWord) newSentence.append(rootWord.toString());
            else newSentence.append(word);

            // Add the space between consecutive sentence words.
            newSentence.append(" ");
        }

        // The loop adds one extra space after the final word,
        // so trim it before returning the reconstructed sentence.
        return newSentence.toString().trim();
    }
}


class Trie {
    // Each index represents one lowercase English character.
    Trie[] map;

    // Marks that a complete dictionary root ends at this node.
    boolean isEnd;

    Trie() {
        map = new Trie[26];
    }
}