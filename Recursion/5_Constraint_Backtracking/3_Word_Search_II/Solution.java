import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        int n = board.length;
        int m = board[0].length;
        List<String> ans = new ArrayList<>();
        Set<String> wordPrefix = new HashSet<>();
        Set<String> wordSet = new HashSet<>();
        for(String word : words) {
            for(int len=1; len<=word.length(); len++) wordPrefix.add(word.substring(0, len));
            wordSet.add(word);
        }
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                recursion(board, i, j, new StringBuilder(), wordSet, new boolean[n][m], wordPrefix);
            }
        }
        for(String word : words) {
            if(!wordSet.contains(word)) ans.add(word);
        }
        return ans;
    }

    private void recursion(char[][] board, int x, int y, StringBuilder word, Set<String> wordSet, boolean[][] visited, Set<String> wordPrefix) {
        if(x>=board.length || y>=board[0].length || x<0 || y<0 || visited[x][y] || word.length()>10) return;
        visited[x][y] = true;
        word.append(board[x][y]);
        String currWord = word.toString();
        if(wordPrefix.contains(currWord)) {
            wordSet.remove(currWord);
            int[][] dirs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for(int[] dir : dirs) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                recursion(board, newX, newY, word, wordSet, visited, wordPrefix);
            } 
        }
        word.deleteCharAt(word.length()-1);
        visited[x][y] = false;
    }
}