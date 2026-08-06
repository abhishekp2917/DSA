class Solution {

    public int minPathSum(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        // dp[col+1]:
        // stores the minimum path sum
        // to reach the current row's cell
        // at column 'col'.
        //
        // We use (m+1) size so that dp[0]
        // acts as a dummy boundary,
        // eliminating separate boundary checks.
        int[] dp = new int[m+1];

        // Initialize dummy boundary.
        //
        // This prevents the algorithm from
        // incorrectly choosing a path
        // outside the left boundary.
        dp[0] = Integer.MAX_VALUE;

        // Starting cell.
        //
        // Minimum cost to reach the first cell
        // is its own value.
        dp[1] = grid[0][0];

        // Build the first row.
        //
        // Since we can only move RIGHT,
        // every cell is reached from its left neighbour.
        for(int col=1; col<m; col++) {
            dp[col+1] = grid[0][col] + dp[col];
        }

        // Process remaining rows.
        for(int row=1; row<n; row++) {

            // Process columns from left to right.
            for(int col=0; col<m; col++) {

                // dp[col+1]:
                // minimum path coming from ABOVE.
                //
                // dp[col]:
                // minimum path coming from LEFT.
                //
                // Choose the cheaper path,
                // then add the current cell's value.
                dp[col+1] =
                    grid[row][col] +
                    Math.min(
                        dp[col+1],
                        dp[col]
                    );
            }
        }

        // Last position stores
        // minimum cost to reach
        // the bottom-right cell.
        return dp[m];
    }
}