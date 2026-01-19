class Solution {

    public int splitArray(int[] nums, int k) {

        // We need to split the array into exactly k subarrays
        // such that the MAXIMUM subarray sum is MINIMIZED.

        // We are NOT searching indices —
        // we are searching the ANSWER: the minimum possible maximum subarray sum.

        int minSum = 0;
        int maxSum = 0;

        // Determine the binary search range:

        for (int num : nums) {

            // Lower bound:
            // The maximum subarray sum can never be smaller than the largest element,
            // because that element must belong to some subarray.
            minSum = Math.max(minSum, num);

            // Upper bound:
            // If we take the entire array as one subarray,
            // maximum subarray sum = total sum of array.
            maxSum += num;
        }

        // This will store the smallest feasible maximum subarray sum found
        int minSubarraySum = 0;

        // Binary search on the ANSWER (maximum allowed subarray sum)
        while (minSum <= maxSum) {

            // Try a candidate maximum subarray sum
            int subarraySum = minSum + (maxSum - minSum) / 2;

            // Compute how many subarrays are REQUIRED
            // if no subarray is allowed to exceed 'subarraySum'
            int subarrayCount = subarrayCount(nums, subarraySum);

            // If we can split into AT MOST k subarrays,
            // then this maximum sum is FEASIBLE
            if (subarrayCount <= k) {

                // Store this as a possible answer
                minSubarraySum = subarraySum;

                // Try to minimize it further
                // Reason: we want the smallest possible maximum sum
                maxSum = subarraySum - 1;
            }

            // If more than k subarrays are needed,
            // then this maximum sum is too small
            // We must allow larger subarray sums
            else {
                minSum = subarraySum + 1;
            }
        }

        // minSubarraySum is the minimum possible largest subarray sum
        return minSubarraySum;
    }

    private int subarrayCount(int[] nums, int maxSubarraySum) {

        // This function calculates:
        // Minimum number of subarrays required such that
        // no subarray has sum greater than maxSubarraySum.

        int subarrayCount = 1;   // At least one subarray always exists
        int subarraySum = 0;

        for (int num : nums) {

            // If adding this number exceeds allowed maximum,
            // we MUST start a new subarray here
            if (subarraySum + num > maxSubarraySum) {

                // Start a new subarray with current number
                subarraySum = num;

                // Increase subarray count
                subarrayCount++;
            }

            // Otherwise, continue extending the current subarray
            else {
                subarraySum += num;
            }
        }

        // Return how many subarrays were needed
        return subarrayCount;
    }
}
