import java.util.*;

class Solution {

    public int longestWPI(int[] hours) {

        int n = hours.length;

        int maxIntervalLen = 0;

        // Map: prefixDiff → earliest index
        //
        // prefixDiff = (#tiring days - #non-tiring days)
        //
        // Why earliest index?
        // Because we want LONGEST interval
        Map<Integer, Integer> prefixTiringDayDiffIdx =
            new HashMap<>();

        // Base case:
        // diff = 0 at index -1
        //
        // Why?
        // To allow intervals starting from index 0
        prefixTiringDayDiffIdx.put(0, -1);

        int currDiff = 0;

        for (int i = 0; i < n; i++) {

            // --------------------------------------------------
            // STEP 1: Transform input
            // --------------------------------------------------
            //
            // hours > 8 → tiring day → +1
            // hours ≤ 8 → non-tiring → -1
            //
            // Why?
            // Because problem asks:
            // more tiring days than non-tiring days
            currDiff += (hours[i] > 8) ? 1 : -1;


            // --------------------------------------------------
            // STEP 2: Determine target prefix
            // --------------------------------------------------
            //
            // We want subarray where:
            // (#tiring - #non-tiring) > 0
            //
            // i.e. sum > 0
            //
            // Two cases:
            //
            // Case 1:
            // currDiff > 0
            // → whole prefix is valid
            //
            // Case 2:
            // currDiff ≤ 0
            // → need earlier prefix such that:
            //
            // prefix[j] - prefix[i] > 0
            //
            // ⇒ prefix[i] < prefix[j]
            //
            // So we search for:
            // earliest prefix < currDiff
            //
            int minSubtractor =
                (currDiff > 0) ? 0 : currDiff - 1;


            // --------------------------------------------------
            // STEP 3: Compute interval length
            // --------------------------------------------------
            int intervalLen =
                i - prefixTiringDayDiffIdx
                        .getOrDefault(minSubtractor, i);

            maxIntervalLen =
                Math.max(maxIntervalLen, intervalLen);


            // --------------------------------------------------
            // STEP 4: Store earliest occurrence
            // --------------------------------------------------
            //
            // Why only first occurrence?
            // Because earlier index gives longer interval
            prefixTiringDayDiffIdx.putIfAbsent(
                currDiff, i
            );
        }

        return maxIntervalLen;
    }
}