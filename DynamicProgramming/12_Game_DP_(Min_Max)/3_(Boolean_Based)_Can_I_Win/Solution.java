class Solution1 {

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {

        // Total sum of all choosable numbers from 1 to maxChoosableInteger
        // If this sum is less than desiredTotal, it is impossible to reach
        // the target no matter how players play.
        int sum = maxChoosableInteger * (maxChoosableInteger + 1) / 2;
        if (sum < desiredTotal) return false;

        // If desiredTotal is already less or equal to maxChoosableInteger, the first player wins immediately
        if (desiredTotal <= maxChoosableInteger) return true;

        // availNumsMask  is a bitmask representing which numbers are still available.
        // Bit i = 1 means number i can still be chosen.
        // We ignore bit 0, so bits 1..maxChoosableInteger are used.
        int availNumsMask = (1 << (maxChoosableInteger + 1)) - 2;

        // currTotal is uniquely determined by availNumsMask,
        // so it does not need to be part of the memoization key.
        // It is passed only to avoid recomputing the sum of already chosen numbers.
        Integer[] memo = new Integer[availNumsMask+1];

        // Start the recursive game simulation
        return canPlayerwin(maxChoosableInteger, desiredTotal, 0, availNumsMask, memo)==1;
    }

    private int canPlayerwin(
        int maxChoosableInteger,
        int desiredTotal,
        int currTotal,
        int availNumsMask,
        Integer[] memo
    ) {
        // If desiredTotal <= currTotal at the start of a player's turn,
        // it means the previous player reached or exceeded the target,
        // so the current player loses.
        if (desiredTotal <= currTotal) return 0;

        // If this state has already been computed, return cached result
        if (memo[availNumsMask]!=null) return memo[availNumsMask];

        // Try every available number as the next move
        for (int num = 1; num <= maxChoosableInteger; num++) {

            boolean isNumAvailable = ((availNumsMask>>num)&1)==1;

            // If number num is not available, skip it
            if (!isNumAvailable) continue;

            // Choose number num:
            // - Remove num from available numbers
            //
            // If after this move the opponent cannot win,
            // then the current player has a winning move.
            int newAvailNumMask = availNumsMask ^ (1<<num);
            int newTotal = currTotal + num;
            if (canPlayerwin(maxChoosableInteger, desiredTotal, newTotal, newAvailNumMask, memo)==0) {

                // Found a move that forces opponent to lose
                memo[availNumsMask] = 1;
                return 1;
            }
        }

        // If all possible moves lead to opponent winning,
        // then the current player loses from this state.
        memo[availNumsMask] = 0;
        return 0;
    }
}

class Solution2 {

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {

        // If even taking every number
        // cannot reach the target,
        // nobody can ever win.
        int sum = maxChoosableInteger * (maxChoosableInteger + 1) / 2;
        if (sum < desiredTotal) return false;

        // If the target itself
        // is already available,
        // the first player wins immediately.
        if (desiredTotal <= maxChoosableInteger) return true;

        // Bit i = 1 means
        // number i is still available.
        //
        // Initially every number
        // is available.
        int availNumsMask = (1 << (maxChoosableInteger + 1)) - 1;

        // dp[mask]:
        // true if the current player
        // can force a win
        // starting from this mask.
        //
        // The current accumulated total
        // is uniquely determined by the mask,
        // so it does not need to be stored
        // as part of the DP state.
        boolean[] dp = new boolean[availNumsMask+1];

        // Smaller masks have fewer available numbers.
        //
        // Their answers are already known
        // when larger masks are processed,
        // because every move removes exactly one bit.
        for(int mask=0; mask<=availNumsMask; mask++) {

            // Recover the current accumulated score
            // from the chosen numbers.
            int currTotal = getTotal(mask, maxChoosableInteger);

            // If the target has already been reached,
            // this state is never encountered
            // because the previous player
            // would have already won.
            if (desiredTotal<=currTotal) continue;

            // Try choosing every available number.
            for (int num = 1; num <= maxChoosableInteger; num++) {

                boolean isNumAvailable = ((mask>>num)&1)==1;

                if (!isNumAvailable) continue;

                // Remove the chosen number
                // from the available set.
                int newMask = mask ^ (1<<num);

                int newTotal = currTotal + num;

                // Current player wins if:
                //
                // 1. This move reaches the target.
                //
                // OR
                //
                // 2. Opponent loses
                //    from the resulting state.
                if (newTotal>=desiredTotal || !dp[newMask]) {
                    dp[mask] = true;
                    break;
                }
            }
        }

        // Initially every number
        // is available.
        return dp[availNumsMask];
    }

    private int getTotal(int availNumsMask, int maxChoosableInteger) {

        int currTotal = 0;

        // Every unavailable bit
        // corresponds to a number
        // that has already been chosen.
        //
        // Sum those numbers
        // to recover
        // the current accumulated score.
        for (int num = 1; num <= maxChoosableInteger; num++) {

            boolean isNumAvailable = ((availNumsMask>>num)&1)==1;

            if(!isNumAvailable) currTotal += num;
        }

        return currTotal;
    }
}