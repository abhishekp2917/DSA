class Solution1 {
    public int minimizeTheDifference(int[][] matrix, int target) {
        int n = matrix.length;
        Integer[][] memo = new Integer[n+1][4901];
        return recursion(matrix, 0, target, 0, memo);
    }

    private int recursion(int[][] matrix, int row, int target, int currSum, Integer[][] memo) {
        if(row==matrix.length) return Math.abs(target-currSum);
        if(memo[row][currSum]!=null) return memo[row][currSum];
        int minAbsDiff = Integer.MAX_VALUE;
        for(int col=0; col<matrix[0].length; col++) {
            minAbsDiff = Math.min(
                minAbsDiff,
                recursion(matrix, row+1, target, currSum+matrix[row][col], memo)
            );
        }
        memo[row][currSum] = minAbsDiff;
        return memo[row][currSum];
    }
}

class Solution2 {
    public int minimizeTheDifference(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n+1][4901];
        for(int currSum=0; currSum<=4900; currSum++) {
            dp[n][currSum] = Math.abs(target-currSum);
        }
        for(int row=n-1; row>=0; row--) {
            for(int currSum=4900; currSum>=0; currSum--) {
                int minAbsDiff = Integer.MAX_VALUE;
                for(int col=0; col<m; col++) {
                    int num = matrix[row][col];
                    minAbsDiff = Math.min(
                        minAbsDiff,
                        (currSum+num<=4900)? dp[row+1][currSum+num] : Integer.MAX_VALUE
                    );
                }
                dp[row][currSum] = minAbsDiff;
            }
        }
        return dp[0][0];
    }
}