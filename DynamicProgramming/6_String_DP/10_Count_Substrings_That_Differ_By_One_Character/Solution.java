class Solution1 {

    public int countSubstrings(String s, String t) {
        int n = s.length();
        int m = t.length();
        Integer[][][] memo = new Integer[n+1][m+1][2];
        int substrCount = 0;
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=m; j++) {
                substrCount += recursion(s, t, i, j, 1, memo);
            }
        }
        return substrCount;
    }

    private int recursion(String s, String t, int i, int j, int state, Integer[][][] memo) {
        if(i==0 || j==0) return 0;
        if(memo[i][j][state]!=null) return memo[i][j][state];
        char c1 = s.charAt(i-1);
        char c2 = t.charAt(j-1);
        if(state==0) {
            if(c1==c2) memo[i][j][0] = 1 + recursion(s, t, i-1, j-1, 0, memo);
            else memo[i][j][0] = 0;
        }
        else {
            if(c1!=c2) memo[i][j][1] = 1 + recursion(s, t, i-1, j-1, 0, memo);
            else memo[i][j][1] = recursion(s, t, i-1, j-1, 1, memo);
        }
        return memo[i][j][state];
    }
}

class Solution2 {
    public int countSubstrings(String s, String t) {
        int n = s.length();
        int m = t.length();
        int substrCount = 0;
        int[][][] dp = new int[n+1][m+1][2];
        // dp[i][j][0] : length of common substring ending at (i, j) without mismatch
        // dp[i][j][1] : length of common substring with exactly one mismatch at the end (x, y)
        for(int i=1; i<=n; i++) {
            for(int j=1; j<=m; j++) {
                if(s.charAt(i-1)!=t.charAt(j-1)) {
                    dp[i][j][1] = 1 + dp[i-1][j-1][0];
                }
                else {
                    dp[i][j][0] = 1 + dp[i-1][j-1][0];
                    dp[i][j][1] = dp[i-1][j-1][1];
                }
                substrCount += dp[i][j][1];
            }
        }
        return substrCount;
    }
}