import java.util.Arrays;

class Solution {

    public int threeSumClosest(int[] nums, int target) {

        int n = nums.length;

        // closestDiff stores the minimum difference seen so far
        // between target and any triplet sum.
        int closestDiff = Integer.MAX_VALUE;

        // closestSum stores the corresponding sum that produced
        // the minimum difference.
        int closestSum = 0;

        // Sorting is required because:
        // 1) It allows two pointer technique.
        // 2) It ensures monotonic movement (left increases sum, right decreases sum).
        Arrays.sort(nums);

        // Fix the first element of the triplet.
        for (int i = 0; i < n - 2; i++) {

            int left = i + 1;
            int right = n - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];

                // Compute how far current sum is from target.
                int diff = Math.abs(target - sum);

                // If this sum is closer to target than previous best,
                // update closestSum and closestDiff.
                if (diff < closestDiff) {
                    closestSum = sum;
                    closestDiff = diff;
                }

                // If sum is smaller than target,
                // we need a larger value → move left pointer right.
                if (sum < target) {
                    left++;
                }

                // If sum is greater than or equal to target,
                // we try reducing the sum → move right pointer left.
                else {
                    right--;
                }
            }
        }

        return closestSum;
    }
}
