import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        int n = board.length;
        int m = board[0].length;
        List<String> matchedWords = new ArrayList<>();

        // Store all dictionary words in a Trie so that during DFS we can
        // immediately determine whether the current board path can form
        // the prefix of at least one dictionary word.
        Trie trie = new Trie();

        for(String word : words) {
            Trie root = trie;

            // Insert the word character by character into the Trie.
            for(char ch : word.toCharArray()) {
                int idx = ch-'a';

                // Create the Trie node only when this character path
                // does not already exist.
                if(root.map[idx]==null) root.map[idx] = new Trie();

                root = root.map[idx];
            }

            // Store the complete word at its final Trie node.
            // This lets us identify a matched word without reconstructing
            // the characters collected during DFS.
            root.word = word;
        }

        // visited prevents using the same board cell more than once
        // in the current path.
        boolean[][] visited = new boolean[n][m];

        // Every cell can be the starting point of a word,
        // so start DFS from every board cell.
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                recursion(board, n, m, i, j, visited, trie, matchedWords);
            }
        }

        return matchedWords;
    }


    private void recursion(char[][] board, int n, int m, int x, int y, boolean[][] visited, Trie root, List<String> matchedWords) {

        int idx = board[x][y]-'a';

        // Stop if this cell is already part of the current path or
        // if the current character cannot extend any Trie prefix.
        // The Trie check is what eliminates a huge amount of unnecessary DFS.
        if(visited[x][y] || root.map[idx]==null) return;

        // Move to the Trie node corresponding to the current board character.
        root = root.map[idx];

        // Mark the cell before exploring neighbours so that the same
        // cell cannot be reused in the current word path.
        visited[x][y] = true;

        // Reaching a Trie node containing a word means the current board
        // path has formed a complete dictionary word.
        if(root.word!=null) {
            matchedWords.add(root.word);

            // The same word can be discovered from multiple DFS paths.
            // Clearing it ensures that each word is added to the result only once.
            root.word = null;
        }

        // Explore all four adjacent cells because a word can move
        // horizontally or vertically, but not diagonally.
        if(x>0) recursion(board, n, m, x-1, y, visited, root, matchedWords);
        if(x<n-1) recursion(board, n, m, x+1, y, visited, root, matchedWords);
        if(y>0) recursion(board, n, m, x, y-1, visited, root, matchedWords);
        if(y<m-1) recursion(board, n, m, x, y+1, visited, root, matchedWords);

        // Backtrack so that this cell becomes available again when
        // exploring a different path or starting position.
        visited[x][y] = false;
    }
}


class Trie {
    // Each index represents one lowercase English character.
    Trie[] map;

    // Stores the complete dictionary word when this node represents
    // the end of a word. Keeping the word here avoids rebuilding it
    // from the DFS path.
    String word;

    Trie() {
        map = new Trie[26];
    }
}