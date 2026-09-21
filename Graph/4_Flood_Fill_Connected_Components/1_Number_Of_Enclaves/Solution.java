class Solution {
    
    public int numEnclaves(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int totalLands = 0;
        int nonEnclavesCount = 0;
        int[][] dirs = new int[][] { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        boolean[][] visited = new boolean[n][m];
        for(int row=0; row<n; row++) {
            for(int col=0; col<m; col++) {
                if(grid[row][col]==1) totalLands++;
            }
        }
        for(int col=0; col<m; col++) {
            if(grid[0][col]==1) {
                nonEnclavesCount += countLands(grid, dirs, visited, 0, col);
            }
            if(grid[n-1][col]==1) {
                nonEnclavesCount += countLands(grid, dirs, visited, n-1, col);
            }
        }
        for(int row=0; row<n; row++) {
            if(grid[row][0]==1) {
                nonEnclavesCount += countLands(grid, dirs, visited, row, 0);
            }
            if(grid[row][m-1]==1) {
                nonEnclavesCount += countLands(grid, dirs, visited, row, m-1);
            }
        }
        return totalLands-nonEnclavesCount;
    }

    private int countLands(int[][] grid, int[][] dirs, boolean[][] visited, int row, int col) {
        int n = grid.length;
        int m = grid[0].length;
        if(row<0 || row>=n || col<0 || col>=m || grid[row][col]==0 || visited[row][col]) {
            return 0;
        }
        int landCount = 1;
        visited[row][col] = true;
        for(int[] dir : dirs) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            landCount += countLands(grid, dirs, visited, nextRow, nextCol);
        }
        return landCount;
    }
}