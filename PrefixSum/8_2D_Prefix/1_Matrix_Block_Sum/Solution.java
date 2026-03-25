class Solution {

    public int[][] matrixBlockSum(int[][] mat, int k) {

        int n = mat.length;
        int m = mat[0].length;

        // --------------------------------------------------
        // STEP 1: Build 2D prefix sum matrix
        // --------------------------------------------------
        //
        // prefixSumMat[i][j] stores sum of submatrix:
        // (0,0) → (i-1, j-1)
        //
        // Extra row & column (n+1, m+1) to avoid boundary checks
        int[][] prefixSumMat = new int[n + 1][m + 1];

        int[][] ans = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                int leftNum = prefixSumMat[i + 1][j];     // left contribution
                int topNum = prefixSumMat[i][j + 1];      // top contribution
                int topLeftNum = prefixSumMat[i][j];      // overlap

                // Inclusion-exclusion principle
                prefixSumMat[i + 1][j + 1] =
                        mat[i][j]
                        + topNum
                        + leftNum
                        - topLeftNum;
            }
        }


        // --------------------------------------------------
        // STEP 2: Query each cell using prefix sum
        // --------------------------------------------------
        //
        // For each cell (i,j),
        // we want sum of block:
        //
        // rows: [i-k, i+k]
        // cols: [j-k, j+k]
        //
        // Clamp boundaries within matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                int top = Math.max(0, i - k);
                int left = Math.max(0, j - k);

                // Note: prefix matrix uses +1 indexing
                int bottom = Math.min(n, i + k + 1);
                int right = Math.min(m, j + k + 1);

                // --------------------------------------------------
                // Use 2D prefix sum formula
                // --------------------------------------------------
                //
                // sum of rectangle:
                // (top,left) → (bottom-1, right-1)
                //
                ans[i][j] =
                        prefixSumMat[bottom][right]
                        - prefixSumMat[bottom][left]
                        - prefixSumMat[top][right]
                        + prefixSumMat[top][left];
            }
        }

        return ans;
    }
}