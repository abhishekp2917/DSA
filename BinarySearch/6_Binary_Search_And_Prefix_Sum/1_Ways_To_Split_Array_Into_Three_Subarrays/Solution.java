class Solution {

    private static final int MOD = 1_000_000_007;

    public int waysToSplit(int[] nums) {

        // We want to split the array into:
        // left | mid | right
        // such that:
        //     sum(left) <= sum(mid) <= sum(right)
        //
        // Instead of trying all pairs of splits (O(n^2)),
        // we fix the first split and binary search valid positions for the second split.

        int length = nums.length;
        long validSplitsCount = 0;

        // Build prefix sum so that we can compute any subarray sum in O(1)
        // Reason: repeated sum calculations inside binary search must be fast
        long[] prefixSum = new long[length + 1];
        for (int index = 1; index <= length; index++) {
            prefixSum[index] = prefixSum[index - 1] + nums[index - 1];
        }

        // Fix the end of the LEFT part
        // We leave at least one element for mid and one for right
        for (int firstSplitEnd = 1; firstSplitEnd < length - 1; firstSplitEnd++) {

            // Once left part is fixed,
            // valid second split positions form a CONTINUOUS RANGE
            // because midSum increases and rightSum decreases monotonically

            // Find the earliest valid second split
            int lowerSecondSplit =
                    findLowerValidSecondSplit(prefixSum, firstSplitEnd + 1);

            // If even the earliest valid split does not exist,
            // then no split is possible for this left part
            if (lowerSecondSplit == -1) continue;

            // Find the latest valid second split
            int upperSecondSplit =
                    findUpperValidSecondSplit(prefixSum, firstSplitEnd + 1);

            // All indices between these two are valid
            // Reason: validity condition is monotonic and continuous in this range
            validSplitsCount += (upperSecondSplit - lowerSecondSplit + 1);
        }

        // Return answer modulo as required
        return (int) (validSplitsCount % MOD);
    }

    /**
     * Finds the SMALLEST index for second split where:
     *      leftSum <= midSum <= rightSum
     */
    private int findLowerValidSecondSplit(long[] prefixSum, int secondSplitStart) {

        // Second split must leave at least one element for right part
        int left = secondSplitStart;
        int right = prefixSum.length - 2;

        // Will store the earliest valid split index
        int validIndex = -1;

        // leftSum is FIXED for this first split
        // Reason: first split is fixed, so leftSum never changes during this search
        long leftSum = prefixSum[secondSplitStart - 1];

        // Total sum used to compute rightSum efficiently
        long totalSum = prefixSum[prefixSum.length - 1];

        // Binary search to find FIRST valid position
        while (left <= right) {

            int mid = left + (right - left) / 2;

            // midSum = sum of middle part
            // Reason: prefix sums allow constant time subarray sum
            long middleSum = prefixSum[mid] - leftSum;

            // rightSum = remaining part after removing left and mid
            long rightSum = totalSum - middleSum - leftSum;

            // Check the required condition:
            // leftSum <= middleSum <= rightSum
            if (middleSum >= leftSum && rightSum >= middleSum) {

                // mid satisfies the condition
                validIndex = mid;

                // Try to move LEFT to find the earliest valid index
                // Reason: we want the first boundary of valid range
                right = mid - 1;
            }

            // If middleSum is smaller than leftSum,
            // then mid is too small → move right to increase middleSum
            else if (middleSum < leftSum) {
                left = mid + 1;
            }

            // Else case means:
            // middleSum is too large OR rightSum is too small
            // So mid is too big → move left
            else {
                right = mid - 1;
            }
        }

        return validIndex;
    }

    /**
     * Finds the LARGEST index for second split where:
     *      leftSum <= midSum <= rightSum
     */
    private int findUpperValidSecondSplit(long[] prefixSum, int secondSplitStart) {

        int left = secondSplitStart;
        int right = prefixSum.length - 2;

        // Will store the last valid split index
        int validIndex = -1;

        long leftSum = prefixSum[secondSplitStart - 1];
        long totalSum = prefixSum[prefixSum.length - 1];

        // Binary search to find LAST valid position
        while (left <= right) {

            int mid = left + (right - left) / 2;

            long middleSum = prefixSum[mid] - leftSum;
            long rightSum = totalSum - middleSum - leftSum;

            if (middleSum >= leftSum && rightSum >= middleSum) {

                // mid is valid
                validIndex = mid;

                // Try to move RIGHT to find the farthest valid index
                // Reason: we want the last boundary of valid range
                left = mid + 1;
            }

            // If middleSum too small → move right
            else if (middleSum < leftSum) {
                left = mid + 1;
            }

            // If middleSum too large or rightSum too small → move left
            else {
                right = mid - 1;
            }
        }

        return validIndex;
    }
}
