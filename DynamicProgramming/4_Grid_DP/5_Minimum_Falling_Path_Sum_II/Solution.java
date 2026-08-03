class Solution {
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int minCost = Integer.MAX_VALUE;
        int[] dp = new int[n];
        for(int col=0; col<n; col++) dp[col] = grid[0][col];
        for(int row=1; row<n; row++) {
            int[] currMin = new int[n];
            for(int col=0; col<n; col++) {
                int prevMin = Integer.MAX_VALUE;
                for(int i=0; i<n; i++) {
                    if(i==col) continue;
                    prevMin = Math.min(prevMin, dp[i]);
                }
                currMin[col] = grid[row][col] + prevMin;
            }
            for(int col=0; col<n; col++) {
                dp[col] = currMin[col];
            }
        }
        for(int col=0; col<n; col++) {
            minCost = Math.min(minCost, dp[col]);
        }
        return minCost;
    }
}

