class Solution {

    public int[][] constructProductMatrix(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        int[][] ans = new int[n][m];

        // --------------------------------------------------
        // STEP 1: Flatten 2D → 1D (conceptually)
        // --------------------------------------------------
        //
        // Instead of handling 2D neighbors,
        // we treat matrix as a 1D array of size n*m
        //
        // This simplifies:
        // "product of all except self" problem

        int[] prefixProd = new int[n * m];
        int[] suffixProd = new int[n * m];

        int prod = 1;
        int idx = 0;


        // --------------------------------------------------
        // STEP 2: Build prefix product
        // --------------------------------------------------
        //
        // prefixProd[i] = product of all elements BEFORE i
        //
        // Why store BEFORE multiplying?
        // Because we want "product except self"
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {

                prefixProd[idx] = prod;

                // Multiply current value for future
                //
                // Important:
                // take mod at each step to avoid overflow
                prod = (prod * (grid[r][c] % 12345)) % 12345;

                idx++;
            }
        }


        // --------------------------------------------------
        // STEP 3: Build suffix product
        // --------------------------------------------------
        //
        // suffixProd[i] = product of all elements AFTER i
        prod = 1;
        idx = n * m - 1;

        for (int r = n - 1; r >= 0; r--) {
            for (int c = m - 1; c >= 0; c--) {

                suffixProd[idx] = prod;

                prod = (prod * (grid[r][c] % 12345)) % 12345;

                idx--;
            }
        }


        // --------------------------------------------------
        // STEP 4: Combine prefix + suffix
        // --------------------------------------------------
        //
        // For each index:
        //
        // answer = product of all elements except itself
        //
        // = prefix * suffix
        idx = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {

                ans[r][c] =
                    (prefixProd[idx] * suffixProd[idx]) % 12345;

                idx++;
            }
        }

        return ans;
    }
}