class Solution1 {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] isPalindrome = getPalindromeMap(s);
        Integer[] memo = new Integer[n];
        return recursion(s, 0, isPalindrome, memo);
    }

    private int recursion(String s, int start, boolean[][] isPalindrome, Integer[] memo) {
        int n = s.length();
        if(start==n) return -1;
        if(memo[start]!=null) return memo[start];
        int minCuts = Integer.MAX_VALUE; 
        for(int end=start; end<n; end++) {
            if(isPalindrome[start][end]) {
                minCuts = Math.min(
                    minCuts,
                    1 + recursion(s, end+1, isPalindrome, memo)
                );
            }
        }
        memo[start] = minCuts;
        return minCuts;
    }

    private boolean[][] getPalindromeMap(String s) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];
        for(int start=0; start<n; start++) isPalindrome[start][start] = true;
        for(int start=n-1; start>=0; start--) {
            for(int end=start+1; end<n; end++) {
                char ch1 = s.charAt(start);
                char ch2 = s.charAt(end);
                isPalindrome[start][end] = (ch1==ch2) && 
                ((start+1<=end-1)? isPalindrome[start+1][end-1] : true);
            }
        }
        return isPalindrome;
    }
}

class Solution2 {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] isPalindrome = getPalindromeMap(s);
        int[] dp = new int[n+1];
        dp[n] = -1;
        for(int start=n-1; start>=0; start--) {
            int minCuts = Integer.MAX_VALUE; 
            for(int end=start; end<n; end++) {
                if(isPalindrome[start][end]) {
                    minCuts = Math.min(
                        minCuts,
                        1 + dp[end+1]
                    );
                }
            }
            dp[start] = minCuts;
        }
        return dp[0];
    }

    private boolean[][] getPalindromeMap(String s) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];
        for(int start=0; start<n; start++) isPalindrome[start][start] = true;
        for(int start=n-1; start>=0; start--) {
            for(int end=start+1; end<n; end++) {
                char ch1 = s.charAt(start);
                char ch2 = s.charAt(end);
                isPalindrome[start][end] = (ch1==ch2) && 
                ((start+1<=end-1)? isPalindrome[start+1][end-1] : true);
            }
        }
        return isPalindrome;
    }
}