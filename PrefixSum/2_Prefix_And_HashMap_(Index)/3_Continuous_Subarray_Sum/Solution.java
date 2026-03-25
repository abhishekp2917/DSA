import java.util.*;

class Solution {

    public boolean checkSubarraySum(int[] nums, int k) {

        int n = nums.length;

        // Map: prefixMod → earliest index where it appeared
        //
        // Why earliest index?
        // Because we want subarray length ≥ 2
        // and earliest index maximizes length.
        Map<Integer, Integer> prefixModSum =
            new HashMap<>();

        // Initialization:
        //
        // prefix sum = 0 occurs at index -1
        //
        // Why?
        // Allows subarrays starting from index 0
        prefixModSum.put(0, -1);

        int sum = 0;

        for (int i = 0; i < n; i++) {

            int num = nums[i];

            // Update running sum modulo k
            sum = (sum + num) % k;

            // Handle negative modulo case
            //
            // Why?
            // Java % can give negative values
            if (sum < 0)
                sum = (sum + k) % k;

            // --------------------------------------------------
            // Key logic:
            //
            // If same mod seen before,
            // subarray sum between them is divisible by k
            //
            // prefix[j] % k == prefix[i] % k
            //
            // ⇒ (prefix[j] - prefix[i]) % k == 0
            // --------------------------------------------------

            int len =
                i - prefixModSum.getOrDefault(sum, i);

            // Check if subarray length ≥ 2
            if (len >= 2)
                return true;

            // Store earliest occurrence only
            //
            // Why?
            // Because earlier index gives longer subarray
            prefixModSum.putIfAbsent(sum, i);
        }

        return false;
    }
}