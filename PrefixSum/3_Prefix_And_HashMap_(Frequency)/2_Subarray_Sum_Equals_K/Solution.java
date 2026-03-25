import java.util.*;

class Solution {

    public int subarraySum(int[] nums, int k) {

        // Map: prefixSum → frequency
        //
        // Why frequency?
        // Because SAME prefix sum can appear multiple times,
        // and each occurrence contributes to valid subarrays.
        Map<Integer, Integer> prefixSumFreq =
            new HashMap<>();

        // Initialization:
        // prefix sum = 0 occurs once before array starts
        //
        // Why?
        // To count subarrays starting from index 0
        prefixSumFreq.put(0, 1);

        int sum = 0;

        int count = 0;

        for (int num : nums) {

            // Update running prefix sum
            sum += num;

            // --------------------------------------------------
            // Key logic:
            //
            // We want subarray sum = k
            //
            // prefix[j] - prefix[i] = k
            //
            // Rearranged:
            // prefix[i] = prefix[j] - k
            //
            // So we check:
            // how many times (sum - k) has occurred
            // --------------------------------------------------
            count +=
                prefixSumFreq.getOrDefault(sum - k, 0);

            // --------------------------------------------------
            // Add current prefix sum to map
            //
            // Why AFTER counting?
            //
            // Because we should not count subarray
            // ending at current index starting at itself
            // --------------------------------------------------
            prefixSumFreq.put(
                sum,
                prefixSumFreq.getOrDefault(sum, 0) + 1
            );
        }

        return count;
    }
}