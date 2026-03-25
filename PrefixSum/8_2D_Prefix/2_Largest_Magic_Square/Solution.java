class Solution {

    public int largestMagicSquare(int[][] grid) {

        int n = grid.length, m = grid[0].length;

        // --------------------------------------------------
        // WHY so many prefix arrays?
        // --------------------------------------------------
        //
        // We need to frequently compute:
        //   - row sums
        //   - column sums
        //   - diagonal sums
        //
        // Without prefix:
        //   Each check = O(k)
        //
        // With prefix:
        //   Each check = O(1)
        //
        // This is CRITICAL because we are checking MANY squares.

        int[][] rowPref = new int[n][m + 1];   // row-wise prefix
        int[][] colPref = new int[n + 1][m];   // column-wise prefix

        int[][] diag1 = new int[n][m]; // "\" diagonal prefix
        int[][] diag2 = new int[n][m]; // "/" diagonal prefix


        // --------------------------------------------------
        // STEP 1: Build prefix arrays
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // -------------------------------
                // Row prefix
                // -------------------------------
                //
                // rowPref[i][j+1] stores sum from:
                // grid[i][0] → grid[i][j]
                //
                // Why j+1?
                // To avoid boundary checks when subtracting
                rowPref[i][j + 1] =
                    rowPref[i][j] + grid[i][j];


                // -------------------------------
                // Column prefix
                // -------------------------------
                //
                // colPref[i+1][j] stores sum from:
                // grid[0][j] → grid[i][j]
                colPref[i + 1][j] =
                    colPref[i][j] + grid[i][j];


                // -------------------------------
                // Diagonal "\" prefix
                // -------------------------------
                //
                // diag1[i][j] stores sum of:
                // top-left diagonal ending at (i,j)
                //
                // Example:
                // (i-1, j-1) → (i,j)
                diag1[i][j] = grid[i][j];

                if (i > 0 && j > 0)
                    diag1[i][j] += diag1[i - 1][j - 1];


                // -------------------------------
                // Diagonal "/" prefix
                // -------------------------------
                //
                // diag2[i][j] stores sum of:
                // top-right diagonal ending at (i,j)
                //
                // Example:
                // (i-1, j+1) → (i,j)
                diag2[i][j] = grid[i][j];

                if (i > 0 && j < m - 1)
                    diag2[i][j] += diag2[i - 1][j + 1];
            }
        }

        int maxLen = 1; // Minimum magic square is 1x1


        // --------------------------------------------------
        // STEP 2: Try all square sizes
        // --------------------------------------------------
        //
        // Why increasing order?
        // Because we want MAX size
        //
        // Could also try decreasing for early break optimization
        for (int k = 2; k <= Math.min(n, m); k++) {

            // Try every possible top-left corner
            for (int i = 0; i + k <= n; i++) {
                for (int j = 0; j + k <= m; j++) {

                    int r = i + k - 1; // bottom row
                    int c = j + k - 1; // right column


                    // --------------------------------------------------
                    // STEP 3: Check diagonals FIRST (PRUNING STEP)
                    // --------------------------------------------------
                    //
                    // WHY FIRST?
                    //
                    // Diagonal check = O(1)
                    // Row/col check = O(k)
                    //
                    // So we eliminate invalid squares EARLY

                    // "\" diagonal sum
                    int d1 =
                        diag1[r][c]
                        - (i > 0 && j > 0
                            ? diag1[i - 1][j - 1]
                            : 0);

                    // "/" diagonal sum
                    int d2 =
                        diag2[r][j]
                        - (i > 0 && c < m - 1
                            ? diag2[i - 1][c + 1]
                            : 0);

                    // If diagonals don't match → impossible
                    if (d1 != d2)
                        continue;


                    // --------------------------------------------------
                    // STEP 4: Check all rows
                    // --------------------------------------------------
                    //
                    // Each row must sum to d1
                    //
                    // Why d1?
                    // Because all sums (rows/cols/diagonals)
                    // must be equal
                    boolean ok = true;

                    for (int x = i; x <= r && ok; x++) {

                        // Row sum in O(1)
                        int sum =
                            rowPref[x][c + 1]
                            - rowPref[x][j];

                        if (sum != d1)
                            ok = false;
                    }


                    // --------------------------------------------------
                    // STEP 5: Check all columns
                    // --------------------------------------------------
                    for (int y = j; y <= c && ok; y++) {

                        // Column sum in O(1)
                        int sum =
                            colPref[r + 1][y]
                            - colPref[i][y];

                        if (sum != d1)
                            ok = false;
                    }


                    // --------------------------------------------------
                    // STEP 6: Update answer
                    // --------------------------------------------------
                    if (ok)
                        maxLen = k;
                }
            }
        }

        return maxLen;
    }
}