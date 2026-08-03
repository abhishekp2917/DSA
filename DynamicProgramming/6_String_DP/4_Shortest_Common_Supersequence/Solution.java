class Solution {
    public String shortestCommonSupersequence(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        String lcs = longestCommonSubsequenceString(s1, s2);
        StringBuilder supersequence = new StringBuilder();
        int i=0, j=0, k=0;
        while(i<n || j<m || k<lcs.length()) {
            char char1 = (i<n)? s1.charAt(i) : '\0';
            char char2 = (j<m)? s2.charAt(j) : '\0';
            char char3 = (k<lcs.length())? lcs.charAt(k) : '\0';
            if(char1==char3 && char2==char3) {
                supersequence.append(char1);
                i++;
                j++;
                k++;
            }
            else {
                while(i<n && s1.charAt(i)!=char3) {
                    supersequence.append(s1.charAt(i));
                    i++;
                }
                while(j<m && s2.charAt(j)!=char3) {
                    supersequence.append(s2.charAt(j));
                    j++;
                }
            }
        }
        return supersequence.toString();
    }

    public String longestCommonSubsequenceString(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        StringBuilder lcs = new StringBuilder();
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
        int i=n, j=m;
        while(i>0 && j>0) {
            char char1 = s1.charAt(i-1);
            char char2 = s2.charAt(j-1);
            if(char1==char2) {
                lcs.append(char1);
                i--;
                j--;
            }
            else if(dp[i-1][j]>dp[i][j-1]) i--;
            else j--;
        }
        return lcs.reverse().toString();
    }
}