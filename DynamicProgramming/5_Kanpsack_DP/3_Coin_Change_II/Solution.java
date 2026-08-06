class Solution1 {

    public int change(int amount, int[] coins) {

        int n = coins.length;

        // memo[arrLen][amount]:
        // stores the number of different combinations
        // that can form 'amount'
        // using the first 'arrLen' coin types.
        //
        // Memoization avoids solving
        // the same state repeatedly.
        Integer[][] memo = new Integer[n+1][amount+1];

        return recursion(coins, n, amount, memo);
    }

    private int recursion(int[] coins, int arrLen, int amount, Integer[][] memo) {

        // Base Case:
        //
        // Required amount has been formed.
        //
        // This contributes exactly
        // one valid combination.
        if (amount==0) return 1;

        // No coin types remain,
        // but amount is still positive.
        //
        // Therefore no valid combination exists.
        if (arrLen==0) return 0;

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

        int include = 0;

        // Option 2:
        // Include this coin.
        //
        // Since every coin can be reused,
        // we keep arrLen unchanged
        // and only reduce the remaining amount.
        if (amount >= coin) {

            include =
                recursion(
                    coins,
                    arrLen,
                    amount-coin,
                    memo
                );
        }

        // Total combinations equal
        // combinations excluding the coin
        // +
        // combinations including the coin.
        memo[arrLen][amount] =
            exclude + include;

        return memo[arrLen][amount];
    }
}

class Solution2 {

    public int change(int amount, int[] coins) {

        int n = coins.length;

        // dp[currAmount]:
        // stores the number of combinations
        // to form currAmount
        // using all processed coin types.
        int[] dp = new int[amount+1];

        // Base Case:
        //
        // There is exactly one way
        // to form amount 0:
        //
        // Choose no coins.
        dp[0] = 1;

        // Process one coin type at a time.
        //
        // This guarantees that
        // different orders of the same coins
        // are NOT counted separately.
        for(int arrLen=1; arrLen<=n; arrLen++) {

            int idx = arrLen-1;

            int coin = coins[idx];

            // Build every amount
            // using the current coin.
            for(int currAmount=1; currAmount<=amount; currAmount++) {

                if(currAmount-coin>=0) {

                    // Every combination that forms
                    // (currAmount - coin)
                    // can be extended
                    // by adding the current coin.
                    //
                    // Add those combinations
                    // to the existing answer.
                    dp[currAmount] =
                        dp[currAmount]
                        +
                        dp[currAmount-coin];
                }
            }
        }

        // Number of combinations
        // to form the required amount.
        return dp[amount];
    }
}