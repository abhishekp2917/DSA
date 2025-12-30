class Solution {

    // Pattern: Bit Manipulation → Greedy Bit Construction under Constraint
    //
    // Goal:
    // Choose a number x (restricted to n bits) such that
    // (a ^ x) * (b ^ x) is maximized.

    public int maximumXorProduct(long a, long b, int n) {

        // Mask to ensure x is limited to n bits
        long bitMask = (1L << n) - 1;

        // Initial x: bits where both a and b have 0
        long x = (~(a | b)) & bitMask;

        // XOR of a and b tells where they differ
        long xor = a ^ b;

        // Iterate from the most significant bit to the least
        for (int bit = n - 1; bit >= 0; bit--) {

            // If a and b have the same bit here, skip
            if (((xor >> bit) & 1) == 0) continue;

            // Compute current transformed values
            long num1 = a ^ x;
            long num2 = b ^ x;

            // Try to balance the product by favoring the smaller value
            if (num1 > num2) {
                if (((a >> bit) & 1) == 1) {
                    x |= (1L << bit);
                }
            }
            else if (num2 > num1) {
                if (((b >> bit) & 1) == 1) {
                    x |= (1L << bit);
                }
            }
        }

        // Return product modulo 1e9 + 7
        return (int)((((a ^ x) % 1000000007) * ((b ^ x) % 1000000007)) % 1000000007);
    }
}
