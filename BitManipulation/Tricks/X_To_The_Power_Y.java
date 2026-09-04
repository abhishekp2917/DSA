package BitManipulation.Tricks;

class X_To_The_Power_Y {

    public static void main(String[] args) {

        final int MOD = 1_000_000_007;

        long x = 4;
        long y = 23;

        // Goal:
        // Calculate (x^y) % MOD efficiently.
        //
        // Instead of multiplying x by itself y times,
        // we use Binary Exponentiation (Fast Power).
        //
        // Core idea:
        //
        // Every exponent can be represented as a sum of powers of 2.
        //
        // Example:
        //
        // y = 23
        //   = 16 + 4 + 2 + 1
        //   = 10111 (binary)
        //
        // Therefore:
        //
        // x^23
        // = x^(16 + 4 + 2 + 1)
        // = x^16 * x^4 * x^2 * x^1
        //
        // We can generate these powers by repeatedly squaring x:
        //
        // x^1
        // x^2
        // x^4
        // x^8
        // x^16
        // ...
        //
        // At every iteration:
        //
        // 1. Check the last bit of y.
        //
        //    (y & 1) == 1
        //
        //    means y is odd, so the current power of x
        //    is required in the final answer.
        //
        // 2. Square x to move to the next power of 2.
        //
        //    x = x * x
        //
        //    Example:
        //    x^1 -> x^2 -> x^4 -> x^8 -> x^16
        //
        // 3. Right shift y by one bit.
        //
        //    y >>= 1
        //
        //    This is equivalent to y / 2.
        //
        //    It allows us to process the exponent one bit at a time.
        //
        // Example for y = 23:
        //
        // Binary:
        //
        // 23 = 10111
        //
        // Bits from right to left:
        //
        //       1  1  1  0  1
        //       ↓  ↓  ↓  ↓  ↓
        // Powers:
        //       1  2  4  8  16
        //
        // Bits containing 1 tell us which powers are needed:
        //
        // x^1, x^2, x^4, x^16
        //
        // Therefore:
        //
        // x^23 = x^1 * x^2 * x^4 * x^16
        //
        // Time Complexity:
        //
        // O(log y)
        //
        // because y is divided by 2 in every iteration.
        //
        // Space Complexity:
        //
        // O(1)

        long result = 1;

        while (y > 0) {

            // Check whether the current least-significant bit
            // of y is 1.
            //
            // If it is 1, the current power of x contributes
            // to the final answer.
            if ((y & 1) == 1) {
                result = (result * x) % MOD;
            }

            // Square x to obtain the next power of 2.
            //
            // x^1 -> x^2 -> x^4 -> x^8 -> x^16 -> ...
            x = (x * x) % MOD;

            // Remove the least-significant bit of y.
            //
            // Equivalent to y = y / 2 for positive integers.
            y >>= 1;
        }

        System.out.println("Pow(x, y) = " + result);
    }
}

