import java.util.*;

class Solution {

    public int numSubmatrixSumTarget(int[][] matrix, int target) {

        int n = matrix.length;
        int m = matrix[0].length;

        int count = 0;

        // --------------------------------------------------
        // STEP 1: Fix starting row
        // --------------------------------------------------
        //
        // Idea:
        // We will "compress" rows into a 1D array
        //
        // For every pair (startRow, endRow),
        // we treat the matrix between them as a 1D array
        for (int startRow = 0; startRow < n; startRow++) {

            // This array stores column-wise sum
            // from startRow → endRow
            //
            // Initially all 0 (no rows included yet)
            int[] colPrefixSum = new int[m];

            // --------------------------------------------------
            // STEP 2: Expand ending row
            // --------------------------------------------------
            //
            // As we expand endRow,
            // we keep adding new row into colPrefixSum
            for (int endRow = startRow; endRow < n; endRow++) {

                // Update column sums
                //
                // colPrefixSum[c] =
                // sum of matrix[startRow → endRow][c]
                for (int c = 0; c < m; c++) {
                    colPrefixSum[c] += matrix[endRow][c];
                }


                // --------------------------------------------------
                // STEP 3: Now problem becomes:
                //
                // Count subarrays in colPrefixSum
                // whose sum = target
                // --------------------------------------------------

                // Map: prefixSum → frequency
                //
                // Why frequency?
                // Because multiple prefix sums can match
                Map<Integer, Integer> prefixFreqMap =
                    new HashMap<>();

                // Base case:
                // prefix sum = 0 occurs once
                //
                // Why?
                // To count subarrays starting at column 0
                prefixFreqMap.put(0, 1);

                int prefixSum = 0;

                for (int c = 0; c < m; c++) {

                    // Build prefix sum
                    prefixSum += colPrefixSum[c];

                    // --------------------------------------------------
                    // Key formula:
                    //
                    // prefix[j] - prefix[i] = target
                    //
                    // ⇒ prefix[i] = prefix[j] - target
                    // --------------------------------------------------
                    int subtractor = prefixSum - target;

                    // Count how many valid prefix exist
                    count +=
                        prefixFreqMap.getOrDefault(subtractor, 0);

                    // Store current prefix
                    prefixFreqMap.put(
                        prefixSum,
                        prefixFreqMap.getOrDefault(prefixSum, 0) + 1
                    );
                }
            }
        }

        return count;
    }
}