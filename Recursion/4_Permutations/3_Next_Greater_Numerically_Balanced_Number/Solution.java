class Solution {

    // Pattern: Backtracking / DFS (Digit Construction + Constraint Checking)
    //
    // Core intuition:
    // A numerically balanced number has the property that
    // digit i appears exactly i times.
    //
    // Since n <= 10^6, we only need to consider digits 1 to 6.
    // We generate candidate numbers by constructing them digit by digit,
    // respecting the allowed counts of each digit.

    public int nextBeautifulNumber(int n) {

        // smallestNum[0] stores the minimum valid number greater than n
        int[] smallestNum = new int[] { Integer.MAX_VALUE };

        // count[i] represents how many times digit i is allowed to be used
        // Initially, digit i can be used at most i times
        recursion(n, new int[] { 0, 1, 2, 3, 4, 5, 6 }, 0, smallestNum);

        return smallestNum[0];
    }

    private void recursion(
        int n,
        int[] count,
        int num,
        int[] smallestNum
    ) {

        // Pruning:
        // If the current number already exceeds the best answer found,
        // there is no point in exploring further.
        if (num >= smallestNum[0]) return;

        // If num is greater than n, check whether it is numerically balanced
        if (num > n) {

            boolean isValid = true;

            // Verify that for each digit i, either:
            // - it is not used at all, or
            // - it is used exactly i times
            for (int i = 1; i <= 6; i++) {
                if (count[i] != 0 && count[i] != i) {
                    isValid = false;
                }
            }

            // If valid, update the smallest valid number
            if (isValid) {
                smallestNum[0] = Math.min(smallestNum[0], num);
                return;
            }
        }

        // Try appending each possible digit from 1 to 6
        for (int i = 1; i <= 6; i++) {

            // If this digit cannot be used anymore, skip
            if (count[i] == 0) continue;

            // Use digit i
            count[i]--;
            recursion(n, count, num * 10 + i, smallestNum);
            count[i]++;

            // Backtrack: restore the count of digit i
        }
    }
}
