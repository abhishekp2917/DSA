class Solution1 {

    public int climbStairs(int n) {

        // memo[i]:
        // stores number of ways to reach stair i.
        //
        // Prevents solving the same subproblem repeatedly.
        Integer[] memo = new Integer[n + 1];

        return recursion(n, memo);
    }

    private int recursion(int n, Integer[] memo) {

        // Base Case:
        //
        // There is exactly ONE way to stand at stair 0:
        // do nothing.
        //
        // There is exactly ONE way to reach stair 1:
        // take one step.
        if (n == 0 || n == 1) return 1;

        // Return previously computed answer.
        if (memo[n] != null) return memo[n];

        // If the last move was a single step,
        // we must have come from stair (n-1).
        int waysIfLastStepWasOne = recursion(n-1, memo);

        // If the last move was a double step,
        // we must have come from stair (n-2).
        int waysIfLastStepWasTwo = recursion(n-2, memo);

        // Total ways =
        // ways from both possible previous stairs.
        memo[n] = waysIfLastStepWasOne + waysIfLastStepWasTwo;

        return memo[n];
    }
}

class Solution2 {
    
    public int climbStairs(int n) {

        // dp[i]:
        // number of ways to reach stair i.
        int[] dp = new int[n+1];

        // Base Cases:
        //
        // Stair 0:
        // one valid way (stay where you are).
        dp[0] = 1;

        // Stair 1:
        // only one possible path.
        dp[1] = 1;

        // Build answers from smaller stairs
        // towards larger stairs.
        for(int i=2; i<=n; i++) {

            // Reach stair i by taking
            // one step from (i-1).
            int waysIfLastStepWasOne = dp[i-1];

            // Reach stair i by taking
            // two steps from (i-2).
            int waysIfLastStepWasTwo = dp[i-2];

            // Both choices are independent,
            // so total ways are added.
            dp[i] = waysIfLastStepWasOne + waysIfLastStepWasTwo;
        }

        return dp[n];
    }
}