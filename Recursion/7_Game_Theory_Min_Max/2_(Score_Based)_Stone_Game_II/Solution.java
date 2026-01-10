import java.util.Arrays;

class Solution {

    public int stoneGameII(int[] piles) {

        int n = piles.length;

        // pilesSuffix[i] stores the total stones from index i to the end
        // This allows us to quickly compute how many stones are still available
        // from any starting index.
        int[] pilesSuffix = new int[n + 1];

        // memo[start][m] stores the maximum stones the current player can collect
        // starting from index `start` when the current value of M is `m`.
        int[][] memo = new int[n][n + 1];

        // Build suffix sum and initialize memo
        for (int i = n - 1; i >= 0; i--) {
            pilesSuffix[i] = pilesSuffix[i + 1] + piles[i];
            Arrays.fill(memo[i], -1);
        }

        // Game starts from index 0 with M = 1
        return playerStones(pilesSuffix, n, 0, 1, memo);
    }

    private int playerStones(
        int[] pilesSuffix,
        int n,
        int start,
        int m,
        int[][] memo
    ) {

        // If all piles are taken, no stones can be collected
        if (start >= n) return 0;

        // Return cached result if already computed
        if (memo[start][m] != -1) return memo[start][m];

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
