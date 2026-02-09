class Solution {
    public int maxTurbulenceSize(int[] arr) {

        // A subarray is turbulent if comparisons alternate:
        //
        //     arr[i] > arr[i+1] < arr[i+2] > arr[i+3] ...
        //  OR
        //     arr[i] < arr[i+1] > arr[i+2] < arr[i+3] ...
        //
        // Since pattern can start either with ">" or "<",
        // we handle both cases separately.

        int n = arr.length;

        int maxLen = 0;

        // -----------------------------
        // Case 1:
        // Assume pattern:
        //   even index → >
        //   odd index  → <
        // -----------------------------

        int leftIdx = 0, rightIdx = 0;

        // prev keeps track of previous element
        int prev = Integer.MIN_VALUE;

        while (rightIdx < n) {

            int curr = arr[rightIdx];

            // If expected alternating condition breaks,
            // restart window from current index.
            //
            // For this pass:
            //   if rightIdx is even → expect curr > prev
            //   if rightIdx is odd  → expect curr < prev
            if ((rightIdx % 2 == 0 && curr <= prev) ||
                (rightIdx % 2 != 0 && curr >= prev)) {

                // Pattern broken → restart window
                leftIdx = rightIdx;
            }

            // Update maximum length
            maxLen = Math.max(maxLen, rightIdx - leftIdx + 1);

            // Update previous value
            prev = curr;

            rightIdx++;
        }

        // -----------------------------
        // Case 2:
        // Assume pattern:
        //   even index → <
        //   odd index  → >
        // -----------------------------

        leftIdx = 0;
        rightIdx = 0;

        // Initialize prev as very large so first comparison works
        prev = Integer.MAX_VALUE;

        while (rightIdx < n) {

            int curr = arr[rightIdx];

            // For this pass:
            //   if rightIdx is even → expect curr < prev
            //   if rightIdx is odd  → expect curr > prev
            if ((rightIdx % 2 == 0 && curr >= prev) ||
                (rightIdx % 2 != 0 && curr <= prev)) {

                // Pattern broken → restart window
                leftIdx = rightIdx;
            }

            // Update maximum length
            maxLen = Math.max(maxLen, rightIdx - leftIdx + 1);

            prev = curr;

            rightIdx++;
        }

        return maxLen;
    }
}
