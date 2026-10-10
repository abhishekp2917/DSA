class Solution {

    public int maximalSquare(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n+1][m+1];
        
        int maxSquareArea = 0;
        for(int row=1; row<=n; row++) {
            for(int col=1; col<=m; col++) {
                if(matrix[row-1][col-1]=='0') continue;
                int maxSquareLength = 1 + Math.min(
                    dp[row-1][col-1],
                    Math.min(
                        dp[row-1][col],
                        dp[row][col-1]
                    )
                );
                dp[row][col] = maxSquareLength;
                maxSquareArea = Math.max(maxSquareArea, maxSquareLength*maxSquareLength);
            }   
        }
        return maxSquareArea;
    }
}