import java.util.Arrays;

class Solution1 {

    public int coinChange(int[] coins, int amount) {

        // memo[arrLen][amount]:
        // stores the minimum number of coins
        // required to form 'amount'
        // using the first 'arrLen' coin types.
        //
        // Memoization avoids solving
        // the same state repeatedly.
        Integer[][] memo = new Integer[coins.length + 1][amount + 1];

        int minCoins = recursion(coins, coins.length, amount, memo);

        // If amount cannot be formed,
        // return -1 as required.
        return (minCoins!=Integer.MAX_VALUE) ? minCoins : -1;
    }

    private int recursion(int[] coins, int arrLen, int amount, Integer[][] memo) {

        // Base Case:
        //
        // Amount becomes zero.
        //
        // No more coins are required.
        if (amount==0) return 0;

        // No coin types remain,
        // but amount is still positive.
        //
        // Therefore this state
        // is impossible.
        if (arrLen==0) return Integer.MAX_VALUE;

        // Return previously computed answer.
        if (memo[arrLen][amount]!=null) {
            return memo[arrLen][amount];
        }

        // Current coin under consideration.
        int idx = arrLen-1;

        int coin = coins[idx];

        // Option 1:
        // Ignore this coin completely
        // and continue with remaining coin types.
        int exclude =
            recursion(
                coins,
                arrLen-1,
                amount,
                memo
            );

        int include = Integer.MAX_VALUE;

        // Option 2:
        // Take this coin.
        //
        // Since every coin type
        // can be used unlimited times,
        // we DO NOT reduce arrLen.
        //
        // We only reduce the remaining amount.
        if (amount >= coin) {

            int subProblem =
                recursion(
                    coins,
                    arrLen,
                    amount-coin,
                    memo
                );

            // Only build upon
            // a valid subproblem.
            if (subProblem!=Integer.MAX_VALUE) {

                include =
                    1 + subProblem;
            }
        }

        // Choose the solution
        // using fewer coins.
        memo[arrLen][amount] =
            Math.min(exclude, include);

        return memo[arrLen][amount];
    }
}

class Solution2 {

    public int coinChange(int[] coins, int amount) {

        int n = coins.length;

        // dp[currAmount]:
        // stores the minimum number of coins
        // required to form currAmount.
        //
        // Initialize every amount
        // as impossible initially.
        int[] dp = new int[amount+1];

        Arrays.fill(dp, Integer.MAX_VALUE);

        // Base Case:
        //
        // Zero amount requires
        // zero coins.
        dp[0] = 0;

        // Process one coin type at a time.
        for(int arrLen=1; arrLen<=n; arrLen++) {

            int idx = arrLen-1;

            int coin = coins[idx];

            // Try forming every amount.
            for(int currAmount=1; currAmount<=amount; currAmount++) {

                // Coin can contribute only if:
                //
                // remaining amount is non-negative
                //
                // and
                //
                // remaining amount itself
                // is achievable.
                if((currAmount-coin>=0)
                        && dp[currAmount-coin]!=Integer.MAX_VALUE) {

                    // Include current coin.
                    //
                    // Since this is an unbounded knapsack,
                    // dp[currAmount-coin] may already
                    // contain solutions using
                    // the same coin multiple times.
                    dp[currAmount] =
                        Math.min(
                            dp[currAmount],
                            1 + dp[currAmount-coin]
                        );
                }
            }
        }

        // If amount remains unreachable,
        // return -1.
        return (dp[amount]!=Integer.MAX_VALUE)
                ? dp[amount]
                : -1;
    }
}