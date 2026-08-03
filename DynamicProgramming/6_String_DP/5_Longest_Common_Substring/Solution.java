class Solution1 {
    public int longCommSubstr(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[] maxSubstrLen = new int[1];
        Integer[][] memo = new Integer[n][m];
        recursion(s1, s2, 0, 0, maxSubstrLen, memo);
        return maxSubstrLen[0];
    }
    
    private int recursion(String s1, String s2, int i, int j, int[] maxSubstrLen, Integer[][] memo) {
        if(i==s1.length() || j==s2.length()) return 0;
        if(memo[i][j]!=null) return memo[i][j];
        char char1 = s1.charAt(i);
        char char2 = s2.charAt(j);
        int substrLen = 0;
        if(char1==char2) {
            substrLen = 1 + recursion(s1, s2, i+1, j+1, maxSubstrLen, memo);
        }
        recursion(s1, s2, i+1, j, maxSubstrLen, memo);
        recursion(s1, s2, i, j+1, maxSubstrLen, memo);
        maxSubstrLen[0] = Math.max(maxSubstrLen[0], substrLen);
        memo[i][j] = substrLen;
        return substrLen;
    }
}

class Solution2 {

    public int longCommSubstr(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int maxSubstrLen = 0;
        int[][] dp = new int[n+1][m+1];
        for(int arrLen1=1; arrLen1<=n; arrLen1++) {
            int idx1 = arrLen1-1;
            char char1 = str1.charAt(idx1);
            for(int arrLen2=1; arrLen2<=m; arrLen2++) {
                int idx2 = arrLen2-1;
                char char2 = str2.charAt(idx2);
                if(char1==char2) {
                    dp[arrLen1][arrLen2] = 1 + dp[arrLen1-1][arrLen2-1];
                    // since here dp[i][j] means longest common substring where S1 ends at i and S2 ends at j i.e. char at i and j must be included,
                    // the subproblem has additional condition i.e. longest common substring ending at (i,j)  
                    // and not longest common substring of substring (0, i) and (0, j), we need to take answer as max of all dp states 
                    maxSubstrLen = Math.max(maxSubstrLen, dp[arrLen1][arrLen2]);
                }
            }
        }
        return maxSubstrLen;
    }
}
