class Solution {

    public void solve(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dirs = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        
        for (int col=0; col<m; col++) {
            if (grid[0][col]=='O') {
                markBoundaryConnected(grid, dirs, 0, col);
            }
            if (grid[n-1][col]=='O') {
                markBoundaryConnected(grid, dirs, n-1, col);
            }
        }

        for (int row=0; row<n; row++) {
            if (grid[row][0]=='O') {
                markBoundaryConnected(grid, dirs, row, 0);
            }
            if (grid[row][m - 1] == 'O') {
                markBoundaryConnected(grid, dirs, row, m - 1);
            }
        }

        for (int row=0; row<n; row++) {
            for (int col=0; col<m; col++) {
                if (grid[row][col]=='O') grid[row][col] = 'X';
                else if (grid[row][col] == '#') grid[row][col] = 'O';
            }
        }
    }

    private void markBoundaryConnected(char[][] grid, int[][] dirs, int row, int col) {
        int n = grid.length;
        int m = grid[0].length;
        if (row<0 || row>=n || col<0 || col>=m || grid[row][col]!='O') {
            return;
        }
        grid[row][col] = '#';
        for (int[] dir : dirs) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            markBoundaryConnected(grid, dirs, nextRow, nextCol);
        }
    }
}