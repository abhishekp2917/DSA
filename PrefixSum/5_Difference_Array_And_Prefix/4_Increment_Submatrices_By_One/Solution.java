class Solution {

    public int[][] rangeAddQueries(int n, int[][] queries) {

        // --------------------------------------------------
        // STEP 1: Create 2D difference array
        // --------------------------------------------------
        //
        // Size = n+1 to safely handle (x2+1, y2+1) updates
        int[][] diff = new int[n + 1][n + 1];

        int[][] matrix = new int[n][n];

        // --------------------------------------------------
        // STEP 2: Apply 2D range updates
        // --------------------------------------------------
        //
        // For each query:
        // Increment all cells inside rectangle:
        // (x1, y1) → (x2, y2)
        //
        // Instead of updating every cell,
        // we mark ONLY 4 corners
        for (int[] query : queries) {

            int x1 = query[0];
            int y1 = query[1];
            int x2 = query[2];
            int y2 = query[3];

            // Top-left corner
            diff[x1][y1]++;

            // Bottom-left boundary
            diff[x2 + 1][y1]--;

            // Top-right boundary
            diff[x1][y2 + 1]--;

            // Bottom-right corner
            diff[x2 + 1][y2 + 1]++;
        }


        // --------------------------------------------------
        // STEP 3: Convert diff → actual matrix (2D prefix sum)
        // --------------------------------------------------
        //
        // Formula:
        //
        // matrix[row][col] =
        //     diff[row][col]
        //     + top
        //     + left
        //     - topLeft
        //
        // Why subtract topLeft?
        // Because it was added twice
        for (int row = 0; row < n; row++) {

            for (int col = 0; col < n; col++) {

                int top =
                    (row - 1 >= 0)
                        ? matrix[row - 1][col]
                        : 0;

                int left =
                    (col - 1 >= 0)
                        ? matrix[row][col - 1]
                        : 0;

                int topLeft =
                    (row - 1 >= 0 && col - 1 >= 0)
                        ? matrix[row - 1][col - 1]
                        : 0;

                matrix[row][col] =
                    diff[row][col]
                    + top
                    + left
                    - topLeft;
            }
        }

        return matrix;
    }
}