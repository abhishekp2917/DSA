class Solution {

    // Pattern: Bit Manipulation → Greedy Partitioning using Bitwise AND
    //
    // Goal:
    // Split the array into the maximum number of subarrays
    // such that the bitwise AND of each subarray is equal to 0.

    public int maxSubarrays(int[] nums) {

        int count = 0;

        // Compute the AND of the entire array
        int minAnd = nums[0];
        for (int i = 0; i < nums.length; i++) {
            minAnd &= nums[i];
        }

        // If the AND of the entire array is not zero,
        // it is impossible to form more than one valid subarray
        if (minAnd != 0) {
            return 1;
        }

        // Current AND value while forming subarrays
        int and = nums[0];

        // Traverse the array to greedily form subarrays
        for (int i = 0; i < nums.length; i++) {

            // Update AND with the current element
            and &= nums[i];

            // If AND becomes zero, a valid subarray is formed
            if (and == 0) {
                count++;

                // Start a new subarray from the next element
                if (i + 1 < nums.length) {
                    and = nums[i + 1];
                }
            }
        }

        return count;
    }
}
