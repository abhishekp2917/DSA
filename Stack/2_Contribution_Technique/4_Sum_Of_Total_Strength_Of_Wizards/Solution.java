import java.util.*;

class Solution {

    // TC : O(n)
    // SC : O(n)
    public int totalStrength(int[] strength) {

        int n = strength.length;
        int MOD = 1_000_000_007;

        // --------------------------------------------------
        // STEP 1: Prefix sum of array
        // --------------------------------------------------
        //
        // preSum[i] = sum of strength[0...i-1]
        //
        // Used to compute subarray sum quickly
        long[] preSum = new long[n + 1];

        for (int i = 0; i < n; i++) {
            preSum[i + 1] =
                (preSum[i] + strength[i]) % MOD;
        }


        // --------------------------------------------------
        // STEP 2: Prefix of prefix (DOUBLE PREFIX)
        // --------------------------------------------------
        //
        // prePrefix[i] = sum of preSum[0...i-1]
        //
        // Why needed?
        //
        // Because we need:
        // sum of MANY subarray sums efficiently
        //
        // This is the KEY trick of this problem
        long[] prePrefix = new long[n + 2];

        for (int i = 0; i <= n; i++) {
            prePrefix[i + 1] =
                (prePrefix[i] + preSum[i]) % MOD;
        }


        // --------------------------------------------------
        // STEP 3: Find nearest smaller to LEFT
        // --------------------------------------------------
        int[] left = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {

            // Maintain increasing stack
            //
            // Pop >= to handle duplicates correctly
            while (!stack.isEmpty() &&
                   strength[i] <= strength[stack.peek()]) {

                stack.pop();
            }

            left[i] =
                stack.isEmpty() ? -1 : stack.peek();

            stack.push(i);
        }


        // --------------------------------------------------
        // STEP 4: Find nearest smaller to RIGHT
        // --------------------------------------------------
        int[] right = new int[n];

        stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {

            // Pop strictly greater
            //
            // Note difference from left side:
            // This avoids double counting
            while (!stack.isEmpty() &&
                   strength[i] < strength[stack.peek()]) {

                stack.pop();
            }

            right[i] =
                stack.isEmpty() ? n : stack.peek();

            stack.push(i);
        }


        // --------------------------------------------------
        // STEP 5: Compute contribution
        // --------------------------------------------------
        //
        // Each element acts as MINIMUM
        //
        // For each i:
        // count contribution of all subarrays
        long ans = 0;

        for (int i = 0; i < n; i++) {

            int l = left[i];
            int r = right[i];

            // --------------------------------------------------
            // Left side contribution
            // --------------------------------------------------
            //
            // Sum of subarrays ending at i
            long leftSum =
                (prePrefix[i + 1] - prePrefix[l + 1] + MOD) % MOD;

            // --------------------------------------------------
            // Right side contribution
            // --------------------------------------------------
            //
            // Sum of subarrays starting at i
            long rightSum =
                (prePrefix[r + 1] - prePrefix[i + 1] + MOD) % MOD;


            // --------------------------------------------------
            // Combine contributions
            // --------------------------------------------------
            //
            // Formula derived from:
            // sum of all subarrays where i is minimum
            //
            long total =
                (rightSum * (i - l) % MOD
                - leftSum * (r - i) % MOD
                + MOD) % MOD;

            ans =
                (ans + total * strength[i]) % MOD;
        }

        return (int) ans;
    }
}