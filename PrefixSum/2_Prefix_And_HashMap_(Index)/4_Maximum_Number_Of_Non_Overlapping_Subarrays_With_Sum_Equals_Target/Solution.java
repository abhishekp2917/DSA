import java.util.*;

class Solution {

    public int maxNonOverlapping(int[] nums, int target) {

        int n = nums.length;

        // Final answer: number of non-overlapping subarrays
        int count = 0;

        // Tracks the ending index of the LAST selected subarray
        //
        // Why needed?
        // To ensure non-overlapping constraint
        int lastSubarrIdx = -1;

        // Map: prefixSum → latest index
        //
        // Why latest index?
        // Because we want the "nearest" valid subarray
        // (greedy: take earliest finishing subarray)
        Map<Long, Integer> prefixSumIdx =
            new HashMap<>();

        // Base case:
        // prefix sum = 0 at index -1
        prefixSumIdx.put(0L, -1);

        long sum = 0;

        for (int i = 0; i < n; i++) {

            sum += nums[i];

            // We want:
            // subarray sum = target
            //
            // prefix[j] - prefix[i] = target
            //
            // ⇒ prefix[i] = prefix[j] - target
            long subtractor = sum - target;

            // Find nearest index where prefix sum matched
            int nearestIdx =
                prefixSumIdx.getOrDefault(
                    subtractor,
                    Integer.MIN_VALUE
                );

            // --------------------------------------------------
            // Check NON-OVERLAPPING condition
            // --------------------------------------------------
            //
            // nearestIdx ≥ lastSubarrIdx
            //
            // Means:
            // new subarray starts AFTER previous subarray ends
            if (nearestIdx >= lastSubarrIdx) {

                count++;

                // Update last chosen subarray end
                lastSubarrIdx = i;
            }

            // Update prefix map
            prefixSumIdx.put(sum, i);
        }

        return count;
    }
}