class Solution1 {

    public boolean isMatch(String word, String pattern) {
        int n = word.length();
        int m = pattern.length();
        Boolean[][] memo = new Boolean[n+1][m+1];
        return recursion(word, pattern, n, m, memo);
    }

    private boolean recursion(String word, String pattern, int wLen, int pLen, Boolean[][] memo) {
        if(wLen==0 && pLen==0) return true;
        if(pLen==0) return false;
        if(wLen==0) {
            for (int i = 0; i < pLen; i++) {
                if (pattern.charAt(i)!='*') return false;
            }
            return true;
        }
        if(memo[wLen][pLen]!=null) return memo[wLen][pLen];
        char char1 = word.charAt(wLen-1);
        char char2 = pattern.charAt(pLen-1);
        if(char1==char2 || char2=='?') {
            memo[wLen][pLen] = recursion(word, pattern, wLen-1, pLen-1, memo);
        }
        else if(char2=='*') {
            memo[wLen][pLen] =
                recursion(word, pattern, wLen-1, pLen-1, memo) ||
                recursion(word, pattern, wLen,   pLen-1, memo) ||
                recursion(word, pattern, wLen-1, pLen,   memo);
        }
        else memo[wLen][pLen] = false;
        return memo[wLen][pLen];
    }
}

class Solution2 {
    public boolean isMatch(String word, String pattern) {
        int n = word.length();
        int m = pattern.length();
        boolean[][] dp = new boolean[n+1][m+1];
        dp[0][0] = true;
        for(int j=1; j<=m; j++) {
            if(pattern.charAt(j-1)!='*') break;
            dp[0][j] = true;
        }
        for(int wLen=1; wLen<=n; wLen++) {
            int idx1 = wLen-1;
            char char1 = word.charAt(idx1);
            for(int pLen=1; pLen<=m; pLen++) {
                int idx2 = pLen-1;
                char char2 = pattern.charAt(idx2);
                if(char1==char2 || char2=='?') {
                    dp[wLen][pLen] = dp[wLen-1][pLen-1];
                }
                else if(char2=='*') {
                    dp[wLen][pLen] = dp[wLen-1][pLen-1] || dp[wLen][pLen-1] || dp[wLen-1][pLen];
                }
            }
        }
        return dp[n][m];
    }
}


