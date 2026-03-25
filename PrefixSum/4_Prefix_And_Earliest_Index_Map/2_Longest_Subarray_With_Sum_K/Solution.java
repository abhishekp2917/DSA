import java.util.*;

class Solution {

    public int longestSubarray(int[] nums, int k) {

        int n = nums.length;

        int longestLen = 0;

        // Map: prefixSum → earliest index where this sum occurred
        //
        // Why earliest index?
        // Because we want the LONGEST subarray.
        //
        // If the same prefix sum occurs again later,
        // keeping the earliest index gives maximum length.
        Map<Long, Integer> prefixSumIdx = new HashMap<>();

        // Important initialization:
        //
        // prefixSum = 0 occurs before array starts (index -1).
        //
        // Why?
        // This allows subarrays starting at index 0
        // to be considered.
        prefixSumIdx.put(0L, -1);

        long sum = 0;

        for (int i = 0; i < n; i++) {

            // Update running prefix sum
            sum += nums[i];

            // We want subarray sum = k
            //
            // prefixSum[j] - prefixSum[i] = k
            //
            // Rearranged:
            // prefixSum[i] = prefixSum[j] - k
            //
            // So we search for (sum - k).
            long subtractor = sum - k;

            // If we have seen this prefix sum before,
            // we can form a subarray with sum k.
            //
            // Length = current index - earliest index of that prefix sum.
            int tempLen =
                i - prefixSumIdx.getOrDefault(subtractor, i);

            longestLen = Math.max(longestLen, tempLen);

            // Store prefix sum only if it hasn't appeared before.
            //
            // Why?
            // Because we want the EARLIEST occurrence
            // to maximize subarray length.
            prefixSumIdx.putIfAbsent(sum, i);
        }

        return longestLen;
    }
}