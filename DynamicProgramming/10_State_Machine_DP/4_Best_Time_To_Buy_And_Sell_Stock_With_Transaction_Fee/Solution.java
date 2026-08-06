class Solution1 {

    public int maxProfit(int[] prices, int fee) {

        int n = prices.length;

        // memo[day][hasStock]:
        // stores the maximum profit obtainable
        // starting from 'day'
        // given whether we currently own a stock.
        Integer[][] memo = new Integer[n][2];

        // Initially:
        // Day = 0
        // No stock is owned.
        return recursion(prices, fee, 0, 0, memo);
    }

    private int recursion(int[] prices, int fee, int day, int hasStock, Integer[][] memo) {

        // No trading days remain.
        //
        // No additional profit
        // can be earned.
        if(day==prices.length) return 0;

        // Return previously computed answer
        // because many trading sequences
        // may reach the same state.
        if(memo[day][hasStock]!=null) return memo[day][hasStock];

        int stockCurrentPrice = prices[day];

        int profit = 0;

        if(hasStock==1) {

            // Already holding one stock.
            //
            // Two choices:
            //
            // 1. Sell today.
            // 2. Continue holding.
            profit = Math.max(

                // Selling earns today's price.
                //
                // Transaction fee has already been paid
                // while buying,
                // so nothing extra is deducted here.
                stockCurrentPrice + recursion(prices, fee, day+1, 0, memo),

                // Skip today's transaction
                // and continue holding.
                recursion(prices, fee, day+1, 1, memo)
            );
        }
        else {

            // Not holding any stock.
            //
            // Two choices:
            //
            // 1. Buy today.
            // 2. Skip today.
            profit = Math.max(

                // Buying spends today's price
                // and also pays the transaction fee.
                //
                // This solution charges the fee
                // during BUY instead of SELL.
                -stockCurrentPrice - fee + recursion(prices, fee, day+1, 1, memo),

                // Ignore today's opportunity.
                recursion(prices, fee, day+1, 0, memo)
            );
        }

        memo[day][hasStock] = profit;

        return profit;
    }
}

class Solution2 {

    public int maxProfit(int[] prices, int fee) {

        int n = prices.length;

        // dp[day][state]:
        //
        // state = 0:
        // maximum profit after processing
        // first 'day' days
        // while not holding any stock.
        //
        // state = 1:
        // maximum profit after processing
        // first 'day' days
        // while holding one stock.
        int[][] dp = new int[n+1][2];

        // Before trading starts,
        // holding a stock
        // is impossible.
        dp[0][1] = Integer.MIN_VALUE/2;

        // Process trading days
        // from left to right.
        for(int day=1; day<=n; day++) {

            int stockCurrentPrice = prices[day-1];

            // End today's trading
            // while holding a stock.
            //
            // Two possibilities:
            //
            // 1. Already holding yesterday.
            // 2. Buy today.
            dp[day][1] = Math.max(

                // Buy today.
                //
                // Today's stock price
                // and transaction fee
                // are both paid now.
                -stockCurrentPrice - fee + dp[day-1][0],

                // Continue holding.
                dp[day-1][1]
            );

            // End today's trading
            // without holding any stock.
            //
            // Two possibilities:
            //
            // 1. Sell today.
            // 2. Continue without stock.
            dp[day][0] = Math.max(

                // Selling receives today's price.
                //
                // Fee is not deducted here
                // because it was already paid
                // during buying.
                stockCurrentPrice + dp[day-1][1],

                // Skip today's transaction.
                dp[day-1][0]
            );
        }

        // Final realised profit
        // must end
        // without holding any stock.
        return dp[n][0];
    }
}