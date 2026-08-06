class Solution {

    public int uniquePaths(int n, int m) {

        // dp[row][col]:
        // stores the number of unique paths
        // from the starting cell (0,0)
        // to cell (row, col).
        int[][] dp = new int[n][m];

        // Base Case:
        // Every cell in the first column
        // can only be reached by moving DOWN.
        //
        // Therefore there is exactly one path.
        for(int row=0; row<n; row++) {
            dp[row][0] = 1;
        }

        // Base Case:
        // Every cell in the first row
        // can only be reached by moving RIGHT.
        //
        // Therefore there is exactly one path.
        for(int col=0; col<m; col++) {
            dp[0][col] = 1;
        }

        // Compute number of paths
        // for every remaining cell.
        for(int row=1; row<n; row++) {

            for(int col=1; col<m; col++) {

                // To reach current cell,
                // the last move must have been:
                //
                // from the cell above
                // OR
                // from the cell on the left.
                //
                // Since these are the only two possibilities,
                // total paths equal the sum of both.
                dp[row][col] =
                    dp[row-1][col] +
                    dp[row][col-1];
            }
        }

        // Bottom-right cell stores
        // the total number of unique paths.
        return dp[n-1][m-1];
    }
}