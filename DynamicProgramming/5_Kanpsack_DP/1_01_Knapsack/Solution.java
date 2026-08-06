class Solution1 {

    public int knapsack(int capacity, int[] values, int[] weights) {

        int n = values.length;

        // memo[arrLen][capacity]:
        // stores the maximum value obtainable
        // using the first 'arrLen' items
        // with remaining knapsack capacity = capacity.
        //
        // Memoization prevents solving
        // the same subproblem repeatedly.
        Integer[][] memo = new Integer[n + 1][capacity + 1];

        return recursion(values, weights, n, capacity, memo);
    }

    private int recursion(int[] values, int[] weights, int arrLen, int capacity, Integer[][] memo) {

        // Base Case:
        //
        // No items remain
        // OR
        // Knapsack is already full.
        //
        // Therefore no additional value
        // can be obtained.
        if (arrLen==0 || capacity==0) return 0;

        // Return previously computed answer.
        if (memo[arrLen][capacity]!=null) {
            return memo[arrLen][capacity];
        }

        // Current item under consideration.
        int idx = arrLen-1;

        int value = values[idx];

        int weight = weights[idx];

        // Option 1:
        // Skip the current item.
        //
        // Capacity remains unchanged.
        int exclude =
            recursion(
                values,
                weights,
                arrLen-1,
                capacity,
                memo
            );

        int include = 0;

        // Option 2:
        // Take the current item.
        //
        // This is possible only if
        // the remaining capacity is sufficient.
        if (capacity >= weight) {

            include =
                value +
                recursion(
                    values,
                    weights,
                    arrLen-1,
                    capacity-weight,
                    memo
                );
        }

        // Choose the better decision.
        memo[arrLen][capacity] =
            Math.max(exclude, include);

        return memo[arrLen][capacity];
    }
}

class Solution2 {

    public int knapsack(int capacity, int values[], int weights[]) {

        int n = values.length;

        // dp[arrLen][capacity]:
        // stores the maximum value obtainable
        // using the first 'arrLen' items
        // with knapsack capacity = capacity.
        int[][] dp = new int[n+1][capacity+1];

        // Build answers by considering
        // one additional item at a time.
        for(int arrLen=1; arrLen<=n; arrLen++) {

            int idx = arrLen-1;

            int value = values[idx];

            int weight = weights[idx];

            // Try every possible capacity.
            for(int currCapacity=1; currCapacity<=capacity; currCapacity++) {

                // Option 1:
                // Skip the current item.
                int exclude =
                    dp[arrLen-1][currCapacity];

                int include = 0;

                // Option 2:
                // Include the current item.
                //
                // Remaining capacity becomes:
                // currCapacity - weight.
                if(currCapacity>=weight) {

                    include =
                        value +
                        dp[arrLen-1][currCapacity-weight];
                }

                // Store the better decision.
                dp[arrLen][currCapacity] =
                    Math.max(exclude, include);
            }
        }

        // Last state considers
        // all items
        // and full knapsack capacity.
        return dp[n][capacity];
    }
}