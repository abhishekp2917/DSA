class Solution {
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        int islandsCount = 0; 

        // traverse the grid for each cell and count the number of islands using DFS
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // If the cell is land and not visited, then it is a new island
                // traverse all such land cells using DFS
                if (grid[i][j] == '1' && !visited[i][j]) {
                    dfs(grid, visited, i, j);
                    // after traversing all the land cells of an island, increment the count
                    islandsCount++;
                }
            }
        }

        return islandsCount;
    }

    private void dfs(char[][] grid, boolean[][] visited, int x, int y) {
        int n = grid.length;
        int m = grid[0].length;

        // Check bounds and whether the cell is land and not visited
        if (x < 0 || x >= n || y < 0 || y >= m || grid[x][y] != '1' || visited[x][y]) {
            return;
        }

        // Mark the cell as visited so that we don't visit it again
        visited[x][y] = true;

        // Visit all adjacent cells of the current cell
        dfs(grid, visited, x - 1, y); // Up
        dfs(grid, visited, x + 1, y); // Down
        dfs(grid, visited, x, y - 1); // Left
        dfs(grid, visited, x, y + 1); // Right
    }
}
