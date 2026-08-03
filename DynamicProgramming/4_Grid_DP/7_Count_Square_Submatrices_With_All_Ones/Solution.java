class Solution1 {
    public int countSquares(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n+1][m+1];
        int squaresCount = 0;
        for(int row=1; row<=n; row++) {
            for(int col=1; col<=m; col++) {
                dp[row][col] = dp[row-1][col] + 
                dp[row][col-1] - 
                dp[row-1][col-1] + matrix[row-1][col-1];
                for(int len=1; len<=Math.min(row, col); len++) {
                    int expectedSum = len*len;
                    int actualSum = dp[row][col] - dp[row-len][col] - dp[row][col-len] + dp[row-len][col-len];
                    if(actualSum==expectedSum) squaresCount++;
                }
            }
        }
        return squaresCount;
    }
}

class Solution2 {
    public int countSquares(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n+1][m+1];
        int squaresCount = 0;
        for(int row=1; row<=n; row++) {
            for(int col=1; col<=m; col++) {
                if(matrix[row-1][col-1]==0) continue;
                dp[row][col] = 1 + Math.min(
                    dp[row-1][col-1],
                    Math.min(
                        dp[row-1][col],
                        dp[row][col-1]
                    )
                );
                squaresCount += dp[row][col];
            }
        }
        return squaresCount;
    }
}
