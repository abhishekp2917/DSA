class Solution {

    public int longestMountain(int[] arr) {

        int n = arr.length;

        int maxLen = 0;

        // --------------------------------------------------
        // left[i] = length of strictly increasing sequence ending at i
        // --------------------------------------------------
        //
        // Meaning:
        // How long is the uphill ending at i
        int[] left = new int[n];

        // --------------------------------------------------
        // right[i] = length of strictly decreasing sequence starting at i
        // --------------------------------------------------
        //
        // Meaning:
        // How long is the downhill starting at i
        int[] right = new int[n];


        // --------------------------------------------------
        // STEP 1: Build increasing prefix (left array)
        // --------------------------------------------------
        left[0] = 1;

        for (int i = 1; i < n; i++) {

            // If strictly increasing
            if (arr[i - 1] < arr[i]) {

                // Extend increasing sequence
                left[i] = left[i - 1] + 1;

            } else {

                // Reset → new sequence starts
                left[i] = 1;
            }
        }


        // --------------------------------------------------
        // STEP 2: Build decreasing suffix (right array)
        // --------------------------------------------------
        right[n - 1] = 1;

        for (int i = n - 2; i >= 0; i--) {

            // If strictly decreasing
            if (arr[i] > arr[i + 1]) {

                // Extend decreasing sequence
                right[i] = right[i + 1] + 1;

            } else {

                right[i] = 1;
            }
        }


        // --------------------------------------------------
        // STEP 3: Identify valid mountains
        // --------------------------------------------------
        //
        // A valid mountain must have:
        //   - left[i] > 1 → has increasing part
        //   - right[i] > 1 → has decreasing part
        //
        // i acts as PEAK
        for (int i = 0; i < n; i++) {

            // Skip invalid peaks
            //
            // Why?
            // A mountain must go UP then DOWN
            if (left[i] == 1 || right[i] == 1)
                continue;

            // Total length:
            //
            // left[i] includes peak
            // right[i] includes peak
            //
            // So subtract 1 to avoid double counting
            int mountainLen =
                left[i] + right[i] - 1;

            maxLen = Math.max(maxLen, mountainLen);
        }

        return maxLen;
    }
}