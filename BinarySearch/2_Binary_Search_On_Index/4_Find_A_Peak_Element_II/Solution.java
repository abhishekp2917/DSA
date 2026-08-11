class Solution {
    public int[] findPeakGrid(int[][] mat) {

        // We need to find ANY peak cell such that:
        //     mat[r][c] > mat[r-1][c]
        //     mat[r][c] > mat[r+1][c]
        //     mat[r][c] > mat[r][c-1]
        //     mat[r][c] > mat[r][c+1]
        //
        // We must solve faster than O(n*m).
        // We use Binary Search on COLUMNS to reduce one dimension.

        int m = mat[0].length;

        // This will store the peak position once found
        int[] peakIdx = new int[2];

        // Binary search boundaries on columns
        int leftCol = 0, rightCol = m - 1;

        while (leftCol <= rightCol) {

            // Choose middle column
            int col = leftCol + (rightCol - leftCol) / 2;

            // Find the row index of the MAX element in this column
            // Reason:
            // A peak in this column must be at the maximum element of the column
            int row = findMaxElementRow(mat, col);

            int curr = mat[row][col];

            // Get left neighbor safely
            int left = (col - 1 >= 0) ? mat[row][col - 1] : -1;

            // Get right neighbor safely
            int right = (col + 1 < m) ? mat[row][col + 1] : -1;

            // Case 1:
            // Current cell is greater than both left and right neighbors
            //
            // Reason:
            // Since this is also the maximum in its column,
            // it is automatically greater than up and down neighbors
            // → this cell is a valid 2D peak
            if (curr > left && curr > right) {

                peakIdx[0] = row;
                peakIdx[1] = col;
                break;
            }

            // Case 2:
            // Current cell is smaller than LEFT neighbor
            //
            // Reason:
            // There exists a higher value on the left side,
            // so a peak MUST exist somewhere in the LEFT half
            else if (curr < left) {

                // Discard right half and search left
                rightCol = col - 1;
            }

            // Case 3:
            // Otherwise, current cell is smaller than RIGHT neighbor
            //
            // Reason:
            // There exists a higher value on the right side,
            // so a peak MUST exist somewhere in the RIGHT half
            else {

                // Discard left half and search right
                leftCol = col + 1;
            }
        }

        // Return the found peak position
        return peakIdx;
    }

    private int findMaxElementRow(int[][] mat, int col) {

        // We scan the given column to find the row index
        // having the maximum value in that column
        //
        // Reason:
        // If a peak exists in this column,
        // it must be at the column's maximum element
        int idx = 0;

        for (int i = 0; i < mat.length; i++) {
            if (mat[i][col] > mat[idx][col]) {
                idx = i;
            }
        }

        return idx;
    }
}
