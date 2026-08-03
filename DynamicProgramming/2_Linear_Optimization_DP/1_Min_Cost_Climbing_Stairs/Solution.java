class Solution1 {
    
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        Integer[] memo = new Integer[n];
        return Math.min(
            recursion(cost, n-1, memo),
            recursion(cost, n-2, memo)
        );
    }

    private int recursion(int[] cost, int currStep, Integer[] memo) {
        if (currStep == 0) return cost[0];
        if (currStep == 1) return cost[1];
        if (memo[currStep] != null) return memo[currStep];
        int costToReachLastStep = recursion(cost, currStep - 1, memo);
        int costToReachLastSecondStep = recursion(cost, currStep - 2, memo);
        memo[currStep] = cost[currStep] + Math.min(
            costToReachLastStep,
            costToReachLastSecondStep
        );
        return memo[currStep];
    }
}

class Solution2 {

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for(int currStep=2; currStep<n; currStep++) {
            int costToReachLastStep = dp[currStep-1];
            int costToReachLastSecondStep = dp[currStep-2];
            dp[currStep] = cost[currStep] + Math.min(
                costToReachLastStep,
                costToReachLastSecondStep
            );
        }
        return Math.min(dp[n-1], dp[n-2]);
    }
}