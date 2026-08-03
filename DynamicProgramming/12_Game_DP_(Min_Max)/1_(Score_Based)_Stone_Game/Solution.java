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
        int totalStones = Arrays.stream(piles).sum();
        int[][] dp = new int[n+1][n+1];
        for(int start=0; start<n; start++) {
            int end = start;
            dp[start][end] = piles[start];
        }
        for(int start=n-1; start>=0; start--) {
            for(int end=start+1; end<n; end++) {
                int maxStones = Math.max(
                    piles[start] + Math.min(
                        (start+2<=n)? dp[start+2][end] : 0,
                        dp[start+1][end-1]
                    ),
                    piles[end] + Math.min(
                        (end-2>=0)? dp[start][end-2] : 0,
                        dp[start+1][end-1]
                    )
                );
                dp[start][end] = maxStones;
            }
        } 
        int aliceStones = dp[0][n-1];
        return 2*aliceStones>totalStones;
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
        int[][] dp = new int[n+1][n+1];
        for(int start=0; start<n; start++) {
            int end = start;
            dp[start][end] = piles[start];
        }
        for(int start=n-1; start>=0; start--) {
            for(int end=start+1; end<n; end++) {
                int stoneDiffIfTakenFirst = piles[start] - dp[start+1][end];
                int stoneDiffIfTakenLast = piles[end] - dp[start][end-1];
                int minDiff = Math.max(
                    stoneDiffIfTakenFirst,
                    stoneDiffIfTakenLast
                );
                dp[start][end] = minDiff;
            }
        } 
        return dp[0][n-1]>0;
    }
}



