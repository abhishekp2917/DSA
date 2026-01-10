import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

class Solution {

    // Pattern: Backtracking / DFS (Grid Path Search with Pruning)
    //
    // Core intuition:
    // We need to find words by walking paths on the board.
    // Each path is formed by moving in 4 directions without revisiting a cell.
    //
    // Prefix pruning is used to stop exploring paths that cannot form
    // any valid word.

    public List<String> findWords(char[][] board, String[] words) {

        int n = board.length;
        int m = board[0].length;

        List<String> ans = new ArrayList<>();

        // wordPrefix stores all prefixes of all words
        // This allows early pruning during DFS
        Set<String> wordPrefix = new HashSet<>();

        // wordSet initially stores all words.
        // When a word is found, it is removed from this set.
        Set<String> wordSet = new HashSet<>();

        // Build prefix set and word set
        for (String word : words) {
            for (int len = 1; len <= word.length(); len++) {
                wordPrefix.add(word.substring(0, len));
            }
            wordSet.add(word);
        }

        // Start DFS from every cell
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                recursion(
                    board,
                    i,
                    j,
                    new StringBuilder(),
                    wordSet,
                    new boolean[n][m],
                    wordPrefix
                );
            }
        }

        // Words removed from wordSet are found
        for (String word : words) {
            if (!wordSet.contains(word)) ans.add(word);
        }

        return ans;
    }

    private void recursion(
        char[][] board,
        int x,
        int y,
        StringBuilder word,
        Set<String> wordSet,
        boolean[][] visited,
        Set<String> wordPrefix
    ) {

        // Boundary checks and pruning
        if (x >= board.length || y >= board[0].length ||
            x < 0 || y < 0 ||
            visited[x][y] ||
            word.length() > 10) return;

        visited[x][y] = true;

        // Extend the current word
        word.append(board[x][y]);
        String currWord = word.toString();

        // Only continue DFS if current prefix is valid
        if (wordPrefix.contains(currWord)) {

            // If current word is complete, remove it from wordSet
            wordSet.remove(currWord);

            // Explore all four directions
            int[][] dirs = new int[][]{
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}
            };

            for (int[] dir : dirs) {
                recursion(
                    board,
                    x + dir[0],
                    y + dir[1],
                    word,
                    wordSet,
                    visited,
                    wordPrefix
                );
            }
        }

        // Backtrack: remove last character and unmark visited
        word.deleteCharAt(word.length() - 1);
        visited[x][y] = false;
    }
}
