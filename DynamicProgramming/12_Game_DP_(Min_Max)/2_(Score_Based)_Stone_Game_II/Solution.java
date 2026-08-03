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
        int[] pilesSuffix = new int[n+1];
        int[][] dp = new int[n+1][n+1];
        for(int i=n-1; i>=0; i--) {
            pilesSuffix[i] = pilesSuffix[i+1]+piles[i];
        }

        for(int start=n-1; start>=0; start--) {
            for(int m=n; m>=1; m--) {
                int stones=0;
                for(int x=1; x<=2*m && start+x<=n; x++) {
                    stones=Math.max(
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
        int total = Arrays.stream(piles).sum();
        Integer[][] memo = new Integer[n][2*n+1];
        int maxDiff = stonesDifference(piles, 0, 1, memo);
        return (total+maxDiff)/2;
    }

    private int stonesDifference(int[] piles, int start, int M, Integer[][] memo) {
        int n = piles.length;
        if(start==n) return 0;
        if(memo[start][M]!=null) return memo[start][M];
        int maxDiff = Integer.MIN_VALUE;
        int stonesCount = 0;
        for(int x=1; x<=2*M && start+x<=n; x++) {
            stonesCount += piles[start+x-1];
            maxDiff = Math.max(
                maxDiff,
                stonesCount - stonesDifference(piles, start+x, Math.max(M, x), memo)
            );
        }
        memo[start][M] = maxDiff;
        return maxDiff;
    }
}

class Solution4 {
    public int stoneGameII(int[] piles) {
        int n=piles.length;
        int total=Arrays.stream(piles).sum();
        int[][] dp=new int[n+1][2*n+1];
        
        for(int start=n-1; start>=0; start--) {
            for(int M=2*n; M>=1; M--) {
                int maxDiff=Integer.MIN_VALUE;
                int stonesCount=0;
                for(int x=1; x<=2*M && start+x<=n; x++) {
                    stonesCount += piles[start+x-1];
                    maxDiff = Math.max(
                        maxDiff,
                        stonesCount-dp[start+x][Math.max(M,x)]
                    );
                }

                dp[start][M] = maxDiff;
            }
        }

        int maxDiff = dp[0][1];
        return (total+maxDiff)/2;
    }
}