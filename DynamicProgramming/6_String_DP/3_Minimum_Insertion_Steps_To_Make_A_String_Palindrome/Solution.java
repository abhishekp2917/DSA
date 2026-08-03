class Solution1 {
    
    public int minInsertions(String s) {
        int n = s.length();
        Integer[][] memo = new Integer[n][n];
        return recurison(s, 0, n-1, memo);
    }

    private int recurison(String s, int i, int j, Integer[][] memo) {
        if(i>=j) return 0;
        if(memo[i][j]!=null) return memo[i][j];
        char char1 = s.charAt(i);
        char char2 = s.charAt(j);
        if(char1==char2) {
            memo[i][j] = recurison(s, i+1, j-1, memo);
        }
        else {
            memo[i][j] = 1 + Math.min(
                recurison(s, i, j-1, memo),
                recurison(s, i+1, j, memo)
            );
        }
        return memo[i][j];
    }
}

class Solution2 {
    public int minInsertions(String s) {
        int n = s.length();
        String reverse = new StringBuilder(s).reverse().toString();
        int lcsLen = longestCommonSubsequence(s, reverse); 
        return n-lcsLen;
    }

    public int longestCommonSubsequence(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n+1][m+1];
        for(int s1Len=1; s1Len<=n; s1Len++) {
            int idx1 = s1Len-1;
            char char1 = s1.charAt(idx1);
            for(int s2Len=1; s2Len<=m; s2Len++) {
                int idx2 = s2Len-1;
                char char2 = s2.charAt(idx2);
                if(char1 == char2)
                    dp[s1Len][s2Len] = dp[s1Len-1][s2Len-1] + 1;
                else
                    dp[s1Len][s2Len] = Math.max(
                        dp[s1Len-1][s2Len],
                        dp[s1Len][s2Len-1]
                    );
            }
        }
        return dp[n][m];
    }
}

