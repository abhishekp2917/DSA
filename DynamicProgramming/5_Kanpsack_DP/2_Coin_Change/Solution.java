import java.util.Arrays;

class Solution1 {

    public int coinChange(int[] coins, int amount) {
        Integer[][] memo = new Integer[coins.length + 1][amount + 1];
        int minCoins = recursion(coins, coins.length, amount, memo);
        return (minCoins!=Integer.MAX_VALUE)? minCoins : -1;
    }

    private int recursion(int[] coins, int arrLen, int amount, Integer[][] memo) {
        if (amount==0) return 0;
        if (arrLen==0) return Integer.MAX_VALUE;
        if (memo[arrLen][amount]!=null) return memo[arrLen][amount];
        int idx = arrLen-1;
        int coin = coins[idx];
        int exclude = recursion(coins, arrLen-1, amount, memo);
        int include = Integer.MAX_VALUE;
        if (amount >= coin) {
            int subProblem = recursion(coins, arrLen, amount-coin, memo);
            if (subProblem!=Integer.MAX_VALUE) {
                include = 1 + subProblem;
            }
        }
        memo[arrLen][amount] = Math.min(exclude, include);
        return memo[arrLen][amount];
    }
}

class Solution2 {

    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int coin = coins[idx];
            for(int currAmount=1; currAmount<=amount; currAmount++) {
                if((currAmount-coin>=0) && dp[currAmount-coin] != Integer.MAX_VALUE) {
                    dp[currAmount] = Math.min(
                        dp[currAmount],
                        1 + dp[currAmount-coin]
                    );
                }
            }
        }
        return (dp[amount]!=Integer.MAX_VALUE)? dp[amount] : -1;
    }   
}
