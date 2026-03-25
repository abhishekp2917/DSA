import java.util.*;

class Solution {

    public int numSubarraysWithSum(int[] nums, int goal) {

        // Map: prefixSum → frequency of occurrence
        //
        // Why frequency?
        // Because SAME prefix sum can occur multiple times,
        // and each occurrence contributes to valid subarrays.
        Map<Integer, Integer> prefixSumFreq = new HashMap<>();

        // Initialization:
        // prefixSum = 0 occurs once before array starts.
        //
        // Why?
        // This allows subarrays starting from index 0
        // to be counted.
        prefixSumFreq.put(0, 1);

        int sum = 0;

        int count = 0;

        // Traverse array
        for (int num : nums) {

            // Update prefix sum
            sum += num;

            // We want:
            // subarray sum = goal
            //
            // prefix[j] - prefix[i] = goal
            //
            // Rearranged:
            // prefix[i] = prefix[j] - goal
            //
            // So we check how many times
            // (sum - goal) has appeared before.
            count += prefixSumFreq.getOrDefault(sum - goal, 0);

            // Add current prefix sum to map
            //
            // Why AFTER counting?
            // Because we don't want to use current index
            // as starting point of itself.
            prefixSumFreq.put(
                sum,
                prefixSumFreq.getOrDefault(sum, 0) + 1
            );
        }

        return count;
    }
}