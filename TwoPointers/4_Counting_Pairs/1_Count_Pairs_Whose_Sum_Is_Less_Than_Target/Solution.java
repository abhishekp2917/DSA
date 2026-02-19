import java.util.Collections;
import java.util.List;

class Solution {

    public int countPairs(List<Integer> nums, int target) {

        int n = nums.size();

        // This will store total number of valid pairs.
        int count = 0;

        // Sorting is necessary because:
        // It allows us to use two pointer technique
        // and count pairs efficiently.
        Collections.sort(nums);

        int left = 0;
        int right = n - 1;

        while (left < right) {

            int sum = nums.get(left) + nums.get(right);

            // If sum is too large,
            // we need a smaller value → move right pointer.
            if (sum >= target) {
                right--;
            }
            else {

                // If sum < target:
                //
                // Since array is sorted,
                // nums[left] + nums[right] < target
                //
                // Then nums[left] + nums[k] < target
                // for ALL k in range (left+1 ... right).
                //
                // So we can count all those pairs at once.
                count += (right - left);

                // Move left pointer to explore next base value.
                left++;
            }
        }

        return count;
    }
}
