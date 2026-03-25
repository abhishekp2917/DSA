import java.util.*;

class Solution {

    public long maxSubarraySum(int[] nums, int k) {

        int n = nums.length;

        // Final answer
        long maxSum = Long.MIN_VALUE;

        // prefixModLen[r] = minimum prefix sum seen so far
        // for indices where (index + 1) % k == r
        //
        // Why store MIN prefix?
        // Because:
        // max subarray sum = currSum - smallest prefix
        long[] prefixModLen = new long[k];

        Arrays.fill(prefixModLen, Long.MAX_VALUE);

        // Base case:
        // prefix sum at index -1 = 0
        //
        // Its "length" = 0 → (0 % k = 0)
        prefixModLen[0] = 0L;

        long currSum = 0;

        // --------------------------------------------------
        // Traverse array
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            currSum += nums[i];

            // Current subarray length = i + 1
            int modLen = (i + 1) % k;

            // We want a previous prefix
            // with SAME modLen
            //
            // Why?
            // Because:
            // (length of subarray) % k == 0
            //
            // (j - i) % k == 0
            // ⇒ (prefix index difference) same mod
            long subtractor = prefixModLen[modLen];

            // If valid prefix exists
            if (subtractor != Long.MAX_VALUE) {

                // Try forming subarray
                maxSum = Math.max(
                    maxSum,
                    currSum - subtractor
                );
            }

            // Update minimum prefix sum for this mod class
            //
            // Why minimum?
            // To maximize (currSum - prefix)
            prefixModLen[modLen] =
                Math.min(prefixModLen[modLen], currSum);
        }

        return maxSum;
    }
}