class Solution1 {

    public boolean divisorGame(int n) {

        Boolean[] memo = new Boolean[n+1];
        // Start the game with number n.
        // Memoization is used to avoid recomputing results for the same n.
        return canPlayerWin(n, memo);
    }

    private boolean canPlayerWin(int num, Boolean[] memo) {

        // Base case:
        // If the current number is 1, the player has no valid move.
        // Hence, the current player loses.
        if (num == 1) return false;

        // If the result for this n is already computed, reuse it.
        if (memo[num]!=null) return memo[num];

        // Try all possible valid moves:
        // Choose a divisor x of n such that 1 <= x < n
        for (int x = 1; x < num; x++) {

            // Skip if i is not a divisor of n
            if (num % x != 0) continue;

            // Make the move:
            // Subtract divisor i from n, and let the opponent play next.
            //
            // If the opponent loses from state (n - i),
            // then the current player can force a win.
            if (!canPlayerWin(num - x, memo)) {

                // Found a winning move
                memo[num] = true;
                return true;
            }
        }

        // If all possible moves lead to opponent winning,
        // then the current player loses.
        memo[num] = false;
        return false;
    }
}

class Solution2 {

    public boolean divisorGame(int n) {

        // dp[num]:
        // true if the current player
        // can force a win
        // when the remaining number is 'num'.
        boolean[] dp = new boolean[n+1];

        // Compute answers
        // from smaller numbers
        // towards larger numbers.
        //
        // Every move reduces the number,
        // so future states
        // are already known.
        for(int num=2; num<=n; num++) {

            // Try every possible divisor.
            for (int x=1; x<num; x++) {

                // A valid move requires
                // x to divide num.
                if (num%x!=0) continue;

                // Choosing x
                // changes the game state
                // from num
                // to num-x.
                //
                // If the opponent loses
                // from that state,
                // current player wins.
                if (!dp[num-x]) {
                    dp[num] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}