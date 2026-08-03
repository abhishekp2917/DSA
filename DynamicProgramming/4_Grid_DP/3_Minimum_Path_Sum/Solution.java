class Solution {
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[] dp = new int[m+1];
        dp[0] = Integer.MAX_VALUE;
        dp[1] = grid[0][0];
        for(int col=1; col<m; col++) dp[col+1] = grid[0][col] + dp[col];
        for(int row=1; row<n; row++) {
            for(int col=0; col<m; col++) {
                dp[col+1] = grid[row][col] + Math.min(dp[col+1], dp[col]);
            }
        }
        return dp[m];
    }
}
