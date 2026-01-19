class Solution {

    public int maxSideLength(int[][] matrix, int threshold) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        // We will frequently query sums of square submatrices.
        // To make each query O(1), we first build a 2D prefix sum matrix.
        // Reason: without prefix sums, each square sum would cost O(k^2),
        // which would be too slow inside binary search.
        int[][] prefixSum = buildPrefixSum(matrix, rows, cols);

        // We are NOT searching in the matrix —
        // we are searching in the RANGE of possible square side lengths.

        // Minimum possible side length = 1
        int left = 1;

        // Maximum possible side length = min(rows, cols)
        // Reason: square must fit inside the matrix
        int right = Math.min(rows, cols);

        // This will store the maximum valid square side found so far
        int maxValidSide = 0;

        // Binary search on the ANSWER (side length)
        while (left <= right) {

            // Try a candidate square size
            int midSideLength = left + (right - left) / 2;

            // Check if there exists ANY square of this size
            // whose sum is <= threshold
            if (existsSquareWithinThreshold(
                    prefixSum, rows, cols, midSideLength, threshold)) {

                // If possible, this size works
                maxValidSide = midSideLength;

                // Try to find a LARGER valid square
                // Reason: we want the maximum possible side length
                left = midSideLength + 1;
            }

            // If not possible, this size is too large
            // We must try smaller sizes
            else {
                right = midSideLength - 1;
            }
        }

        // maxValidSide is the largest square side whose sum <= threshold
        return maxValidSide;
    }

    /**
     * Builds a 2D prefix sum matrix where:
     * prefixSum[r][c] = sum of elements inside rectangle
     * from (0,0) to (r-1, c-1)
     */
    private int[][] buildPrefixSum(int[][] matrix, int rows, int cols) {

        int[][] prefixSum = new int[rows + 1][cols + 1];

        // Standard 2D prefix sum formula:
        // current cell = matrix value
        //               + top prefix
        //               + left prefix
        //               - top-left prefix (to remove double counting)
        //
        // Reason:
        // This ensures each rectangle sum can later be computed in O(1)
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {

                prefixSum[r][c] = matrix[r - 1][c - 1]
                        + prefixSum[r - 1][c]
                        + prefixSum[r][c - 1]
                        - prefixSum[r - 1][c - 1];
            }
        }

        return prefixSum;
    }

    /**
     * Checks whether there exists at least one square of given side length
     * whose total sum is <= threshold
     */
    private boolean existsSquareWithinThreshold(
            int[][] prefixSum,
            int rows,
            int cols,
            int sideLength,
            int threshold) {

        // We slide a square window of size (sideLength x sideLength)
        // over all possible positions in the matrix.

        // bottomRow and bottomCol represent the bottom-right corner of the square
        for (int bottomRow = sideLength; bottomRow <= rows; bottomRow++) {
            for (int bottomCol = sideLength; bottomCol <= cols; bottomCol++) {

                // Using prefix sum to compute square sum in O(1):
                //
                // squareSum =
                //     prefix[br][bc]
                //   - prefix[br - side][bc]
                //   - prefix[br][bc - side]
                //   + prefix[br - side][bc - side]
                //
                // Reason:
                // Inclusion-exclusion removes extra added regions
                int squareSum = prefixSum[bottomRow][bottomCol]
                        - prefixSum[bottomRow - sideLength][bottomCol]
                        - prefixSum[bottomRow][bottomCol - sideLength]
                        + prefixSum[bottomRow - sideLength][bottomCol - sideLength];

                // If any square has sum <= threshold,
                // then this side length is feasible
                if (squareSum <= threshold) {
                    return true;
                }
            }
        }

        // If no valid square found, this side length is not feasible
        return false;
    }
}
