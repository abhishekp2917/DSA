class Solution {

    // Pattern: Bit Manipulation → XOR + Brian Kernighan’s Algorithm
    //
    // Goal:
    // Count the number of bit positions where x and y differ.

    public int hammingDistance(int x, int y) {

        // XOR highlights differing bit positions
        int xor = x ^ y;

        int diffCount = 0;

        // Each iteration removes the lowest set bit
        while (xor != 0) {
            diffCount++;
            xor = xor & (xor - 1);
        }

        return diffCount;
    }
}
