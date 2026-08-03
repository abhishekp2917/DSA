class Solution1 {
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        Integer[][][] memo = new Integer[n][m][m];
        int[] dirs = new int[] {-1, 0, 1};
        return recursion(0, 0, m-1, dirs, grid, memo);
    }

    private int recursion(int row, int col1, int col2, int[] dirs, int[][] grid, Integer[][][] memo) {
        int m = grid[0].length;
        if(row==grid.length) return 0;
        if(col1<0 || col1>=m || col2<0 || col2>=m) return 0;
        if(memo[row][col1][col2]!=null) return memo[row][col1][col2];
        int sum = 0;
        if(col1!=col2) sum += grid[row][col1] + grid[row][col2];
        else sum += grid[row][col1];
        int maxSum = 0;
        for(int dir1 : dirs) {
            int newCol1 = col1 + dir1;
            for(int dir2 : dirs) {
                int newCol2 = col2 + dir2;
                maxSum = Math.max(
                    maxSum, 
                    recursion(row+1, newCol1, newCol2, dirs, grid, memo)
                );
            }
        }
        memo[row][col1][col2] = sum + maxSum;
        return memo[row][col1][col2];
    } 
}

class Solution2 {
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][][] dp = new int[n][m][m];
        
        for(int row=n-1; row>=0; row--) {
            for(int col1=0; col1<m; col1++) {
                for(int col2=0; col2<m; col2++) {
                    int sum = 0; 
                    if(col1==col2) sum = grid[row][col1];
                    else sum = grid[row][col1] + grid[row][col2];
                    int maxSum = 0;
                    if(row<n-1) {
                        int[] dirs = new int[] {-1, 0, 1};
                        for(int dir1 : dirs) {
                            int newCol1 = col1 + dir1;
                            if(newCol1<0 || newCol1>=m) continue;
                            for(int dir2 : dirs) {
                                int newCol2 = col2 + dir2;
                                if(newCol2<0 || newCol2>=m) continue;
                                maxSum = Math.max(maxSum, dp[row+1][newCol1][newCol2]);
                            }
                        }
                    }
                    dp[row][col1][col2] = sum + maxSum;
                }
            }
        }
        return dp[0][0][m-1];
    }
}



