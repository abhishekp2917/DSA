class Solution1 {

    public int climbStairs(int n) {
        Integer[] memo = new Integer[n + 1];
        return recursion(n, memo);
    }

    private int recursion(int n, Integer[] memo) {
        if (n == 0 || n == 1) return 1;
        if (memo[n] != null) return memo[n];
        int waysIfLastStepWasOne = recursion(n-1, memo);
        int waysIfLastStepWasTwo = recursion(n-2, memo);
        memo[n] = waysIfLastStepWasOne + waysIfLastStepWasTwo;
        return memo[n];
    }
}

class Solution2 {
    
    public int climbStairs(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i=2; i<=n; i++) {
            int waysIfLastStepWasOne = dp[i-1];
            int waysIfLastStepWasTwo = dp[i-2];
            dp[i] = waysIfLastStepWasOne + waysIfLastStepWasTwo;
        }
        return dp[n];
    }
}