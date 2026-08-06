class Solution1 {

    public int cherryPickup(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        // memo[row][col1][col2]:
        // stores the maximum cherries collectible
        // starting from 'row' when:
        //
        // Robot 1 is at column col1
        // Robot 2 is at column col2.
        //
        // This uniquely defines the state because
        // both robots always move to the next row together.
        Integer[][][] memo = new Integer[n][m][m];

        // Each robot may move:
        // left diagonal
        // down
        // right diagonal.
        int[] dirs = new int[] {-1, 0, 1};

        // Initial positions:
        //
        // Robot 1 -> first column
        // Robot 2 -> last column.
        return recursion(0, 0, m-1, dirs, grid, memo);
    }

    private int recursion(int row, int col1, int col2, int[] dirs, int[][] grid, Integer[][][] memo) {

        int m = grid[0].length;

        // Base Case:
        // Both robots have moved
        // beyond the last row.
        if(row==grid.length) return 0;

        // Invalid position.
        //
        // This movement cannot contribute
        // to a valid path.
        if(col1<0 || col1>=m || col2<0 || col2>=m) {
            return 0;
        }

        // Return previously computed answer.
        if(memo[row][col1][col2]!=null) {
            return memo[row][col1][col2];
        }

        int sum = 0;

        // If both robots occupy
        // different cells,
        // collect cherries from both.
        if(col1!=col2) {
            sum += grid[row][col1] + grid[row][col2];
        }

        // Same cell:
        // collect cherries only once.
        else {
            sum += grid[row][col1];
        }

        int maxSum = 0;

        // Robot 1 has 3 choices.
        for(int dir1 : dirs) {

            int newCol1 = col1 + dir1;

            // Robot 2 also has 3 choices.
            for(int dir2 : dirs) {

                int newCol2 = col2 + dir2;

                // Explore every possible
                // movement combination.
                maxSum = Math.max(
                    maxSum,
                    recursion(
                        row+1,
                        newCol1,
                        newCol2,
                        dirs,
                        grid,
                        memo
                    )
                );
            }
        }

        // Current cherries
        // +
        // best future path.
        memo[row][col1][col2] = sum + maxSum;

        return memo[row][col1][col2];
    }
}

class Solution2 {

    public int cherryPickup(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        // dp[row][col1][col2]:
        // maximum cherries collectible
        // starting from this state.
        int[][][] dp = new int[n][m][m];

        // Build answers from bottom row
        // towards the top,
        // because every state depends
        // on the next row.
        for(int row=n-1; row>=0; row--) {

            for(int col1=0; col1<m; col1++) {

                for(int col2=0; col2<m; col2++) {

                    int sum;

                    // Same cell:
                    // count cherries only once.
                    if(col1==col2) {
                        sum = grid[row][col1];
                    }

                    // Different cells:
                    // collect both cherries.
                    else {
                        sum = grid[row][col1] + grid[row][col2];
                    }

                    int maxSum = 0;

                    // Last row has no future moves.
                    if(row<n-1) {

                        int[] dirs = new int[] {-1, 0, 1};

                        // Try every movement
                        // for Robot 1.
                        for(int dir1 : dirs) {

                            int newCol1 = col1 + dir1;

                            if(newCol1<0 || newCol1>=m) continue;

                            // Try every movement
                            // for Robot 2.
                            for(int dir2 : dirs) {

                                int newCol2 = col2 + dir2;

                                if(newCol2<0 || newCol2>=m) continue;

                                maxSum = Math.max(
                                    maxSum,
                                    dp[row+1][newCol1][newCol2]
                                );
                            }
                        }
                    }

                    // Current cherries
                    // +
                    // best future path.
                    dp[row][col1][col2] = sum + maxSum;
                }
            }
        }

        // Initial positions:
        //
        // Robot 1 -> column 0
        // Robot 2 -> last column.
        return dp[0][0][m-1];
    }
}