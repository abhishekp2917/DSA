import java.util.*;

class Solution {

    public long countInterestingSubarrays(
            List<Integer> nums,
            int modulo,
            int k) {

        int n = nums.size();

        long count = 0;

        // Map: prefixMod → frequency
        //
        // prefixMod = (# of "good" elements so far) % modulo
        //
        // Why frequency?
        // Because multiple occurrences of same prefixMod
        // contribute multiple valid subarrays.
        Map<Integer, Long> prefixModSumFreq =
            new HashMap<>();

        // Initialization:
        // prefixMod = 0 occurs once before array starts
        //
        // Why?
        // To count subarrays starting from index 0
        prefixModSumFreq.put(0, 1L);

        int modSum = 0;

        for (int i = 0; i < n; i++) {

            // --------------------------------------------------
            // STEP 1: Transform element
            // --------------------------------------------------
            //
            // We only care whether:
            // nums[i] % modulo == k
            //
            // If yes → contribute 1
            // Else   → contribute 0
            //
            // Why?
            // Because we are counting how many such elements
            // exist in subarray.
            int num =
                (nums.get(i) % modulo == k) ? 1 : 0;

            // Update prefix count modulo
            modSum = (modSum + num) % modulo;


            // --------------------------------------------------
            // STEP 2: Find valid previous prefix
            // --------------------------------------------------
            //
            // We want subarray such that:
            //
            // (count of good elements) % modulo == k
            //
            // prefix[j] - prefix[i] ≡ k (mod modulo)
            //
            // Rearranged:
            // prefix[i] ≡ prefix[j] - k (mod modulo)
            //
            int subtractor =
                (modSum - k + modulo) % modulo;

            // Count how many such prefix exist
            count +=
                prefixModSumFreq.getOrDefault(subtractor, 0L);


            // --------------------------------------------------
            // STEP 3: Store current prefix
            // --------------------------------------------------
            prefixModSumFreq.put(
                modSum,
                prefixModSumFreq.getOrDefault(modSum, 0L) + 1
            );
        }

        return count;
    }
}