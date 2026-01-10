import java.util.HashMap;
import java.util.Map;

class Solution {

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {

        // Total sum of all choosable numbers from 1 to maxChoosableInteger
        // If this sum is less than desiredTotal, it is impossible to reach
        // the target no matter how players play.
        int sum = maxChoosableInteger * (maxChoosableInteger + 1) / 2;
        if (sum < desiredTotal) return false;

        // If desiredTotal is already 0 or less, the first player wins immediately
        if (desiredTotal <= 0) return true;

        // availNums is a bitmask representing which numbers are still available.
        // Bit i = 1 means number i can still be chosen.
        // We ignore bit 0, so bits 1..maxChoosableInteger are used.
        int availNums = (1 << (maxChoosableInteger + 1)) - 2;

        // Memoization map:
        // key   -> current availability bitmask
        // value -> whether the current player can force a win from this state
        Map<Integer, Boolean> memo = new HashMap<>();

        // Start the recursive game simulation
        return canPlayerwin(availNums, desiredTotal, memo);
    }

    private boolean canPlayerwin(
        int availNums,
        int desiredTotal,
        Map<Integer, Boolean> memo
    ) {

        // If desiredTotal <= 0 at the start of a player's turn,
        // it means the previous player reached or exceeded the target,
        // so the current player loses.
        if (desiredTotal <= 0) return false;

        // If this state has already been computed, return cached result
        if (memo.containsKey(availNums)) return memo.get(availNums);

        // Try every available number as the next move
        for (int i = 1; i <= 20; i++) {

            // If number i is not available, skip it
            if (((availNums >> i) & 1) == 0) continue;

            // Choose number i:
            // - Remove i from available numbers
            // - Reduce desiredTotal by i
            //
            // If after this move the opponent cannot win,
            // then the current player has a winning move.
            if (!canPlayerwin(
                    availNums ^ (1 << i),
                    desiredTotal - i,
                    memo
                )) {

                // Found a move that forces opponent to lose
                memo.put(availNums, true);
                return true;
            }
        }

        // If all possible moves lead to opponent winning,
        // then the current player loses from this state.
        memo.put(availNums, false);
        return false;
    }
}
