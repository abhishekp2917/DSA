import java.util.Arrays;

class Solution1 {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        Integer[][][] memo = new Integer[n][2][k+1];
        return recursion(prices, 0, 0, k, memo);
    }

    private int recursion(int[] prices, int day, int hasStock, int buyCountRemain, Integer[][][] memo) {
        if(day==prices.length) return 0;
        if(memo[day][hasStock][buyCountRemain]!=null) return memo[day][hasStock][buyCountRemain];
        int stockCurrentPrice = prices[day];
        int profit = 0;
        if(hasStock==1) {
            profit = Math.max(
                stockCurrentPrice + recursion(prices, day+1, 0, buyCountRemain, memo),
                recursion(prices, day+1, 1, buyCountRemain, memo)
            );
        }
        else {
            profit = recursion(prices, day+1, 0, buyCountRemain, memo);
            if(buyCountRemain>0) {
                profit = Math.max(
                    profit, 
                    -stockCurrentPrice + recursion(prices, day+1, 1, buyCountRemain-1, memo)
                );
            }
        }
        memo[day][hasStock][buyCountRemain] = profit;
        return profit;
    }   
}

class Solution2 {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int maxProfit = 0;
        int[][][] dp = new int[n+1][2][k+1];
        Arrays.fill(dp[0][0], Integer.MIN_VALUE/2);
        Arrays.fill(dp[0][1], Integer.MIN_VALUE/2);
        dp[0][0][k] = 0;
        for(int day=1; day<=n; day++) {
            int stockCurrentPrice = prices[day-1];
            for(int buyCountRemain=k-1; buyCountRemain>=0; buyCountRemain--) {
                dp[day][1][buyCountRemain] = Math.max(
                    -stockCurrentPrice + dp[day-1][0][buyCountRemain+1],
                    dp[day-1][1][buyCountRemain]
                );
                dp[day][0][buyCountRemain] = Math.max(
                    stockCurrentPrice + dp[day-1][1][buyCountRemain],
                    dp[day-1][0][buyCountRemain]
                );
            }
        }
        for(int buyCountRemain=0; buyCountRemain<=k; buyCountRemain++) {
            maxProfit = Math.max(maxProfit, dp[n][0][buyCountRemain]);
        }
        return maxProfit;
    }
}