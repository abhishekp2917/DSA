import java.util.Arrays;

class Solution1 {

    public int stoneGameII(int[] piles) {

        int n = piles.length;

        // pilesSuffix[i] stores the total stones from index i to the end
        // This allows us to quickly compute how many stones are still available
        // from any starting index.
        int[] pilesSuffix = new int[n + 1];

        // memo[start][m] stores the maximum stones the current player can collect
        // starting from index `start` when the current value of M is `m`.
        Integer[][] memo = new Integer[n][n + 1];

        // Build suffix sum and initialize memo
        for (int i = n - 1; i >= 0; i--) {
            pilesSuffix[i] = pilesSuffix[i + 1] + piles[i];
        }

        // Game starts from index 0 with M = 1
        return playerStones(pilesSuffix, n, 0, 1, memo);
    }

    private int playerStones(
        int[] pilesSuffix,
        int n,
        int start,
        int m,
        Integer[][] memo
    ) {

        // If all piles are taken, no stones can be collected
        if (start >= n) return 0;

        // Return cached result if already computed
        if (memo[start][m] != null) return memo[start][m];

        int stones = 0;

        // The current player can take X piles,
        // where 1 <= X <= 2*M
        for (int x = 1; x <= 2 * m && start + x <= n; x++) {

            // pilesSuffix[start] = total stones available from `start`
            // After taking x piles, the opponent will play optimally
            // starting from (start + x) with M updated to max(m, x).
            //
            // Since this is a zero-sum game:
            // current player's stones =
            // total remaining stones - opponent's best possible stones
            stones = Math.max(
                stones,
                pilesSuffix[start] -
                playerStones(
                    pilesSuffix,
                    n,
                    start + x,
                    Math.max(m, x),
                    memo
                )
            );
        }

        // Store the best result for this state
        memo[start][m] = stones;

        return stones;
    }
}

class Solution2 {

    public int stoneGameII(int[] piles) {

        int n = piles.length;

        // pilesSuffix[i]:
        // stores the total stones
        // from index i till the end.
        //
        // This lets us compute
        // remaining stones in O(1)
        // instead of summing repeatedly.
        int[] pilesSuffix = new int[n+1];

        // dp[start][M]:
        // stores the maximum stones
        // the current player can collect
        // starting from index 'start'
        // when the current value of M is 'M'.
        int[][] dp = new int[n+1][n+1];

        // Build suffix sums.
        for(int i=n-1; i>=0; i--) {
            pilesSuffix[i] = pilesSuffix[i+1] + piles[i];
        }

        // Compute DP
        // from the end
        // towards the beginning.
        for(int start=n-1; start>=0; start--) {

            for(int m=n; m>=1; m--) {

                int stones = 0;

                // Current player
                // may take
                // 1 to 2*M piles.
                for(int x=1; x<=2*m && start+x<=n; x++) {

                    // Remaining stones after taking x piles
                    // will be played optimally by the opponent.
                    //
                    // Therefore:
                    //
                    // Current Player =
                    //
                    // Total Remaining Stones
                    //
                    // -
                    //
                    // Opponent's Best Score
                    stones = Math.max(
                        stones,
                        pilesSuffix[start] - dp[start+x][Math.max(m,x)]
                    );
                }

                dp[start][m] = stones;
            }
        }

        return dp[0][1];
    }
}

class Solution3 {

    public int stoneGameII(int[] piles) {

        int n = piles.length;

        // Total stones are required
        // to convert score difference
        // into Alice's final score.
        int total = Arrays.stream(piles).sum();

        // memo[start][M]:
        // stores the maximum score difference
        // (Current Player - Opponent)
        // starting from index 'start'
        // with current value of M.
        Integer[][] memo = new Integer[n][2*n+1];

        int maxDiff = stonesDifference(piles, 0, 1, memo);

        // Let:
        //
        // Alice = A
        // Bob = B
        //
        // A + B = total
        // A - B = maxDiff
        //
        // Solving gives:
        //
        // A = (total + maxDiff) / 2
        return (total + maxDiff) / 2;
    }

    private int stonesDifference(int[] piles, int start, int M, Integer[][] memo) {

        int n = piles.length;

        // No piles remain.
        //
        // Score difference becomes zero.
        if(start==n) return 0;

        // Return previously computed answer.
        if(memo[start][M]!=null) return memo[start][M];

        int maxDiff = Integer.MIN_VALUE;

        int stonesCount = 0;

        // Try taking
        // every valid number of piles.
        for(int x=1; x<=2*M && start+x<=n; x++) {

            // Running sum of stones
            // taken in this move.
            stonesCount += piles[start+x-1];

            // After current player moves,
            // opponent becomes
            // the current player.
            //
            // Therefore subtract
            // opponent's best score difference.
            maxDiff = Math.max(
                maxDiff,
                stonesCount - stonesDifference(
                    piles,
                    start+x,
                    Math.max(M, x),
                    memo
                )
            );
        }

        memo[start][M] = maxDiff;

        return maxDiff;
    }
}

class Solution4 {

    public int stoneGameII(int[] piles) {

        int n = piles.length;

        int total = Arrays.stream(piles).sum();

        // dp[start][M]:
        // stores the maximum score difference
        // (Current Player - Opponent)
        // starting from index 'start'
        // with current value of M.
        int[][] dp = new int[n+1][2*n+1];

        // Build answers
        // from the end
        // towards the beginning.
        for(int start=n-1; start>=0; start--) {

            for(int M=2*n; M>=1; M--) {

                int maxDiff = Integer.MIN_VALUE;

                int stonesCount = 0;

                // Try every valid move.
                for(int x=1; x<=2*M && start+x<=n; x++) {

                    // Running sum avoids
                    // recomputing the taken stones.
                    stonesCount += piles[start+x-1];

                    // Current player's advantage
                    // equals:
                    //
                    // Stones taken now
                    //
                    // minus
                    //
                    // Opponent's best advantage.
                    maxDiff = Math.max(
                        maxDiff,
                        stonesCount - dp[start+x][Math.max(M,x)]
                    );
                }

                dp[start][M] = maxDiff;
            }
        }

        int maxDiff = dp[0][1];

        // Convert score difference
        // into Alice's actual score.
        return (total + maxDiff) / 2;
    }
}