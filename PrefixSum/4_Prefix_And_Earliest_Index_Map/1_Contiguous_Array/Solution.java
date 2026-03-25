import java.util.*;

class Solution {

    public int findMaxLength(int[] nums) {

        int n = nums.length;

        int maxLen = 0;

        // Map: (zeroOneDiff) → earliest index
        //
        // zeroOneDiff = (# of 1s - # of 0s)
        //
        // Why earliest index?
        // Because we want the LONGEST subarray.
        Map<Integer, Integer> prefixZeroOneDiffIdx =
            new HashMap<>();

        // Initialization:
        // diff = 0 at index -1
        //
        // Why?
        // To allow subarrays starting from index 0
        prefixZeroOneDiffIdx.put(0, -1);

        int zeroOneFreqDiff = 0;

        for (int i = 0; i < n; i++) {

            // --------------------------------------------------
            // STEP 1: Update difference
            // --------------------------------------------------
            //
            // Treat:
            // 0 → -1
            // 1 → +1
            //
            // Why?
            // Because equal number of 0s and 1s
            // means total sum = 0
            zeroOneFreqDiff +=
                (nums[i] == 0) ? -1 : 1;


            // --------------------------------------------------
            // STEP 2: Check if this diff appeared before
            // --------------------------------------------------
            //
            // If same diff seen before:
            //
            // prefix[j] == prefix[i]
            //
            // ⇒ subarray sum between them = 0
            // ⇒ equal number of 0s and 1s
            int len =
                i - prefixZeroOneDiffIdx
                        .getOrDefault(zeroOneFreqDiff, i);

            maxLen = Math.max(maxLen, len);


            // --------------------------------------------------
            // STEP 3: Store earliest occurrence
            // --------------------------------------------------
            //
            // Why only first occurrence?
            // Because earlier index gives longer subarray
            prefixZeroOneDiffIdx.putIfAbsent(
                zeroOneFreqDiff, i
            );
        }

        return maxLen;
    }
}