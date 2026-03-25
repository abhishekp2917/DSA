import java.util.*;

class Solution {

    public int countSubarrays(int[] nums, int k) {

        int n = nums.length;

        // Index (in prefix array) where k occurs
        //
        // Why important?
        // Because valid subarrays must INCLUDE k
        int kIdx = -1;

        int subarrCount = 0;

        // Map: prefix value → frequency (only for LEFT side of k)
        //
        // Why only left side?
        // Because we will pair left prefixes with right prefixes
        Map<Integer, Integer> prefixFreq = new HashMap<>();

        // Prefix array of transformed values
        //
        // Transformation:
        // nums[i] < k → -1
        // nums[i] > k → +1
        // nums[i] == k →  0
        //
        // Why this transformation?
        // Because median = k condition translates to:
        // equal number of elements > k and < k (or off by 1)
        int[] prefixArr = new int[n + 1];


        // --------------------------------------------------
        // STEP 1: Build transformed prefix array
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            int num =
                (nums[i] == k) ? 0 :
                ((nums[i] > k) ? 1 : -1);

            prefixArr[i + 1] =
                prefixArr[i] + num;

            // Track position of k
            //
            // Why i+1?
            // Because prefix array is 1-indexed
            if (num == 0)
                kIdx = i + 1;
        }


        // --------------------------------------------------
        // STEP 2: Store prefix frequencies BEFORE k
        // --------------------------------------------------
        //
        // These represent left boundaries of subarrays
        //
        // Only prefixes BEFORE k are considered,
        // ensuring every subarray includes k.
        for (int i = 0; i < kIdx; i++) {

            int prefix = prefixArr[i];

            prefixFreq.put(
                prefix,
                prefixFreq.getOrDefault(prefix, 0) + 1
            );
        }


        // --------------------------------------------------
        // STEP 3: Iterate over right side and count matches
        // --------------------------------------------------
        //
        // For each right endpoint,
        // we look for matching left prefix
        //
        // Conditions:
        //   prefix[r] - prefix[l] = 0
        //   OR
        //   prefix[r] - prefix[l] = 1
        //
        // These correspond to valid median conditions.
        for (int i = kIdx; i <= n; i++) {

            int prefix = prefixArr[i];

            // Case 1: balanced subarray
            //
            // prefix[r] == prefix[l]
            subarrCount +=
                prefixFreq.getOrDefault(prefix, 0);

            // Case 2: one extra element > k
            //
            // prefix[r] = prefix[l] + 1
            subarrCount +=
                prefixFreq.getOrDefault(prefix - 1, 0);
        }

        return subarrCount;
    }
}