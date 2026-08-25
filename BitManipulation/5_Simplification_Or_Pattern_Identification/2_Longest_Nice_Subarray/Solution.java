class Solution {

    // Pattern: Bit Manipulation → Sliding Window with Bitwise Conflict Check
    //
    // Goal:
    // Find the longest subarray where no two elements share a common set bit.

    public int longestNiceSubarray(int[] nums) {

        int maxLen = 0;

        // Stores the OR of elements currently in the window
        int or = 0;

        int start = 0, end = 0;

        // Expand the window using end pointer
        while (end < nums.length) {

            // If nums[end] shares any set bit with current window,
            // shrink the window from the left until conflict is resolved
            while (start <= end && (nums[end] & or) != 0) {
                or ^= nums[start++];
            }

            // Add nums[end] to the window
            or |= nums[end];

            // Update maximum window length
            maxLen = Math.max(maxLen, end - start + 1);

            // Move right pointer forward
            end++;
        }

        return maxLen;
    }
}
