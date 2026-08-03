class Solution1 {
    public int findPaths(int n, int m, int maxMove, int startRow, int startColumn) {
        final int MOD = 1000_000_007;
        Long[][][] memo = new Long[n][m][maxMove+1];
        int[][] dirs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        return (int)(recursion(n, m, dirs, startRow, startColumn, maxMove, memo)%MOD);
    }

    private long recursion(int n, int m, int[][] dirs, int row, int col, int maxMove, Long[][][] memo) {
        if((row==n || col==m || row==-1 || col==-1) && maxMove>=0) return 1;
        if(maxMove==0) return 0;
        if(memo[row][col][maxMove]!=null) return memo[row][col][maxMove];
        final int MOD = 1000_000_007;
        long pathCount = 0;
        for(int[] dir : dirs) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            pathCount += recursion(n, m, dirs, newRow, newCol, maxMove-1, memo);
        }
        memo[row][col][maxMove] = pathCount%MOD;
        return pathCount;
    }
}

class Solution2 {
    public int findPaths(int n, int m, int maxMove, int startRow, int startColumn) {
        final int MOD = 1000_000_007;
        long[][][] dp = new long[n][m][maxMove+1];
        int[][] dirs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for(int moves=1; moves<=maxMove; moves++) {
            for(int row=0; row<n; row++) {
                for(int col=0; col<m; col++) {
                    long pathCount = 0;
                    for(int[] dir : dirs) {
                        int newRow = row + dir[0];
                        int newCol = col + dir[1];
                        if(newRow<0 || newRow==n || newCol<0 || newCol==m) pathCount++;
                        else pathCount += dp[newRow][newCol][moves-1];
                    }
                    dp[row][col][moves] = pathCount%MOD;       
                }
            }
        }
        return (int)dp[startRow][startColumn][maxMove];
    }
}