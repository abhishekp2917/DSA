class Solution1 {
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int longestIncreasingPath = 0;
        Integer[][] memo = new Integer[n][m];
        int[][] dirs = new int[][] { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
        for(int row=0; row<n; row++) {
            for(int col=0; col<m; col++) {
                longestIncreasingPath = Math.max(
                    longestIncreasingPath,
                    recursion(matrix, dirs, row, col, memo)
                );
            }
        }
        return longestIncreasingPath;
    }

    private int recursion(int[][] matrix, int[][] dirs, int row, int col, Integer[][] memo) {
        int n = matrix.length;
        int m = matrix[0].length;
        if(memo[row][col]!=null) return memo[row][col];
        int longestIncreasingPath = 1;
        int curr = matrix[row][col];
        for(int[] dir : dirs) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if(newRow>=n || newRow<0 || newCol>=m || newCol<0 || matrix[newRow][newCol]<=curr) continue;
            longestIncreasingPath = Math.max(
                longestIncreasingPath,
                1 + recursion(matrix, dirs, newRow, newCol, memo)
            );
        }
        memo[row][col] = longestIncreasingPath;
        return longestIncreasingPath;
    }
}