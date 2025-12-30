class Solution {

    // Pattern: Bit Manipulation → Binary Long Division using Bit Shifts
    //
    // Goal:
    // Divide two integers without using multiplication, division, or modulo.

    public int divide(int dividend, int divisor) {

        // Handle overflow edge case
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        int ans = 0;

        // Result is negative if exactly one of dividend or divisor is negative
        boolean isNegative = (dividend < 0) ^ (divisor < 0);

        // Work with absolute values using long to avoid overflow
        long numerator = abs(dividend);
        long denominator = abs(divisor);

        // Try subtracting shifted divisor from numerator
        for (int bit = 31; bit >= 0; bit--) {

            // If denominator shifted by bit fits into numerator
            if (numerator >= (denominator << bit)) {

                // Subtract and accumulate quotient contribution
                numerator -= (denominator << bit);
                ans += (1 << bit);
            }
        }

        // Apply sign to the result
        return isNegative ? -ans : ans;
    }

    // Computes absolute value without branching
    private long abs(long n) {

        long mask = (n >> 31);
        return (n ^ mask) - mask;
    }
}
