import java.util.Arrays;

class Solution1 {
    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
        Integer[][] memo = new Integer[m][m];
        Arrays.sort(cuts);
        return recursion(n, cuts, 0, m-1, memo);
    }   

    private int recursion(int n, int[] cuts, int start, int end, Integer[][] memo) {
        if(start>end) return 0;
        if(memo[start][end]!=null) return memo[start][end];
        int stickCuttingCost = Integer.MAX_VALUE;
        for(int i=start; i<=end; i++) {
            int stickLen = ((end+1<cuts.length)? cuts[end+1] : n)-((start-1>=0)? cuts[start-1] : 0);
            int leftStickCuttingCost = recursion(n, cuts, start, i-1, memo);
            int rightStickCuttingCost = recursion(n, cuts, i+1, end, memo);
            stickCuttingCost = Math.min(
                stickCuttingCost,
                stickLen + leftStickCuttingCost + rightStickCuttingCost
            );
        }
        memo[start][end] = stickCuttingCost;
        return stickCuttingCost;
    }
}

class Solution2 {
    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
        int[][] dp = new int[m][m];
        Arrays.sort(cuts);
        for(int start=m-1; start>=0; start--) {
            for(int end=start; end<m; end++) {
                int stickCuttingCost = Integer.MAX_VALUE;
                for(int i=start; i<=end; i++) {
                    int stickLen = ((end+1<m)? cuts[end+1] : n)-((start-1>=0)? cuts[start-1] : 0);
                    int leftStickCuttingCost = ((i-1>=0)? dp[start][i-1] : 0);
                    int rightStickCuttingCost = ((i+1<m)? dp[i+1][end] : 0);
                    stickCuttingCost = Math.min(
                        stickCuttingCost,
                        stickLen + leftStickCuttingCost + rightStickCuttingCost
                    );
                    dp[start][end] = stickCuttingCost;
                }
            }
        }
        return dp[0][m-1];
    }   
}


