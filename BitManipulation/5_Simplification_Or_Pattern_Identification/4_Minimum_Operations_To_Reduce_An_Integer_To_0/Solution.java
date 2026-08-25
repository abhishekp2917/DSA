class Solution {

    // Pattern: Bit Manipulation → Greedy Bit Reduction
    //
    // Goal:
    // Reduce the number n to 0 using the minimum number of operations.

    public int minOperations(int n) {

        int opCount = 0;

        // Continue until n becomes 0
        while (n > 0) {

            // If the last two bits are both 1 (binary ...11),
            // incrementing n helps reduce more bits efficiently
            if ((n & 3) == 3) {
                n++;
                opCount++;
            }

            // If n is odd, one operation is required to handle the LSB
            if ((n & 1) == 1) {
                opCount++;
            }

            // Shift right to divide n by 2
            n >>= 1;
        }

        return opCount;
    }
}
