import java.util.*;

class Solution {

    public int maxSumSubmatrix(int[][] matrix, int k) {

        int maxSum = Integer.MIN_VALUE;

        int n = matrix.length;
        int m = matrix[0].length;

        // --------------------------------------------------
        // STEP 1: Fix starting row
        // --------------------------------------------------
        //
        // Same pattern as:
        // "2D → 1D reduction"
        for (int rowStart = 0; rowStart < n; rowStart++) {

            // This array stores column-wise sum
            // between rowStart → rowEnd
            int[] prefixColSum = new int[m];

            // --------------------------------------------------
            // STEP 2: Expand ending row
            // --------------------------------------------------
            for (int rowEnd = rowStart; rowEnd < n; rowEnd++) {

                // --------------------------------------------------
                // STEP 3: Use TreeSet to solve:
                //
                // "max subarray sum ≤ k"
                // --------------------------------------------------
                //
                // Why TreeSet?
                //
                // Because we need:
                // prefixSum[i] ≥ prefixSum[j] - k
                //
                // → need ordered structure for binary search
                TreeSet<Integer> prefixSumSet =
                    new TreeSet<>();

                int prefixSum = 0;

                for (int col = 0; col < m; col++) {

                    // Add previous prefix BEFORE updating
                    //
                    // Why?
                    // So subarray can start at this index
                    prefixSumSet.add(prefixSum);

                    // Update compressed column sum
                    prefixColSum[col] += matrix[rowEnd][col];

                    // Update running prefix sum
                    prefixSum += prefixColSum[col];


                    // --------------------------------------------------
                    // CORE LOGIC:
                    //
                    // We want:
                    // subarray sum ≤ k
                    //
                    // prefix[j] - prefix[i] ≤ k
                    //
                    // ⇒ prefix[i] ≥ prefix[j] - k
                    //
                    // So we search:
                    int subtractor = prefixSum - k;

                    // Find smallest prefix ≥ subtractor
                    Integer candidate =
                        prefixSumSet.ceiling(subtractor);

                    // If exists, compute subarray sum
                    if (candidate != null) {

                        maxSum = Math.max(
                            maxSum,
                            prefixSum - candidate
                        );
                    }
                }
            }
        }

        return maxSum;
    }
}