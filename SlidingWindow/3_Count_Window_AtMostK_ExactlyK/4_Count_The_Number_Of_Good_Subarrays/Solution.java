import java.util.HashMap;
import java.util.Map;

class Solution {

    public long countGood(int[] nums, int k) {

        // A subarray is GOOD if it contains
        // at least k equal pairs.
        //
        // Instead of directly counting subarrays
        // with at least k pairs,
        // we use complement counting.

        long n = nums.length;

        // Total number of subarrays
        long totalSubstring = n * (n + 1) / 2;

        // Subarrays with AT MOST (k-1) equal pairs
        //
        // So:
        //   good_subarrays =
        //       total_subarrays
        //       -
        //       subarrays_with_at_most_(k-1)_pairs
        return totalSubstring - countAtmostK(nums, k - 1);
    }

    public long countAtmostK(int[] nums, int k) {

        // This function counts subarrays
        // having AT MOST k equal pairs.

        long pairCount = 0;  // number of equal pairs in current window
        long count = 0;      // total valid subarrays
        int start = 0;

        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int end = 0; end < nums.length; end++) {

            // When adding nums[end],
            // it forms new pairs equal to its current frequency.
            //
            // If a number appears f times already,
            // adding one more creates f new pairs.
            pairCount += freqMap.getOrDefault(nums[end], 0);

            // Update frequency
            freqMap.put(nums[end],
                        freqMap.getOrDefault(nums[end], 0) + 1);

            // If pairCount exceeds k,
            // shrink window from the left
            while (start <= end && pairCount > k) {

                // Before decreasing frequency,
                // removing nums[start] reduces pairs by:
                //   (freq - 1)
                //
                // But since we already reduced frequency by 1 below,
                // we subtract the new frequency.
                freqMap.put(nums[start],
                            freqMap.get(nums[start]) - 1);

                pairCount -= freqMap.get(nums[start]);

                start++;
            }

            // Now window [start ... end]
            // has at most k pairs.
            //
            // All subarrays ending at 'end'
            // and starting from start to end are valid.
            count += (end - start + 1);
        }

        return count;
    }
}
