class Solution1 {
    
    public int minCostClimbingStairs(int[] cost) {

        int n = cost.length;

        // memo[i]:
        // stores minimum cost required
        // to reach stair i.
        //
        // Prevents solving the same subproblem repeatedly.
        Integer[] memo = new Integer[n];

        // Top (beyond last stair) can be reached
        // from either:
        // last stair
        // or
        // second last stair.
        //
        // Choose the cheaper option.
        return Math.min(
            recursion(cost, n-1, memo),
            recursion(cost, n-2, memo)
        );
    }

    private int recursion(int[] cost, int currStep, Integer[] memo) {

        // Base Case:
        //
        // Reaching first or second stair
        // costs exactly their own cost,
        // because we can start from either.
        if (currStep == 0) return cost[0];

        if (currStep == 1) return cost[1];

        // Return previously computed answer.
        if (memo[currStep] != null) return memo[currStep];

        // Last move was a single step.
        int costToReachLastStep =
            recursion(cost, currStep - 1, memo);

        // Last move was a double step.
        int costToReachLastSecondStep =
            recursion(cost, currStep - 2, memo);

        // Pay current stair cost,
        // then choose the cheaper previous path.
        memo[currStep] =
            cost[currStep] +
            Math.min(
                costToReachLastStep,
                costToReachLastSecondStep
            );

        return memo[currStep];
    }
}

class Solution2 {

    public int minCostClimbingStairs(int[] cost) {

        int n = cost.length;

        // dp[i]:
        // minimum cost required
        // to reach stair i.
        int[] dp = new int[n];

        // Base Cases:
        //
        // We may directly start
        // from stair 0 or stair 1.
        dp[0] = cost[0];

        dp[1] = cost[1];

        // Build answers from smaller stairs
        // towards larger stairs.
        for(int currStep=2; currStep<n; currStep++) {

            // Reach current stair
            // from previous stair.
            int costToReachLastStep =
                dp[currStep-1];

            // Reach current stair
            // from two stairs below.
            int costToReachLastSecondStep =
                dp[currStep-2];

            // Pay current stair cost,
            // then choose the cheaper path.
            dp[currStep] =
                cost[currStep] +
                Math.min(
                    costToReachLastStep,
                    costToReachLastSecondStep
                );
        }

        // Top is beyond last stair,
        // so it can be reached from:
        // last stair
        // or
        // second last stair.
        return Math.min(dp[n-1], dp[n-2]);
    }
}