class Solution {
    public int search(int[] nums, int target) {

        // We start with the full range of the array
        // 'start' and 'end' define the current portion of the array where the target might exist
        int start = 0, end = nums.length - 1;

        // We keep searching while there is still a valid range
        // If start crosses end, it means the target cannot exist anymore
        while (start <= end) {

            // We pick the middle index to divide the search space into two halves
            // Using this formula avoids integer overflow for very large indices
            int mid = start + (end - start) / 2;

            // If middle element is greater than target,
            // then all elements to the right of mid are also greater (array is sorted)
            // So the target CANNOT be on the right side → we discard the right half
            if (nums[mid] > target) {
                end = mid - 1;
            }

            // If middle element is smaller than target,
            // then all elements to the left of mid are also smaller
            // So the target CANNOT be on the left side → we discard the left half
            else if (nums[mid] < target) {
                start = mid + 1;
            }

            // If nums[mid] equals target,
            // we have directly found the required element
            // No more searching is needed
            else {
                return mid;
            }
        }

        // If we come out of the loop,
        // it means we eliminated all possible ranges without finding the target
        // Hence, the target does not exist in the array
        return -1;
    }
}
