import java.util.Arrays;

class Solution1 {

    public boolean stoneGame(int[] piles) {

        int n = piles.length;

        // Total number of stones in the game
        int totalStones = 0;
        for (int stones : piles) totalStones += stones;

        // memo[start][end] stores the maximum stones Alice can collect
        // from the subarray piles[start...end], assuming both players
        // play optimally.
        Integer[][] memo = new Integer[n][n];

        // Alice wins if she can collect more than half of the stones
        return totalStones < 2 * aliceStones(piles, 0, n - 1, memo);
    }

    private int aliceStones(int[] piles, int start, int end, Integer[][] memo) {

        // If no piles remain, Alice gains no stones
        if (start > end) return 0;

        // Use cached result if available
        if (memo[start][end] != null) return memo[start][end];

        // Case 1: Alice takes the first pile
        // Bob will then play optimally to minimize Alice's future gain.
        int takeFirst =
            piles[start] +
            Math.min(
                // Bob takes start+1
                aliceStones(piles, start + 2, end, memo),
                // Bob takes end
                aliceStones(piles, start + 1, end - 1, memo)
            );

        // Case 2: Alice takes the last pile
        int takeLast =
            piles[end] +
            Math.min(
                // Bob takes start
                aliceStones(piles, start + 1, end - 1, memo),
                // Bob takes end-1
                aliceStones(piles, start, end - 2, memo)
            );

        // Alice chooses the option that maximizes her total stones
        memo[start][end] = Math.max(takeFirst, takeLast);

        return memo[start][end];
    }
}

class Solution2 {

    public boolean stoneGame(int[] piles) {

        int n = piles.length;

        // Total stones present in the game.
        //
        // After computing Alice's maximum possible score,
        // we compare it against the remaining stones.
        int totalStones = Arrays.stream(piles).sum();

        // dp[start][end]:
        // stores the maximum stones the current player
        // can collect from piles[start...end],
        // assuming both players play optimally.
        int[][] dp = new int[n+1][n+1];

        // Base Case:
        // If only one pile remains,
        // the current player simply takes it.
        for(int start=0; start<n; start++) {
            int end = start;
            dp[start][end] = piles[start];
        }

        // Solve smaller intervals first
        // so larger intervals can reuse them.
        for(int start=n-1; start>=0; start--) {
            for(int end=start+1; end<n; end++) {

                // Option 1:
                // Take the left pile.
                //
                // Opponent now chooses optimally
                // and tries to minimize
                // our future score.
                //
                // Hence we take the minimum
                // of the two possible states
                // the opponent can leave for us.
                int takeFirst =
                    piles[start] + Math.min(
                        (start+2<=n) ? dp[start+2][end] : 0,
                        dp[start+1][end-1]
                    );

                // Option 2:
                // Take the right pile.
                //
                // Again,
                // opponent minimizes
                // our future gain.
                int takeLast =
                    piles[end] + Math.min(
                        (end-2>=0) ? dp[start][end-2] : 0,
                        dp[start+1][end-1]
                    );

                // Current player always chooses
                // the better of the two options.
                dp[start][end] = Math.max(
                    takeFirst,
                    takeLast
                );
            }
        }

        int aliceStones = dp[0][n-1];

        // Alice wins
        // if she collects
        // strictly more than half
        // of the total stones.
        return 2*aliceStones > totalStones;
    }
}

class Solution3 {

    public boolean stoneGame(int[] piles) {

        int n = piles.length;

        // memo[start][end] stores the maximum score difference
        // (current player stones - opponent stones)
        // achievable from subarray [start...end].
        Integer[][] memo = new Integer[n][n];

        // Alice starts first.
        // If she can guarantee a positive score difference, she wins.
        return scoreDiff(piles, 0, n - 1, memo) > 0;
    }

    private int scoreDiff(int[] piles, int start, int end, Integer[][] memo) {

        // No piles left → no score difference
        if (start > end) return 0;

        // Return cached result
        if (memo[start][end] != null) return memo[start][end];

        // Option 1:
        // Take the first pile.
        // Opponent then plays optimally on the remaining range.
        int stoneDiffIfTakenFirst =
            piles[start] -
            scoreDiff(piles, start + 1, end, memo);

        // Option 2:
        // Take the last pile.
        int stoneDiffIfTakenLast =
            piles[end] -
            scoreDiff(piles, start, end - 1, memo);

        // Current player chooses the move that maximizes advantage
        memo[start][end] = Math.max(stoneDiffIfTakenFirst, stoneDiffIfTakenLast);

        return memo[start][end];
    }
}

class Solution4 {

    public boolean stoneGame(int[] piles) {

        int n = piles.length;

        // dp[start][end]:
        // stores the maximum score difference
        // (Current Player - Opponent)
        // obtainable from piles[start...end].
        //
        // This formulation is simpler because
        // we no longer need to explicitly model
        // the opponent's minimizing move.
        int[][] dp = new int[n+1][n+1];

        // Base Case:
        // With only one pile,
        // current player gains
        // exactly that many stones,
        // while opponent gains zero.
        for(int start=0; start<n; start++) {
            int end = start;
            dp[start][end] = piles[start];
        }

        // Build answers
        // from smaller intervals
        // towards larger intervals.
        for(int start=n-1; start>=0; start--) {
            for(int end=start+1; end<n; end++) {

                // Take the left pile.
                //
                // After taking it,
                // players swap roles.
                //
                // Therefore the opponent's
                // best score difference
                // must be subtracted.
                int stoneDiffIfTakenFirst =
                    piles[start] - dp[start+1][end];

                // Take the right pile.
                //
                // Again subtract the opponent's
                // optimal score difference.
                int stoneDiffIfTakenLast =
                    piles[end] - dp[start][end-1];

                // Current player chooses
                // the move giving
                // the larger score difference.
                dp[start][end] = Math.max(
                    stoneDiffIfTakenFirst,
                    stoneDiffIfTakenLast
                );
            }
        }

        // Positive score difference means
        // Alice finishes
        // with more stones than Bob.
        return dp[0][n-1] > 0;
    }
}
