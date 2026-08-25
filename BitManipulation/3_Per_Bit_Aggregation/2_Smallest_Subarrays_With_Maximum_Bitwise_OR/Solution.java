import java.util.Arrays;

class Solution {

    // Pattern: Bit Manipulation → Per Bit Aggregation (Nearest Right Set Bit)
    //
    // Idea:
    // For each index i, we want the smallest subarray starting at i
    // whose bitwise OR is maximum.
    //
    // The maximum OR is obtained by ensuring that every bit
    // that appears to the right of i is included in the subarray.
    //
    // Each bit is processed independently.

    public int[] smallestSubarrays(int[] nums) {

        int n = nums.length;

        // ans[i] stores the minimum length of the subarray
        // starting at index i with maximum OR
        int[] ans = new int[n];

        // Minimum possible length is 1 (the element itself)
        Arrays.fill(ans, 1);

        // Process each bit independently
        for (int bit = 0; bit < 30; bit++) {

            // Stores the nearest index to the right
            // where the current bit is set
            int nearestRightSetBitIdx = n;

            // Traverse from right to left
            for (int i = n - 1; i >= 0; i--) {

                int num = nums[i];

                // If current bit is set in nums[i],
                // update the nearest index
                if (((num >> bit) & 1) == 1) {
                    nearestRightSetBitIdx = i;
                }

                // If this bit appears somewhere to the right,
                // the subarray must extend till that position
                if (nearestRightSetBitIdx != n) {
                    ans[i] = Math.max(ans[i], nearestRightSetBitIdx - i + 1);
                }
            }
        }

        return ans;
    }
}
