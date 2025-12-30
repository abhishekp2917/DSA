import java.util.HashMap;
import java.util.Map;

class Solution {
    Map<Integer, Boolean> memo = new HashMap<>();

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {

        // Sum of all numbers 1..max
        int totalSum = (maxChoosableInteger * (maxChoosableInteger + 1)) / 2;

        // If even picking all numbers cannot reach desiredTotal
        if (totalSum < desiredTotal) return false;

        // If desiredTotal is already <= 0
        if (desiredTotal <= 0) return true;

        // All numbers available except bit 0
        int availNums = ((1 << (maxChoosableInteger + 1)) - 1) & (~1);

        return canWin(availNums, desiredTotal);
    }

    private boolean canWin(int availNums, int desiredTotal) {

        // If already computed
        if (memo.containsKey(availNums)) {
            return memo.get(availNums);
        }

        // Try all available moves
        int temp = availNums;
        while (temp > 0) {
            int num = temp & (-temp);          // lowest set bit
            int value = Integer.numberOfTrailingZeros(num);

            // Immediate win
            if (value >= desiredTotal) {
                memo.put(availNums, true);
                return true;
            }

            // Opponent loses
            if (!canWin(availNums & (~num), desiredTotal - value)) {
                memo.put(availNums, true);
                return true;
            }

            temp &= ~num;
        }

        memo.put(availNums, false);
        return false;
    }
}