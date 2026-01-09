class Solution {

    // Pattern: Backtracking / DFS (Combination + Optimization)
    //
    // Core intuition:
    // Bob is competing with Alice over 12 scoring sections (0 to 11).
    // For a score `i`, Bob can win those points only if he shoots
    // strictly more arrows than Alice at index `i`.
    //
    // Each scoring index can either be:
    // - skipped (Bob does not contest it), or
    // - taken (Bob spends aliceArrows[i] + 1 arrows to win i points)
    //
    // The goal is to maximize Bob's total score under a fixed arrow budget.

    public int[] maximumBobPoints(int numArrows, int[] aliceArrows) {

        int[] maxBobArrows = new int[12];

        // Start recursion from the highest score (11) downward
        recurrsion(
            numArrows,
            aliceArrows,
            11,
            new int[12],
            new int[1],
            maxBobArrows,
            new int[1]
        );

        return maxBobArrows;
    }

    private void recurrsion(
        int numArrows,
        int[] aliceArrows,
        int startScore,
        int[] bobArrows,
        int[] bobScore,
        int[] maxBobArrows,
        int[] maxBobScore
    ) {

        // At every recursion state, we may have a better score than before.
        // If so, record this configuration as the best one.
        if (bobScore[0] > maxBobScore[0]) {

            maxBobScore[0] = bobScore[0];

            // Copy the current arrow distribution
            for (int i = 0; i < bobArrows.length; i++) {
                maxBobArrows[i] = bobArrows[i];
            }

            // Any leftover arrows must be placed at index 0
            // since it gives no points but is required to use all arrows
            maxBobArrows[0] += numArrows;
        }

        // Try to win each score from startScore down to 1
        for (int score = startScore; score >= 1; score--) {

            // Bob can win this score only if he has enough arrows
            if (numArrows > aliceArrows[score]) {

                // Spend arrows to win this score
                bobScore[0] += score;
                bobArrows[score] = aliceArrows[score] + 1;

                // Recurse to the next lower score
                recurrsion(
                    numArrows - bobArrows[score],
                    aliceArrows,
                    score - 1,
                    bobArrows,
                    bobScore,
                    maxBobArrows,
                    maxBobScore
                );

                // Backtrack: undo the choice
                bobScore[0] -= score;
                bobArrows[score] = 0;
            }
        }
    }
}
