class Solution {
    public double findMaxAverage(int[] nums, int k) {

        // We need to find the maximum average
        // among all contiguous subarrays of size exactly k.

        // Since subarray size is FIXED (k),
        // we use Sliding Window instead of recomputing sums repeatedly.

        int n = nums.length;

        double sum = 0;

        int start = 0, end = 0;

        // Step 1:
        // Build the first window of size k
        //
        // Reason:
        // This gives us the starting window sum,
        // and all other windows can be derived from this.
        while (end < k) {
            sum += nums[end];
            end++;
        }

        // Compute average of first window
        double maxAvg = sum / k;

        // Step 2:
        // Slide the window across the array
        //
        // For each step:
        //   - Remove element going out
        //   - Add new element coming in
        while (end < n) {

            // Remove leftmost element of previous window
            sum -= nums[start];

            // Add new element at the right
            sum += nums[end];

            // Update maximum average
            // Reason:
            // Every window has exactly k elements,
            // so comparing sums is equivalent to comparing averages
            maxAvg = Math.max(maxAvg, sum / k);

            // Move window forward
            start++;
            end++;
        }

        // maxAvg stores the highest average found
        return maxAvg;
    }
}
