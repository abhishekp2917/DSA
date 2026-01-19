class Solution {
    public int[] searchRange(int[] nums, int target) {

        // We need to find:
        // - the first position of target
        // - the last position of target
        // Using binary search twice:
        //   1st search → leftmost occurrence
        //   2nd search → rightmost occurrence

        int start = 0, end = nums.length - 1;

        // Initialize answer with -1 (default if target not found)
        int[] ans = new int[]{-1, -1};

        // ---------- First Binary Search: Find LEFTMOST occurrence ----------

        // We try to push the search as far left as possible
        while (start <= end) {

            // Middle index of current range
            int mid = (start + end) / 2;

            // If nums[mid] is greater than or equal to target,
            // then the first occurrence can only be on the LEFT side (including mid)
            if (nums[mid] >= target) {

                // If this mid equals target, it is a possible answer
                // But there might exist an earlier occurrence on the left
                if (nums[mid] == target) {
                    ans[0] = mid;
                }

                // Continue searching in the left half to find the earliest index
                end = mid - 1;
            }

            // If nums[mid] is smaller than target,
            // then the first occurrence must lie on the RIGHT side
            else {
                start = mid + 1;
            }
        }

        // Reset pointers for the second binary search
        start = 0;
        end = nums.length - 1;

        // ---------- Second Binary Search: Find RIGHTMOST occurrence ----------

        // We try to push the search as far right as possible
        while (start <= end) {

            // Middle index of current range
            int mid = (start + end) / 2;

            // If nums[mid] is greater than target,
            // then the last occurrence must be on the LEFT side
            if (nums[mid] > target) {
                end = mid - 1;
            }

            // If nums[mid] is less than or equal to target,
            // then the last occurrence can be at mid or on the RIGHT side
            else {

                // If this mid equals target, it is a possible answer
                // But there might exist a later occurrence on the right
                if (nums[mid] == target) {
                    ans[1] = mid;
                }

                // Continue searching in the right half to find the latest index
                start = mid + 1;
            }
        }

        // After both searches, ans[0] holds first position
        // and ans[1] holds last position (or -1 if not found)
        return ans;
    }
}
