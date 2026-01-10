import java.util.HashMap;
import java.util.Map;

class Solution {

    public boolean divisorGame(int n) {

        // Start the game with number n.
        // Memoization is used to avoid recomputing results for the same n.
        return canPlayerWin(n, new HashMap<>());
    }

    private boolean canPlayerWin(int n, Map<Integer, Boolean> memo) {

        // Base case:
        // If the current number is 1, the player has no valid move.
        // Hence, the current player loses.
        if (n == 1) return false;

        // If the result for this n is already computed, reuse it.
        if (memo.containsKey(n)) return memo.get(n);

        // Try all possible valid moves:
        // Choose a divisor i of n such that 1 <= i < n
        for (int i = 1; i * i < n; i++) {

            // Skip if i is not a divisor of n
            if (n % i != 0) continue;

            // Make the move:
            // Subtract divisor i from n, and let the opponent play next.
            //
            // If the opponent loses from state (n - i),
            // then the current player can force a win.
            if (!canPlayerWin(n - i, memo)) {

                // Found a winning move
                memo.put(n, true);
                return true;
            }
        }

        // If all possible moves lead to opponent winning,
        // then the current player loses.
        memo.put(n, false);
        return false;
    }
}
