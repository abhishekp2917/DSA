import java.util.*;

class Solution {

    public int minSubarray(int[] nums, int p) {

        int n = nums.length;

        // --------------------------------------------------
        // STEP 1: Compute total sum
        // --------------------------------------------------
        //
        // Why?
        // Because we need to check if the whole array
        // is already divisible by p.
        long totalSum = 0;

        for (int num : nums)
            totalSum += num;

        int remainder = (int)(totalSum % p);

        // If already divisible, no removal needed
        if (remainder == 0)
            return 0;


        // --------------------------------------------------
        // STEP 2: We need to remove a subarray such that:
        //
        // (totalSum - subarraySum) % p == 0
        //
        // ⇒ subarraySum % p == remainder
        // --------------------------------------------------

        int smallestLen = n;

        long sum = 0L;

        // Map: prefixMod → latest index
        //
        // Why latest index?
        // Because we want MINIMUM length subarray.
        Map<Integer, Integer> prefixModSum =
            new HashMap<>();

        // Initialization:
        //
        // prefixMod = 0 at index -1
        //
        // Why?
        // Allows subarrays starting from index 0
        prefixModSum.put(0, -1);


        // --------------------------------------------------
        // STEP 3: Traverse array
        // --------------------------------------------------
        for (int i = 0; i < nums.length; i++) {

            sum += nums[i];

            // Current prefix modulo
            int modSum = (int)(sum % p);

            // --------------------------------------------------
            // We want:
            //
            // (prefix[j] - prefix[i]) % p = remainder
            //
            // Rearranged:
            //
            // prefix[i] % p =
            // (prefix[j] - remainder) % p
            //
            // So we search for:
            int target =
                (int)((sum - remainder + p) % p);

            // If such prefix exists,
            // we found a valid subarray
            if (prefixModSum.containsKey(target)) {

                int len =
                    i - prefixModSum.get(target);

                smallestLen =
                    Math.min(smallestLen, len);
            }

            // Store latest index of this modulo
            prefixModSum.put(modSum, i);
        }

        return (smallestLen != n)
                ? smallestLen
                : -1;
    }
}