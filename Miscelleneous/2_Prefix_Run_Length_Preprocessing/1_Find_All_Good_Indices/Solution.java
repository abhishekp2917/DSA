import java.util.*;

class Solution {

    public List<Integer> goodIndices(int[] nums, int k) {

        int n = nums.length;

        // --------------------------------------------------
        // left[i] = length of non-increasing sequence ending at i
        // --------------------------------------------------
        //
        // Meaning:
        // How many consecutive elements (including i)
        // form a non-increasing sequence ending at i
        //
        // Example:
        // nums = [5,4,4,3]
        // left = [1,2,3,4]
        int[] left = new int[n];

        // --------------------------------------------------
        // right[i] = length of non-decreasing sequence starting at i
        // --------------------------------------------------
        //
        // Meaning:
        // How many consecutive elements (including i)
        // form a non-decreasing sequence starting at i
        int[] right = new int[n];


        // --------------------------------------------------
        // STEP 1: Build left array
        // --------------------------------------------------
        left[0] = 1;

        for (int i = 1; i < n; i++) {

            // If current <= previous,
            // sequence continues
            if (nums[i] <= nums[i - 1]) {

                left[i] = left[i - 1] + 1;
            } else {

                // Break in sequence → reset
                left[i] = 1;
            }
        }


        // --------------------------------------------------
        // STEP 2: Build right array
        // --------------------------------------------------
        right[n - 1] = 1;

        for (int i = n - 2; i >= 0; i--) {

            // If current <= next,
            // sequence continues (non-decreasing)
            if (nums[i] <= nums[i + 1]) {

                right[i] = right[i + 1] + 1;
            } else {

                right[i] = 1;
            }
        }


        // --------------------------------------------------
        // STEP 3: Check valid indices
        // --------------------------------------------------
        //
        // For index i to be "good":
        //
        // 1. Left side (i-k → i-1) must be non-increasing
        //    ⇒ left[i-1] >= k
        //
        // 2. Right side (i+1 → i+k) must be non-decreasing
        //    ⇒ right[i+1] >= k
        //
        List<Integer> result = new ArrayList<>();

        for (int i = k; i < n - k; i++) {

            if (left[i - 1] >= k && right[i + 1] >= k) {

                result.add(i);
            }
        }

        return result;
    }
}