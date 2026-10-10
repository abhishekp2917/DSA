class Solution1 {

    public int countSquares(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        // Prefix Sum matrix.
        //
        // dp[row][col] stores the total number of 1s
        // inside the rectangle:
        //
        // (0,0) -> (row-1,col-1)
        //
        // Extra row and column remove
        // boundary checks while querying.
        int[][] dp = new int[n+1][m+1];

        int squaresCount = 0;

        // Build Prefix Sum matrix.
        for(int row=1; row<=n; row++) {

            for(int col=1; col<=m; col++) {

                // Standard 2D Prefix Sum formula.
                //
                // Current rectangle =
                // Top rectangle
                // + Left rectangle
                // - Overlapping rectangle
                // + Current cell
                dp[row][col] =
                    dp[row-1][col] +
                    dp[row][col-1] -
                    dp[row-1][col-1] +
                    matrix[row-1][col-1];

                // Treat current cell as
                // the bottom-right corner
                // of every possible square.
                //
                // Try every possible side length.
                for(int len=1; len<=Math.min(row, col); len++) {

                    // If a square contains only 1s,
                    // then its total sum must equal:
                    //
                    // side × side
                    int expectedSum = len * len;

                    // Query sum of current square
                    // in O(1) using Prefix Sum.
                    int actualSum =
                        dp[row][col]
                        - dp[row-len][col]
                        - dp[row][col-len]
                        + dp[row-len][col-len];

                    // Sum equals area,
                    // therefore every cell is 1.
                    if(actualSum==expectedSum) {
                        squaresCount++;
                    }
                }
            }
        }

        return squaresCount;
    }
}

class Solution2 {

    public int countSquares(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        // dp[row][col]:
        // stores the side length of the
        // largest all-1 square whose
        // bottom-right corner is
        // matrix[row-1][col-1].
        //
        // Extra row and column simplify
        // boundary handling.
        int[][] dp = new int[n+1][m+1];

        int squaresCount = 0;

        for(int row=1; row<=n; row++) {

            for(int col=1; col<=m; col++) {

                // A square cannot end
                // at a cell containing 0.
                if(matrix[row-1][col-1]==0) continue;

                // To form a square of side x,
                // all three neighbouring squares
                // must already support it.
                //
                // These neighbours are:
                //
                // Top
                // Left
                // Top-left diagonal
                //
                // The smallest neighbour becomes
                // the bottleneck.
                //
                // Example:
                //
                // Top = 4
                // Left = 3
                // Diagonal = 2
                //
                // Largest possible square here
                // can only be:
                //
                // 1 + min(4,3,2)
                // = 3
                //
                // Even though two neighbours
                // can support larger squares,
                // the diagonal limits expansion.
                dp[row][col] =
                    1 + Math.min(
                        dp[row-1][col-1],
                        Math.min(
                            dp[row-1][col],
                            dp[row][col-1]
                        )
                    );

                // If largest square size is x,
                // then this cell also forms
                // every smaller square ending here.
                //
                // Example:
                //
                // dp = 3
                //
                // contributes:
                // 1×1
                // 2×2
                // 3×3
                //
                // Hence contribution equals dp value.
                squaresCount += dp[row][col];
            }
        }

        return squaresCount;
    }
}