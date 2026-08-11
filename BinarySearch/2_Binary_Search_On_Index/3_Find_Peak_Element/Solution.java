class Solution {
    public int findPeakElement(int[] nums) {

        // We need to find ANY peak element such that:
        //     nums[i] > nums[i-1] AND nums[i] > nums[i+1]
        //
        // The array may be unsorted, but we can still solve in O(log n)
        // using Binary Search based on slope direction.

        int i = 0, j = nums.length - 1;

        while (i <= j) {

            int mid = (i + j) / 2;

            // Get left neighbor safely
            // If mid is at index 0, treat left as -infinity
            // Reason:
            // Edge elements are allowed to be peaks
            long left = (mid - 1 < 0) ? Long.MIN_VALUE : nums[mid - 1];

            // Get right neighbor safely
            // If mid is at last index, treat right as -infinity
            long right = (mid + 1 == nums.length) ? Long.MIN_VALUE : nums[mid + 1];

            int curr = nums[mid];

            // Case 1:
            // If current element is greater than both neighbors,
            // then it is a PEAK by definition
            if (curr > left && curr > right) {

                // We found a valid peak, return immediately
                return mid;
            }

            // Case 2:
            // If current element is smaller than right neighbor,
            // then the slope is increasing to the RIGHT
            //
            // Reason:
            // If we move right, values are increasing,
            // and there MUST exist a peak somewhere on the right side
            else if (curr < right) {

                // Discard left half and search right
                i = mid + 1;
            }

            // Case 3:
            // Otherwise, slope is decreasing to the RIGHT,
            // which means slope is increasing to the LEFT
            //
            // Reason:
            // There MUST exist a peak somewhere on the left side
            else {

                // Discard right half and search left
                j = mid - 1;
            }
        }

        // Problem guarantees at least one peak exists,
        // so this line is theoretically unreachable
        return -1;
    }
}
