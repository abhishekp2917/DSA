class Solution1 {

    public int knapsack(int capacity, int[] values, int[] weights) {
        int n = values.length;
        Integer[][] memo = new Integer[n + 1][capacity + 1];
        return recursion(values, weights, n, capacity, memo);
    }

    private int recursion(int[] values, int[] weights, int arrLen, int capacity, Integer[][] memo) {
        if (arrLen==0 || capacity==0) return 0;
        if (memo[arrLen][capacity]!=null) return memo[arrLen][capacity];
        int idx = arrLen-1;
        int value = values[idx];
        int weight = weights[idx];
        int exclude = recursion(values, weights, arrLen-1, capacity, memo);
        int include = 0;
        if (capacity >= weight) {
            include = value + recursion(values, weights, arrLen-1, capacity-weight, memo);
        }
        memo[arrLen][capacity] = Math.max(exclude, include);
        return memo[arrLen][capacity];
    }
}

class Solution2 {

    public int knapsack(int capacity, int values[], int weights[]) {
        int n = values.length;
        int[][] dp = new int[n+1][capacity+1];
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int value = values[idx];
            int weight = weights[idx];
            for(int currCapacity=1; currCapacity<=capacity; currCapacity++) {
                dp[arrLen][currCapacity] = Math.max(
                    dp[arrLen-1][currCapacity],
                    ((currCapacity-weight>=0)? (value + dp[arrLen-1][currCapacity-weight]) : 0)
                );
            }
        }
        return dp[n][capacity];
    }
}
