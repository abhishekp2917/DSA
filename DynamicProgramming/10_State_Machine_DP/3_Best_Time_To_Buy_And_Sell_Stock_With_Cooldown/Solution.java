class Solution1 {

    public int maxProfit(int[] prices) {

        int n = prices.length;

        // memo[day][hasStock]:
        // stores the maximum profit obtainable
        // starting from 'day'
        // given whether we currently own a stock.
        Integer[][] memo = new Integer[n][2];

        // Initially:
        // Day = 0
        // No stock is owned.
        return recursion(prices, 0, 0, memo);
    }

    private int recursion(int[] prices, int day, int hasStock, Integer[][] memo) {

        // No trading days remain.
        //
        // No additional profit
        // can be earned.
        if(day>=prices.length) return 0;

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
                // IMPORTANT:
                // After selling,
                // the next day becomes
                // a mandatory cooldown day.
                //
                // Therefore trading resumes
                // from day + 2.
                stockCurrentPrice + recursion(prices, day+2, 0, memo),

                // Skip today's transaction
                // and continue holding.
                recursion(prices, day+1, 1, memo)
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

                // Buying spends today's price.
                -stockCurrentPrice + recursion(prices, day+1, 1, memo),

                // Ignore today's opportunity.
                recursion(prices, day+1, 0, memo)
            );
        }

        memo[day][hasStock] = profit;

        return profit;
    }
}

class Solution {

    public int maxProfit(int[] prices) {

        int n = prices.length;

        // dp[day][state]:
        //
        // state = 0:
        // maximum profit after processing
        // first 'day' days
        // while not holding a stock.
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

        // After the first day,
        // the only possible holding state
        // is buying on day 0.
        dp[1][1] = -prices[0];

        // Process remaining days.
        for(int day=2; day<=n; day++) {

            int stockCurrentPrice = prices[day-1];

            // End today
            // while holding a stock.
            //
            // Two possibilities:
            //
            // 1. Already holding yesterday.
            // 2. Buy today.
            //
            // IMPORTANT:
            // Buying today requires
            // yesterday to be a cooldown day
            // if we sold yesterday.
            //
            // Therefore buying is allowed only
            // from dp[day-2][0].
            dp[day][1] = Math.max(

                // Continue holding.
                dp[day-1][1],

                // Buy today
                // after cooldown.
                -stockCurrentPrice + dp[day-2][0]
            );

            // End today
            // without holding a stock.
            //
            // Two possibilities:
            //
            // 1. Already without stock.
            // 2. Sell today.
            dp[day][0] = Math.max(

                // Skip today's transaction.
                dp[day-1][0],

                // Sell today.
                stockCurrentPrice + dp[day-1][1]
            );
        }

        // Final realised profit
        // must end
        // without holding any stock.
        return dp[n][0];
    }
}