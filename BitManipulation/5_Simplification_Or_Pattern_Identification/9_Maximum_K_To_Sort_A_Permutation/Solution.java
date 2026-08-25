class Solution {

    // Pattern: Bit Manipulation → Bitwise AND Aggregation over Mismatched Positions
    //
    // Goal:
    // Determine the minimum value k such that sorting the permutation using
    // bitwise operations is possible under the given constraints.

    public int sortPermutation(int[] nums) {

        // Initialize k with all bits set (acts as an identity for AND)
        int k = (1 << 31) - 1;

        // Traverse the permutation
        for (int i = 0; i < nums.length; i++) {

            // Only consider positions where the value is not already correct
            if (nums[i] != i) {

                // Aggregate the AND of all misplaced values
                k &= nums[i];
            }
        }

        // If no elements were misplaced, return 0
        return (k == (1 << 31) - 1) ? 0 : k;
    }
}
