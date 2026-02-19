import java.util.Arrays;

class Solution {

    public int triangleNumber(int[] nums) {

        int n = nums.length;

        // This will store the total number of valid triangles.
        int count = 0;

        // Sorting is crucial because:
        // 1) Triangle inequality becomes easier to check.
        // 2) It enables two-pointer optimization.
        Arrays.sort(nums);

        // Fix the largest side of the triangle.
        // We iterate from the end because after sorting,
        // nums[i] will act as the largest side.
        for (int i = n - 1; i >= 2; i--) {

            int largestSide = nums[i];

            int left = 0;
            int right = i - 1;

            // Now we need to find pairs (left, right)
            // such that:
            // nums[left] + nums[right] > largestSide
            //
            // (Triangle inequality condition)

            while (left < right) {

                int sum = nums[left] + nums[right];

                // If sum is not large enough,
                // increase it by moving left pointer.
                if (sum <= largestSide) {
                    left++;
                }
                else {

                    // If nums[left] + nums[right] > largestSide,
                    // then ALL elements between left and right-1
                    // will also satisfy the condition with nums[right].
                    //
                    // Because array is sorted:
                    // nums[left+1] >= nums[left]
                    //
                    // So we can count all in one go.
                    count += (right - left);

                    // Try next smaller right to find more combinations.
                    right--;
                }
            }
        }

        return count;
    }
}
