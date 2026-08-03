class Solution1 {

    public int minDistance(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        Integer[][] memo = new Integer[n+1][m+1];
        return recursion(s1, s2, n, m, memo);
    }

    private int recursion(String s1, String s2, int s1Len, int s2Len, Integer[][] memo) {
        if (s1Len==0) return s2Len;
        if (s2Len==0) return s1Len;
        if (memo[s1Len][s2Len]!=null) return memo[s1Len][s2Len];
        char c1 = s1.charAt(s1Len-1);
        char c2 = s2.charAt(s2Len-1);
        if(c1==c2) {
            memo[s1Len][s2Len] = recursion(s1, s2, s1Len-1, s2Len-1, memo);
        }
        else {
            int replace = recursion(s1, s2, s1Len-1, s2Len-1, memo);
            int insert  = recursion(s1, s2, s1Len, s2Len-1, memo);
            int delete  = recursion(s1, s2, s1Len-1, s2Len, memo);
            memo[s1Len][s2Len] = 1 + Math.min(replace, Math.min(insert, delete));
        }
        return memo[s1Len][s2Len];
    }
}

class Solution2 {
    public int minDistance(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n+1][m+1];
        for(int s1Len=1; s1Len<=n; s1Len++) dp[s1Len][0] = s1Len;
        for(int s2Len=1; s2Len<=m; s2Len++) dp[0][s2Len] = s2Len;
        for(int s1Len=1; s1Len<=n; s1Len++) {
            int idx1 = s1Len-1;
            char char1 = s1.charAt(idx1);
            for(int s2Len=1; s2Len<=n; s2Len++) {
                int idx2 = s2Len-1;
                char char2 = s2.charAt(idx2);
                if(char1==char2) {
                    dp[s1Len][s2Len] = dp[s1Len-1][s2Len-1];
                }
                else {
                    dp[s1Len][s2Len] = 1 + Math.min(
                        dp[s1Len-1][s2Len-1],
                        Math.min(
                            dp[s1Len][s2Len-1],
                            dp[s1Len-1][s2Len]
                        )
                    );
                }
            }
        }
        return dp[n][m];
    }
}



