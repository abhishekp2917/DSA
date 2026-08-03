class Solution1 {
    public boolean isInterleave(String s1, String s2, String s3) {
        int n1 = s1.length();
        int n2 = s2.length();
        int n3 = s3.length();
        if(n1+n2!=n3) return false;
        Boolean[][] memo = new Boolean[n1+1][n2+1];
        return recursion(s1, s2, s3, 0, 0, memo);
    }

    private boolean recursion(String s1, String s2, String s3, int start1, int start2, Boolean[][] memo) {
        if(start1==s1.length() && start2==s2.length()) return true;
        if(memo[start1][start2]!=null) return memo[start1][start2];
        boolean isPoss = false;
        int start3 = start1 + start2;
        if(start1<s1.length() && s1.charAt(start1)==s3.charAt(start3)) {
            isPoss |= recursion(s1, s2, s3, start1+1, start2, memo);
        }
        if(start2<s2.length() && s2.charAt(start2)==s3.charAt(start3)) {
            isPoss |= recursion(s1, s2, s3, start1, start2+1, memo);
        }
        memo[start1][start2] = isPoss;
        return isPoss;
    }
}

class Solution2 {
    public boolean isInterleave(String s1, String s2, String s3) {
        int n1 = s1.length();
        int n2 = s2.length();
        int n3 = s3.length();
        if(n1+n2!=n3) return false;
        boolean[][] dp = new boolean[n1+1][n2+1];
        dp[n1][n2] = true;
        for(int start1=n1; start1>=0; start1--) {
            for(int start2=n2; start2>=0; start2--) {
                if(start1==n1 && start2==n2) continue;
                boolean isPoss = false;
                int start3 = start1 + start2;
                if(start1<s1.length() && s1.charAt(start1)==s3.charAt(start3)) {
                    isPoss |= dp[start1+1][start2];
                }
                if(start2<s2.length() && s2.charAt(start2)==s3.charAt(start3)) {
                    isPoss |= dp[start1][start2+1];
                }
                dp[start1][start2] = isPoss;
            }
        }
        return dp[0][0];
    }
}