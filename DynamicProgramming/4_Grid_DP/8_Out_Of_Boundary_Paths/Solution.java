class Solution1 {

    public int findPaths(int n, int m, int maxMove, int startRow, int startColumn) {

        final int MOD = 1000_000_007;

        // memo[row][col][moves]:
        // stores the number of paths
        // that move outside the grid
        // starting from (row, col)
        // with at most 'moves' moves remaining.
        //
        // Memoization prevents solving
        // the same state repeatedly.
        Long[][][] memo = new Long[n][m][maxMove+1];

        // Four possible movement directions.
        int[][] dirs = new int[][] {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        return (int)(
            recursion(
                n,
                m,
                dirs,
                startRow,
                startColumn,
                maxMove,
                memo
            ) % MOD
        );
    }

    private long recursion(int n, int m, int[][] dirs, int row, int col, int maxMove, Long[][][] memo) {

        // Base Case:
        //
        // Ball has already moved
        // outside the grid.
        //
        // This forms one valid path.
        if((row==n || col==m || row==-1 || col==-1) && maxMove>=0) {
            return 1;
        }

        // No moves left,
        // but ball is still inside the grid.
        //
        // Therefore no valid path exists.
        if(maxMove==0) {
            return 0;
        }

        // Return previously computed answer.
        if(memo[row][col][maxMove]!=null) {
            return memo[row][col][maxMove];
        }

        final int MOD = 1000_000_007;

        long pathCount = 0;

        // Try all four possible moves.
        for(int[] dir : dirs) {

            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Every direction contributes
            // independently to the answer,
            // so add all valid paths.
            pathCount += recursion(
                n,
                m,
                dirs,
                newRow,
                newCol,
                maxMove-1,
                memo
            );
        }

        memo[row][col][maxMove] = pathCount % MOD;

        return pathCount;
    }
}

class Solution2 {

    public int findPaths(int n, int m, int maxMove, int startRow, int startColumn) {

        final int MOD = 1000_000_007;

        // dp[row][col][moves]:
        // stores the number of paths
        // that move outside the grid
        // starting from (row, col)
        // using at most 'moves' moves.
        long[][][] dp = new long[n][m][maxMove+1];

        // Four possible movement directions.
        int[][] dirs = new int[][] {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        // Build answers
        // for increasing number of moves.
        for(int moves=1; moves<=maxMove; moves++) {

            for(int row=0; row<n; row++) {

                for(int col=0; col<m; col++) {

                    long pathCount = 0;

                    // Try every possible move.
                    for(int[] dir : dirs) {

                        int newRow = row + dir[0];
                        int newCol = col + dir[1];

                        // Ball leaves the grid.
                        //
                        // This contributes
                        // one valid path.
                        if(newRow<0 || newRow==n ||
                           newCol<0 || newCol==m) {

                            pathCount++;
                        }

                        // Otherwise continue
                        // from the neighbouring cell
                        // using one fewer move.
                        else {

                            pathCount +=
                                dp[newRow][newCol][moves-1];
                        }
                    }

                    dp[row][col][moves] =
                        pathCount % MOD;
                }
            }
        }

        // Required starting position
        // with maximum allowed moves.
        return (int)dp[startRow][startColumn][maxMove];
    }
}