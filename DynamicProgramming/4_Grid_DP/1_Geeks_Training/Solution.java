class Solution {
    public int maximumPoints(int mat[][]) {
        int n = mat.length;
        int[][] dp = new int[n][4];
        dp[0][0] = Math.max(mat[0][1], mat[0][2]);
        dp[0][1] = Math.max(mat[0][0], mat[0][2]);
        dp[0][2] = Math.max(mat[0][0], mat[0][1]);
        dp[0][3] = Math.max(mat[0][0], Math.max(mat[0][1], mat[0][2]));
        for(int i=1; i<n; i++) {
            int runningPoints = mat[i][0] + dp[i-1][0];
            int fightingPoints = mat[i][1] + dp[i-1][1];
            int learningPoints = mat[i][2] + dp[i-1][2];
            dp[i][0] = Math.max(fightingPoints, learningPoints);
            dp[i][1] = Math.max(runningPoints, learningPoints);
            dp[i][2] = Math.max(runningPoints, fightingPoints);
            dp[i][3] = Math.max(runningPoints, Math.max(fightingPoints, learningPoints));
        }
        return dp[n-1][3];
    }
}