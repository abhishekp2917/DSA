class Solution1 {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        Integer[][] memo = new Integer[n][n];
        return recursion(values, 0, n-1, memo);
    }

    private int recursion(int[] values, int start, int end, Integer[][] memo) {
        if(end-start==1) return 0;
        if(memo[start][end]!=null) return memo[start][end];
        int minScore = Integer.MAX_VALUE;
        for(int i=start+1; i<end; i++) {
            int v1 = values[start];
            int v2 = values[i];
            int v3 = values[end];
            int triangleScore = v1*v2*v3;
            int leftPolygonScore = recursion(values, start, i, memo);
            int rightPolygonScore = recursion(values, i, end, memo);
            minScore = Math.min(
                minScore,
                triangleScore + leftPolygonScore + rightPolygonScore
            );
        }
        memo[start][end] = minScore;
        return minScore;
    }
}

class Solution2 {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n+1][n+1];
        for(int start=n-1; start>=0; start--) {
            for(int end=start+2; end<=n-1; end++) {
                int minScore = Integer.MAX_VALUE;
                for(int i=start+1; i<end; i++) {
                    int v1 = values[start];
                    int v2 = values[i];
                    int v3 = values[end];
                    int triangleScore = v1*v2*v3;
                    int leftPolygonScore = dp[start][i];
                    int rightPolygonScore = dp[i][end];
                    minScore = Math.min(
                        minScore,
                        triangleScore + leftPolygonScore + rightPolygonScore
                    );
                }
                dp[start][end] = minScore;
            }
        }
        return dp[0][n-1];
    }
}
