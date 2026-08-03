class Solution1 {
    public int getMoneyAmount(int n) {
        Integer[][] memo = new Integer[n+1][n+1];
        return recursion(1, n, memo);
    }

    private int recursion(int start, int end, Integer[][] memo) {
        if(start>=end) return 0;
        if(memo[start][end]!=null) return memo[start][end];
        int minMoneyNeededForWin = Integer.MAX_VALUE;
        for(int guess=start; guess<=end; guess++) {
            int moneyNeededIfHigher = recursion(guess+1, end, memo);
            int moneyNeededIfLower = recursion(start, guess-1, memo);
            int maxMoneyNeededInWorstCase = Math.max(
                moneyNeededIfHigher,
                moneyNeededIfLower
            );
            minMoneyNeededForWin = Math.min(
                minMoneyNeededForWin,
                guess + maxMoneyNeededInWorstCase
            );
        }
        memo[start][end] = minMoneyNeededForWin;
        return minMoneyNeededForWin;
    }
}

class Solution2 {
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n+1][n+1];
        for(int start=n-1; start>=1; start--) {
            for(int end=start+1; end<=n; end++) {
                int minMoneyNeededForWin = Integer.MAX_VALUE;
                for(int guess=start; guess<=end; guess++) {
                    int moneyNeededIfHigher = (guess+1<=n)? dp[guess+1][end] : 0;
                    int moneyNeededIfLower = (guess-1>=0)? dp[start][guess-1] : 0;
                    int maxMoneyNeededInWorstCase = Math.max(
                        moneyNeededIfHigher,
                        moneyNeededIfLower
                    );
                    minMoneyNeededForWin = Math.min(
                        minMoneyNeededForWin,
                        guess + maxMoneyNeededInWorstCase
                    );
                }
                dp[start][end] = minMoneyNeededForWin;
            }
        }
        return dp[1][n];
    }
}