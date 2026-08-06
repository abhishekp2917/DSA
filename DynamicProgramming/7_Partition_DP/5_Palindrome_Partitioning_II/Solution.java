class Solution1 {

    public int minCut(String s) {

        int n = s.length();

        // Precompute whether every substring
        // is a palindrome.
        //
        // This allows every palindrome check
        // during DP to become O(1)
        // instead of repeatedly scanning substrings.
        boolean[][] isPalindrome = getPalindromeMap(s);

        // memo[start]:
        // stores the minimum cuts required
        // for the suffix starting at 'start'.
        Integer[] memo = new Integer[n];

        return recursion(s, 0, isPalindrome, memo);
    }

    private int recursion(String s, int start, boolean[][] isPalindrome, Integer[] memo) {

        int n = s.length();

        // Entire string
        // has already been partitioned.
        //
        // Return -1 because
        // the last palindrome partition
        // should not contribute
        // an extra cut.
        //
        // Example:
        //
        // "aba"
        //
        // We partition once,
        // but perform zero cuts.
        if(start==n) return -1;

        // Reuse previously computed answer.
        if(memo[start]!=null) return memo[start];

        int minCuts = Integer.MAX_VALUE;

        // Try every possible palindrome
        // starting from 'start'.
        for(int end=start; end<n; end++) {

            if(isPalindrome[start][end]) {

                // Make one cut
                // after the current palindrome
                // and solve the remaining suffix.
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

        // isPalindrome[start][end]
        // tells whether
        // substring(start,end)
        // is a palindrome.
        boolean[][] isPalindrome = new boolean[n][n];

        // Every single character
        // is always a palindrome.
        for(int start=0; start<n; start++) isPalindrome[start][start] = true;

        // Build longer substrings
        // from shorter substrings.
        //
        // We iterate start backwards
        // so that
        // isPalindrome[start+1][end-1]
        // has already been computed.
        for(int start=n-1; start>=0; start--) {

            for(int end=start+1; end<n; end++) {

                char ch1 = s.charAt(start);

                char ch2 = s.charAt(end);

                // A substring is a palindrome if:
                //
                // 1. Both boundary characters match.
                //
                // 2. The inner substring
                //    is also a palindrome.
                //
                // For length 2,
                // there is no inner substring,
                // so it is automatically true.
                isPalindrome[start][end] =
                    (ch1==ch2) &&
                    ((start+1<=end-1)? isPalindrome[start+1][end-1] : true);
            }
        }

        return isPalindrome;
    }
}

class Solution2 {

    public int minCut(String s) {

        int n = s.length();

        // Precompute palindrome information
        // once for all DP states.
        boolean[][] isPalindrome = getPalindromeMap(s);

        // dp[start]:
        // minimum cuts required
        // for suffix starting at 'start'.
        int[] dp = new int[n+1];

        // Base case:
        //
        // No characters remain.
        //
        // Return -1 so that
        // the last palindrome
        // does not contribute
        // an unnecessary cut.
        dp[n] = -1;

        // Build answers
        // from the end of the string
        // because every transition
        // moves to a larger index.
        for(int start=n-1; start>=0; start--) {

            int minCuts = Integer.MAX_VALUE;

            // Try every palindrome
            // beginning at 'start'.
            for(int end=start; end<n; end++) {

                if(isPalindrome[start][end]) {

                    // Make one cut
                    // after this palindrome
                    // and solve the remaining suffix.
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

        // isPalindrome[start][end]
        // stores whether
        // substring(start,end)
        // is a palindrome.
        boolean[][] isPalindrome = new boolean[n][n];

        // Every single character
        // is a palindrome.
        for(int start=0; start<n; start++) isPalindrome[start][start] = true;

        // Compute longer substrings
        // after smaller substrings
        // because every state depends
        // on its inner substring.
        for(int start=n-1; start>=0; start--) {

            for(int end=start+1; end<n; end++) {

                char ch1 = s.charAt(start);

                char ch2 = s.charAt(end);

                isPalindrome[start][end] =
                    (ch1==ch2) &&
                    ((start+1<=end-1)? isPalindrome[start+1][end-1] : true);
            }
        }

        return isPalindrome;
    }
}