class Solution {
    public boolean search(int[] nums, int target) {

        // The array is sorted but rotated AND may contain DUPLICATES.
        // We must find whether target exists in O(log n) average time.

        // Duplicates break the usual rotated binary search logic,
        // because we may NOT be able to determine which half is sorted.

        int i = 0, j = nums.length - 1;

        while (i <= j) {

            int mid = (i + j) / 2;
            int temp = nums[mid];

            // If mid directly equals target, we are done
            if (temp == target) return true;

            // Case 1:
            // Mid lies in the LEFT SORTED PART (or ambiguous because of duplicates)
            //
            // Condition:
            //   temp >= nums[i]  AND  temp >= nums[j]
            //
            // Reason:
            //   In rotated array:
            //     - Left sorted region has values >= nums[i]
            //     - Right rotated region has smaller tail values
            else if (temp >= nums[i] && temp >= nums[j]) {

                // Special ambiguous case:
                // nums[i] == nums[mid] == nums[j]
                //
                // Reason:
                // All three equal → we CANNOT determine which half is sorted.
                // The only safe move is to shrink both ends.
                if (temp == nums[i] && temp == nums[j]) {

                    // Shrink search window by 1 from both sides
                    // Reason:
                    // These boundary values are useless for deciding sorted half
                    i++;
                    j--;
                }

                // Now normal left-sorted logic applies
                else if (target >= nums[i] && target < temp) {

                    // Target lies inside LEFT sorted range
                    // So discard RIGHT half
                    j = mid - 1;
                }

                else {

                    // Target does NOT lie in left sorted half
                    // So it must be in RIGHT half
                    i = mid + 1;
                }
            }

            // Case 2:
            // Mid lies in the RIGHT SORTED PART
            //
            // Reason:
            // If not in left-sorted region, it must be in right-sorted region
            else {

                // Right sorted range is: (nums[mid] ... nums[j]]

                if (target > temp && target <= nums[j]) {

                    // Target lies inside RIGHT sorted half
                    // So discard LEFT half
                    i = mid + 1;
                }

                else {

                    // Target does NOT lie in right sorted half
                    // So it must be in LEFT half
                    j = mid - 1;
                }
            }
        }

        // If we exit loop, target does not exist
        return false;
    }
}
