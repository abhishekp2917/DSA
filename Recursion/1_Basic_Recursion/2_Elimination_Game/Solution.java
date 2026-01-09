class Solution {

    // Pattern: Recursion + Mathematical Observation
    //
    // Idea:
    // Simulate the elimination process using recursion instead of
    // explicitly maintaining the list.

    public int lastRemaining(int n) {
        // Start elimination from the left
        return recurrsion(n, true);
    }

    private int recurrsion(int n, boolean fromLeft) {

        // Base case: only one number left
        if (n == 1) return 1;

        // If eliminating from the left:
        // Every second number is removed, and the remaining numbers
        // are effectively doubled.
        if (fromLeft) {
            return recurrsion(n / 2, !fromLeft) * 2;
        }
        else {
            // If eliminating from the right:
            // Behavior depends on whether n is even or odd
            if (n % 2 == 0) {
                return recurrsion(n / 2, !fromLeft) * 2 - 1;
            }
            else {
                return recurrsion(n / 2, !fromLeft) * 2;
            }
        }
    }
}
