class Solution {

    public int minFallingPathSum(int[][] grid) {

        int n = grid.length;

        int minCost = Integer.MAX_VALUE;

        // dp[col]:
        // stores the minimum path sum
        // to reach column 'col'
        // in the previous row.
        int[] dp = new int[n];

        // Base Case:
        //
        // Any cell in the first row
        // can be chosen as the starting point.
        //
        // Therefore minimum cost equals
        // the cell's own value.
        for(int col=0; col<n; col++) {
            dp[col] = grid[0][col];
        }

        // Process remaining rows.
        for(int row=1; row<n; row++) {

            // Stores answers
            // for the current row.
            int[] currMin = new int[n];

            for(int col=0; col<n; col++) {

                int prevMin = Integer.MAX_VALUE;

                // Previous row can contribute
                // from any column
                // EXCEPT the current column.
                //
                // This enforces the problem constraint
                // that adjacent rows cannot use
                // the same column.
                for(int i=0; i<n; i++) {

                    if(i==col) continue;

                    prevMin =
                        Math.min(prevMin, dp[i]);
                }

                // Choose the cheapest valid parent
                // and add current cell value.
                currMin[col] =
                    grid[row][col] + prevMin;
            }

            // Current row becomes
            // the previous row
            // for the next iteration.
            for(int col=0; col<n; col++) {
                dp[col] = currMin[col];
            }
        }

        // Path may end
        // at any column
        // in the last row.
        for(int col=0; col<n; col++) {

            minCost =
                Math.min(minCost, dp[col]);
        }

        return minCost;
    }
}