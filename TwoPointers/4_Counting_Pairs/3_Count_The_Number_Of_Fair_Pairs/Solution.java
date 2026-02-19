import java.util.Arrays;

class Solution {

    public long countFairPairs(int[] nums, int lower, int upper) {

        // Sorting is required to apply two pointer counting logic.
        Arrays.sort(nums);

        // We need number of pairs with:
        //      lower <= sum <= upper
        //
        // Instead of counting directly in range,
        // we compute:
        //
        // count(sum <= upper) - count(sum <= lower-1)
        //
        // This converts range counting into prefix counting.
        return countPairLessThanEqualToK(nums, upper)
             - countPairLessThanEqualToK(nums, lower - 1);
    }

    private long countPairLessThanEqualToK(int[] nums, int k) {

        int n = nums.length;

        long count = 0;

        int left = 0;
        int right = n - 1;

        while (left < right) {

            long sum = (long) nums[left] + nums[right];

            // If sum is too large,
            // decrease it by moving right pointer.
            if (sum > k) {
                right--;
            }
            else {

                // If nums[left] + nums[right] <= k,
                //
                // Then for this fixed left,
                // all indices between (left+1 ... right)
                // will also form valid pairs.
                //
                // Because array is sorted,
                // and decreasing right only decreases sum.
                count += (right - left);

                // Move left to explore next base element.
                left++;
            }
        }

        return count;
    }
}
