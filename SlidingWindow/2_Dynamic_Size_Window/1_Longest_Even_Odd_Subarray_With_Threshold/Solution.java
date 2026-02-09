class Solution {
    public int longestAlternatingSubarray(int[] nums, int threshold) {

        // We need the longest contiguous subarray such that:
        // 1) Every element <= threshold
        // 2) First element is EVEN
        // 3) Parity alternates (even, odd, even, odd, ...)

        // Since we are dealing with a contiguous subarray
        // and expanding/shrinking based on constraints,
        // we use Sliding Window.

        int n = nums.length;

        int maxLen = 0;

        int left = 0, right = 0;

        while (right < n) {

            // If current element exceeds threshold,
            // this window becomes invalid.
            // We must restart window after this element.
            if (nums[right] > threshold) {

                // Reason:
                // No valid subarray can include this element.
                left = right + 1;
            }

            // If parity does not alternate compared to previous element,
            // reset window starting at current position.
            //
            // Condition:
            // left < right ensures we are inside a window.
            if (left < right && nums[right] % 2 == nums[right - 1] % 2) {

                // Reason:
                // Alternation is broken,
                // so the longest valid window must restart here.
                left = right;
            }

            // Ensure the window always starts with an EVEN number.
            //
            // If starting element is odd,
            // we move left forward until we find an even number.
            while (left <= right && nums[left] % 2 != 0) {

                // Reason:
                // Subarray must start with even number.
                left++;
            }

            // If current window is valid,
            // update maximum length.
            //
            // Window length = right - left + 1
            maxLen = Math.max(maxLen, right - left + 1);

            // Expand window
            right++;
        }

        return maxLen;
    }
}
