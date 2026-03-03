import java.util.*;

class Solution {

    public long[] mostFrequentIDs(int[] nums, int[] freqs) {

        int n = nums.length;

        // id → current frequency
        //
        // Why?
        // Because we must update frequency
        // of specific ID efficiently.
        HashMap<Integer, Long> idToFreqMap =
            new HashMap<>();

        // frequency → set of IDs having that frequency
        //
        // Why TreeMap?
        // Because we need to quickly get
        // the maximum frequency after each update.
        //
        // TreeMap keeps keys sorted.
        TreeMap<Long, Set<Integer>> freqToIdMap =
            new TreeMap<>();

        long[] ans = new long[n];

        for (int i = 0; i < n; i++) {

            int num = nums[i];
            int freq = freqs[i];

            // Current frequency of this ID
            long currFreq =
                idToFreqMap.getOrDefault(num, 0L);

            long newFreq = currFreq + freq;

            // Update id → frequency mapping
            idToFreqMap.put(num, newFreq);

            // Step 1:
            // Add ID to new frequency bucket
            Set<Integer> newIdSet =
                freqToIdMap.getOrDefault(
                    newFreq,
                    new HashSet<>()
                );

            newIdSet.add(num);
            freqToIdMap.put(newFreq, newIdSet);

            // Step 2:
            // Remove ID from old frequency bucket
            //
            // Only if it previously existed.
            if (currFreq > 0) {

                Set<Integer> oldIdSet =
                    freqToIdMap.get(currFreq);

                oldIdSet.remove(num);

                // If no IDs remain with old frequency,
                // remove that frequency key.
                if (oldIdSet.isEmpty()) {
                    freqToIdMap.remove(currFreq);
                }
            }

            // The maximum frequency is the largest key.
            ans[i] = freqToIdMap.lastKey();
        }

        return ans;
    }
}
