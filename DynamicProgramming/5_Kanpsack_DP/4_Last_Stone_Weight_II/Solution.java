import java.util.Arrays;

class Solution1 {

    public int lastStoneWeightII(int[] stones) {

        int n = stones.length;

        // Total weight of all stones.
        //
        // Eventually the stones are partitioned
        // into two groups.
        int totalWeight = Arrays.stream(stones).sum();

        // memo[i][sum]:
        // stores the minimum possible remaining weight
        // after processing stones from index 'i'
        // when the first group currently has sum = 'sum'.
        //
        // Memoization avoids solving
        // the same partition repeatedly.
        Integer[][] memo = new Integer[n][totalWeight+1];

        return recursion(stones, totalWeight, 0, 0, memo);
    }

    private int recursion(int[] stones, int totalWeight, int i, int sum, Integer[][] memo) {

        // Base Case:
        //
        // Every stone has been assigned
        // to one of the two groups.
        //
        // Group 1 weight = sum
        // Group 2 weight = totalWeight - sum
        //
        // Remaining stone weight equals
        // the absolute difference
        // between the two groups.
        if(i==stones.length) {
            return Math.abs(totalWeight - 2*sum);
        }

        // Return previously computed answer.
        if(memo[i][sum]!=null) {
            return memo[i][sum];
        }

        int weight = stones[i];

        // Option 1:
        // Put current stone
        // into the first group.
        int include =
            recursion(
                stones,
                totalWeight,
                i+1,
                sum+weight,
                memo
            );

        // Option 2:
        // Put current stone
        // into the second group.
        //
        // Since second group's weight
        // can always be derived from:
        //
        // totalWeight - sum
        //
        // we only need to track
        // the first group's sum.
        int exclude =
            recursion(
                stones,
                totalWeight,
                i+1,
                sum,
                memo
            );

        // Choose the partition
        // producing the smaller difference.
        memo[i][sum] =
            Math.min(include, exclude);

        return memo[i][sum];
    }
}

class Solution2 {

    public int lastStoneWeightII(int[] stones) {

        int n = stones.length;

        int totalWeight = Arrays.stream(stones).sum();

        // dp[i][sum]:
        // stores the minimum remaining weight
        // after processing stones from index i
        // when the first group
        // currently has weight = sum.
        int[][] dp = new int[n+1][totalWeight+1];

        // Base Case:
        //
        // No stones remain.
        //
        // Compute final difference
        // between the two groups.
        for(int sum=0; sum<=totalWeight; sum++) {

            dp[n][sum] =
                Math.abs(totalWeight - 2*sum);
        }

        // Build answers backwards.
        for(int i=n-1; i>=0; i--) {

            int weight = stones[i];

            // Current group's sum
            // cannot exceed:
            //
            // totalWeight - weight
            //
            // otherwise adding current stone
            // would exceed total weight.
            for(int sum=totalWeight-weight; sum>=0; sum--) {

                // Option 1:
                // Add current stone
                // to first group.
                int include =
                    dp[i+1][sum+weight];

                // Option 2:
                // Keep current stone
                // in second group.
                int exclude =
                    dp[i+1][sum];

                // Store the better partition.
                dp[i][sum] =
                    Math.min(include, exclude);
            }
        }

        // Initially no stone
        // belongs to the first group.
        return dp[0][0];
    }
}