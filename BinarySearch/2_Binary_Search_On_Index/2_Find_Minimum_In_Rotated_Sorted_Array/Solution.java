class Solution {
    public int findMin(int[] nums) {

        // The array is sorted but rotated (no duplicates).
        // We need to find the MINIMUM element in O(log n).

        // We use modified binary search by identifying
        // whether mid lies in the left sorted part or the right rotated part.

        int i = 0, j = nums.length - 1;

        // Initialize answer with first element
        // Reason:
        // In worst case (not rotated), nums[0] itself is the minimum
        int ans = nums[0];

        while (i <= j) {

            int mid = (i + j) / 2;
            int temp = nums[mid];

            // Case 1:
            // mid lies in the LEFT SORTED PART
            //
            // Condition:
            //   nums[mid] >= nums[0]  AND  nums[mid] >= nums[last]
            //
            // Reason:
            //   In rotated array:
            //     - Left sorted part has values >= nums[0]
            //     - Right rotated part has values <= nums[last]
            //
            // If mid is in left sorted part,
            // then the minimum cannot be in [i ... mid]
            // because all these values are >= nums[0]
            if (temp >= nums[0] && temp >= nums[nums.length - 1]) {

                // Discard left half and move to the right
                // Reason:
                // The rotation point (minimum) must be on the right side
                i = mid + 1;
            }

            // Case 2:
            // mid lies in the RIGHT ROTATED PART
            //
            // Reason:
            //   This region contains the rotation pivot and the minimum value
            else {

                // mid is a candidate for minimum
                ans = temp;

                // Try to find an even smaller value on the LEFT side of mid
                // Reason:
                // The minimum in this region lies towards the left boundary
                j = mid - 1;
            }
        }

        // ans stores the smallest element found
        return ans;
    }
}
