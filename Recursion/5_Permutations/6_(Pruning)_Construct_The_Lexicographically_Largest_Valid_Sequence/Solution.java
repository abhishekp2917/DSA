class Solution {

    // Pattern: Backtracking / DFS (Permutation + Position Constraints)
    //
    // Core intuition:
    // We must construct a sequence of length (2*n - 1) such that:
    // - Number 1 appears exactly once
    // - Every number i > 1 appears exactly twice
    // - The two occurrences of i are exactly i distance apart
    //
    // Among all valid sequences, we must return the lexicographically largest one.

    public int[] constructDistancedSequence(int n) {

        // Result array, initially empty (filled with 0s)
        int[] sequence = new int[2 * n - 1];

        // used[i] indicates whether number i has already been placed
        recursion(n, 0, new boolean[n + 1], sequence);

        return sequence;
    }

    private boolean recursion(
        int n,
        int ptr,
        boolean[] used,
        int[] sequence
    ) {

        // Move ptr to the next empty position
        while (ptr < sequence.length && sequence[ptr] != 0) {
            ptr++;
        }

        // Base case:
        // If all positions are filled, a valid sequence is formed
        if (ptr == sequence.length) {
            return true;
        }

        // Try placing numbers from n down to 1 to ensure
        // lexicographically largest sequence
        for (int i = n; i >= 1; i--) {

            // Skip numbers already placed
            if (used[i]) continue;

            // Special case for number 1 (only appears once)
            if (i == 1) {

                used[i] = true;
                sequence[ptr] = i;

                if (recursion(n, ptr + 1, used, sequence)) return true;

                // Backtrack
                sequence[ptr] = 0;
                used[i] = false;
            }
            else {

                // For number i > 1, we must place it twice
                // at indices ptr and ptr + i
                if (ptr + i >= sequence.length || sequence[ptr + i] != 0) {
                    continue;
                }

                used[i] = true;
                sequence[ptr] = i;
                sequence[ptr + i] = i;

                if (recursion(n, ptr + 1, used, sequence)) return true;

                // Backtrack
                sequence[ptr] = 0;
                sequence[ptr + i] = 0;
                used[i] = false;
            }
        }

        // No valid placement found at this position
        return false;
    }
}
