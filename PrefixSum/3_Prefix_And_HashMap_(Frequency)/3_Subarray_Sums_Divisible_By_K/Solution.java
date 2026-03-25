import java.util.*;

class Solution {

    public int subarraysDivByK(int[] nums, int k) {

        int count = 0;

        // Map: prefixMod → frequency
        //
        // Why frequency?
        // Because multiple same remainders
        // contribute multiple valid subarrays.
        Map<Integer, Integer> prefixModSum =
            new HashMap<>();

        // Initialization:
        // prefix mod = 0 occurs once before array starts
        //
        // Why?
        // To count subarrays starting from index 0
        prefixModSum.put(0, 1);

        int sum = 0;

        for (int num : nums) {

            // Update prefix sum modulo k
            sum = (sum + num) % k;

            // Handle negative modulo
            //
            // Why?
            // Java % can produce negative values
            if (sum < 0)
                sum = (sum + k) % k;

            // --------------------------------------------------
            // Key idea:
            //
            // If two prefix sums have same remainder,
            // their difference is divisible by k
            //
            // prefix[j] % k == prefix[i] % k
            //
            // ⇒ (prefix[j] - prefix[i]) % k == 0
            // --------------------------------------------------

            // Count how many times this remainder occurred before
            count +=
                prefixModSum.getOrDefault(sum, 0);

            // Store current remainder
            prefixModSum.put(
                sum,
                prefixModSum.getOrDefault(sum, 0) + 1
            );
        }

        return count;
    }
}