class Solution {
    public int kthSmallest(int[][] matrix, int k) {

        // The matrix is sorted in both rows and columns.
        // We need to find the k-th smallest element in the entire matrix.

        int n = matrix.length;
        int m = matrix[0].length;

        // We binary search on the VALUE range, not on indices.

        // Minimum possible value = smallest element in matrix
        int left = matrix[0][0];

        // Maximum possible value = largest element in matrix
        int right = matrix[n - 1][m - 1];

        // This will store the smallest value such that
        // at least k elements are <= that value
        int ans = -1;

        // Binary search on the ANSWER (matrix value)
        while (left <= right) {

            // Try a candidate value
            int mid = (left + right) / 2;

            // Count how many elements in the matrix are <= mid
            int count = countElementLessOrEqual(matrix, n, m, mid);

            // If there are LESS than k elements <= mid,
            // then mid is TOO SMALL
            // We must search in the higher value range
            if (count < k) {
                left = mid + 1;
            }

            // If there are at least k elements <= mid,
            // then mid is a VALID candidate for the k-th smallest
            else {

                // Store mid as a possible answer
                ans = mid;

                // Try to find a smaller valid value
                // Reason: we want the smallest value that still has >= k elements before it
                right = mid - 1;
            }
        }

        // ans now holds the k-th smallest element
        return ans;
    }

    private int countElementLessOrEqual(int[][] matrix,
                                        int n,
                                        int m,
                                        int target) {

        // We count how many elements are <= target in O(n + m) time
        // by starting from the TOP-RIGHT corner.

        int count = 0;

        // Start from first row, last column
        int i = 0;
        int j = m - 1;

        while (i < n && j >= 0) {

            // If current element <= target,
            // then ALL elements in this row from column 0 to j are <= target
            //
            // Reason:
            // Rows are sorted left to right
            if (matrix[i][j] <= target) {

                // Add all j+1 elements from this row
                count += j + 1;

                // Move DOWN to next row to count more
                i++;
            }

            // If current element > target,
            // then this element and all elements BELOW it in this column are > target
            //
            // Reason:
            // Columns are sorted top to bottom
            else {

                // Move LEFT to find smaller values
                j--;
            }
        }

        // Return total count of elements <= target
        return count;
    }
}
