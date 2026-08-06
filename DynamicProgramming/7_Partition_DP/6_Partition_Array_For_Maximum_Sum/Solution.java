class Solution1 {

    public int maxSumAfterPartitioning(int[] nums, int k) {

        int n = nums.length;

        // memo[start]:
        // stores the maximum sum obtainable
        // from the suffix starting at index 'start'.
        Integer[] memo = new Integer[n];

        return recursion(nums, k, 0, memo);
    }

    private int recursion(int[] nums, int k, int start, Integer[] memo) {

        int n = nums.length;

        // Every element
        // has already been partitioned.
        //
        // No contribution remains.
        if(start==nums.length) return 0;

        // Reuse previously computed suffix.
        if(memo[start]!=null) return memo[start];

        int maxSum = 0;

        // Maximum element
        // inside the current partition.
        int maxNum = 0;

        // Current partition length.
        int length = 1;

        // Try every valid partition
        // starting from 'start'.
        //
        // Partition length
        // cannot exceed k.
        for(int end=start; end<Math.min(start+k, n); end++, length++) {

            // Update the largest element
            // incrementally instead of rescanning
            // the partition every time.
            maxNum = Math.max(maxNum, nums[end]);

            // Every element in the partition
            // becomes equal to the partition maximum.
            //
            // Contribution of current partition:
            //
            // maxNum × partition length
            //
            // Then recursively solve
            // the remaining suffix.
            maxSum = Math.max(
                maxSum,
                maxNum*length + recursion(nums, k, end+1, memo)
            );
        }

        memo[start] = maxSum;

        return maxSum;
    }
}

class Solution2 {

    public int maxSumAfterPartitioning(int[] nums, int k) {

        int n = nums.length;

        // dp[start]:
        // stores the maximum sum obtainable
        // from the suffix starting at index 'start'.
        int[] dp = new int[n+1];

        // dp[n] = 0
        //
        // Once every element
        // has been partitioned,
        // no additional contribution remains.

        // Build answers
        // from right to left
        // because every transition
        // moves to a larger starting index.
        for(int start=n-1; start>=0; start--) {

            int maxSum = 0;

            // Largest element
            // inside the current partition.
            int maxNum = 0;

            // Current partition length.
            int length = 1;

            // Try every partition
            // beginning at 'start'
            // whose length is at most k.
            for(int end=start; end<Math.min(start+k, n); end++, length++) {

                // Maintain the partition maximum
                // incrementally,
                // avoiding repeated scans.
                maxNum = Math.max(maxNum, nums[end]);

                // If this partition is chosen,
                // every element contributes
                // the partition maximum.
                //
                // Solve the remaining suffix
                // using the already computed DP value.
                maxSum = Math.max(
                    maxSum,
                    maxNum*length + dp[end+1]
                );
            }

            dp[start] = maxSum;
        }

        return dp[0];
    }
}