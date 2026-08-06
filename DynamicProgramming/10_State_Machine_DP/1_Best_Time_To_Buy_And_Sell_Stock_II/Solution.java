class Solution1 {

    public int maxProfit(int[] prices) {

        int n = prices.length;

        // memo[day][hasStock]:
        // stores the maximum profit
        // obtainable starting from 'day'
        // when:
        //
        // hasStock = 0 -> currently not holding any stock.
        // hasStock = 1 -> currently holding one stock.
        Integer[][] memo = new Integer[n][2];

        // Initially we start on day 0
        // without owning any stock.
        return recursion(prices, 0, 0, memo);
    }

    private int recursion(int[] prices, int day, int hasStock, Integer[][] memo) {

        // No trading days remain.
        //
        // No additional profit
        // can be earned.
        if(day==prices.length) return 0;

        // Return previously computed answer
        // because this state may be reached
        // through multiple trading sequences.
        if(memo[day][hasStock]!=null) return memo[day][hasStock];

        int stockCurrentPrice = prices[day];

        int profit = 0;

        if(hasStock==1) {

            // Since we already own a stock,
            // we have only two choices:
            //
            // 1. Sell today.
            // 2. Continue holding it.
            profit = Math.max(

                // Selling earns today's price
                // and tomorrow we no longer own a stock.
                stockCurrentPrice + recursion(prices, day+1, 0, memo),

                // Skip today's transaction
                // and continue holding the stock.
                recursion(prices, day+1, 1, memo)
            );
        }
        else {

            // Since we do not own a stock,
            // we again have two choices:
            //
            // 1. Buy today.
            // 2. Skip today.
            profit = Math.max(

                // Buying spends today's price,
                // therefore profit decreases.
                //
                // Tomorrow we own one stock.
                -stockCurrentPrice + recursion(prices, day+1, 1, memo),

                // Ignore today's opportunity
                // and remain without any stock.
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

        // dp[day][state]:
        //
        // state = 0:
        // maximum profit after processing
        // first 'day' days
        // while NOT holding any stock.
        //
        // state = 1:
        // maximum profit after processing
        // first 'day' days
        // while holding one stock.
        int[][] dp = new int[n+1][2];

        // Before trading starts,
        // it is impossible
        // to already own a stock.
        //
        // Therefore initialize it
        // to negative infinity
        // so this invalid state
        // never gets selected.
        dp[0][1] = Integer.MIN_VALUE;

        // Process trading days
        // from left to right.
        for(int day=1; day<=n; day++) {

            int stockCurrentPrice = prices[day-1];

            // End today's trading
            // while holding a stock.
            //
            // Two possibilities:
            //
            // 1. Already held it yesterday.
            // 2. Buy today.
            dp[day][1] = Math.max(

                // Continue holding.
                dp[day-1][1],

                // Buy today.
                //
                // Buying decreases profit
                // by today's price.
                -stockCurrentPrice + dp[day-1][0]
            );

            // End today's trading
            // without holding a stock.
            //
            // Two possibilities:
            //
            // 1. Sell today.
            // 2. Continue without a stock.
            dp[day][0] = Math.max(

                // Sell today.
                //
                // Selling adds today's price
                // to the accumulated profit.
                stockCurrentPrice + dp[day-1][1],

                // Skip today's transaction.
                dp[day-1][0]
            );
        }

        // We want the maximum realised profit.
        //
        // Holding a stock at the end
        // means it was never sold,
        // so that profit is incomplete.
        return dp[n][0];
    }
}