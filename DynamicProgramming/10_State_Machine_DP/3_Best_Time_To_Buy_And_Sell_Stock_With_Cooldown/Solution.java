class Solution1 {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        Integer[][] memo = new Integer[n][2];
        return recursion(prices, 0, 0, memo);
    }

    private int recursion(int[] prices, int day, int hasStock, Integer[][] memo) {
        if(day>=prices.length) return 0;
        if(memo[day][hasStock]!=null) return memo[day][hasStock];
        int stockCurrentPrice = prices[day];
        int profit = 0;
        if(hasStock==1) {
            profit = Math.max(
                stockCurrentPrice + recursion(prices, day+2, 0, memo),
                recursion(prices, day+1, 1, memo)
            );
        }
        else {
            profit = Math.max(
                -stockCurrentPrice + recursion(prices, day+1, 1, memo),
                recursion(prices, day+1, 0, memo)
            );
        }
        memo[day][hasStock] = profit;
        return profit;
    }
}

class Solution2 {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        dp[0][1] = Integer.MIN_VALUE/2;
        dp[1][1] = -prices[0];
        for(int day=2; day<=n; day++) {
            int stockCurrentPrice = prices[day-1];
            dp[day][1] = Math.max(
                dp[day-1][1],
                -stockCurrentPrice + dp[day-2][0]
            );
            dp[day][0] = Math.max(
                dp[day-1][0],
                stockCurrentPrice + dp[day-1][1]
            );
        }
        return dp[n][0];
    }
}
