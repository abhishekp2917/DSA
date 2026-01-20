class Solution {
    public int search(int[] nums, int target) {

        // The array is sorted but ROTATED at some pivot.
        // We must find target in O(log n) time.

        // We use modified binary search by identifying
        // which half (left or right) is SORTED at each step.

        int i = 0, j = nums.length - 1;

        while (i <= j) {

            int mid = (i + j) / 2;
            int temp = nums[mid];

            // If we directly found the target, return index
            if (temp == target) return mid;

            // Case 1:
            // Mid element lies in the LEFT SORTED PART
            //
            // Condition:
            //   nums[0] <= nums[mid]  AND  nums[mid] > nums[last]
            //
            // Reason:
            //   In rotated array, left part contains values >= nums[0]
            //   while right part contains values <= nums[last]
            else if (temp >= nums[0] && temp > nums[nums.length - 1]) {

                // Now check if target lies inside this left sorted range
                // Range is: [nums[0] ... nums[mid])

                if (target >= nums[0] && target < temp) {

                    // Target lies in the left sorted half
                    // So discard right half
                    j = mid - 1;
                }

                else {

                    // Target does NOT lie in left sorted half
                    // So it must be in the right half
                    i = mid + 1;
                }
            }

            // Case 2:
            // Mid element lies in the RIGHT SORTED PART
            //
            // Reason:
            //   If not in left sorted region, it must be in right sorted region
            else {

                // Right sorted range is: (nums[mid] ... nums[last]]

                if (target > temp && target <= nums[nums.length - 1]) {

                    // Target lies inside the right sorted half
                    // So discard left half
                    i = mid + 1;
                }

                else {

                    // Target does NOT lie in right sorted half
                    // So it must be in the left half
                    j = mid - 1;
                }
            }
        }

        // If we exit the loop, target does not exist
        return -1;
    }
}
