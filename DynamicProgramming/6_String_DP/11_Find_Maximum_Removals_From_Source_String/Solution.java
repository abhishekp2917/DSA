class Solution1 {
    public int maxRemovals(String source, String pattern, int[] targetIndices) {
        int n = source.length();
        int m = pattern.length();
        boolean[] canRemove = new boolean[n];
        for(int index : targetIndices) canRemove[index] = true;
        Integer[][] memo = new Integer[n][m];
        return recursion(source, pattern, canRemove, 0, 0, memo);
    }

    private int recursion(String source, String pattern, boolean[] canRemove, int i, int j, Integer[][] memo) {
        int n = source.length();
        int m = pattern.length();
        if(j==m) {
            int removeRemaining = 0;
            while(i<n) {
                removeRemaining += (canRemove[i]? 1 : 0);
                i++;
            }
            return removeRemaining;
        }
        if(i==n) return Integer.MIN_VALUE;
        if(memo[i][j]!=null) return memo[i][j];
        int maxOperation = recursion(source, pattern, canRemove, i+1, j, memo);
        if(source.charAt(i)==pattern.charAt(j)) {
            maxOperation = Math.max(
                maxOperation,
                recursion(source, pattern, canRemove, i+1, j+1, memo)
            );
        }
        if(canRemove[i]) {
            maxOperation = Math.max(
                maxOperation,
                1 + recursion(source, pattern, canRemove, i+1, j, memo)
            );
        }
        memo[i][j] = maxOperation;
        return maxOperation;
    }
}

class Solution2 {
    public int maxRemovals(String source, String pattern, int[] targetIndices) {
        int n = source.length();
        int m = pattern.length();
        boolean[] canRemove = new boolean[n];
        for(int index : targetIndices) canRemove[index] = true;
        int[][] dp = new int[n+1][m+1];
        for(int j=0; j<m; j++) dp[n][j] = Integer.MIN_VALUE;
        for(int i=0; i<n; i++) {
            int idx = i;
            int operationRemaining = 0;
            while(idx<n) {
                operationRemaining += (canRemove[idx]? 1 : 0);
                idx++;
            }
            dp[i][m] = operationRemaining;
        }
        for(int i=n-1; i>=0; i--) {
            for(int j=m-1; j>=0; j--) {
                int maxOperation = dp[i+1][j];
                if(source.charAt(i)==pattern.charAt(j)) {
                    maxOperation = Math.max(
                        maxOperation,
                        dp[i+1][j+1]
                    );
                }
                if(canRemove[i]) {
                    maxOperation = Math.max(
                        maxOperation,
                        1 + dp[i+1][j]
                    );
                }
                dp[i][j] = maxOperation;
            }
        }
        return dp[0][0];
    }
}






