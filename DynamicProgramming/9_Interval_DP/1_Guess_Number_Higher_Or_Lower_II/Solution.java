class Solution1 {

    public int getMoneyAmount(int n) {

        // memo[start][end]:
        // stores the minimum money required
        // to guarantee finding the correct number
        // when the answer lies in [start...end].
        Integer[][] memo = new Integer[n+1][n+1];

        return recursion(1, n, memo);
    }

    private int recursion(int start, int end, Integer[][] memo) {

        // If the range contains
        // zero or one number,
        // no guessing is required.
        if(start>=end) return 0;

        // Reuse previously computed interval.
        if(memo[start][end]!=null) return memo[start][end];

        int minMoneyNeededForWin = Integer.MAX_VALUE;

        // Try every possible first guess.
        //
        // Our goal is to choose
        // the guess that minimizes
        // the guaranteed cost.
        for(int guess=start; guess<=end; guess++) {

            // If the hidden number
            // is greater than the guess,
            // we must continue searching
            // in the right interval.
            int moneyNeededIfHigher =
                recursion(guess+1, end, memo);

            // If the hidden number
            // is smaller than the guess,
            // we continue searching
            // in the left interval.
            int moneyNeededIfLower =
                recursion(start, guess-1, memo);

            // We do not know
            // which side the answer lies on.
            //
            // Therefore we must prepare
            // for the WORST possible outcome.
            int maxMoneyNeededInWorstCase = Math.max(
                moneyNeededIfHigher,
                moneyNeededIfLower
            );

            // Guessing 'guess'
            // immediately costs 'guess' dollars.
            //
            // Total guaranteed cost
            // equals:
            //
            // current guess cost
            //
            // +
            //
            // worst future cost.
            minMoneyNeededForWin = Math.min(
                minMoneyNeededForWin,
                guess + maxMoneyNeededInWorstCase
            );
        }

        memo[start][end] = minMoneyNeededForWin;

        return minMoneyNeededForWin;
    }
}

class Solution2 {

    public int getMoneyAmount(int n) {

        // dp[start][end]:
        // stores the minimum money required
        // to guarantee finding the answer
        // inside interval [start...end].
        int[][] dp = new int[n+1][n+1];

        // Build intervals
        // from smaller lengths
        // towards larger lengths.
        //
        // Every transition depends only
        // on strictly smaller intervals.
        for(int start=n-1; start>=1; start--) {

            for(int end=start+1; end<=n; end++) {

                int minMoneyNeededForWin = Integer.MAX_VALUE;

                // Try every possible first guess.
                for(int guess=start; guess<=end; guess++) {

                    // Cost if the answer
                    // lies on the right side.
                    int moneyNeededIfHigher =
                        (guess+1<=n)
                        ? dp[guess+1][end]
                        : 0;

                    // Cost if the answer
                    // lies on the left side.
                    int moneyNeededIfLower =
                        (guess-1>=0)
                        ? dp[start][guess-1]
                        : 0;

                    // We must assume
                    // the adversary always forces
                    // the more expensive branch.
                    int maxMoneyNeededInWorstCase = Math.max(
                        moneyNeededIfHigher,
                        moneyNeededIfLower
                    );

                    // Guaranteed cost
                    // for choosing this guess.
                    minMoneyNeededForWin = Math.min(
                        minMoneyNeededForWin,
                        guess + maxMoneyNeededInWorstCase
                    );
                }

                dp[start][end] = minMoneyNeededForWin;
            }
        }

        return dp[1][n];
    }
}