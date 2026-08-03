class Solution1 {
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        Integer[][] memo = new Integer[n][m];
        return recursion(s, t, 0, 0, memo);
    }

    private int recursion(String s, String t, int i, int j, Integer[][] memo) {
        if(i==s.length()) return (j==t.length())? 1 : 0;
        if(j==t.length()) return 1;
        if(memo[i][j]!=null) return memo[i][j];
        int subsequenceCount = recursion(s, t, i+1, j, memo);
        if(s.charAt(i)==t.charAt(j)) {
            subsequenceCount += recursion(s, t, i+1, j+1, memo);
        }
        memo[i][j] = subsequenceCount;
        return subsequenceCount;
    }
}

class Solution2 {
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] dp = new int[n+1][m+1];
        for(int i=0; i<=n; i++) dp[i][m] = 1;
        for(int i=n-1; i>=0; i--) {
            for(int j=m-1; j>=0; j--) {
                dp[i][j] = dp[i+1][j];
                if(s.charAt(i)==t.charAt(j)) {
                    dp[i][j] += dp[i + 1][j + 1];
                }
            }
        }
        return dp[0][0];
    }
}