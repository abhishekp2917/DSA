class Solution1 {

    public int change(int amount, int[] coins) {
        int n = coins.length;
        Integer[][] memo = new Integer[n+1][amount+1];
        return recursion(coins, n, amount, memo);
    }

    private int recursion(int[] coins, int arrLen, int amount, Integer[][] memo) {
        if (amount==0) return 1;
        if (arrLen==0) return 0;
        if (memo[arrLen][amount]!=null) return memo[arrLen][amount];
        int idx = arrLen-1;
        int coin = coins[idx];
        int exclude = recursion(coins, arrLen-1, amount, memo);
        int include = 0;
        if (amount >= coin) {
            include = recursion(coins, arrLen, amount-coin,memo);
        }
        memo[arrLen][amount] = exclude + include;
        return memo[arrLen][amount];
    }
}

class Solution2 {
    
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int coin = coins[idx];
            for(int currAmount=1; currAmount<=amount; currAmount++) {
                if((currAmount-coin>=0)) {
                    dp[currAmount] = dp[currAmount] + dp[currAmount-coin];
                }
            }
        }
        return dp[amount];
    }
}