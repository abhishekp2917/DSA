import java.util.Arrays;
import java.util.List;

class Solution {

    public int minimumTotal(List<List<Integer>> triangle) {

        int n = triangle.size();

        int minCost = Integer.MAX_VALUE;

        // dp[row][col]:
        // stores the minimum path sum
        // to reach cell (row, col).
        //
        // Cells that do not exist in the triangle
        // are initialized with INF so they are
        // never chosen during transitions.
        int[][] dp = new int[n][n];

        for(int row=0; row<n; row++) {
            Arrays.fill(dp[row], Integer.MAX_VALUE);
        }

        // Base Case:
        //
        // Top element is the starting point,
        // so minimum cost equals its own value.
        dp[0][0] = triangle.get(0).get(0);

        // Compute answers row by row.
        for(int row=1; row<n; row++) {

            for(int col=0; col<row+1; col++) {

                // A triangle cell can only be reached from:
                //
                // Same column in previous row
                // OR
                // Previous column in previous row.
                //
                // Choose the cheaper path
                // and add current cell value.
                dp[row][col] =
                    triangle.get(row).get(col) +
                    Math.min(
                        dp[row-1][col],
                        (col-1>=0)
                            ? dp[row-1][col-1]
                            : Integer.MAX_VALUE
                    );
            }
        }

        // Path may end at any cell
        // in the last row.
        //
        // Choose the minimum among them.
        for(int col=0; col<n; col++) {

            minCost =
                Math.min(minCost, dp[n-1][col]);
        }

        return minCost;
    }
}