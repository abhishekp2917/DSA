import java.util.Arrays;

class Solution1 {

    public int maxProfit(int k, int[] prices) {

        int n = prices.length;

        // memo[day][hasStock][buyCountRemain]:
        // stores the maximum profit obtainable
        // starting from 'day',
        // given whether we currently own a stock
        // and how many BUY operations are still available.
        //
        // We decrement the transaction count during BUY,
        // because every transaction always starts with a buy.
        Integer[][][] memo = new Integer[n][2][k+1];

        // Initially:
        // Day = 0
        // No stock owned
        // All k buys are available.
        return recursion(prices, 0, 0, k, memo);
    }

    private int recursion(int[] prices, int day, int hasStock, int buyCountRemain, Integer[][][] memo) {

        // No trading days remain.
        //
        // No additional profit
        // can be earned.
        if(day==prices.length) return 0;

        // Return previously computed answer
        // because many different trading paths
        // may reach the same state.
        if(memo[day][hasStock][buyCountRemain]!=null)
            return memo[day][hasStock][buyCountRemain];

        int stockCurrentPrice = prices[day];

        int profit = 0;

        if(hasStock==1) {

            // Already holding one stock.
            //
            // Two possible choices:
            //
            // 1. Sell today.
            // 2. Continue holding.
            profit = Math.max(

                // Selling earns today's price.
                //
                // Selling does NOT consume
                // a transaction because
                // the transaction was already counted
                // when we bought the stock.
                stockCurrentPrice + recursion(prices, day+1, 0, buyCountRemain, memo),

                // Skip today's transaction
                // and continue holding.
                recursion(prices, day+1, 1, buyCountRemain, memo)
            );
        }
        else {

            // Not holding any stock.
            //
            // First option:
            // Skip today's opportunity.
            profit = recursion(prices, day+1, 0, buyCountRemain, memo);

            // Buying is possible
            // only if transactions remain.
            if(buyCountRemain>0) {

                profit = Math.max(

                    profit,

                    // Buying spends today's price
                    // and consumes one future transaction.
                    -stockCurrentPrice +
                    recursion(
                        prices,
                        day+1,
                        1,
                        buyCountRemain-1,
                        memo
                    )
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

        // dp[day][hasStock][buyCountRemain]:
        //
        // day:
        // first 'day' trading days processed.
        //
        // hasStock:
        // 0 -> not holding any stock.
        // 1 -> currently holding one stock.
        //
        // buyCountRemain:
        // number of BUY operations still available.
        int[][][] dp = new int[n+1][2][k+1];

        // Initialize every state
        // as impossible.
        Arrays.fill(dp[0][0], Integer.MIN_VALUE/2);
        Arrays.fill(dp[0][1], Integer.MIN_VALUE/2);

        // Before trading begins,
        // we have completed zero profit,
        // own no stock,
        // and still have all k buys available.
        dp[0][0][k] = 0;

        // Process trading days
        // from left to right.
        for(int day=1; day<=n; day++) {

            int stockCurrentPrice = prices[day-1];

            // Remaining buys decrease
            // only after buying,
            // therefore process
            // from larger to smaller values.
            for(int buyCountRemain=k-1; buyCountRemain>=0; buyCountRemain--) {

                // End today's trading
                // while holding a stock.
                //
                // Two possibilities:
                //
                // 1. Already held it yesterday.
                // 2. Buy today.
                dp[day][1][buyCountRemain] = Math.max(

                    // Buy today.
                    //
                    // Yesterday we must not
                    // have owned any stock,
                    // and one extra buy
                    // must have been available.
                    -stockCurrentPrice +
                    dp[day-1][0][buyCountRemain+1],

                    // Continue holding.
                    dp[day-1][1][buyCountRemain]
                );

                // End today's trading
                // without holding a stock.
                //
                // Two possibilities:
                //
                // 1. Sell today.
                // 2. Continue without stock.
                dp[day][0][buyCountRemain] = Math.max(

                    // Selling completes
                    // the previously started transaction.
                    stockCurrentPrice +
                    dp[day-1][1][buyCountRemain],

                    // Skip today's transaction.
                    dp[day-1][0][buyCountRemain]
                );
            }
        }

        // We must finish
        // without holding any stock.
        //
        // Remaining unused transactions
        // are perfectly acceptable.
        for(int buyCountRemain=0; buyCountRemain<=k; buyCountRemain++) {
            maxProfit = Math.max(
                maxProfit,
                dp[n][0][buyCountRemain]
            );
        }

        return maxProfit;
    }
}