class Solution {

    // Pattern: Bit Manipulation → XOR Invariant / Parity Insight
    //
    // Key Insight:
    // - XOR of a subsequence becomes 0 only when all elements cancel out.
    // - If the XOR of the entire array is non-zero, we can take all elements.
    // - If the XOR of the entire array is zero, we must remove at least one
    //   element to make the XOR non-zero.
    //
    // Observation:
    // - Removing any single element breaks the total XOR cancellation.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    public int longestSubsequence(int[] nums) {

        int totalXor = 0;

        // Compute XOR of the entire array
        for (int num : nums) {
            totalXor ^= num;
        }

        // If total XOR is already non-zero,
        // the entire array is a valid subsequence
        if (totalXor != 0) {
            return nums.length;
        }

        // Otherwise, remove exactly one element
        // to make the XOR non-zero
        return nums.length - 1;
    }
}
