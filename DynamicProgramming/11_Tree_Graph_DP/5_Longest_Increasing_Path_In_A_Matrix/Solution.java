class Solution {

    public int longestIncreasingPath(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        int longestIncreasingPath = 0;

        // memo[row][col]:
        // stores the length of the longest increasing path
        // starting from this cell.
        //
        // Once computed, the answer for a cell never changes
        // because every recursive call explores the same
        // increasing neighbours.
        Integer[][] memo = new Integer[n][m];

        // Four possible movement directions.
        int[][] dirs = new int[][] {
            {1, 0},
            {0, 1},
            {-1, 0},
            {0, -1}
        };

        // Every cell can potentially be the starting point
        // of the longest increasing path.
        //
        // Therefore compute the answer starting
        // from every cell and keep the maximum.
        for(int row=0; row<n; row++) {
            for(int col=0; col<m; col++) {

                longestIncreasingPath = Math.max(
                    longestIncreasingPath,
                    recursion(matrix, dirs, row, col, memo)
                );
            }
        }

        return longestIncreasingPath;
    }

    private int recursion(int[][] matrix, int[][] dirs, int row, int col, Integer[][] memo) {

        int n = matrix.length;
        int m = matrix[0].length;

        // This cell's longest path
        // has already been computed.
        //
        // Reusing it avoids exploring
        // the same increasing paths again.
        if(memo[row][col]!=null) {
            return memo[row][col];
        }

        // Every cell itself forms
        // an increasing path
        // of length 1.
        int longestIncreasingPath = 1;

        int curr = matrix[row][col];

        // Try extending the path
        // in every direction.
        for(int[] dir : dirs) {

            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Ignore invalid cells.
            //
            // Also ignore cells
            // having smaller or equal value
            // because the path
            // must be strictly increasing.
            if(newRow>=n || newRow<0 ||
               newCol>=m || newCol<0 ||
               matrix[newRow][newCol]<=curr) {
                continue;
            }

            // Valid increasing neighbour.
            //
            // Current cell contributes 1,
            // then continue the longest path
            // starting from the neighbour.
            longestIncreasingPath = Math.max(
                longestIncreasingPath,
                1 + recursion(
                    matrix,
                    dirs,
                    newRow,
                    newCol,
                    memo
                )
            );
        }

        // Store the computed answer
        // so future DFS calls
        // starting from this cell
        // can directly reuse it.
        memo[row][col] = longestIncreasingPath;

        return longestIncreasingPath;
    }
}