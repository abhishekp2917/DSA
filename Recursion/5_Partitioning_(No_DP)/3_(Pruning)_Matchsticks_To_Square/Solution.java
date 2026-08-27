import java.util.Arrays;

class Solution {

    // Pattern: Backtracking / DFS (Partition into Equal Buckets)
    //
    // Core intuition:
    // We want to check whether all matchsticks can be partitioned into
    // exactly 4 groups such that each group has the same total length.
    //
    // Each matchstick must be used exactly once, and each stick must be
    // assigned to one of the four sides.

    public boolean makesquare(int[] matchsticks) {

        long totalLen = 0;

        // Sorting helps place larger matchsticks first,
        // which leads to earlier pruning in recursion.
        Arrays.sort(matchsticks);

        // Compute total length of all matchsticks
        for (int len : matchsticks) totalLen += len;

        // If total length is not divisible by 4, forming a square is impossible
        if (totalLen % 4 != 0) return false;

        // Start assigning matchsticks from the largest one
        return recursion(
            matchsticks,
            matchsticks.length - 1,
            new long[4],
            totalLen / 4
        );
    }

    private boolean recursion(
        int[] matchsticks,
        int ptr,
        long[] sidelen,
        long expectedLen
    ) {

        // Base case:
        // If all matchsticks have been assigned,
        // check whether all four sides have equal length.
        if (ptr < 0) {
            return (
                sidelen[0] == sidelen[1] &&
                sidelen[1] == sidelen[2] &&
                sidelen[2] == sidelen[3]
            );
        }

        // Try assigning the current matchstick to each of the four sides
        for (int i = 0; i < 4; i++) {

            // Prune paths where side length would exceed the target
            if (sidelen[i] + matchsticks[ptr] > expectedLen) continue;

            // Assign matchstick to side i
            sidelen[i] += matchsticks[ptr];

            // Recurse to assign the next matchstick
            if (recursion(matchsticks, ptr - 1, sidelen, expectedLen)) {
                return true;
            }

            // Backtrack: remove matchstick from side i
            sidelen[i] -= matchsticks[ptr];
        }

        // If the matchstick cannot be placed on any side, return false
        return false;
    }
}
