import java.util.Arrays;

class Solution1 {

    public int minCost(int n, int[] cuts) {

        int m = cuts.length;

        // memo[start][end]:
        // stores the minimum cost
        // to perform all cuts
        // from cuts[start] to cuts[end].
        Integer[][] memo = new Integer[m][m];

        // Sorting ensures
        // every subproblem represents
        // a contiguous segment of the stick.
        Arrays.sort(cuts);

        return recursion(n, cuts, 0, m-1, memo);
    }

    private int recursion(int n, int[] cuts, int start, int end, Integer[][] memo) {

        // No cuts remain
        // inside this stick segment.
        if(start>end) return 0;

        // Reuse previously computed answer.
        if(memo[start][end]!=null) return memo[start][end];

        int stickCuttingCost = Integer.MAX_VALUE;

        // Try making every remaining cut first.
        //
        // The first cut determines
        // how the current stick
        // gets divided into two independent pieces.
        for(int i=start; i<=end; i++) {

            // Current stick boundaries
            // are determined by
            // the nearest cuts
            // outside the current interval.
            //
            // If there is no previous cut,
            // the stick starts at 0.
            //
            // If there is no next cut,
            // the stick ends at n.
            int stickLen = ((end+1<cuts.length)? cuts[end+1] : n)-((start-1>=0)? cuts[start-1] : 0);

            // Cost to perform
            // all cuts
            // on the left piece.
            int leftStickCuttingCost = recursion(n, cuts, start, i-1, memo);

            // Cost to perform
            // all cuts
            // on the right piece.
            int rightStickCuttingCost = recursion(n, cuts, i+1, end, memo);

            // Pay the current cutting cost once,
            // then recursively solve
            // the two resulting sticks.
            stickCuttingCost = Math.min(
                stickCuttingCost,
                stickLen + leftStickCuttingCost + rightStickCuttingCost
            );
        }

        memo[start][end] = stickCuttingCost;

        return stickCuttingCost;
    }
}

class Solution2 {

    public int minCost(int n, int[] cuts) {

        int m = cuts.length;

        // dp[start][end]:
        // stores the minimum cost
        // to perform all cuts
        // from cuts[start] to cuts[end].
        int[][] dp = new int[m][m];

        // Sort cuts
        // so every interval
        // represents one continuous stick segment.
        Arrays.sort(cuts);

        // Build smaller intervals first
        // because larger intervals
        // depend on them.
        for(int start=m-1; start>=0; start--) {

            for(int end=start; end<m; end++) {

                int stickCuttingCost = Integer.MAX_VALUE;

                // Assume every cut
                // is performed first
                // and choose the minimum cost.
                for(int i=start; i<=end; i++) {

                    // Length of the current stick
                    // before making the first cut.
                    int stickLen = ((end+1<m)? cuts[end+1] : n)-((start-1>=0)? cuts[start-1] : 0);

                    // Cost of solving
                    // the left sub-stick.
                    int leftStickCuttingCost = ((i-1>=start)? dp[start][i-1] : 0);

                    // Cost of solving
                    // the right sub-stick.
                    int rightStickCuttingCost = ((i+1<=end)? dp[i+1][end] : 0);

                    // Current cut divides the stick
                    // into two completely independent subproblems.
                    stickCuttingCost = Math.min(
                        stickCuttingCost,
                        stickLen + leftStickCuttingCost + rightStickCuttingCost
                    );
                }

                dp[start][end] = stickCuttingCost;
            }
        }

        return dp[0][m-1];
    }
}