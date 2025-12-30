class Solution {

    // Pattern: Bit Manipulation → Per Bit Aggregation + Sliding Window
    //
    // Goal:
    // Find the minimum length subarray whose bitwise OR is >= k.
    //
    // Approach:
    // Use a sliding window and maintain:
    // - The current OR value of the window
    // - Count of set bits per bit position inside the window

    public int minimumSubarrayLength(int[] nums, int k) {

        int n = nums.length;

        // Stores the minimum valid subarray length found
        int len = Integer.MAX_VALUE;

        // Current bitwise OR of the sliding window
        int or = 0;

        // setBitsCount[bit] = how many numbers in the window
        // have this bit set
        int[] setBitsCount = new int[30];

        // Left pointer of the sliding window
        int start = 0;

        // Right pointer expands the window
        for (int i = 0; i < nums.length; i++) {

            // Add nums[i] into the window
            for (int bit = 0; bit < 30; bit++) {
                setBitsCount[bit] += (nums[i] >> bit) & 1;
                or |= (((nums[i] >> bit) & 1) << bit);
            }

            // Try to shrink the window from the left
            // while the OR condition is satisfied
            while (or >= k && start <= i) {

                // Update minimum length
                len = Math.min(len, i - start + 1);

                // Remove nums[start] from the window
                for (int bit = 0; bit < 30; bit++) {
                    setBitsCount[bit] -= (nums[start] >> bit) & 1;

                    // If no number in the window has this bit set,
                    // remove it from the OR value
                    if (setBitsCount[bit] == 0) {
                        or &= ~(1 << bit);
                    }
                }

                // Move left pointer forward
                start++;
            }
        }

        // If no valid subarray was found, return -1
        return (len != Integer.MAX_VALUE) ? len : -1;
    }
}
