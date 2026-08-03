import java.util.Arrays;
import java.util.List;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int minCost = Integer.MAX_VALUE;
        int[][] dp = new int[n][n];
        for(int row=0; row<n; row++) Arrays.fill(dp[row], Integer.MAX_VALUE);
        dp[0][0] = triangle.get(0).get(0);
        for(int row=1; row<n; row++) {
            for(int col=0; col<row+1; col++) {
                dp[row][col] = triangle.get(row).get(col) + 
                Math.min(
                    dp[row-1][col],
                    (col-1>=0)? dp[row-1][col-1] : Integer.MAX_VALUE
                );
            }
        }
        for(int col=0; col<n; col++) {
            minCost = Math.min(minCost, dp[n-1][col]);
        }
        return minCost;
    }
}
