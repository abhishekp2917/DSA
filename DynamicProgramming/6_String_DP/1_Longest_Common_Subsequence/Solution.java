class Solution1 {
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        Integer[][] memo = new Integer[n+1][m+1];
        return recursion(text1, text2, n, m, memo);
    }

    private int recursion(String text1, String text2, int text1Len, int text2Len, Integer[][] memo) {
        if(text1Len<=0 || text2Len<=0) return 0;
        if(memo[text1Len][text2Len]!=null) return memo[text1Len][text2Len];
        char ch1 = text1.charAt(text1Len-1);
        char ch2 = text2.charAt(text2Len-1);
        if(ch1==ch2) {
            memo[text1Len][text2Len] = 1 + recursion(text1, text2, text1Len-1, text2Len-1, memo);
        }
        else {
            memo[text1Len][text2Len] = Math.max(
                recursion(text1, text2, text1Len-1, text2Len, memo),
                recursion(text1, text2, text1Len, text2Len-1, memo)
            );
        }
        return memo[text1Len][text2Len];
    }
}

class Solution2 {
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][] dp = new int[n+1][m+1];
        for(int text1Len=1; text1Len<=n; text1Len++) {
            int idx1 = text1Len-1;
            char char1 = text1.charAt(idx1);
            for(int text2Len=1; text2Len<=m; text2Len++) {
                int idx2 = text2Len-1;
                char char2 = text2.charAt(idx2);
                if(char1 == char2)
                    dp[text1Len][text2Len] = dp[text1Len-1][text2Len-1] + 1;
                else
                    dp[text1Len][text2Len] = Math.max(
                        dp[text1Len-1][text2Len],
                        dp[text1Len][text2Len-1]
                    );
            }
        }
        return dp[n][m];
    }
}